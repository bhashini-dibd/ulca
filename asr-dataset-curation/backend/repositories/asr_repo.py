from pytube import YouTube
import os
from models.response import CustomResponse, post_error
from config import shared_storage_path
import subprocess
from mutagen.wave import WAVE
import wave
import webrtcvad
import collections
from pydub import AudioSegment
import shutil
import base64
import requests
import json
from time import sleep
import uuid
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

from mongo_util import MongoUtil

mongo_repo = MongoUtil()

TRIGGER_THRESHOLD_1 = 0.9  # % of voiced frames
TRIGGER_THRESHOLD_2 = 0.9  # % of unvoiced frames
MIN_DUR = 3                # minimum duration of a valid audio event in seconds
MAX_DUR = 20               # maximum duration of an event
"""
filepath = r"/home/kafka/asr_dataset_creation_test/chunk_dir/sample_youtube_audio.wav"

output_file = r"/home/kafka/asr_dataset_creation_test/chunk_dir/final_youtube_audio.wav"
input_file = r"/home/kafka/asr_dataset_creation_test/chunk_dir/temp_youtube_audio.wav"

url = "https://www.youtube.com/watch?v=7IZ-jATBq9A"
folder_name= "/home/kafka/asr_dataset_creation_test/chunk_dir/"

#create folder
if os.path.exists(folder_name):
    shutil.rmtree(folder_name)
os.makedirs(folder_name)
"""

class ASRAudioChunk:

    def create_audio_chunks(self, youtube_url):
        """
        Creating Audio Chunks
        """
        # filepath = r"/home/kafka/asr_creation_final/chunk_dir/sample_youtube_audio.wav"

        # output_file = r"/home/kafka/asr_creation_final/chunk_dir/final_youtube_audio.wav"
        # input_file = r"/home/kafka/asr_creation_final/chunk_dir/temp_youtube_audio.wav"
        url = youtube_url
        folder_name= shared_storage_path + str(uuid.uuid4()) + "/"

        filepath = f'{folder_name}sample-youtube-audio.wav'
        input_file = f'{folder_name}temp_youtube_audio.wav'
        input_file_enhanced = f'{folder_name}temp_youtube_audio_enhanced.wav'
        output_file = f'{folder_name}final_youtube_audio.wav'

        # folder_name= "/home/kafka/asr_creation_final/chunk_dir/"

        #create folder
        if os.path.exists(folder_name):
            shutil.rmtree(folder_name)
            os.makedirs(folder_name)
        else:
            os.makedirs(folder_name)



        yt = YouTube(url)
        yt.streams.filter(type = "audio").first().download()
        shutil.move(yt.streams.filter(type = "audio").first().default_filename, filepath)

        subprocess.call(["ffmpeg -loglevel error -y -i {} -ar {} -ac {} -bits_per_raw_sample {} -vn {}".format(filepath, 16000, 1, 16, input_file)], shell=True)
        os.remove(filepath)
        subprocess.call(["python3 -m denoiser.enhance --dns48 --noisy_dir={} --out_dir={} --num_workers=1".format(folder_name, folder_name)], shell=True)
        # input_file = r"/home/kafka/asr_dataset_creation_test/chunk_dir/temp_youtube_audio_enhanced.wav"
        subprocess.call(["ffmpeg -i {} -ar {} -ac {} -bits_per_raw_sample {} -vn {}".format(input_file_enhanced, 16000, 1, 16, output_file)], shell=True)


        wav_audio = WAVE(output_file)
        audio_length = wav_audio.info.length

        with wave.open(output_file, 'rb') as wf:
            num_channels = wf.getnchannels()
            assert num_channels == 1
            sample_width = wf.getsampwidth()
            assert sample_width == 2
            sample_rate = wf.getframerate()
            assert sample_rate in (8000, 16000, 32000, 48000)
            pcm_data = wf.readframes(wf.getnframes())

        frames = list(frame_generator(30, pcm_data, sample_rate))
        #print(num_channels, sample_width, sample_rate, len(frames))

        start_time = []
        end_time = []
        vad = webrtcvad.Vad(3)
        segments = vad_collector(sample_rate, 30, 300, vad, frames, start_time, end_time)
        chunks = 0
        for i, segment in enumerate(segments):
            chunks = chunks + 1
        if chunks != len(start_time):
            print("Error: Segments not broken properly")
            exit
        #print(len(list(segments)))
        audio_list = []
        counter = 0
        for i in range(len(start_time)):
            chunk_duration = end_time[i] - start_time[i]

            if chunk_duration >= MIN_DUR and chunk_duration <= MAX_DUR:
                temp_file_path = f'{folder_name}audio_chunk_{counter}.wav'
                newAudio = AudioSegment.from_wav(output_file)
                newAudio = newAudio[start_time[i] * 1000:end_time[i] * 1000]
                newAudio.export(temp_file_path, format="wav")
                #newAudio.export(folder_name + '/audio_chunk_{}.wav'.format(counter), format="wav")
                encoded_data = base64.b64encode(open(temp_file_path, "rb").read())
                inference = make_vakyansh_model_inference_call(encoded_data)
                audio_list.append({"audioContent":str(encoded_data), "inference":inference})
                counter = counter+1

        if os.path.exists(folder_name):
            shutil.rmtree(folder_name)

        mongo_repo.insert(audio_list)
        for audio_dict in audio_list:
            del audio_dict['_id']
        return audio_list

def make_vakyansh_model_inference_call(encoded_data):

    #encoded_data = base64.b64encode(open(file_path, "rb").read())
    #print(encoded_data)

    headers = {"Content-Type": "application/json"}
    body = {"config": {"language": {"sourceLanguage": "en"},"transcriptionFormat": {"value":"transcript"},"audioFormat": "wav",
            "punctuation": True,"enableInverseTextNormalization": False},"audio": [{"audioContent": str(encoded_data.decode("utf-8"))}]}

    request_url = "https://meity-dev-asr.ulcacontrib.org/asr/v1/recognize/en"
    response = requests.post(url=request_url, headers = headers, json = body,verify=False)
    content = response.content
    #print(content)
    response_data = json.loads(content)
    inferred_data = response_data["output"][0]["source"]
    #print(inferred_data, type(inferred_data))
    return inferred_data

class Frame(object):
    """Represents a "frame" of audio data."""
    def __init__(self, bytes, timestamp, duration):
        self.bytes = bytes
        self.timestamp = timestamp
        self.duration = duration

def frame_generator(frame_duration_ms, audio, sample_rate):
    """Generates audio frames from PCM audio data.
    Takes the desired frame duration in milliseconds, the PCM data, and
    the sample rate.
    Yields Frames of the requested duration.
    """
    n = int(sample_rate * (frame_duration_ms / 1000.0) * 2)
    offset = 0
    timestamp = 0.0
    duration = (float(n) / sample_rate) / 2.0
    while offset + n < len(audio):
        yield Frame(audio[offset:offset + n], timestamp, duration)
        timestamp += duration
        offset += n

def vad_collector(sample_rate, frame_duration_ms,
                  padding_duration_ms, vad, frames, start_time, end_time):
    """Filters out non-voiced audio frames.
    Given a webrtcvad.Vad and a source of audio frames, yields only
    the voiced audio.
    Uses a padded, sliding window algorithm over the audio frames.
    When more than 90% of the frames in the window are voiced (as
    reported by the VAD), the collector triggers and begins yielding
    audio frames. Then the collector waits until 90% of the frames in
    the window are unvoiced to detrigger.
    The window is padded at the front and back to provide a small
    amount of silence or the beginnings/endings of speech around the
    voiced frames.
    Arguments:
    sample_rate - The audio sample rate, in Hz.
    frame_duration_ms - The frame duration in milliseconds.
    padding_duration_ms - The amount to pad the window, in milliseconds.
    vad - An instance of webrtcvad.Vad.
    frames - a source of audio frames (sequence or generator).
    Returns: A generator that yields PCM audio data.
    """
    num_padding_frames = int(padding_duration_ms / frame_duration_ms)
    # We use a deque for our sliding window/ring buffer.
    ring_buffer = collections.deque(maxlen=num_padding_frames)
    # We have two states: TRIGGERED and NOTTRIGGERED. We start in the
    # NOTTRIGGERED state.
    triggered = False

    voiced_frames = []
    for frame in frames:
        is_speech = vad.is_speech(frame.bytes, sample_rate)

        #sys.stdout.write('1' if is_speech else '0')
        if not triggered:
            ring_buffer.append((frame, is_speech))
            num_voiced = len([f for f, speech in ring_buffer if speech])
            # If we're NOTTRIGGERED and more than 90% of the frames in
            # the ring buffer are voiced frames, then enter the
            # TRIGGERED state.
            if num_voiced > TRIGGER_THRESHOLD_1 * ring_buffer.maxlen:
                triggered = True
                start_time.append(ring_buffer[0][0].timestamp)
                #sys.stdout.write('+(%s)' % (ring_buffer[0][0].timestamp,))
                # We want to yield all the audio we see from now until
                # we are NOTTRIGGERED, but we have to start with the
                # audio that's already in the ring buffer.
                for f, s in ring_buffer:
                    voiced_frames.append(f)
                ring_buffer.clear()
        else:
            # We're in the TRIGGERED state, so collect the audio data
            # and add it to the ring buffer.
            voiced_frames.append(frame)
            ring_buffer.append((frame, is_speech))
            num_unvoiced = len([f for f, speech in ring_buffer if not speech])
            # If more than 90% of the frames in the ring buffer are
            # unvoiced, then enter NOTTRIGGERED and yield whatever
            # audio we've collected.
            if num_unvoiced > TRIGGER_THRESHOLD_2 * ring_buffer.maxlen:
                #sys.stdout.write('-(%s)' % (frame.timestamp + frame.duration))
                end_time.append(frame.timestamp + frame.duration)
                triggered = False
                yield b''.join([f.bytes for f in voiced_frames])
                ring_buffer.clear()
                voiced_frames = []
    if triggered:
        end_time.append(frame.timestamp + frame.duration)
        #sys.stdout.write('-(%s)' % (frame.timestamp + frame.duration))
        #sys.stdout.write('\n')
    # If we have any leftover voiced audio when we run out of input, yield it
    if voiced_frames:
        yield b''.join([f.bytes for f in voiced_frames])






let socket = null;
let defaultSampleRate = 48000,
  speechEvents,
  input,
  processor;
const hark = require("hark");
const io = require("socket.io-client");
/** Note: */
// auto mic close needs to be added.
// check for socket null in all used places.
// add default callbacks to avoid errors.
// Error handling

// state parameters
let audioData = [];
let recordingLength = 0;
let userId;
let isStreaming = false;
let isStreamingOver = false;
let isSilenceTransmitted = true;
let localBuffer = null;
let language = "en";
let bufferSize = 16384;
let isSpeaking = false;

function setStateOnMicStart() {
  isStreaming = true;
  audioData = [];
  recordingLength = 0;
}
function setStateOnMicStop() {
  isStreaming = false;
}

async function getAudioMediaStream() {
  let constraints = { audio: true, video: false };
  let stream = await navigator.mediaDevices.getUserMedia(constraints);
  return stream;
}

function flattenArray(channelBuffer, recordingLength) {
  let result = new Float32Array(recordingLength);
  let offset = 0;
  for (let i = 0; i < channelBuffer.length; i++) {
    let buffer = channelBuffer[i];
    result.set(buffer, offset);
    offset += buffer.length;
  }
  return result;
}

function writeUTFBytes(view, offset, string) {
  for (var i = 0; i < string.length; i++) {
    view.setUint8(offset + i, string.charCodeAt(i));
  }
}

function generateWavBlob(finalBuffer) {
  let buffer = new ArrayBuffer(44 + finalBuffer.length * 2);
  let view = new DataView(buffer);

  // RIFF chunk descriptor
  writeUTFBytes(view, 0, "RIFF");
  view.setUint32(4, 44 + finalBuffer.length * 2, true);
  writeUTFBytes(view, 8, "WAVE");
  // FMT sub-chunk
  writeUTFBytes(view, 12, "fmt ");
  view.setUint32(16, 16, true); // chunkSize
  view.setUint16(20, 1, true); // wFormatTag
  view.setUint16(22, 1, true); // wChannels:mono(1 channel) / stereo (2 channels)
  view.setUint32(24, defaultSampleRate, true); // dwSamplesPerSec
  view.setUint32(28, defaultSampleRate * 2, true); // dwAvgBytesPerSec
  view.setUint16(32, 4, true); // wBlockAlign
  view.setUint16(34, 16, true); // wBitsPerSample
  // data sub-chunk
  writeUTFBytes(view, 36, "data");
  view.setUint32(40, finalBuffer.length * 2, true);

  // write the PCM samples
  let index = 44;
  let volume = 1;
  for (var i = 0; i < finalBuffer.length; i++) {
    view.setInt16(index, finalBuffer[i] * (0x7fff * volume), true);
    index += 2;
  }

  // our final blob
  let blob = new Blob([view], { type: "audio/wav" });
  return blob;
}

function setSilenceDetector(audioStream, context) {
  let options = { audioContext: context };
  speechEvents = hark(audioStream, options);

  speechEvents.on("speaking", function () {
    isSpeaking = true;
    // console.log('speaking');
  });

  speechEvents.on("stopped_speaking", function () {
    // console.log('stopped_speaking');
    isSpeaking = false;
  });
}

function downSampleBuffer(buffer, sampleRate, outSampleRate) {
  if (outSampleRate == sampleRate) {
    return buffer;
  }
  if (outSampleRate > sampleRate) {
    throw "down-sampling rate show be smaller than original sample rate";
  }
  let sampleRateRatio = sampleRate / outSampleRate;
  let newLength = Math.round(buffer.length / sampleRateRatio);
  let result = new Int16Array(newLength);
  let offsetResult = 0;
  let offsetBuffer = 0;
  while (offsetResult < result.length) {
    let nextOffsetBuffer = Math.round((offsetResult + 1) * sampleRateRatio);
    let accum = 0,
      count = 0;
    for (let i = offsetBuffer; i < nextOffsetBuffer && i < buffer.length; i++) {
      accum += buffer[i];
      count++;
    }

    result[offsetResult] = Math.min(1, accum / count) * 0x7fff;
    offsetResult++;
    offsetBuffer = nextOffsetBuffer;
  }
  return result.buffer;
}

function appendBuffer(buffer1, buffer2) {
  const buffer = new ArrayBuffer(buffer1.byteLength + buffer2.byteLength);
  let tmp = new Uint8Array(buffer);
  tmp.set(new Uint8Array(buffer1), 0);
  tmp.set(new Uint8Array(buffer2), buffer1.byteLength);
  return buffer;
}

function streamAudioProcess(e) {
  audioData.push(new Float32Array(e.inputBuffer.getChannelData(0)));
  recordingLength += bufferSize;
  if (isStreaming === true) {
    isStreamingOver = false;
    let data_44100 = e.inputBuffer.getChannelData(0);
    let data_16000 = downSampleBuffer(data_44100, defaultSampleRate, 16000);
    if (isSpeaking) {
      isSilenceTransmitted = false;

      if (localBuffer !== undefined && localBuffer !== null) {
        data_16000 = appendBuffer(localBuffer, data_16000);
      }
      socket.emit("mic_data", data_16000, language, true, false);
      localBuffer = null;
    } else {
      if (!isSilenceTransmitted) {
        isSilenceTransmitted = true;
        socket.emit("mic_data", data_16000, language, false, false);
        // console.log("emitted last");
      } else {
        localBuffer = data_16000;
      }
    }
  } else {
    if (!isStreamingOver) {
      var data_44100 = e.inputBuffer.getChannelData(0);
      var data_16000 = downSampleBuffer(data_44100, defaultSampleRate, 16000);
      // let data_16000 = data_44100;
      isStreamingOver = true;
      socket.emit("mic_data", data_16000, language, false, true);
      console.log("emitted last");
    }
  }
}

async function startStreaming(responseCallback = function () {}) {
  setStateOnMicStart();

  let stream = await getAudioMediaStream();

  // connect socket here if needed

  let audioContextClass = window.AudioContext || window.webkitAudioContext;
  let context = new audioContextClass({
    latencyHint: "interactive",
  });
  defaultSampleRate = context.sampleRate;

  setSilenceDetector(stream.clone(), context);

  input = context.createMediaStreamSource(stream);
  processor = context.createScriptProcessor(bufferSize, 1, 1);

  input.connect(processor);
  processor.connect(context.destination);

  processor.onaudioprocess = streamAudioProcess;

  // clear states
  // access media library, proceed to next if access enabled / throw error
  // if needed, connect to socket
  // set silence detector
  // stream processor(responseCallback)

  socket.on("response", function (data, language) {
    if (language === "en-IN") data = data.toLowerCase();
    responseCallback(data);
  });
}

function stopStreaming(callback = function () {}) {
  // revoke access to media library
  // if needed, disable socket
  // disable silence detector
  // disable stream processor
  // clear states

  setStateOnMicStop();
  socket.emit("mic_data", null, language, false, true);

  if (speechEvents && speechEvents !== null) speechEvents.stop();
  if (input && input !== null) input.disconnect();
  if (processor && processor !== null) processor.disconnect();

  let finalBuffer = flattenArray(audioData, recordingLength);
  let blob = generateWavBlob(finalBuffer);
  if (blob == null) {
    console.log("no blob generated");
    callback(null);
    return;
  }
  callback(blob);
  disconnect();
}

function connect(
  socketURL,
  transcription_language,
  onSuccess = function () {},
  onError = function () {}
) {
  // establish connection
  // emit connect event
  // listen on connect success
  // trigger onSuccess/onError depending on response

  language = transcription_language;

  socket = io(socketURL, { autoConnect: false, query: `language=${language}` });
  socket.connect();

  socket.on("connect", function () {
    userId = socket.id;
    socket.emit("connect_mic_stream");
  });

  socket.on("connect-success", function (data) {
    console.log("connect-success", userId);
    onSuccess(null, userId);
  });

  socket.on("disconnect", function () {
    console.log("disconnected");
  });
  socket.on("terminate", function () {
    onSuccess("Terminate", userId);
  });

  socket.on("abort", function () {
    onError("The server is busy at the moment, please try after sometime.");
  });
}

function disconnect() {
  // emit disconnect event
  // trigger onSuccess/onError depending on response

  socket.disconnect();
}

module.exports = {
  connect,
  startStreaming,
  stopStreaming,
};

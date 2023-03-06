const hark = require("hark");
const { io } = require("socket.io-client");
const $ = require("jquery");

export default function StreamingClient() {
  const _this = this;
  this.socket = null;
  this.defaultSampleRate = 48000;
  this.speechEvents = null;
  this.input = null;
  this.processor = null;

  /** Note:
   * TODO:
   * auto mic close needs to be added.
   * check for socket null in all used places.
   * add default callbacks to avoid errors.
   * Error handling
   */

  // state parameters
  this.audioData = [];
  this.recordingLength = 0;
  this.userId = "";
  this.isStreaming = false;
  this.isStreamingOver = false;
  this.isSilenceTransmitted = true;
  this.localBuffer = null;
  this.language = "en";
  this.bufferSize = 16384;
  this.isSpeaking = false;
  this.isSocketConnected = false;

  function setStateOnMicStart() {
    _this.isStreaming = true;
    _this.audioData = [];
    _this.recordingLength = 0;
  }

  function setStateOnMicStop() {
    _this.isStreaming = false;
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
    view.setUint32(24, _this.defaultSampleRate, true); // dwSamplesPerSec
    view.setUint32(28, _this.defaultSampleRate * 2, true); // dwAvgBytesPerSec
    view.setUint16(32, 4, true); // wBlockAlign
    view.setUint16(34, 16, true); // wBitsPerSample
    // data sub-chunk
    writeUTFBytes(view, 36, "data");
    view.setUint32(40, finalBuffer.length * 2, true);

    // write the PCM samples
    let index = 44;
    let volume = 1;
    for (let i = 0; i < finalBuffer.length; i++) {
      view.setInt16(index, finalBuffer[i] * (0x7fff * volume), true);
      index += 2;
    }

    // our final blob
    let blob = new Blob([view], { type: "audio/wav" });
    return blob;
  }

  function setSilenceDetector(audioStream, context) {
    let options = { audioContext: context };
    _this.speechEvents = hark(audioStream, options);

    _this.speechEvents.on("speaking", function () {
      _this.isSpeaking = true;
    });

    _this.speechEvents.on("stopped_speaking", function () {
      _this.isSpeaking = false;
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
      for (
        let i = offsetBuffer;
        i < nextOffsetBuffer && i < buffer.length;
        i++
      ) {
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
    _this.audioData.push(new Float32Array(e.inputBuffer.getChannelData(0)));
    _this.recordingLength += _this.bufferSize;
    if (_this.isStreaming === true) {
      _this.isStreamingOver = false;
      let data_44100 = e.inputBuffer.getChannelData(0);
      let data_16000 = downSampleBuffer(
        data_44100,
        _this.defaultSampleRate,
        16000
      );
      if (_this.isSpeaking) {
        _this.isSilenceTransmitted = false;

        if (_this.localBuffer !== undefined && _this.localBuffer !== null) {
          data_16000 = appendBuffer(_this.localBuffer, data_16000);
        }
        _this.socket.emit("mic_data", data_16000, _this.language, true, false);
        _this.localBuffer = null;
      } else {
        if (!_this.isSilenceTransmitted) {
          _this.isSilenceTransmitted = true;
          _this.socket.emit(
            "mic_data",
            data_16000,
            _this.language,
            false,
            false
          );
        } else {
          _this.localBuffer = data_16000;
        }
      }
    } else {
      if (!_this.isStreamingOver) {
        var data_44100 = e.inputBuffer.getChannelData(0);
        var data_16000 = downSampleBuffer(
          data_44100,
          _this.defaultSampleRate,
          16000
        );
        // let data_16000 = data_44100;
        _this.isStreamingOver = true;
        _this.socket.emit("mic_data", data_16000, _this.language, false, true);
        // console.log("emitted last");
      }
    }
  }

  this.startStreaming = async (
    responseCallback = () => {},
    errorCallback = () => {}
  ) => {
    try {
      setStateOnMicStart();
      let stream = await getAudioMediaStream();

      // connect socket here if needed

      let audioContextClass = window.AudioContext || window.webkitAudioContext;
      let context = new audioContextClass({
        latencyHint: "interactive",
      });
      _this.defaultSampleRate = context.sampleRate;

      setSilenceDetector(stream.clone(), context);

      _this.input = context.createMediaStreamSource(stream);
      _this.processor = context.createScriptProcessor(_this.bufferSize, 1, 1);

      _this.input.connect(_this.processor);
      _this.processor.connect(context.destination);

      _this.processor.onaudioprocess = streamAudioProcess;

      // clear states
      // access media library, proceed to next if access enabled / throw error
      // if needed, connect to socket
      // set silence detector
      // stream processor(responseCallback)

      _this.socket.on("response", function (data, language) {
        if (language === "en-IN") data = data.toLowerCase();
        responseCallback(data);
      });
    } catch (e) {
      errorCallback(e);
    }
  };

  this.stopStreaming = (callback = () => {}) => {
    // revoke access to media library
    // if needed, disable socket
    // disable silence detector
    // disable stream processor
    // clear states
    setStateOnMicStop();
    _this.socket.emit("mic_data", null, _this.language, false, true);

    if (_this.speechEvents && _this.speechEvents !== null)
      _this.speechEvents.stop();
    if (_this.input && _this.input !== null) _this.input.disconnect();
    if (_this.processor && _this.processor !== null)
      _this.processor.disconnect();

    let finalBuffer = flattenArray(_this.audioData, _this.recordingLength);
    let blob = generateWavBlob(finalBuffer);
    if (blob == null) {
      callback(null);
      return;
    }
    callback(blob);
    _this.disconnect();
  };

  this.connect = (
    socketURL,
    transcription_language,
    onSuccess = () => {},
    onError = () => {}
  ) => {
    // establish connection
    // emit connect event
    // listen on connect success
    // trigger onSuccess/onError depending on response

    _this.language = transcription_language;

    _this.socket = io(socketURL, {
      autoConnect: false,
      query: `language=${_this.language}`,
    });
    _this.socket.connect();

    _this.socket.on("connect", function () {
      _this.userId = _this.socket.id;
      _this.socket.emit("connect_mic_stream");
    });

    _this.socket.on("connect-success", function (data) {
      onSuccess(null, _this.userId);
      _this.isSocketConnected = true;
    });

    _this.socket.on("disconnect", function () {
      _this.isSocketConnected = false;
    });

    _this.socket.on("terminate", function () {
      onSuccess("Terminate", _this.userId);
    });

    _this.socket.on("abort", function () {
      onError("The server is busy at the moment, please try after sometime.");
    });
  };

  this.disconnect = () => {
    // emit disconnect event
    // trigger onSuccess/onError depending on response
    _this.socket.disconnect();
  };

  this.isSocketConnected = () => _this.isSocketConnected;

  this.punctuateText = (
    textToPunctuate,
    punctuationUrl,
    onSuccess = () => {},
    onError = () => {}
  ) => {
    let formData = new FormData();
    formData.append("text", textToPunctuate);
    formData.append("language", _this.language);
    $.ajax({
      type: "POST",
      url: punctuationUrl,
      data: formData,
      contentType: false,
      processData: false,
      crossDomain: true,
      success: function (response, textStatus, jqXHR) {
        const resp = response["data"];
        onSuccess(textStatus, resp["text"]);
      },
      error: function (jqXHR, textStatus, errorThrown) {
        onError(textStatus, errorThrown);
      },
    });
  };
}

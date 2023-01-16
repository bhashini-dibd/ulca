
import { Divider, Grid } from "@material-ui/core";
import SpeechToSpeechFilter from "./SpeechToSpeechFilter";
import SpeechToSpeechOptions from "./SpeechToSpeechOptions";
import { useCallback, useState } from "react";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import SearchModel from "../../../../../redux/actions/api/Model/ModelSearch/SearchModel";
import APITransport from "../../../../../redux/actions/apitransport/apitransport";
import { useSelector } from "react-redux";
import C from "../../../../../redux/actions/constants";
import Snackbar from "../../../../components/common/Snackbar";
import Start from "../../../../../assets/start.svg";
import Stop from "../../../../../assets/stopIcon.svg";
import AudioReactRecorder, { RecordState } from "audio-react-recorder";
import ComputeAPI from "../../../../../redux/actions/api/Model/ModelSearch/HostedInference";
import { Language } from "../../../../../configs/DatasetItems";
import Modal from '../../../../components/common/Modal';
import FeedbackModal from '../../../../components/common/Feedback';
import GetMasterDataAPI from "../../../../../redux/actions/api/Common/getMasterData";
import SubmitFeedback from "../../../../../redux/actions/api/Model/ModelSearch/SubmitFeedback";

const SpeechToSpeech = () => {
  const dispatch = useDispatch();
  const { asr, tts, translation, sourceLanguage, targetLanguage } = useSelector(
    (state) => state.getBulkModelSearch
  );
  const [gender, setGender] = useState("female")
  const [data, setData] = useState("");
  const [url, setUrl] = useState("");
  const [recordAudio, setRecordAudio] = useState("");
  const [audio, setAudio] = useState("");
  const [error, setError] = useState({ url: "" });
  const [base, setBase] = useState("");
  const [textArea, setTextArea] = useState({
    asr: "",
    translation: "",
  });
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
  const [filter, setFilter] = useState({
    src: "",
    tgt: "",
    asr: "",
    translation: "",
    tts: "",
  });
  const [output, setOutput] = useState({
    asr: "",
    translation: "",
  });

  const [outputBase64, setOutputBase64] = useState("");

  const [suggestEdit, setSuggestEdit] = useState(null)
  const [modal, setModal] = useState(false);
  const [suggestEditValues, setSuggestEditValues] = useState({ asr: "", translation: "" })
  
  const [comment, setComment] = useState("")
  const { feedbackQns } = useSelector((state) => state.getMasterData);

  // useEffect(() => {
  //   if (!feedbackQns) {
  //     const obj = new GetMasterDataAPI(["feedbackQns"]);
  //     dispatch(APITransport(obj));
  //   }
  // }, [])

  useEffect(() => {
    if (filter.src && filter.tgt) {
     // const asrVal = asr.filter(a => a.sourceLanguage === filter.src.value);
      const asrVal = asr.filter(a => a.sourceLanguage === filter.src.value && a.inferenceEndPoint.schema.modelProcessingType.type === 'batch')
      // const asrVal = asr.filter(a => a.sourceLanguage === filter.src.value && a.inferenceEndPoint.schema.modelProcessingType.type === 'batch' && a.label.includes('AI4Bharat') )
      //const vakyanshAsr = asrVal.filter(asr => asr.label.toLowerCase().includes('vakyansh'));
      const vakyanshAsr = asrVal.filter(asr => asr.label.toLowerCase().includes('ai4bharat'));
      // const vakyanshAsr = asrVal.filter(asr => asr.label.toLowerCase().includes('vakyansh'));
      const translationVal = translation.filter(a => a.sourceLanguage === filter.src.value && a.targetLanguage === filter.tgt.value);
      const indictransTranslation = translationVal.filter(asr => asr.label.toLowerCase().includes('indictrans'));
     // const ttsVal = tts.filter(a => a.sourceLanguage === filter.tgt.value);
     const ttsVal = tts.filter(a => a.sourceLanguage === filter.tgt.value && a.inferenceEndPoint.schema.modelProcessingType.type === 'batch');
      const vakyanshTts = ttsVal.filter(asr => asr.label.toLowerCase().includes('ai4bharat'));
      if (vakyanshAsr.length) {
        setFilter((prev) => ({ ...prev, asr: vakyanshAsr[0] }))
      } else {
        setFilter((prev) => ({ ...prev, asr: asrVal[0] }))
      }
      if (indictransTranslation.length) {
        setFilter((prev) => ({ ...prev, translation: indictransTranslation[0] }))
      } else {
        setFilter((prev) => ({ ...prev, translation: translationVal[0] }))
      } if (vakyanshTts.length) {
        setFilter((prev) => ({ ...prev, tts: vakyanshTts[0] }))
      } else {
        setFilter((prev) => ({ ...prev, tts: ttsVal[0] }))
      }
    }
  }, [filter.src, filter.tgt])

  const [index, setIndex] = useState(0);

  const handleChange = (data, id) => {
    switch (id) {
      case "src":
        if (data === null) {
          setFilter({ src: "", tgt: "", asr: "", tts: "", translation: "" });
          setBase("");
          setUrl("");
          setAudio("");
          setData("");
        } else
          setFilter({
            ...filter,
            [id]: data,
            tgt: "",
            asr: "",
            tts: "",
            translation: "",
          });
        break;
      case "tgt":
        if (data === null)
          setFilter({ ...filter, tgt: "", asr: "", tts: "", translation: "" });
        else
          setFilter({
            ...filter,
            [id]: data,
            asr: "",
            tts: "",
            translation: "",
          });
        break;
      default:
        setFilter({ ...filter, [id]: data !== null ? data : "" });
        break;
    }
  };

  const handleStartRecording = (data) => {
    setData(null);
    setAudio("");
    setOutput({ asr: "", translation: "" });
    setSuggestEditValues({ asr: "", translation: "" });
    setTextArea({ asr: "", translation: "" });
    if (checkFilter()) {
      setSnackbarInfo({
        ...snackbar,
        open: true,
        message: "Please select all the drop down values...",
        variant: "error",
      });
    } else {
      setRecordAudio(RecordState.START);
      setTimeout(() => {
        handleStopRecording();
      }, 60000);
    }
  };

  const handleStopRecording = (value) => {
    setRecordAudio(RecordState.STOP);
  };

  useEffect(() => {
    dispatch({ type: C.CLEAR_BULK_MODEL_SEARCH });
    return () => dispatch({ type: C.CLEAR_BULK_MODEL_SEARCH });
  }, []);

  const makeModelSearchAPICall = (type, src, tgt) => {
    const apiObj = new SearchModel(type, src, tgt, true);
    dispatch(APITransport(apiObj));
  };

  const blobToBase64 = (blob) => {
    var reader = new FileReader();
    reader.readAsDataURL(blob.blob);
    reader.onloadend = function () {
      let base64data = reader.result;
      setBase(base64data);
    };
  };

  const onStopRecording = (data) => {
    if (data && data.hasOwnProperty('url')) {
      setData(data.url);
      setBase(blobToBase64(data));
    }
  };

  useEffect(() => {
    makeModelSearchAPICall("asr", "", "");
    makeModelSearchAPICall("translation", "", "");
    makeModelSearchAPICall("tts", "", "");
  }, []);

  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false });
  };

  const handleTabChange = (e, val) => {
    setIndex(val);
  };

  const validURL = (str) => {
    var pattern = new RegExp(
      "^((ft|htt)ps?:\\/\\/)?" + // protocol
      "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|" + // domain name and extension
      "((\\d{1,3}\\.){3}\\d{1,3}))" + // OR ip (v4) address
      "(\\:\\d+)?" + // port
      "(\\/[-a-z\\d%@_.~+&:]*)*" + // path
      "(\\?[;&a-z\\d%@_.,~+&:=-]*)?" + // query string
      "(\\#[-a-z\\d_]*)?$",
      "i"
    );
    return pattern.test(str);
  };

  const b64toBlob = (b64Data, contentType = "", sliceSize = 512) => {
    const byteCharacters = atob(b64Data);
    const byteArrays = [];

    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
      const slice = byteCharacters.slice(offset, offset + sliceSize);

      const byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }

      const byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }

    const blob = new Blob(byteArrays, { type: contentType });
    return blob;
  };

  const handleTextAreaChange = (e, prop) => {
    setTextArea((prev) => ({ ...prev, [prop]: e.target.value }));
  };

  const makeTranslationAPICall = () => {
    setSnackbarInfo({
      ...snackbar,
      open: true,
      message: "Please wait while we process your request...",
      variant: "info",
    });
    setTextArea((prev) => ({ ...prev, translation: "", asr: "" }));
    setOutput((prev) => ({ ...prev, asr: textArea.asr }));
    setSuggestEditValues((prev) => ({ ...prev, asr: textArea.asr }));
    setOutputBase64("")
    const obj = new ComputeAPI(
      filter.translation.value,
      textArea.asr,
      "translation",
      false,
      filter.src,
      filter.translation.inferenceEndPoint,
      ""
    );

    fetch(obj.apiEndPoint(), {
      method: "post",
      headers: obj.getHeaders().headers,
      body: JSON.stringify(obj.getBody()),
    }).then(async (translationResp) => {
      let rsp_data = await translationResp.json();
      if (translationResp.ok) {
        setOutput((prev) => ({
          ...prev,
          translation: rsp_data?.output[0]?.target,
        }));
        setSuggestEditValues(async (prev) => ({
          ...prev,
          translation: rsp_data?.output[0]?.target,
        }));
        const obj = new ComputeAPI(
          filter.tts.value,
          rsp_data?.output[0]?.target,
          "tts",
          "",
          "",
          filter.tts.inferenceEndPoint,
          "female"
        );
        await fetch(obj.apiEndPoint(), {
          method: "post",
          headers: obj.getHeaders().headers,
          body: JSON.stringify(obj.getBody()),
        }).then(async (ttsResp) => {
          let rsp_data = await ttsResp.json();
          if (ttsResp.ok) {
            const blob = b64toBlob(rsp_data?.audio[0]?.audioContent, "audio/wav");
            setOutputBase64(rsp_data?.audio[0]?.audioContent);
            const urlBlob = window.URL.createObjectURL(blob);
            setAudio(urlBlob);
            setSnackbarInfo({ ...snackbar, open: false, message: "" });
          } else {
            setSnackbarError(rsp_data?.message);
          }
        });
      } else {
        setSnackbarError(rsp_data.message);
      }
    });
  };

  const makeTTSAPICall = () => {
    setSnackbarInfo({
      ...snackbar,
      open: true,
      message: "Please wait while we process your request...",
      variant: "info",
    });
    setTextArea((prev) => ({ ...prev, translation: "" }));
    setOutput((prev) => ({ ...prev, translation: textArea.translation }));
    setSuggestEditValues((prev) => ({ ...prev, translation: textArea.translation }));

    const obj = new ComputeAPI(
      filter.tts.value,
      textArea.translation,
      "tts",
      "",
      "",
      filter.tts.inferenceEndPoint,
      "female"
    );
    fetch(obj.apiEndPoint(), {
      method: "post",
      headers: obj.getHeaders().headers,
      body: JSON.stringify(obj.getBody()),
    }).then(async (ttsResp) => {
      let rsp_data = await ttsResp.json();
      if (ttsResp.ok) {
        const blob = b64toBlob(rsp_data?.audio[0]?.audioContent, "audio/wav");
        setOutputBase64(rsp_data?.audio[0]?.audioContent);
        const urlBlob = window.URL.createObjectURL(blob);
        setAudio(urlBlob);
        setSnackbarInfo({ ...snackbar, open: false, message: "" });
      } else {
        setSnackbarError(rsp_data.message);
      }
    });
  };
  const genderHandler = (value) => {
    setGender(value)

  }
  const setSnackbarError = (errorMsg) => {
    setSnackbarInfo({
      ...snackbar,
      open: true,
      message: errorMsg,
      variant: "error",
    });
    setTimeout(() => {
      setSnackbarInfo({
        ...snackbar,
        open: false,
        message: "",
        variant: null,
      });
    }, 3000);
  };

  const makeComputeAPICall = (type) => {
    setSnackbarInfo({
      ...snackbar,
      open: true,
      message: "Please wait while we process your request...",
      variant: "info",
    });
    setAudio(null);
    const apiObj = new ComputeAPI(
      filter.asr.value, //modelId
      type === "url" ? url : base, //input URL
      "asr", //task
      type === "voice" ? true : false, //boolean record audio
      filter.src.value, //source
      filter.asr.inferenceEndPoint, //inference endpoint
      "" //gender
    );
    fetch(apiObj.apiEndPoint(), {
      method: "post",
      body: JSON.stringify(apiObj.getBody()),
      headers: apiObj.getHeaders().headers,
    })
      .then(async (resp) => {
        let rsp_data = await resp.json();
        if (resp.ok && rsp_data !== null) {
          setOutput((prev) => ({ ...prev, asr: rsp_data.data.source }));
          setSuggestEditValues((prev) => ({ ...prev, asr: rsp_data.data.source }));

          const obj = new ComputeAPI(
            filter.translation.value,
            rsp_data.data.source,
            "translation",
            "",
            "",
            filter.translation.inferenceEndPoint,
            ""
          );
          fetch(obj.apiEndPoint(), {
            method: "post",
            body: JSON.stringify(obj.getBody()),
            headers: obj.getHeaders().headers,
          }).then(async (translationResp) => {
            let rsp_data = await translationResp.json();
            if (translationResp.ok) {
              setOutput((prev) => ({
                ...prev,
                translation: rsp_data.output[0].target,
              }));
              setSuggestEditValues((prev) => ({
                ...prev,
                translation: rsp_data.output[0].target,
              }));
              const obj = new ComputeAPI(
                filter.tts.value,
                rsp_data.output[0].target,
                "tts",
                "",
                "",
                filter.tts.inferenceEndPoint,
                gender
              );
              fetch(obj.apiEndPoint(), {
                method: "post",
                headers: obj.getHeaders().headers,
                body: JSON.stringify(obj.getBody()),
              }).then(async (ttsResp) => {
                let rsp_data = await ttsResp.json();
                if (ttsResp.ok) {
                  if(rsp_data.audio[0].audioContent) {
                    const blob = b64toBlob(rsp_data.audio[0].audioContent, "audio/wav");
                    setOutputBase64(rsp_data.audio[0].audioContent);
                    const urlBlob = window.URL.createObjectURL(blob);
                    setAudio(urlBlob);
                  } else {
                    setOutputBase64(rsp_data.audio[0].audioUri);
                    setAudio(rsp_data.audio[0].audioUri);
                  }
                  setSnackbarInfo({ ...snackbar, open: false, message: "" });
                } else {
                  setSnackbarError(rsp_data.message);
                }
              }).catch(async (error) => {
                setSnackbarError(
                  "Unable to process your request at the moment. Please try after sometime."
                );
              });
            } else {
              setSnackbarError(rsp_data.message);
            }
          }).catch(async (error) => {
            setSnackbarError(
              "Unable to process your request at the moment. Please try after sometime."
            );
          });
        } else {
          setSnackbarError(rsp_data.message);
        }
      })
      .catch(async (error) => {
        setSnackbarError(
          "Unable to process your request at the moment. Please try after sometime."
        );
      });
  };

  const clearTranslation = () => {
    setTextArea((prev) => ({ ...prev, translation: "" }));
  };

  const clearAsr = () => {
    setTextArea((prev) => ({ ...prev, asr: "" }));
  };

  const checkFilter = () => {
    const { src, tgt, asr, translation, tts } = filter;
    if (src && tgt && asr && translation && tts) {
      return false;
    }
    return true;
  };

  const handleUrlSubmit = (e) => {
    if (!validURL(url)) {
      setError({ ...error, url: "Invalid URL" });
    }
    if (checkFilter()) {
      setSnackbarInfo({
        ...snackbar,
        open: true,
        message: "Please select all the drop down values...",
        variant: "error",
      });
    } else {
      makeComputeAPICall("url");
    }
  };

  const handleCompute = () => {
    makeComputeAPICall("voice");
  };

  const handleResetBtnClick = () => {
    setFilter({ asr: "", tts: "", translation: "", src: "", tgt: "" });
    setBase("");
    setUrl("");
    setAudio("");
    setData("");
    clearAsr();
    clearTranslation();
    output.asr = false;
  };

  const handleCopyClick = (prop) => {
    setTextArea({ ...textArea, [prop]: output[prop] });
  };

  const isDisabled = () => {
    const { asr, tts, translation, src, tgt } = filter;
    return asr || tts || translation || src || tgt ? false : true;
  };

  function getUniqueListBy(arr, key) {
    return [...new Map(arr.map((item) => [item[key], item])).values()];
  }

  const renderTargetVal = () => {
    let updatedTargets = [];
    targetLanguage.forEach((lang) => {
      translation.forEach((tr) => {
        if (
          tr.sourceLanguage === filter.src.value &&
          tr.targetLanguage === lang.value
        ) {
          updatedTargets.push(lang);
        }
      });
    });
    updatedTargets = [...new Set(updatedTargets)];
    return getUniqueListBy(updatedTargets, "value");
  };

  const handleOnChange = (param, e) => {
    setSuggestEditValues((prev) => ({ ...prev, [param]: e.target.value }))
  }

  const getQuestions = useCallback(() => {
    return feedbackQns.filter(elem => elem.code === 'sts')
  }, [feedbackQns])

  const handleFeedbackSubmit = (initialRating, asrRating, translationRating, ttsRating) => {
    const feedback = [
      {
        feedbackQuestion: "Are you satisfied with this translation?",
        feedbackQuestionResponse: initialRating
      },
      {
        feedbackQuestion: "Add your comments",
        feedbackQuestionResponse: comment
      }
    ]

    const detailedFeedback = [
      {
        taskType: 'asr',
        modelId: filter.asr.value,
        input: base.replace("data:audio/wav;base64,", ""),
        ouput: output.asr,
        feedback: [
          {
            feedbackQuestion: "Rate Speech to Text Quality?",
            feedbackQuestionResponse: asrRating,
            suggestedOutput: output.asr === suggestEditValues.asr ? null : suggestEditValues.asr
          }
        ]
      },
      {
        taskType: 'translation',
        modelId: filter.translation.value,
        input: output.asr,
        ouput: output.translation,
        feedback: [
          {
            feedbackQuestion: "Rate Translated Text Quality",
            feedbackQuestionResponse: translationRating,
            suggestedOutput: output.translation === suggestEditValues.translation ? null : suggestEditValues.translation
          }
        ]
      },
      {
        taskType: 'tts',
        modelId: filter.tts.value,
        input: output.translation,
        ouput: outputBase64,
        feedback: [
          {
            feedbackQuestion: "Rate Translated Speech Quality",
            feedbackQuestionResponse: ttsRating,

          }
        ]
      },

    ]

    console.log(detailedFeedback)
    const apiObj = new SubmitFeedback(
      'sts', //taskType
      base.replace("data:audio/wav;base64,", ""), //input
      outputBase64, //output
      feedback, //feedback
      detailedFeedback //detailedFeedback
    );

    setSnackbarInfo({ open: true, message: 'Please wait while we process your request...', variant: 'info' })

    fetch(apiObj.apiEndPoint(), {
      method: 'POST',
      body: JSON.stringify(apiObj.getBody()),
      headers: apiObj.getHeaders().headers
    }).then(async res => {
      const rsp_data = await res.json();
      if (res.ok) {
        setSnackbarInfo({ open: true, message: rsp_data.message, variant: 'success' })
      }
    })
    setModal(false);
    setTimeout(() => setSnackbarInfo((prev) => ({ ...prev, open: false })), 3000)
  }

  const handleCommentChange = (e) => {
    setComment(e.target.value);
  }

  return (
    <>
      <Grid container spacing={5}>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <SpeechToSpeechFilter
            asr={asr}
            tts={tts}
            translation={translation}
            filter={filter}
            handleClick={handleResetBtnClick}
            handleChange={handleChange}
            sourceLanguage={sourceLanguage}
            targetLanguage={renderTargetVal()}
            disabled={isDisabled()}
          />
        </Grid>
        <Divider />
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>

          <SpeechToSpeechOptions
            setModal={setModal}
            Start={Start}
            Stop={Stop}
            data={data}
            url={url}
            base={base}
            setBase={setBase}
            suggestEdit={suggestEdit}
            setSuggestEdit={setSuggestEdit}
            error={error}
            setUrl={setUrl}
            setError={setError}
            makeTTSAPICall={makeTTSAPICall}
            makeTranslationAPICall={makeTranslationAPICall}
            onStopRecording={onStopRecording}
            handleStartRecording={handleStartRecording}
            handleStopRecording={handleStopRecording}
            handleSubmit={handleUrlSubmit}
            AudioReactRecorder={AudioReactRecorder}
            recordAudio={recordAudio}
            handleCompute={handleCompute}
            audio={audio}
            output={output}
            handleTextAreaChange={handleTextAreaChange}
            textArea={textArea}
            clearAsr={clearAsr}
            clearTranslation={clearTranslation}
            index={index}
            handleTabChange={handleTabChange}
            handleCopyClick={handleCopyClick}
            gender={genderHandler}
            genderValue={gender}
          />
        </Grid>
      </Grid>
      {snackbar.open && (
        <Snackbar
          open={snackbar.open}
          handleClose={handleSnackbarClose}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          message={snackbar.message}
          variant={snackbar.variant}
        />
      )}
      {
        modal && (
          <Modal open={modal} handleClose={() => setModal(false)}>
            <FeedbackModal
              setModal={setModal}
              setSuggestEdit={setSuggestEdit}
              suggestEdit={suggestEdit}
              asrValue={suggestEditValues.asr}
              ttsValue={suggestEditValues.translation}
              handleOnChange={handleOnChange}
              questions={getQuestions()}
              handleFeedbackSubmit={handleFeedbackSubmit}
              comment={comment}
              handleCommentChange={handleCommentChange}
              setComment={setComment}
              setSuggestEditValues={setSuggestEditValues}
              output={output}
            />
          </Modal>
        )
      }
    </>
  );
};

export default SpeechToSpeech;

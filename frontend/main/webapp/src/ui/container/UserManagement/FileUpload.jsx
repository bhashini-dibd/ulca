import React, { useEffect, useRef, useState } from 'react';
import { Dialog, DialogTitle, DialogContent, Button, Grid, Typography, Box, IconButton, CircularProgress } from '@material-ui/core';
import { CloudUpload as CloudUploadIcon } from '@material-ui/icons';
import uploadImg from '../../../assets/glossary/upload.svg';
import recording from '../../../assets/recording.svg';
import recordLive from '../../../assets/recordLive.svg';
import recordHover from '../../../assets/recordHover.svg';
import recordStop from '../../../assets/recordstop.svg';
import DeleteIcon from '@material-ui/icons/Delete';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import CloseIcon from '@material-ui/icons/Close';
import { makeStyles } from '@material-ui/core/styles';
import AddSpeakerEnrollmentDataApi from '../../../redux/actions/api/UserManagement/AddSpeakerEnrollmentData';
import APITransport from "../../../redux/actions/apitransport/apitransport";
import { useDispatch, useSelector } from 'react-redux';
import AddSpeakerVerificationDataApi from '../../../redux/actions/api/UserManagement/AddSpeakerVerificationData';
import AudioReactRecorder, { RecordState } from "audio-react-recorder";
const useStyles = makeStyles({
  dividerLine: {
    display: 'flex',
    alignItems: 'center',
    textAlign: 'center',
    color: '#E7E7E7',
    margin: '8px 0', // Adjusts the margin
    '&::before, &::after': {
      content: '""',
      flex: 1,
      borderBottom: '1px solid #E7E7E7', // Line color
      margin: '0 10px', // Spacing around the text
    },
  },
});




const FileUpload = ({ open,setOpen, handleClose, title, description, buttonText, handleAction, status,value,selectedFile,setSelectedFile,inputValue,setInputValue,audioURL,setAudioURL,base64Audio,setBase64Audio, base64Recording,setBase64Recording,serviceProviderName,appName,fetchUserId,setSnackbarInfo,getApiSpeakerData,handleVerifyGlobalDialogOpen,enrollmentSuccess, setEnrollmentSuccess,verificationData,setVerificationData, url, setUrl}) => {
  const [dragging, setDragging] = useState(false);
  const [error, setError] = useState('');
  const [hover, setHover] = useState(false);
  const [enrollmentLoading,setEnrollmentLoading] = useState(false)
  const [verifyLoading,setVerifyLoading] = useState(false)
  const EnrolledSpeakerData = useSelector((state)=>state?.enrolledSpeakerData?.enrolledData?.pipelineResponse?.[0]?.output?.[0]);
  const VerifiedSpeakerData = useSelector((state)=>state?.verifySpeakerData?.verifyData);
  console.log(VerifiedSpeakerData,"EnrolledSpeakerData");
  const [recordState, setRecordState] = useState(null); // For controlling recording
  const audioRef = useRef(null);
  const durationRef = useRef(null); 
  
  
  const classes = useStyles();
  const dispatch = useDispatch()

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    console.log("hello");
    
    const allowedTypes = ['audio/wav', 'audio/mpeg', 'audio/flac', 'audio/ogg']; // MIME types for WAV, MP3, FLAC, and OGG

    if (file && allowedTypes.includes(file.type)) {
      const reader = new FileReader();

      // When the file reading is finished, set the base64 value
      reader.onloadend = () => {
        const base64String = reader.result.split(',')[1]; // Extract base64 without metadata
        setBase64Audio(base64String);
        console.log(base64String, "base64String");
        
      };

      reader.onerror = () => {
        console.error('Error reading the file.');
      };

      // Convert the file to a Data URL (base64 format)
      reader.readAsDataURL(file);
  
      setSelectedFile(file);
      // console.log(file.split("base64,")[1], "base64data");
      setError('');
    } else {
      setSelectedFile(null);
      setError('Error:  Failed to Upload Mp3 File;  Format is not supported.');
    }
  };

  const handleDrop = (event) => {
    event.preventDefault();
    setDragging(false);
    const file = event.dataTransfer.files[0];
    const allowedTypes = ['audio/wav', 'audio/mpeg', 'audio/flac', 'audio/ogg']; // MIME types for WAV, MP3, FLAC, and OGG

    if (file && allowedTypes.includes(file.type)) {
      const reader = new FileReader();

      // When the file reading is finished, set the base64 value
      reader.onloadend = () => {
        const base64String = reader.result.split(',')[1]; // Extract base64 without metadata
        setBase64Audio(base64String);
        console.log(base64String, "base64String");
        
      };

      reader.onerror = () => {
        console.error('Error reading the file.');
      };

      // Convert the file to a Data URL (base64 format)
      reader.readAsDataURL(file);
      setSelectedFile(file);
      // console.log(file.split("base64,")[1], "base64data");
      setError('');
    } else {
      setSelectedFile(null);
      setError('Error:  Failed to Upload Mp3 File;  Format is not supported.');
    }
  };
  const handleDragOver = (event) => {
    event.preventDefault();
    setDragging(true);
  };

  const handleDragLeave = () => {
    setDragging(false);
  };


  const handleSpeakerEnrollmentButtonClick = async() => {
    // handleAction(selectedFile);
    setEnrollmentLoading(true)
    const apiObj = new AddSpeakerEnrollmentDataApi(appName,serviceProviderName,base64Audio, base64Recording, url, inputValue);
    const res = await fetch(apiObj.apiEndPoint(), {
      method: "POST",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody()),
    });
    // dispatch(APITransport(apiObj));
    const resp = await res.json();
    if (res.ok) {
      dispatch({
        type: "ENROLL_SPEAKER_DATA",
        payload: resp, // assuming the response has the enrollment data
      });
      getApiSpeakerData()
      setEnrollmentSuccess(true)
      setEnrollmentLoading(false);
      setSelectedFile(null)
      setAudioURL('')
      setBase64Recording('')
      setUrl('')
      setInputValue('')
      setBase64Audio('')
      setSnackbarInfo({
        open: true,
        message: 'Speaker enrolled successfully',
        variant: "success",
      });
      console.log(resp,"res");
      
      // setInputValue('')
      // setTimeout(() => {
      //   setEnrollmentSuccess(false)
      //   setInputValue('')
      // }, 3000)
    } else {
      setSnackbarInfo({
        open: true,
        message: resp?.detail?.message,
        variant: "error",
      });
      setBase64Recording('')
      setBase64Audio('')
      setSelectedFile(null)
      setUrl('')
      setInputValue('')
      setOpen(false)
      setEnrollmentLoading(false);

  
  }
  };

  useEffect(() => {
    setVerificationData(false);  // Reset cancel state when new data comes in or actions change
  }, [VerifiedSpeakerData, value, title]);

  const handleSpeakerVerificationButtonClick = async() => {
    // handleAction(selectedFile);
    setVerifyLoading(true)
    const apiObj = new AddSpeakerVerificationDataApi(appName,serviceProviderName,base64Audio, base64Recording, url,inputValue, fetchUserId);
    // dispatch(APITransport(apiObj));
    const res = await fetch(apiObj.apiEndPoint(), {
      method: "POST",
      headers: apiObj.getHeaders().headers,
      body: JSON.stringify(apiObj.getBody()),
    });
    // dispatch(APITransport(apiObj));
    const resp = await res.json();
    if (res.ok) {
      dispatch({
        type: "VERIFY_SPEAKER_DATA",
        payload: resp, // assuming the response has the enrollment data
      });

      setVerifyLoading(false)
      setSelectedFile(null)
      setAudioURL('')
      setBase64Audio('')
      setBase64Recording('')
      // setInputValue('')
      // setTimeout(() => {
      //   setEnrollmentSuccess(false)
      //   setInputValue('')
      // }, 3000)
    } else {
      setSnackbarInfo({
        open: true,
        message: resp?.detail?.message,
        variant: "error",
      });
      setBase64Recording('')
      setSelectedFile(null)
      setBase64Audio('')
      setUrl('')
      setOpen(false)
      setVerifyLoading(false)

  
  }
    
  };

  const handleFileRemove = () => {
    setSelectedFile(null);
    setAudioURL('');
    setError('');
    setBase64Recording("");
  };
  
  const blobToBase64 = (blob) => {
    var reader = new FileReader();
    reader.readAsDataURL(blob.blob);
    reader.onloadend = function () {
      let base64data = reader.result;
      setBase64Recording(base64data);
      console.log(base64data,"aaa");
    
    };
  };
  
  

  const toggleRecording = () => {
    if (recordState === RecordState.START) {
      setRecordState(RecordState.STOP);
    } else {
      setRecordState(RecordState.START);
    }
    // setShowRecorder(!showRecorder); // Toggle visibility of recorder
  };

  const getRecordingIcon = () => {
    if (recordState === RecordState.START) {
      return recordLive; // Icon while recording
    } else if (hover) {
      return recordHover; // Hover icon
    } else {
      return recording; // Initial icon
    }
  };

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleURLChange = (event) => {
    setUrl(event.target.value);
  };

  const isSubmitDisabled = (value === 'local' || !status) ? !(selectedFile || base64Recording || url) : !((selectedFile || base64Recording || url) && inputValue);


  // Handle the audio data after recording is stopped
  const onStop = (audioData) => {
    const url = audioData.url;
    console.log(url,"uuu");
    blobToBase64(audioData)
    setBase64Recording(url); // Set the audio URL to play it back
  };

  // Format time in mm:ss
  const formatTime = (seconds) => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = Math.floor(seconds % 60);
    return `${minutes}:${remainingSeconds < 10 ? "0" : ""}${remainingSeconds}`;
  };

  // Handle loaded metadata to get the duration
  const handleLoadedMetadata = () => {
    if (audioRef.current) {
      const audioDuration = audioRef.current.duration;
      if (durationRef.current) {
        durationRef.current.textContent = formatTime(audioDuration);
      }
    }
  };



  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
       {enrollmentSuccess ? <Grid>
         {/* Close button in the top-right corner */}
      <IconButton
        style={{
          position: 'absolute',
          right: 8,
          top: 8,
        }}
        onClick={handleClose}
      >
        <CloseIcon />
      </IconButton>
      
      {/* Dialog content */}
      <DialogContent style={{ textAlign: 'center', padding: '24px' }}>
        <CheckCircleIcon style={{ fontSize: '50px', color: '#4caf50', marginBottom: '10px' }} />
        <Typography style={{ color: 'green', fontWeight: 'bold', marginBottom: '8px' }}>
          Speaker ID: {EnrolledSpeakerData?.speakerId} | Speaker Name: {EnrolledSpeakerData?.speakerName}
        </Typography>

        <Typography variant="h6" gutterBottom>
          Speaker Enrolled Successfully!
        </Typography>

        <Typography variant="body2">
          You can verify speaker using{' '}
          <Button color="primary"  onClick={handleVerifyGlobalDialogOpen}  style={{ textTransform: 'none' }}>
            "Verify Speaker"
          </Button>
          .
        </Typography>
      </DialogContent>
       </Grid>:
       <>
      <DialogTitle>
        <Box>{title}</Box>
      </DialogTitle>
      <DialogContent className='mb-3'>
       
        <Grid container className='SpeakerEnrollmentStyle' spacing={2} direction="column" alignItems="start" >
        {value === 'local' && <Grid item>
          <Typography variant="body2" fontWeight={600}  style={{ marginTop: '10px' }}>
               Speaker ID
              </Typography>
          <input
                type="text"
               style={{width:"100%",height:"40px", marginTop:"5px", marginBottom:"15px"}}
                value={fetchUserId}
                disabled
              />
          </Grid>}
          <Grid item>
            <Typography variant="body2" fontWeight={600} color="#6D6D6D">
            {description}
            </Typography>
          </Grid>
          <Grid item>
            <div
              onDrop={handleDrop}
              onDragOver={handleDragOver}
              onDragLeave={handleDragLeave}
              style={{
                border: dragging ? '2px dashed #000' : '1px dashed #ccc',
                padding: '20px',
                width: '100%',
                textAlign: 'center',
                cursor: 'pointer'
              }}
            >
              <input
                type="file"
                accept=".wav, .mp3, .flac, .ogg"
                onChange={handleFileChange}
                style={{ display: 'none' }}
                id="file-upload"
                disabled={base64Recording || url}
              />
              <label htmlFor="file-upload" style={{ cursor: 'pointer' }}>
                {/* <CloudUploadIcon fontSize="large" /> */}
                <img src={uploadImg} alt="upload"/>
                <Typography className='mt-1'>Drag your file(s) to start uploading  </Typography>
                <Typography variant='body2' className={classes.dividerLine}>OR</Typography>
                {/* <Typography>click to browse</Typography> */}
                <Button variant="outlined" style={{color:"#1849D6", border:"1px solid #1849D6"}} component="span" className='my-3'>
                  Browse files
                </Button>
                <Typography variant='body2' sx={{color:"#E7E7E7"}}>Only support WAV,MP3,FLAC, & OGG</Typography>
              </label>
              <Typography variant='body2'  className={classes.dividerLine} >OR</Typography>
             
      <>
      <IconButton
        onMouseEnter={() => setHover(true)}
        onMouseLeave={() => setHover(false)}
        onClick={toggleRecording}
        color={recordState === RecordState.START ? 'secondary' : 'primary'}
        disabled={!!(selectedFile || url)}
      >
          <img src={getRecordingIcon()} alt="Recording Status" style={{ width: '30px', height: '30px' }} />
      {/* Recorder Component */}
      <div style={{display:"none"}}>

      <AudioReactRecorder
        state={recordState}
        onStop={onStop}
        backgroundColor="rgb(255,255,255)" // Set a background color for visualization
      />
      </div>
      </IconButton>
        <Typography variant='body2' sx={{color:"#E7E7E7"}} className='mt-1'>Record Audio</Typography> 
      </>
              <Grid item>
            {error && (
              <Typography variant="body2" color="error" style={{ marginTop: '10px', color:"red" }}>
                {error}
              </Typography>
            )}
          </Grid>
          <Typography variant='body2' className={classes.dividerLine}>OR</Typography>
          <Grid item>
          <Typography variant='body2' style={{color:"#787878", textAlign:"left"}} className=''>Attach Audio Link</Typography>
          <input
                type="text"
                onChange={handleURLChange}
                style={{width:"100%",height:"40px",border:"1px solid #787878", marginTop:"5px", marginBottom:"10px",padding:"2px 10px", borderRadius:"4px"}}
                placeholder='Paste URL here...'
                disabled={!!(selectedFile || base64Recording)}
              />

          </Grid>
            </div>
           
      {base64Recording ? (
        <div style={{ marginTop: '10px', display: 'flex', alignItems: 'center',justifyContent: "space-between" }}>
          <audio ref={audioRef}
            controls src={base64Recording}
            onLoadedMetadata={handleLoadedMetadata} style={{ marginRight: '10px', borderRadius:"3px", width:"100%" }}>
            Your browser does not support the audio element.
          </audio>
          <IconButton onClick={handleFileRemove} color="error" aria-label="delete">
            <DeleteIcon style={{ color: 'red' }}/>
          </IconButton>
        </div>
      ) : selectedFile ? (
        <div style={{ marginTop: '20px',paddingLeft:"10px", display: 'flex', alignItems: 'center',justifyContent: "space-between",
          border: "1px solid #11AF22" }}>
          <Box>{selectedFile.name}</Box>
          <IconButton onClick={handleFileRemove} color="error" aria-label="delete">
            <DeleteIcon style={{ color: 'red' }}/>
          </IconButton>
        </div>
      ) : null}
          </Grid>
         {status && <Grid item>
          <Typography variant="body2" fontWeight={600}  style={{ marginTop: '10px' }}>
               Speaker Name
              </Typography>
          <input
                type="text"
               style={{width:"100%",height:"40px", marginTop:"5px", marginBottom:"15px"}}
                id="file-upload"
                value={inputValue}
                onChange={handleInputChange}
              />
          </Grid>}
         {((VerifiedSpeakerData?.length === 0 || value === 'none' || title === "Speaker Enrolllment") || verificationData)? <Grid item style={{display:"flex", justifyContent:"space-between"}}>
            <Button
              variant="contained"
              color="secondary"
              onClick={handleClose}
              style={{backgroundColor:"lightGray"}}
             
            >
              Cancel
            </Button>
            <Button
              variant="contained"
              color="primary"
              onClick={status ? handleSpeakerEnrollmentButtonClick :  handleSpeakerVerificationButtonClick}
              disabled={isSubmitDisabled}
              style={{padding:"12px 20px", borderRadius:"5px"}}
            >
              {(enrollmentLoading || verifyLoading) && <CircularProgress color="secondary" size={24} style={{ marginRight: "10px" }} />}{buttonText}
            </Button>
          </Grid> :
           <Grid item style={{marginTop:"20px"}}>
            <Typography fontWeight={600} fontFamily="Noto-Bold" style={{color:"#11AF22"}}>{VerifiedSpeakerData?.pipelineResponse?.[0]?.output?.[0]?.message}</Typography>
            <Typography fontWeight={500}  fontFamily="Noto-Regular" style={{marginTop:"10px",fontSize:"16px"}}>ID: {VerifiedSpeakerData?.pipelineResponse?.[0]?.output?.[0]?.speakerId} | {VerifiedSpeakerData?.pipelineResponse?.[0]?.output?.[0]?.speakerName} | Confidence : {(VerifiedSpeakerData?.pipelineResponse?.[0]?.output?.[0]?.confidence * 100).toFixed(0)}%</Typography>
            <Grid item style={{display:"flex", justifyContent:"end", marginTop:"35px"}}>

            <Button
              variant="outlined"
              color="primary"
              onClick={handleClose}
              // style={{backgroundColor:"lightGray"}}
             
            >
              Cancel
            </Button>
            </Grid>
           
          </Grid>}
        </Grid>
      </DialogContent>
      </>
      }
    </Dialog>
  );
};

export default FileUpload;
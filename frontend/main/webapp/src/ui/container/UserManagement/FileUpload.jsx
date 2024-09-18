import React, { useRef, useState } from 'react';
import { Dialog, DialogTitle, DialogContent, Button, Grid, Typography, Box, IconButton } from '@material-ui/core';
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




const FileUpload = ({ open, handleClose, title, description, buttonText, handleAction, status,value,selectedFile,setSelectedFile,inputValue,setInputValue,audioURL,setAudioURL }) => {
  const [dragging, setDragging] = useState(false);
  const [error, setError] = useState('');
  const [isRecording, setIsRecording] = useState(false);
  const [hover, setHover] = useState(false);
  const [url, setUrl] = useState('');
  const [successState,setSuccessState] = useState(false)
  const mediaRecorderRef = useRef(null);
  const classes = useStyles();
 

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    const allowedTypes = ['audio/wav', 'audio/mpeg', 'audio/flac', 'audio/ogg']; // MIME types for WAV, MP3, FLAC, and OGG

    if (file && allowedTypes.includes(file.type)) {
      setSelectedFile(file);
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
      setSelectedFile(file);
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

  const handleButtonClick = () => {
    handleAction(selectedFile);
    console.log(selectedFile, audioURL, url, inputValue,"hello");
    
  };

  const handleFileRemove = () => {
    setSelectedFile(null);
    setAudioURL('');
    setError('');
  };

  const handleStartRecording = async () => {
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
        mediaRecorderRef.current = new MediaRecorder(stream);
        mediaRecorderRef.current.ondataavailable = (event) => {
          const audioBlob = event.data;
          const audioUrl = URL.createObjectURL(audioBlob);
          setAudioURL(audioUrl);
          setSelectedFile(null);
        };
        mediaRecorderRef.current.start();
        setIsRecording(true);
      } catch (err) {
        setError('Unable to access the microphone.');
      }
    } else {
      setError('Your browser does not support audio recording.');
    }
  };

  const handleStopRecording = () => {
    if (mediaRecorderRef.current) {
      mediaRecorderRef.current.stop(); // Stop the media recorder
  
      // Stop all tracks to ensure the microphone is disabled
      if (mediaRecorderRef.current.stream) {
        mediaRecorderRef.current.stream.getTracks().forEach((track) => track.stop());
      }
  
      setIsRecording(false); // Update the state
    }
  };

  const getRecordingIcon = () => {
    if (isRecording) {
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

  const isSubmitDisabled = !((selectedFile || audioURL || url) && inputValue);

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
       {successState ? <Grid>
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
          Speaker ID: #1111 | Speaker Name: Aamir
        </Typography>

        <Typography variant="h6" gutterBottom>
          Speaker Enrolled Successfully!
        </Typography>

        <Typography variant="body2">
          You can verify speaker using{' '}
          <Button color="primary" /* onClick={onVerify} */ style={{ textTransform: 'none' }}>
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
                id="file-upload"
                value='1111'
                disabled
              />
          </Grid>}
          <Grid item>
            {/* <span style={{color:"#555353", fontSize:"14px"}}>

            Sample File : 
            </span> */}
            {/* <a href="/path/to/sample-file.csv" download style={{ textDecoration: 'none' }}>
              <Button
                variant="outline"
                color="primary"
                startIcon={<CloudUploadIcon />}
              >
                <span style={{fontSize:"14px", color:"#483EA8"}}>Download</span>
              </Button>
            </a> */}
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
                disabled={audioURL || url}
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
              {/* {!isRecording ? (
                <>
        <img src={recording}  onClick={handleStartRecording} style={{marginTop:"10px"}} />  
        <Typography variant='body2' sx={{color:"#E7E7E7"}} className='mt-1'>Record Audio</Typography>  
        </> 
      ) : (
        <Button variant="contained" color="primary" onClick={handleStopRecording}>
          Stop Recording
        </Button>
      )} */}
      <>
      <IconButton
        onMouseEnter={() => setHover(true)}
        onMouseLeave={() => setHover(false)}
        onClick={isRecording ? handleStopRecording : handleStartRecording}
        color={isRecording ? 'secondary' : 'primary'}
        disabled={!!(selectedFile || url)}
      >
          <img src={getRecordingIcon()} alt="Recording Status" style={{ width: '30px', height: '30px' }} />
      </IconButton>
        <Typography variant='body2' sx={{color:"#E7E7E7"}} className='mt-1'>Record Audio</Typography> 
      </>
              <Grid item>
            {/* {selectedFile && (
              <Typography variant="body2" style={{ marginTop: '10px', color:"red" }}>
                Selected file: {selectedFile.name}
              </Typography>
            )} */}
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
                disabled={!!(selectedFile || audioURL)}
              />

          </Grid>
            </div>
            {/* {(selectedFile) && (
        <div style={{ marginTop: '20px',paddingLeft:"10px", display: 'flex', alignItems: 'center', justifyContent: "space-between",
          border: "1px solid #11AF22", height:"32px" }}>
          <Box>{selectedFile?.name || 'Recorded Audio'}</Box>
          <IconButton onClick={handleFileRemove} color="error" aria-label="delete">
            <DeleteIcon />
          </IconButton>
        </div>
      )}
      {(audioURL && toggling && selectedFile === null) && (
        <audio controls src={audioURL} style={{ marginTop: '10px', width:"100%" }}>
          Your browser does not support the audio element.
        </audio>
      )} */}
      {audioURL ? (
        <div style={{ marginTop: '10px', display: 'flex', alignItems: 'center',justifyContent: "space-between" }}>
          <audio controls src={audioURL} style={{ marginRight: '10px', borderRadius:"3px", width:"100%" }}>
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
          <Grid item style={{display:"flex", justifyContent:"space-between"}}>
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
              onClick={handleButtonClick}
              disabled={isSubmitDisabled}
              style={{padding:"12px 20px", borderRadius:"5px"}}
            >
              {buttonText}
            </Button>
          </Grid>
        </Grid>
      </DialogContent>
      </>
      }
    </Dialog>
  );
};

export default FileUpload;
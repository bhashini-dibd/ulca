import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, Button, Grid, Typography, Box } from '@material-ui/core';
import { CloudUpload as CloudUploadIcon } from '@material-ui/icons';
import uploadImg from '../../../assets/glossary/upload.svg';
const FileUpload = ({ open, handleClose, title, description, buttonText, handleAction }) => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [dragging, setDragging] = useState(false);
  const [error, setError] = useState('');

 

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file && file.type === 'text/csv') {
      setSelectedFile(file);
      setError('');
    } else {
      setSelectedFile(null);
      setError('Only CSV files are allowed.');
    }
  };

  const handleDrop = (event) => {
    event.preventDefault();
    setDragging(false);
    const file = event.dataTransfer.files[0];
    if (file && file.type === 'text/csv') {
      setSelectedFile(file);
      setError('');
    } else {
      setSelectedFile(null);
      setError('Only CSV files are allowed.');
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
  };

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
      <DialogTitle>
        <Box>{title}</Box>
        <Box>
            <Typography variant='body2' color="#6D6D6D">
            {description}
            </Typography>
            </Box>
      </DialogTitle>
      <DialogContent className='mb-3'>
        <Grid container spacing={2} direction="column" alignItems="start" >
          <Grid item>
            {/* <Typography>{description}</Typography> */}
            {/* <Button
              variant="contained"
              color="primary"
              startIcon={<CloudUploadIcon />}
              component="label"
            >
              Download Sample File
            </Button> */}
            <span style={{color:"#555353", fontSize:"14px"}}>

            Sample File : 
            </span>
            <a href="/path/to/sample-file.csv" download style={{ textDecoration: 'none' }}>
              <Button
                variant="outline"
                color="primary"
                startIcon={<CloudUploadIcon />}
              >
                <span style={{fontSize:"14px", color:"#483EA8"}}>Download</span>
              </Button>
            </a>
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
                accept=".csv"
                onChange={handleFileChange}
                style={{ display: 'none' }}
                id="file-upload"
              />
              <label htmlFor="file-upload" style={{ cursor: 'pointer' }}>
                {/* <CloudUploadIcon fontSize="large" /> */}
                <img src={uploadImg} alt="upload"/>
                <Typography className='mt-1'>Drag your file(s) to start uploading  </Typography>
                <Typography variant='body2' sx={{color:"#E7E7E7"}} className='mt-1'>or</Typography>
                {/* <Typography>click to browse</Typography> */}
                <Button variant="outlined" style={{color:"#1849D6", border:"1px solid #1849D6"}} component="span" className='my-3'>
                  Browse files
                </Button>
                <Typography variant='body2' sx={{color:"#E7E7E7"}}>Only support CSV files</Typography>
              </label>
              <Grid item>
            {selectedFile && (
              <Typography variant="body2" style={{ marginTop: '10px', color:"red" }}>
                Selected file: {selectedFile.name}
              </Typography>
            )}
            {error && (
              <Typography variant="body2" color="error" style={{ marginTop: '10px', color:"red" }}>
                {error}
              </Typography>
            )}
          </Grid>
            </div>
          </Grid>
          
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
              disabled={!selectedFile}
            >
              {buttonText}
            </Button>
          </Grid>
        </Grid>
      </DialogContent>
    </Dialog>
  );
};

export default FileUpload;

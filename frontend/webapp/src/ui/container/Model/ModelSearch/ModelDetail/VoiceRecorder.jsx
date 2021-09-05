// import { ReactMic } from 'react-mic';
// import {
//     Grid,
//     Typography,
//     TextField,
//     Button,
//     CardContent, Card,CardActions
// } from '@material-ui/core';
// import { withStyles } from '@material-ui/core/styles';
// import DatasetStyle from '../../../../styles/Dataset';
// import { useState, useEffect, useRef } from 'react';
// import Start from "../../../../../assets/start.svg";
// import Stop from "../../../../../assets/stopIcon.svg";
// import { CollectionsOutlined, SettingsSystemDaydreamTwoTone } from '@material-ui/icons';
// import HostedInferenceAPI from "../../../../../redux/actions/api/Model/ModelSearch/HostedInference";
// import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
    

// const AudioRecord = (props) => {
//     const { classes,modelId} = props;
//     const [recordAudio, setRecordAudio] = useState(false);
//     const [base, setBase] = useState("");
//     const [data, setData] = useState(null);



    
//     const blobToBase64 = (blob) => {
//         var reader = new FileReader();
//         reader.readAsDataURL(blob.blob); 
//         reader.onloadend = function() {
//             let base64data = reader.result;      
//             setBase(base64data)
//         }
                
//     };
//     const handleStop =  (data) =>{
//         setData(data.blobURL)
//         blobToBase64(data)
//     }

   

//     const handleCompute = () => {
//         const apiObj = new HostedInferenceAPI(modelId,base,"asr",true);
//         fetch(apiObj.apiEndPoint(), {
//             method: 'POST',
//             headers: apiObj.getHeaders().headers,
//             body: JSON.stringify(apiObj.getBody())
//         }).then(async resp => {
//             let rsp_data = await resp.json();
//             if (resp.ok) {
//                 if (rsp_data.hasOwnProperty('translation') && rsp_data.translation) {
//                     // setTarget(rsp_data.translation.output[0].target)
//                     //   setTarget(rsp_data.translation.output[0].target.replace(/\s/g,'\n'));
//                     // setTranslationState(true)
//                 }
//             } else {
//                 // setSnackbarInfo({
//                 //     ...snackbar,
//                 //     open: true,
//                 //     message: "The model is not accessible currently. Please try again later",
//                 //     variant: 'error'
//                 // })
//                 // Promise.reject(rsp_data);
//             }
//         }).catch(err => {
//             console.log(err)
//             // setSnackbarInfo({
//             //     ...snackbar,
//             //     open: true,
//             //     message: "The model is not accessible currently. Please try again later",
//             //     variant: 'error'
//             // })
//         })
//     };

//     const handleData = (data) =>{
//     }

//     const handleClick = (value) =>{
//         setRecordAudio(value)
//     }
// return (

//     <Card className={classes.asrCard}>

//     <Grid container className={classes.cardHeader}>
//                 <Typography variant='h6' className={classes.titleCard}>Hosted inference API {< InfoOutlinedIcon className={classes.buttonStyle} fontSize="small" color="disabled" />}</Typography>
//         </Grid>
//                         <CardContent>
//         {recordAudio ?<div className={classes.center}><img src={Stop} alt="" onClick={()=>handleClick(false)} style={{cursor:"pointer"}}/> </div>:
//         <div className={classes.center}><img src={Start} alt ="" onClick={()=>handleClick(true)} style={{cursor:"pointer"}}/> </div>
//         }

// <div className={classes.center}><Typography style = {{height:"12px"}}variant="caption">{recordAudio ? "Recording..." : ""}</Typography> </div>
//         <div style={{display:"none"}}>
//     <ReactMic 
//     record={recordAudio}
//     visualSetting= "none"
//     onStop={handleStop}
//     onData={handleData}
//     strokeColor="#000000"
//     channelCount={1}
//     sampleRate={16000}
//     mimeType="audio/wav" 
//     backgroundColor="#FF4081" />
//     </div>
//     <div  className={classes.centerAudio}>
//   <audio  src={ data } controls id="sample" >
//       </audio>
//       </div>
  
//                         </CardContent>
//                         <CardActions style={{justifyContent:"flex-end",paddingRight:"20px"}}>
//                         <Button
//                         color="primary"
//                         variant="contained"
//                         size={'small'}

//                         onClick={()=>handleCompute()}
//                     >
//                         Convert
//                     </Button>
//       </CardActions>
//                     </Card>
                    
  
// )
// }

// export default  withStyles(DatasetStyle)(AudioRecord);


import { ReactMic } from 'react-mic';
import {
    Grid,
    Typography,
    TextField,
    Button,
    CardContent, Card,CardActions
} from '@material-ui/core';
import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { useState, useEffect, useRef } from 'react';
import Start from "../../../../../assets/start.svg";
import Stop from "../../../../../assets/stopIcon.svg";
import { CollectionsOutlined, SettingsSystemDaydreamTwoTone } from '@material-ui/icons';
import HostedInferenceAPI from "../../../../../redux/actions/api/Model/ModelSearch/HostedInference";
import InfoOutlinedIcon from "@material-ui/icons/InfoOutlined";
import AudioReactRecorder, { RecordState } from 'audio-react-recorder'; 

const AudioRecord = (props) => {
    const { classes,modelId} = props;
    const [recordAudio, setRecordAudio] = useState("");
    const [base, setBase] = useState("");
    const [data, setData] = useState("");



    const blobToBase64 = (blob) => {
        var reader = new FileReader();
        reader.readAsDataURL(blob.blob); 
        reader.onloadend = function() {
            let base64data = reader.result;      
            setBase(base64data)
        }
                
    };


    
 

   

    const handleCompute = () => {
        props.handleApicall(modelId,base,"asr",true)
        // const apiObj = new HostedInferenceAPI(modelId,base,"asr",true);
        // fetch(apiObj.apiEndPoint(), {
        //     method: 'POST',
        //     headers: apiObj.getHeaders().headers,
        //     body: JSON.stringify(apiObj.getBody())
        // }).then(async resp => {
        //     let rsp_data = await resp.json();
        //     if (resp.ok) {
        //         if (rsp_data.hasOwnProperty('translation') && rsp_data.translation) {
        //             // setTarget(rsp_data.translation.output[0].target)
        //             //   setTarget(rsp_data.translation.output[0].target.replace(/\s/g,'\n'));
        //             // setTranslationState(true)
        //         }
        //     } else {
        //         // setSnackbarInfo({
        //         //     ...snackbar,
        //         //     open: true,
        //         //     message: "The model is not accessible currently. Please try again later",
        //         //     variant: 'error'
        //         // })
        //         // Promise.reject(rsp_data);
        //     }
        // }).catch(err => {
        //     console.log(err)
        //     // setSnackbarInfo({
        //     //     ...snackbar,
        //     //     open: true,
        //     //     message: "The model is not accessible currently. Please try again later",
        //     //     variant: 'error'
        //     // })
        // })
    };

    const handleStart = (data) =>{
        setData(null)
        setRecordAudio(RecordState.START)
    }

    const handleStop = (value) =>{

        setRecordAudio(RecordState.STOP)
    }

    const onStop = (data) =>{
        setData(data.url)
        setBase(blobToBase64(data))
    }

return (

    <Card className={classes.asrCard}>

    <Grid container className={classes.cardHeader}>
                <Typography variant='h6' className={classes.titleCard}>Hosted inference API {< InfoOutlinedIcon className={classes.buttonStyle} fontSize="small" color="disabled" />}</Typography>
        </Grid>
                        <CardContent>
        {recordAudio==="start" ?<div className={classes.center}><img src={Stop} alt="" onClick={()=>handleStop()} style={{cursor:"pointer"}}/> </div>:
        <div className={classes.center}><img src={Start} alt ="" onClick={()=>handleStart()} style={{cursor:"pointer"}}/> </div>
        }

<div className={classes.center}><Typography style = {{height:"12px"}}variant="caption">{recordAudio==="start" ? "Recording..." : ""}</Typography> </div>
       
<div style={{display:"none"}}>
 <AudioReactRecorder state={recordAudio} onStop={onStop} style ={{display:"none"}}/>

      </div>
      <div  className={classes.centerAudio}>
      {data ? <audio  src={ data } controls id="sample" >
       </audio>
       :<audio  src={ "test" } controls id="sample" >
       </audio>}
       </div>
  
                        </CardContent>
                        <CardActions style={{justifyContent:"flex-end",paddingRight:"20px"}}>
                        <Button
                        color="primary"
                        variant="contained"
                        size={'small'}
                        disabled= {data ? false:true}
                        onClick={()=>handleCompute()}
                    >
                        Convert
                    </Button>
      </CardActions>
                    </Card>
                    
  
)
}

export default  withStyles(DatasetStyle)(AudioRecord);



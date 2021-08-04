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
import { useReactMediaRecorder } from "react-media-recorder";
    

const AudioRecord = (props) => {
    const { classes} = props;
    const {
        status,
        startRecording,
        stopRecording,
        mediaBlobUrl,
      } = useReactMediaRecorder({ video: true });
    const [recordAudio, setRecordAudio] = useState(false);
    const [data, setData] = useState(null);

    const handleStop =  (data) =>{
        console.log(typeof data.blobURL)
        // var textPromise = data.text();
        // console.log("((((((((((((((((((((",textPromise)
        // data.text().then(text => console.log("==========",text)/* do something with the text */);
//         var reader = new FileReader();
// reader.onload = function() {
//     alert(reader.data);
// }
// reader.readAsText(data);

// console.log(reader)
        setData(data.blobURL)
    }

    const handleCompute = () => {
        const apiObj = new HostedInferenceAPI();
        fetch(apiObj.apiEndPoint(), {
            method: 'POST',
            headers: apiObj.getHeaders().headers,
            body: JSON.stringify(apiObj.getBody())
        }).then(async resp => {
            let rsp_data = await resp.json();
            if (resp.ok) {
                if (rsp_data.hasOwnProperty('translation') && rsp_data.translation) {
                    // setTarget(rsp_data.translation.output[0].target)
                    //   setTarget(rsp_data.translation.output[0].target.replace(/\s/g,'\n'));
                    // setTranslationState(true)
                }
            } else {
                // setSnackbarInfo({
                //     ...snackbar,
                //     open: true,
                //     message: "The model is not accessible currently. Please try again later",
                //     variant: 'error'
                // })
                // Promise.reject(rsp_data);
            }
        }).catch(err => {
            console.log(err)
            // setSnackbarInfo({
            //     ...snackbar,
            //     open: true,
            //     message: "The model is not accessible currently. Please try again later",
            //     variant: 'error'
            // })
        })
    };

    const handleData = (data) =>{
       
console.log(data)
        
    }

    const handleClick = (value) =>{

        setRecordAudio(value)
    }

    // blobToBase64(blob) {
    //     const reader = new FileReader();
    //     reader.readAsDataURL(blob);
    //     return new Promise(resolve => {
    //         reader.onloadend = () => {
    //             resolve(reader.result);
    //         };
    //     });
    // };


    console.log(data)
return (

    <Card className={classes.asrCard}>


                        <CardContent>
        {recordAudio ?<div className={classes.center}><img src={Stop} onClick={()=>handleClick(false)} style={{cursor:"pointer"}}/> </div>:
        <div className={classes.center}><img src={Start} onClick={()=>handleClick(true)} style={{cursor:"pointer"}}/> </div>
        }
        <div style={{display:"none"}}>
    <ReactMic 
    record={recordAudio}
    visualSetting= "none"
    onStop={handleStop}
    onData={handleData}
    strokeColor="#000000"
    backgroundColor="#FF4081" />
    </div>
    <div  className={classes.center}>
  <audio  controls id="sample" >
  <source src={ data } id="sample"/>
      </audio>
      </div>
  
                        </CardContent>
                        <CardActions style={{justifyContent:"flex-end",paddingRight:"20px"}}>
                        <Button
                        color="primary"
                        variant="contained"
                        size={'small'}

                        onClick={()=>handleCompute()}
                    >
                        Convert
                    </Button>
      </CardActions>
                    </Card>
  
)
}

export default  withStyles(DatasetStyle)(AudioRecord);
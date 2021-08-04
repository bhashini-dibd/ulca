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
    

const AudioRecord = (props) => {
    const { classes,modelId} = props;
    const [recordAudio, setRecordAudio] = useState(false);
    const [base, setBase] = useState("");
    const [data, setData] = useState(null);



    const blobToBase64 = (blob) => {
        
        var reader = new FileReader();
        reader.readAsDataURL(blob.blob); 
        var base64data = ""
        reader.onloadend = function() {
            base64data = reader.result;           
            setBase(base64data)
        }
                return base64data;
    };
    const handleStop =  (data) =>{
        setData(data.blobURL)
        blobToBase64(data)
        
    }

   

    const handleCompute = () => {
        debugger
        const apiObj = new HostedInferenceAPI(modelId,base,"asr",true);
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
    }

    const handleClick = (value) =>{
        setRecordAudio(value)
    }

    console.log(base)
return (

    <Card className={classes.asrCard}>

    <Grid container className={classes.cardHeader}>
                <Typography variant='h6' className={classes.hosted}>Hosted inference API {< InfoOutlinedIcon className={classes.buttonStyle} fontSize="small" color="disabled" />}</Typography>
        </Grid>
                        <CardContent>
        {recordAudio ?<div className={classes.center}><img src={Stop} onClick={()=>handleClick(false)} style={{cursor:"pointer"}}/> </div>:
        <div className={classes.center}><img src={Start} onClick={()=>handleClick(true)} style={{cursor:"pointer"}}/> </div>
        }

<div className={classes.center}><Typography style = {{height:"12px"}}variant="caption">{recordAudio ? "Recording..." : ""}</Typography> </div>
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
  <audio  src={ data } controls id="sample" >
  
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
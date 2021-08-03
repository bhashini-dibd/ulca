import { ReactMic } from 'react-mic';
import { useState, useEffect, useRef } from 'react';
import Start from "../../../../../assets/start.svg";
import Stop from "../../../../../assets/stopIcon.svg";
import { CollectionsOutlined, SettingsSystemDaydreamTwoTone } from '@material-ui/icons';

import { useReactMediaRecorder } from "react-media-recorder";
    

const AudioRecord = (props) => {

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

   

return (
    
    <div>
        {recordAudio ?<img src={Stop} onClick={()=>handleClick(false)} style={{cursor:"pointer"}}/> :
        <img src={Start} onClick={()=>handleClick(true)} style={{cursor:"pointer"}}/> 
        }
    <ReactMic
    record={recordAudio}
    visualSetting= "none"
    onStop={handleStop}
    onData={handleData}
    strokeColor="#000000"
    backgroundColor="#FF4081" />
  <audio src={data} controls />
    </div>
)
}

export default AudioRecord;
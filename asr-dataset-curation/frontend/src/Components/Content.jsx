import React, { useState, useEffect,useRef } from "react";
import TextField from '@material-ui/core/TextField';
// import stockData from './data.json';
import { Button } from "@material-ui/core";
const useAudio = url => {
  const [audio] = useState(new Audio(url));
  const [playing, setPlaying] = useState(false);
 
  

  const toggle = () => setPlaying(!playing);

  useEffect(() => {
      playing ? audio.play() : audio.pause();
    },
    [playing]
  );

  useEffect(() => {
    audio.addEventListener('ended', () => setPlaying(false));
    return () => {
      audio.removeEventListener('ended', () => setPlaying(false));
    };
  }, []);
  

  return [playing, toggle];
};

const Content = ({ url}) => {
  const [playing, toggle] = useAudio(url);
  const [audiofile , setAudioFile] = useState()
  const [base, setBase] = useState([]);
  const [data, setData] = useState("")
  const [count, setCount] = useState(0)
  const [source, setSource] = useState()
  const [inputVisible, setInputVisible] = useState(false);
  const [inital, setInital] = useState("The TextField wrapper component is a complete form control including a label");
  const [texts, setTexts] = useState(inital);
  const audioRef = useRef()

  useEffect(() => {
    updateSong(url)
  }, [count])
  
  const updateSong = (source) => {
    let encode=source.data[count].audioContent
const sliced = encode.slice(2,-1)

 let temp =`data:audio/mpeg;base64,${sliced}`

    setSource(temp);
    if(audioRef.current){
        audioRef.current.pause();
        audioRef.current.load();
        audioRef.current.play();
    }
}


console.log(url.data[count].audioContent)
console.log(url)

fetch('https://dev-auth.ulcacontrib.org/ulca/apis/asr/v1/audio/breakdown', {
  method: 'post',
  body: JSON.stringify( ),
   headers: {
    "Content-Type": "application/json"
  }
  
})
.then(async response => {
//  let rsp_data = await response.json();
//  console.log(rsp_data)
//  setOpen(false);
//  seturl(rsp_data)
})
console.log( JSON.stringify( ))





  return (
    <div>
     
      <div style={{marginLeft:"500px",marginTop:"100px"}} >

      {/* <audio controls><source src={audioplay}></source></audio> */}
      <audio controls ref={audioRef}>
    <source src={source} type='audio/mpeg' />
</audio>
     <Button className="primary" style={{marginBottom:"50px"}} onClick={()=>{
       if(count < url.data.length -1  ){
       setCount(count+1)}}}>Next</Button>

      </div>
      <div  style={{marginLeft:"500px",marginTop:"100px"}}>
      {inputVisible ? (
        <div> <TextField
         
        id="filled-basic"  variant="filled"
          style={{ margin: 8 ,width:"500px"}}
          
          fullWidth
          margin="normal"
       // ref={inputRef} // Set the Ref
         value={texts} // Now input value uses local state
        onChange={(e) => {
          setTexts(e.target.value);
        }}
      />
      <Button onClick={() => setInputVisible(false)  }>save</Button>
      <Button onClick={() => {    setTexts(inital) 
        setInputVisible(false) }}>clear</Button>
       </div>
       
      ) : (
        //<span >{texts}</span>
        <p style={{marginLeft:"450px",}}> {url.data[count].inference}  </p>
       
      )}
     
          


         
           </div>

         
           {console.log(url.data[count].inference)} 

           
     
    </div>
  );
};

export default Content;
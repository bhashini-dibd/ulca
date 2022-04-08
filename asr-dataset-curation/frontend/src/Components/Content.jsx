import React, { useState, useEffect,useRef } from "react";
import axios from 'axios';
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

// function audioplay(){

//   return(
//     <div style={{marginLeft:"500px",marginTop:"100px"}} >
//       {/* <audio controls ref="audio"> <source src={temp} /> </audio> */}

//     {/* <audio controls><source src={temp}></source></audio> */}
//     </div>
//   )
// }


//  const Audiourl= URL.createObjectURL(data)
// console.log( Audiourl)

  // useEffect(()=>{
  //   axios.get('')
  //   .then(response => {
  //       console.log(response)
  //       setBase(response.data)
  //       // let fhg =`${url}/${data}`
  //       //  let audio=new Audio().play
  //       //   console.log("sssss",audio);
  //   })
  // })



//  const Fetchdata = () =>{
//   fetch('',{
//     method: 'GET',
//     mode: 'cors',
//     headers: {
//       "Content-type": "application/json",
//       // "Accept": "*/*",
//       // "Access-Control-Allow-Headers": "*",
//       // "Access-Control-Allow-Methods": "*",
//       // "Access-Control-Allow-Origin": "*",
//       //"Authorization": token
//     },

//    body:JSON.stringify()
//   })
//   .then(response => { console.log( response)
//     return response.json()})
//   .then(json => console.log(json))
// //  base64String.substr(base64String.indexOf(', ') + 1));

//  // var decoded = atob(encoded);
//   let fhg =`${url}/${data}`
//  let audio=new Audio(data).play
//  console.log("sssss",audio);

//base[count].audio_content
//  }
//  useEffect(() => {
//   Fetchdata()
// }, []);

console.log(url.data[count].audioContent)
console.log(url)
// let encode=url.data[count].audioContent
// const sliced = encode.slice(2,-1)

//  let temp=`data:audio/mpeg;base64,${sliced}`


// const byteCharacters = atob(encode);
// const byteNumbers = new Array(byteCharacters.length);
// for (let i = 0; i < byteCharacters.length; i++) {
//   byteNumbers[i] = byteCharacters.charCodeAt(i);
// }
// const byteArray = new Uint8Array(byteNumbers);
// const blob = new Blob([byteArray], {type: 'audio/mp3'});

// const byteCharacters = atob(temp)
//console.log(stockData.data);
//  console.log(sliced);
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

            <p style={{marginLeft:"450px",}}> {url.data[count].inference}  </p>
           {console.log(url.data[count].inference)} 

           
     
    </div>
  );
};

export default Content;
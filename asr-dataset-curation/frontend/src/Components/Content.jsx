import React, { useState, useEffect } from "react";
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
let encode=url.data[count].audioContent
let temp=`data:audio/mpeg;base64,${encode}`

// const byteCharacters = atob(temp)
//console.log(stockData.data);
 console.log(temp);
  return (
    <div>
     
      <div style={{marginLeft:"500px",marginTop:"100px"}} >

      <audio controls><source src={ temp}></source></audio>
     

     <Button className="primary" style={{marginBottom:"50px"}} onClick={()=>{
       if(count <= url.data.length   ){
       setCount(count+1)}}}>Done{count}</Button>

      </div>

            <p style={{marginLeft:"450px",}}> {url.data[count].inference}  </p>
           {console.log(url.data[count].inference)} 
      <div style={{marginLeft:"650px",marginTop:"100px"}}>
       
       
      {/* <button onClick={toggle}>{playing ? "Pause" : "Play"}</button> */}
      </div>
    </div>
  );
};

export default Content;
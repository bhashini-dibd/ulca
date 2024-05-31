import React, { useRef } from "react";
import img1 from '../assets/whyulca1.svg';
import img2 from '../assets/whyulca2.svg';
import img3 from '../assets/whyulca3.svg';
import img4 from '../assets/whyulca4.svg';
import img5 from '../assets/WhyUlca5.svg';
import img6 from '../assets/WhyUlca6.svg';
import img7 from '../assets/WhyUlca7.svg';
import img8 from '../assets/WhyUlca8.svg';
import arrowBack from '../assets/arrow_back.svg';
import arrowFront from '../assets/arrow_front.svg';
import Slider from 'react-slick';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import '../styles/VideoSection.css'
import useMedia from "../hooks/useMedia";
import { Col, Row } from "react-bootstrap";
export const HomeApproachData = [
 
  {
      id:1,
      title:'Metadata Collection',
      description:"Collect extensive metadata related to dataset for various analysis. ",
      image:img1,
  },
  {
      id:2,
      title:'Establishing Dominance',
      description:"Aiming to become the leading data repository for indian language resources. ",
      image:img2,
  },
  {
      id:3,
      title:'Curate and standardize',
      description:"Collect datasets for MT, ASR, TTS, OCR and various NLP tasks in standardized but extensible formats.",
      image:img3,
  },
  {
    id:4,
    title:'Seamless Exploration',
    description:"Simple interface to search and download datasets based on various filters.",
    image:img4,
},
{
    id:5,
    title:'Attributing Excellence',
    description:"Proper attribution for every contributor at the record level.",
    image:img5,
},
{
  id:6,
  title:'Elevating Standards',
  description:"Perform various quality checks on the submitted datasets.",
  image:img6,
},
{
  id:7,
  title:'Precision Perfected',
  description:"Trained models for language specific tasks.",
  image:img7,
},
{
  id:8,
  title:'Efficiency Unleashed',
  description:"Deduplication capability built-in",
  image:img8,
},
  // {
  //     id:1,
  //     title:'Problem',
  //     description:"Getting to define a problem is presumably one of the most intricate and vigorously dismissed stages in the data analytics.",
  //     image:Soln2,
  // },
  // {
  //     id:1,
  //     title:'Problem',
  //     description:"Getting to define a problem is presumably one of the most intricate and vigorously dismissed stages in the data analytics.",
  //     image:Soln3,
  // },

]

 const cardData = [
  { title: "Card 1", description: "This is card 1" },
  { title: "Card 2", description: "This is card 2" },
  { title: "Card 3", description: "This is card 3" },
  { title: "Card 4", description: "This is card 4" },
  { title: "Card 5", description: "This is card 5" },
];
const VideoSection = () => {
  const sliderRef = useRef(null);
  const isMobile = useMedia("(max-width:600px)");
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 4.2,
    slidesToScroll: 1,
    arrows: false,
    easing: 'swing',
    // customPaging: function (i) {
    //   return <div className="">{i + 1}</div>;
    // },
    // dotsClass: "slick-dots slick-thumb",
    responsive: [
      {
        breakpoint: 2600,
        settings: {
          slidesToShow: 6.2,
          slidesToScroll: 1,
          infinite: true,
          dots: false
        }
      },
      {
        breakpoint: 2024,
        settings: {
          slidesToShow: 5.2,
          slidesToScroll: 1,
          infinite: true,
          dots: false
        }
      },
      {
        breakpoint: 1804,
        settings: {
          slidesToShow: 4.2,
          slidesToScroll: 1,
          infinite: true,
          dots: false
        }
      },
      {
        breakpoint: 1354,
        settings: {
          slidesToShow: 3.2,
          slidesToScroll: 1,
          infinite: true,
          dots: false
        }
      },
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 1,
          infinite: true,
          dots: false
        }
      },
      {
        breakpoint: 800,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1
        }
      }
    ]
  };
  function SampleNextArrow(props) {
    const { className, style, onClick } = props;
    return (
      <div
        className={className}
        style={{ ...style, display: "block", height:"100%",fontSize:"0px", width:"180px", position:"absolute", right:"-65px" }}
        onClick={onClick}
      />
    );
  }

  function SamplePrevArrow(props) {
    const { className, style, onClick } = props;
    return (
      <div
        className={className}
        style={{ ...style, display: "block", height:"30px", width:"40px", borderRadius:"50%", zIndex:"1" }}
        onClick={onClick}
      />
    );
  }
  return (
    <>
    <div className="VideoBoxSection">
    <div className="container mt-3">
      <div
        className="text-center  overviewHeading"
        style={{
          fontSize: "36px",
          fontWeight: 600,
          fontFamily: "Noto-Bold",
          letterSpacing: "1px",
          marginBottom:"40px"
        }}
      >
       Why <span style={{color:"#2947A3"}}>ULCA</span> 
      </div>
      <div
        className="display-6 text-center"
        style={{
          fontFamily: "Noto-Regular",
          fontSize: "16px",
          fontWeight: 400,
          lineHeight: "21.79px",
          marginBottom:"10px"
        }}
      >
        Your premier hub for Indian language resources, providing curated datasets and enhanced <br /> language-specific tasks for cutting-edge linguistic innovation and research.
      </div>
      
    </div>
    <div className="carousel-container mt-4" >
    {/* <Row> */}
        {/* <Col xs={12} md={6} lg={3} className="w-100"> */}
          <Slider {...settings} ref={sliderRef} className="m-0 p-0">
            {HomeApproachData.map((data) => (
              <div className="WhyUlcaSectionBox" data-aos="fade-out">
                <div className="WhyUlcaImgSection">

                <img src={data?.image} alt="Icon" />
                </div>
                <div className="WhyUlcaHeading" style={{color:"white"}}>{data.title}</div>
                <div className="WhyUlcaPara" style={{color:"white"}}>{data.description}</div>
              </div>
            ))}
          </Slider>
          <div className="carousel-navigation">
        <button onClick={() => sliderRef.current.slickPrev()}><img src={arrowBack} width="21px"/></button>
        <button onClick={() => sliderRef.current.slickNext()}><img src={arrowFront} width="15px"/></button>
      </div>
        {/* </Col> */}
      {/* </Row> */}
  </div>
  </div>
  </>
  );
};

export default VideoSection;

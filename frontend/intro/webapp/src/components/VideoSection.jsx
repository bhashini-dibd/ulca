import React, { useRef } from "react";
import img1 from '../assets/whyulca1.svg';
import img2 from '../assets/whyulca2.svg';
import img3 from '../assets/whyulca3.svg';
import img4 from '../assets/whyulca4.svg';
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
      title:'Identify Problem',
      description:"We meticulously analyze business challenges and data gaps to pinpoint the specific problem areas in your business. Through comprehensive assessments, we understand the nuances of the issues and their impact on business objectives.",
      image:img1,
  },
  {
      id:1,
      title:'Preparation',
      description:"We strategize and outline a data-driven plan, identifying the necessary resources and technologies. We build a very strong foundational data layer for your business that sets the stage to efficiently tackle the upcoming challenges through effective analytics and AI implementation.",
      image:img1,
  },
  {
      id:1,
      title:'Prototype',
      description:"We develop prototypes and models to test potential solutions and gauge their effectiveness. By iteratively refining prototypes based on feedback, we ensure the alignment of proposed solutions with your business requirements. ",
      image:img1,
  },
  {
      id:1,
      title:'Evaluation',
      description:"GWC lets you experience and rigorously evaluate the performance of analytics models against predefined success metrics. We gather insights from evaluations to fine-tune algorithms, ensuring they align with your evolving business needs and objectives. ",
      image:img1,
  },
  {
      id:1,
      title:'Kick-Start',
      description:"This stage involves the Kick-Start the partnership for Data & AI solutions. Discuss the partnership model, objectives, targets and road map. GWC will be an extended arm for you’re your business with laser focused approach on ROI.",
      image:img1,
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
        className="text-center mb-3 overviewHeading"
        style={{
          fontSize: "36px",
          fontWeight: 600,
          fontFamily: "Inter-Bold",
          letterSpacing: "1px",
        }}
      >
        A Quick Overview of the ULCA
      </div>
      <div
        className="display-6 text-center"
        style={{
          fontFamily: "OS-Regular",
          fontSize: "16px",
          fontWeight: 400,
          lineHeight: "24px",
        }}
      >
        Get ready for a swift introduction to ULCA! Our video provides a quick
        overview, highlighting the key features
        <br />
        and benefits of ULCA's platform.
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

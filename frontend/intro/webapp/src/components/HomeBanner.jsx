import React from "react";
import "../styles/Home.css";

import Slider from "react-slick";
import { Col, Container, Row } from "react-bootstrap";
import dataset from "../img/dataset.svg";
import model from "../img/model.svg";
import benchmark from "../img/benchmark.svg";

// import thumbsUp from '../../assets/icons/thumbsUp.png'

// const DatasetData = [
//   {
//     heading: "Open Source",
//     icon: dataset,
//     title1: "Dataset",
//     title2: "Language Datasets",
//   },
//   {
//     heading: "Transparent",
//     icon: model,
//     title1: "Models",
//     title2: "Language Specific Tasks",
//   },
//   {
//     heading: "Inclusive",
//     icon: benchmark,
//     title1: "Benchmark",
//     title2: "Open Benchmarking",
//   },
// ];

const HomeBanner = () => {
  var settings = {
    dots: true,
    infinite: true,
    autoplay: true,
    arrows: true,
    autoplaySpeed: 3000,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
          initialSlide: 0,
          infinite: true,
          dots: true,
        },
      },
      {
        breakpoint: 600,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
          initialSlide: 0,
          dots: true,
          arrows: false,
        },
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
          dots: true,
          arrows: true,
        },
      },
    ],
  };

  return (
    <Container fluid className="p-0">
      <Row className="SliderSection">
        <Col md={12}>
          <Slider {...settings}>
            <div className="Homesection">
              {/* <div className="content1">
                <div className="Header__heading">
                  <div className="Header__heading2">
                    UNIVERSAL LANGUAGE CONTRIBUTION API
                  </div>
                  <b className="Bannerheading">
                    Empowering Indian Languages with AI Technologies
                  </b>
                  <div className="Header__heading2">
                    ULCA is a standard API and open scalable data platform
                    (supporting various types of datasets) for Indian language
                    datasets and models.
                  </div>
                </div>
              </div> */}
            </div>
            <div className="Homesection homeBanner2">
              <div className="content1">
                {/* <div className="Header__heading Carousel2BoxHeading">
                  {DatasetData.map((data) => (
                    <>
                      <div>
                        <div className="mb-2">{data?.heading}</div>
                        <div className="Carousel2Box">
                          <img src={data?.icon} className="Carousel2Img" />
                          <div className="Carousel2Title1">{data?.title1}</div>
                          <div className="Carousel2Title2">{data?.title2}</div>
                        </div>
                      </div>
                    </>
                  ))}

              
                </div> */}

                <div className="Header__buttonContainer">
                  <a
                    href="https://app.swaggerhub.com/apis/ulca/ULCA/0.7.0"
                    target="_blank"
                  >
                    {" "}
                    <div className="Header__buttonTxt">Get the API</div>
                  </a>
                </div>
              </div>
              {/* <div className={styles.rectangleParent}>
                    <div className={styles.frameChild} />
                    <div className={styles.frameItem} />
                  </div> */}
            </div>
          </Slider>
        </Col>
      </Row>
    </Container>
  );
};

export default HomeBanner;

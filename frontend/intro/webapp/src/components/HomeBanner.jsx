import React from "react";
import "../styles/Home.css";

import Slider from "react-slick";
import { Breadcrumb, Col, Container, Row } from "react-bootstrap";
import bannerImg from "../assets/bannerImg1.png";


// import thumbsUp from '../../assets/icons/thumbsUp.png'


const HomeBanner = () => {
 
  return (
    <Container fluid className="p-0">
      <Row className="SliderSection">
        <Col md={12}>
       
            <div className="ULCA__Homesection">
            <Container style={{height:"100%"}}>
                <Row style={{height:"100%"}}>
                    <Col md={6} className="ULCA__banner-content">
                      <div>

                        <div className="ULCA__bannerHeading pb-3">Empowering Indian Languages
with AI Technologies</div>
                        <p className="ULCA__bannerDesc">ULCA is a standard API and open scalable data platform supporting various types of datasets for Indian languages datasets and models.</p>
                      </div>
                      <div>

                        <Breadcrumb className="ULCA__breadcrumb">
                            <Breadcrumb.Item href="https://bhashini.gov.in/" style={{color:"#F1F1F1"}}><span style={{color:"#F1F1F1"}}>Home</span></Breadcrumb.Item>
                            <Breadcrumb.Item href="https://bhashini.gov.in/sanchalak" ><span style={{color:"#F1F1F1"}}>Sanchalak</span></Breadcrumb.Item>
                            <Breadcrumb.Item active style={{color:"#F1F1F1"}}>ULCA</Breadcrumb.Item>
                        </Breadcrumb>
                      </div>
                    </Col>
                    <Col md={6} className="d-flex justify-content-center align-items-center ULCA__bannerImg">
                        <img src={bannerImg} className="img-fluid" alt="Responsive image" />
                    </Col>
                </Row>
            </Container>
            </div>
          
          {/* </Slider> */}
        </Col>
      </Row>
    </Container>
  );
};

export default HomeBanner;

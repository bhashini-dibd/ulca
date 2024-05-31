import React from "react";
import "../styles/Contact.css";
import ArrowRight from '../assets/Arrow-white.svg'

import { Breadcrumb, Button, Col, Container, Row } from "react-bootstrap";
import bannerImg from "../assets/bannerImg1.png";
const Contactus = () => {
  return (
    <Container fluid className="p-0">
    <Row className="SliderSection">
      <Col md={12}>
     
          <div className="ContactUs__Banner">
          <Container style={{height:"100%"}}>
              <Row style={{height:"100%"}}>
                  <Col md={6} className="Contact__banner-content" style={{display:"flex", justifyContent:"center"}}>
                

                      <div className="Contact__bannerHeading pb-3">Need more information?</div>
                      <p className="Contact__bannerDesc">Write your concern to us and we will get back to you.</p>
                      
                    
                  </Col>
                  <Col md={6} className="d-flex justify-content-center align-items-center Contact__bannerImg">
                     <a href="#" className="ContactUs__contactButton">
                      <button className="ContactUs__contactButtonText">Contact us <img src={ArrowRight} className="ml-2"/></button>
                     </a>
                  </Col>
              </Row>
          </Container>
          </div>
        
        {/* </Slider> */}
      </Col>
    </Row>
  </Container>
  )
}

export default Contactus
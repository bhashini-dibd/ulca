import React from "react";
import "../styles/Home.css";


import { Breadcrumb, Button, Col, Container, Row } from "react-bootstrap";
import bannerImg from "../assets/bannerImg1.png";
const Contactus = () => {
  return (
    <Container fluid className="p-0">
    <Row className="SliderSection">
      <Col md={12}>
     
          <div className="ULCA__Homesection">
          <Container style={{height:"100%"}}>
              <Row style={{height:"100%"}}>
                  <Col md={6} className="ULCA__banner-content" style={{display:"flex", justifyContent:"center"}}>
                

                      <div className="ULCA__bannerHeading pb-3">Empowering Indian Languages
with AI Technologies</div>
                   
                    
                  </Col>
                  <Col md={6} className="d-flex justify-content-center align-items-center ULCA__bannerImg">
                     <Button>Contact us</Button>
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
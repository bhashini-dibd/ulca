import React from "react"; // Import React library
import FooterLogo from '../assets/footerLogo.svg'

import "./FooterNewDesign.css"; // Importing CSS file
import { Col, Container, Row } from "react-bootstrap"; // Importing components from react-bootstrap

import playStore from '../img/DownloadApp/googlePlay.png';
import appStore from '../img/DownloadApp/appStore.png'
import useMedia from "../hooks/useMedia";
import youtube from '../assets/socialmedia/youtube.svg'
import facebook from '../assets/socialmedia/facebook.svg'
import instagram from '../assets/socialmedia/instagram.svg'
import twitter from '../assets/socialmedia/twitter.svg'
import linkedin from '../assets/socialmedia/linkedin.svg'

const FooterNewDesign = () => {
  const isMobile = useMedia("(max-width:600px)");
 
  return (
    <div className="FooterNewDesignContainer pt-5">
      {" "}
      {/* Applying FooterNewDesignContainer class */}
      <Container>
        <Row>
        <Col sm={12} md={4} lg={3}>
          <div>
            <div>Digital India Bhashini Division</div>
            <p>Electronics Niketan, 6-CGO Complex, New Delhi - 110003</p>
            <div className="FooterNewDesign_logo">
              <p className="m-0">Powered By</p>
              <img src={FooterLogo}/>
            </div>
            <p>Digital India Corporation(DIC)
Ministry of Electronics & IT (MeitY)
Government of India®</p>
          </div>
        </Col>
        <Col sm={12} md={4} lg={2} className={`${isMobile ? 'mt-3' : ''}`}>
          <div>
            <div className="mb-2">Quick Links</div>
            <div>

            <li>item1</li>
            <li>item1</li>
            <li>item1</li>
            <li>item1</li>
            </div>
            </div>
        </Col>
        <Col sm={12} md={4} lg={2} className={`${isMobile ? 'mt-3' : ''}`}>
        <div>
            <div className="mb-2">Category 1</div>
            <div>

            <li>item1</li>
            <li>item1</li>
            <li>item1</li>
            <li>item1</li>
            </div>
            </div>
        </Col>
        <Col sm={12} md={4} lg={2}>
        <div>
            <div className="mb-2">Category 2</div>
            <div>

            <li>item1</li>
            <li>item1</li>
            <li>item1</li>
            <li>item1</li>
            </div>
            </div>
        </Col>
        <Col sm={12} md={4} lg={3} className={`${isMobile ? 'mt-4' : ''}`}>
          <div>
            <div>Get the Bhashini app</div>
            <div className="d-flex justify-content-start align-items-center">
              <img src={playStore} className="bhashiniapp__footer"/>
              <img src={appStore} className="bhashiniapp__footer"/>
            </div>
            <div className={`${isMobile ? 'mt-3' : ''}`}>Join Us</div>
            <div>
            <div className="d-flex justify-content-start align-items-center">
              <img src={youtube} className="bhashiniapp__footer"/>
              <img src={facebook} className="bhashiniapp__footer"/>
              <img src={instagram} className="bhashiniapp__footer"/>
              <img src={twitter} className="bhashiniapp__footer"/>
              <img src={linkedin} className="bhashiniapp__footer"/>
            </div>
            </div>
            </div>
        </Col>
        </Row>
        <hr />
        <div className="d-flex flex-column flex-md-row justify-content-between align-items-center pb-3">
             <div>© 2024 - Copyright All rights reserved.  </div>
             <div className=" Footer__documents">
              <li>Terms & Condition</li>
              <li>Privacy Policy</li>
              <li>Contact Us</li>
             </div>
            </div>
       </Container>
    </div>
  );
};

export { FooterNewDesign }; // Exporting Footer component

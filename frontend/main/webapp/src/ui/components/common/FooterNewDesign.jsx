import React from "react"; // Import React library
import FooterLogo from "../../../assets/BhashiniFooterlogo.png";
import 'bootstrap/dist/css/bootstrap.min.css';
import "../../styles/FooterNewDesign.css"; // Importing CSS file
import { Col, Container, Row } from "react-bootstrap"; // Importing components from react-bootstrap

import playStore from "../../../assets/googlePlay.png";
import appStore from "../../../assets/appStore.png";
import youtube from "../../../assets/socialmedia/youtube.svg";
import facebook from "../../../assets/socialmedia/facebook.svg";
import instagram from "../../../assets/socialmedia/instagram.svg";
import twitter from "../../../assets/socialmedia/twitter.svg";
import linkedin from "../../../assets/socialmedia/linkedin.svg";
import mailIcon from '../../../assets/mailIcon.svg'
import callIcon from '../../../assets/callIcon.svg'
import { useMediaQuery } from "@material-ui/core";
const FooterNewDesign = () => {
  const isMobile = useMediaQuery("(max-width:600px)");

  return (
    <div className="FooterNewDesignContainer pt-5">
      {" "}
      {/* Applying FooterNewDesignContainer class */}
      <Container>
        <Row>
          <Col sm={12} md={4} lg={3}>
            <div className="about_company">
            <div className="FooterNewDesign_logo mb-3">
                {/* <p className="m-0 company_address">Powered By</p> */}
                <img src={FooterLogo} />
              </div>
              <div className="company_name">
                Digital India Bhashini Division
              </div>
              <p className="company_address m-0">
                Electronics Niketan, 6-CGO Complex, <br /> New Delhi - 110003
              </p>

              {/* <p className="company_address">
                Digital India Corporation(DIC) Ministry of Electronics & IT
                (MeitY) Government of India®
              </p> */}
               <div className="FooterNewDesign_logo mt-2">
                <img src={mailIcon} className="mr-2" />
                <p className="m-0 company_address">ceo-dibd[at]digitalgov[dot]co[dot]in</p>
              </div>
              <div className="FooterNewDesign_logo">
                <img src={callIcon} className="mr-2" />
                <p className="m-0 company_address">011-24301361</p>
              </div>
            </div>
          </Col>
          <Col sm={12} md={4} lg={2} className={`${isMobile ? "mt-4" : ""}`}>
            <div>
              {/* <div className="mb-3 footerlinks__header">Quick Links</div> */}
              <div className="footerlinks__links">
                <li>
                  <a href="https://bhashini.gov.in/">Home</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/about-bhashini">About</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/services">Arpan</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/sahyogi">Sahyogi</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/career">Careers</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/bhashadaan/en/home">Bhashadaan</a>
                </li>
              </div>
            </div>
          </Col>
          <Col sm={12} md={4} lg={2} className={`${isMobile ? "mt-4" : ""}`}>
            <div>
              {/* <div className="mb-3 footerlinks__header">Category 1</div> */}
              <div className="footerlinks__links">
                <li>
                  <a href="https://bhashini.gov.in/sanchalak">Sahyogi</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/rajyam">Rajyam</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/sanchalan">Sanchalan/SEVA</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/udbhav">Udbhav/Startups</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/pravakta">Pravakta</a>
                </li>
              </div>
            </div>
          </Col>
          <Col sm={12} md={4} lg={2} className={`${isMobile ? "mt-4" : ""}`}>
            <div>
              {/* <div className="mb-3 footerlinks__header">Category 2</div> */}
              <div className="footerlinks__links">
                <li>
                  <a href="https://bhashini.gov.in/parikshan-app">Parikshan App</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/sahyogi/vatika">Model And Data Vatica</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/abhiyantrikee-Prayogshala">Abhiyantrikee</a>
                </li>
                <li>
                  <a href="https://bhashini.gov.in/avinya-prayogshala">Avinya Prayogshala</a>
                </li>
              </div>
            </div>
          </Col>

          <Col sm={12} md={4} lg={3} className={`${isMobile ? "mt-4" : ""}`}>
            <div className="bhashini__app">
              <div className="company_name">Get the Bhashini app</div>
              <div className="d-flex justify-content-start align-items-center">
               
                 <a href="https://play.google.com/store/apps/details?id=com.dibd.bhashini&pcampaignid=web_share">
                 <img src={playStore} className="bhashiniapp__footer" />
                 </a>
               <a href="https://apps.apple.com/in/app/bhashini/id1570975989"> 
               <img src={appStore} className="bhashiniapp__footer" /> </a>
              </div>
              <div>
                <div
                  className={`${
                    isMobile ? "mt-3" : "mt-2"
                  } footerlinks__header`}
                >
                  Join Us
                </div>
                <div className="d-flex justify-content-start align-items-center">
                  <a href="https://www.youtube.com/@_Bhashini"><img
                    src={youtube}
                    className="bhashiniapp__footerIcons"
                    height="24px"
                    width="24px"
                  /></a>
                  <a href="https://www.facebook.com/people/Bhashini/100093281985246/?rdid=LLWrUz7yR11swTQT&share_url=https%3A%2F%2Fwww.facebook.com%2Fshare%2F1GB5cdxEgw%2F" target="_blank"><img src={facebook} className="bhashiniapp__footerIcons" /></a>
                  <a href="https://www.instagram.com/_officialbhashini/" target="_blank"><img src={instagram} className="bhashiniapp__footerIcons" /></a>
                  <a href="https://x.com/_BHASHINI?t=UE9RHyJRZuZ9rl2_W7B_9g&s=09" target="_blank"><img src={twitter} className="bhashiniapp__footerIcons" /></a>
                  <a href="https://www.linkedin.com/company/digiital-india-bhashini-division" target="_blank"><img src={linkedin} className="bhashiniapp__footerIcons" /></a>
                </div>

              </div>
            </div>
          </Col>
        </Row>
        <hr />
        <div className="d-flex flex-column flex-md-row justify-content-between align-items-center pb-3">
          <div>© 2024 - Copyright All rights reserved. </div>
          <div className=" Footer__documents">
            <li><a href="https://bhashini.gov.in/termsAndConditions">Terms & Condition</a></li>
            <li style={{ color: "#73B8F9", display: isMobile ? 'none':"" }}>|</li>
            <li><a href="https://bhashini.gov.in/privacy-policy">Privacy Policy</a></li>
            <li style={{ color: "#73B8F9", display: isMobile ? 'none':"" }}>|</li>
            <li><a href="https://bhashini.gov.in/connect">Contact Us</a></li>
          </div>
        </div>
      </Container>
    </div>
  );
};

export { FooterNewDesign }; // Exporting Footer component
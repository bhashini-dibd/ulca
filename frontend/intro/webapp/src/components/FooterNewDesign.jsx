import React from "react"; // Import React library

import { FooterData, FootersData, socialMedia } from "./FooterNewDesignData"; // Importing data from FooterData.js
import "./FooterNewDesign.css"; // Importing CSS file
import { Col, Container, Row } from "react-bootstrap"; // Importing components from react-bootstrap
// import { Link } from "react-router-dom"; // Importing Link component from react-router-dom
// import CustomButton from "../buttonComponent/CustomButton";

import Meity from "../img/Footer/MeityBlack.svg";
import BhashiniImg from "../img/Footer/BhashiniBlack.svg";
import useMedia from "../hooks/useMedia";
import { OverlayTrigger, Tooltip } from "react-bootstrap";

const FooterNewDesign = () => {
  const isMobile = useMedia("(max-width:600px)");
  const renderTooltip = (message) => (
    <Tooltip className="navbar-tooltip">
      {message}
    </Tooltip>
  );
  return (
    <div className="FooterNewDesignContainer pt-5">
      {" "}
      {/* Applying FooterNewDesignContainer class */}
      <Container>
        {" "}
        {/* Using div component */}
        <Row>
          {FootersData.map((data) => (
            <Col xs={6} md={6} lg={data?.size} className="mb-4 ps-4 mb-md-0 ps-md-0 FooterColumn" style={{paddingLeft: "1.5rem",'@media (min-width: 768px)': {
              paddingLeft: "0"
             }}}>
              <div className="FooterNewDesignManagement">
                {" "}
                {data?.title === 'Prayog' || data?.title === "Sahyogi" || data?.title === "Sanchalak" ? <OverlayTrigger placement="top"
                  delay={{ show: 250, hide: 300 }}
                  overlay={renderTooltip(data?.tooltipData)}>
                  <div className="FooterNewDesignHeading"> {data.title}</div>
                </OverlayTrigger> : <div className="FooterNewDesignHeading"> {data.title}</div>}
                {/* Applying FooterNewDesignManagement class */}
                {" "}
                {/* Applying FooterNewDesignHeading class */}
                {data.links.map((value) => (
                  <div className="FooterNewDesignContent">
                    {" "}
                    {/* Applying FooterNewDesignContent class */}
                    {isMobile || (value.text === 'State gov' || value.text === 'Mitra' || value.text === 'Udayat' || value.text === 'Vanianuvaad' || value.text === 'Lekhaanuvaad') ? (
                      <a href={`https://bhashini.gov.in${value.link}`} target="_blank">
                        <div className="py-1 FooterNewDesignLinksColor">
                          {" "}
                          {value.text}
                        </div>
                      </a>
                    ) : (value.text == 'ULCA' || value.text == 'Chitraanuvaad' || value.text == "Anuvaad" ? (
                      <a href={value.link} target="_blank">
                        <div className="py-1 FooterNewDesignLinksColor">
                          {" "}
                          {value.text}
                        </div>
                      </a>
                    ) : <a href={`https://bhashini.gov.in${value.link}`} target="_blank">
                        <div className="py-1 FooterNewDesignLinksColor">
                          {" "}
                          {value.text}
                        </div>
                      </a>)}
                  </div>
                ))}
              </div>
            </Col>
          ))}
          <hr className="my-2 my-md-2"/>
          <div className="FooterNewDesignBottomLinks">
            <div className="FooterNewDesignBottomLinksSection">
              <span className="FooterNewDesignBottomLinksName">
                {/* <CustomButton
          className="global_btn"
          text={`Bhashadaan `}
          link={"https://bhashini.gov.in/bhashadaan/en/home"}
          target={"_blank"}
        /> */}
                <a
                  href="https://bhashini.gov.in/bhashadaan/en/home"
                  target="_blank"
                >
                  <div className="Footer_btn">Contribute to Bhashadaan</div>
                </a>
              </span>{" "}
              {/* Applying FooterNewDesignBottomLinksName class */}
            </div>
            <div className="FooterSocialMedia">
              <div className="FooterSocialMedia_Heading">Connect with us</div>
              {socialMedia.map((data) => (
                <a href={data?.link} target="_blank">
                  <img src={data?.image} className="FooterSocialMediaImg" />
                </a>
              ))}
            </div>
          </div>
        </Row>
      </Container>
      <div className="FooterNewDesignBottomLinks1">
        <Container>
          <div className="FooterNewDesignBottomLinkSeparator">
            <div>
              <span className="FooterNewDesignBottomLinks2Name">
                <div className="media">
                  <img
                    className="img-fluid u-image"
                    src={Meity}
                    alt="meity-img"
                  />
                </div>
              </span>
            </div>
            <div className="FooterNewDesignBottomPolicyTerms">
              <div className="media">
                <img
                  className="img-fluid u-image"
                  src={BhashiniImg}
                  alt="meity-img"
                />
              </div>
            </div>
          </div>
        </Container>
      </div>
      <div className="FooterCopyRight">
        <div className="FooterCopyRightHeading">
          Designed, Developed & Hosted by
        </div>
        <div className="FooterCopyRightDesc">
          Digital India Bhashini Division (DIBD)
        </div>
      </div>
    </div>
  );
};

export { FooterNewDesign }; // Exporting Footer component

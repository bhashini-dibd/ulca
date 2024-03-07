import { useMediaQuery } from "@material-ui/core";

const Footer = () => {
  const isSmallScreen = useMediaQuery("(max-width:600px)");

  return (
    <>
      <footer className="footer">
        <div className="container-fluid">
          <div className="footer-links row">
            <div className="col-md-4 footerBg bg1">
              <img
                src={`${process.env.PUBLIC_URL}/img/web.png`}
                alt="Web.png"
                className=""
              />
              <div className="">
                Web
                <br />
                <a
                  className=""
                  href="https://bhashini.gov.in/"
                  target="_self"
                  rel="noopener noreferrer"
                >
                  www.bhashini.gov.in
                </a>
              </div>
            </div>
            <div className="col-md-4 footerBg bg2">
              <img
                src={`${process.env.PUBLIC_URL}/img/email-id.png`}
                alt="email-id.png"
                className=""
              />
              <div className="">
                Mail
                <br />
                <a className="" href="mailto:ceo-dibd@digitalindia.gov.in">
                  ceo-dibd[at]digitalindia[dot]gov[dot]in
                </a>
              </div>
            </div>
            <div className="col-md-4 footerBg bg3">
              <img
                src={`${process.env.PUBLIC_URL}/img/location.png`}
                alt="location.png"
                className=""
              />
              <div className="">
                Address
                <br />
                <a className="ft-20 fw-500" href="https://www.google.com/maps/@28.5865266,77.2395194,19z?entry=ttu" target="_blank">
                  Electronics Niketan, 6, CGO Complex, Lodhi Road, New Delhi -
                  110003
                  <br />
                  <span className="ft-14">Tel: 011-24301361</span>
                </a>
              </div>
            </div>
          </div>
        </div>
        <div className="section primary-color">
          <div className="container">
            <div className="row align-items-center">
              <div className="col-md-6">
                <ul
                  className="d-block d-lg-flex link"
                  style={{ textAlign: isSmallScreen ? "center" : "" }}
                >
                  {/* <li>
                    <a href="https://bhashini.gov.in/images/Bhashini_-_Whitepaper.pdf">
                      Whitepaper
                    </a>
                  </li> */}
                  <li>
                    <a
                      className="footer-anchor-redirect-links"
                      target="_blank"
                      href="https://bhashini.gov.in/ecosystem"
                    >
                      Ecosystem
                    </a>
                  </li>
                  <li>
                    <a
                      className="footer-anchor-redirect-links"
                      href="http://bhashini.gov.in/bhashadaan"
                      target="_blank"
                      rel="noopener noreferrer"
                    >
                      {" "}
                      Join Bhasha Daan{" "}
                    </a>
                  </li>
                </ul>
              </div>
              <div className="col-md-6">
                <ul className="socialLink link d-flex justify-content-end">
                  <li>
                    <a
                      href="https://www.instagram.com/_officialbhashini/"
                      target="_self"
                    >
                      {" "}
                      <img
                        src={`${process.env.PUBLIC_URL}/img/insta.png`}
                        alt="instagram"
                      />
                    </a>
                  </li>
                  <li>
                    <a
                      href="https://www.facebook.com/profile.php?id=100093281985246"
                      target="_self"
                    >
                      <img
                        src={`${process.env.PUBLIC_URL}/img/facebook.png`}
                        alt="facebook"
                      />
                    </a>
                  </li>
                  <li>
                    <a
                      href="https://www.linkedin.com/company/96244597/admin/feed/posts/"
                      target="_self"
                    >
                      <img
                        src={`${process.env.PUBLIC_URL}/img/linkedin.png`}
                        style={{ borderRadius: "100%" }}
                        alt="linkedin"
                      />
                    </a>
                  </li>
                  <li>
                    <a href="https://twitter.com/_BHASHINI" target="_self">
                      <img
                        src={`${process.env.PUBLIC_URL}/img/tw.png`}
                        alt="twitter"
                      />
                    </a>
                  </li>
                  <li>
                    <a
                      href="https://www.kooapp.com/profile/_BHASHINI"
                      target="_self"
                    >
                      <img
                        src={`${process.env.PUBLIC_URL}/img/koo.svg`}
                        alt="twitter"
                      />
                    </a>
                  </li>
                </ul>
              </div>
            </div>
            <hr />
            <div className="row align-items-center mt-4 mb-4">
              <div className="col-md-8 col-lg-6">
                {/* <p className="lighGrey mb-0">
                  Copyright @2021 NLTM. All Rights Reserved.
                  <br /> NLTM: National Language Translation Mission
                </p> */}
              </div>
              <div className="col-md-4 col-lg-6">
                <ul className={`d-flex d-lg-flex link ${isSmallScreen ? 'justify-content-center' : 'justify-content-end '} `}>
                  {/* <li>
                    <a
                      href="https://bhashini.gov.in/en/web-information-manager"
                      target="_self"
                    >
                      Web Information Manager
                    </a>
                  </li> */}
                  <li>
                    <a
                      href="https://bhashini.gov.in/privacy-policy"
                      target="_self"
                      className="footer-anchor-redirect-links"
                    >
                      Privacy Policy
                    </a>
                  </li>
                  <li>
                    <a
                      href="https://bhashini.gov.in/terms-of-use"
                      target="_self"
                      className="footer-anchor-redirect-links"
                    >
                      {" "}
                      Terms of Use
                    </a>
                  </li>
                </ul>
              </div>
            </div>
            <div className="row align-items-center justify-content-between mt-5">
              <div className="col-md-7">
                {/* <a href="" className="tdl-logo"> */}
                  <img
                    src={`${process.env.PUBLIC_URL}/img/meity_logo.png`}
                    alt="TDIL logo"
                  />
                {/* </a> */}
              </div>
              <div className="col-md-3 text-center">
                <div className="caption mb-3">
                  <span className="lighGrey text-center ft-14">
                    Designed, Developed &amp; Hosted by
                  </span>
                  <div style={{color:"white"}}> Digital India Corporation(DIC)</div>
                </div>
                {/* <a href="" className="dg-india-logo "> */}
                  <img
                    src={`${process.env.PUBLIC_URL}/img/dg-india.png`}
                    alt="dg-india logo"
                  />
                {/* </a> */}
              </div>
            </div>
            <div className="row mt-4 pt-4">
              <div className="col-md-5">
                <p className="lighGrey text-center ft-14">
                  Technology Development for Indian Languages Programme
                </p>
              </div>
              <div className="col-md-4">
                {/* <p className="lighGrey text-center ft-14 ">
                  JavaScript must be enabled to access this site. Supports :
                  Firefox, Google Chrome, Internet Explorer 10.0+, Safari
                </p> */}
              </div>
              <div className="col-md-4">
                {/* <p className="lighGrey text-center ft-14">
                  Last reviewed and updated on:16–Jun-2021
                </p> */}
              </div>
            </div>
          </div>
        </div>
      </footer>
    </>
  );
};

export default Footer;

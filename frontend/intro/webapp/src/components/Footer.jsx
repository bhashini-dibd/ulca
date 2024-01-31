const Footer = () => {
  return (
    <>
      <footer class="footer">
        <div class="container-fluid">
          <div class="footer-links row">
            <div class="col-md-4 footerBg bg1">
              <img
                src={`${process.env.PUBLIC_URL}/img/web.png`}
                alt="Web.png"
                class=""
              />
              <div class="">
                Web
                <br />
                <a
                  class=""
                  href="https://bhashini.gov.in/"
                  target="_self"
                  rel="noopener noreferrer"
                >
                  www.bhashini.gov.in
                </a>
              </div>
            </div>
            <div class="col-md-4 footerBg bg2">
              <img
                src={`${process.env.PUBLIC_URL}/img/email-id.png`}
                alt="email-id.png"
                class=""
              />
              <div class="">
                Mail
                <br />
                <a class="" href="mailto:ceo-dibd@digitalindia.gov.in">
                  ceo-dibd[at]digitalindia[dot]gov[dot]in
                </a>
              </div>
            </div>
            <div class="col-md-4 footerBg bg3">
              <img
                src={`${process.env.PUBLIC_URL}/img/location.png`}
                alt="location.png"
                class=""
              />
              <div class="">
                Address
                <br />
                <p class="ft-20 fw-500" href="">
                  Electronics Niketan, 6, CGO Complex, Lodhi Road, New Delhi -
                  110003
                  <br />
                  <span class="ft-14">Tel: 011-24301361</span>
                </p>
              </div>
            </div>
          </div>
        </div>
        <div class="section primary-color">
          <div class="container">
            <div class="row align-items-center">
              <div class="col-md-6">
                <ul class="d-lg-flex link">
                  {/* <li>
                    <a href="https://bhashini.gov.in/images/Bhashini_-_Whitepaper.pdf">
                      Whitepaper
                    </a>
                  </li> */}
                  <li>
                    <a class="footer-anchor-redirect-links" href="https://bhashini.gov.in/en/ecosystem">Ecosystem</a>
                  </li>
                  <li>
                    <a
                      class="footer-anchor-redirect-links"
                      href="http://bhashini.gov.in/bhashadaan"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      {" "}
                      Join Bhasha Daan{" "}
                    </a>
                  </li>
                </ul>
              </div>
              <div class="col-md-6">
                <ul class="socialLink link d-flex justify-content-end">
                  <li>
                    <a href="https://www.instagram.com/_officialbhashini/" target="_self">
                      {" "}
                      <img
                        src={`${process.env.PUBLIC_URL}/img/insta.png`}
                        alt="instagram"
                      />
                    </a>
                  </li>
                  <li>
                    <a href="https://www.facebook.com/profile.php?id=100093281985246" target="_self">
                      <img
                        src={`${process.env.PUBLIC_URL}/img/facebook.png`}
                        alt="facebook"
                      />
                    </a>
                  </li>
                  <li>
                    <a href="https://www.linkedin.com/company/96244597/admin/feed/posts/" target="_self">
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
                    <a href="https://www.kooapp.com/profile/_BHASHINI" target="_self">
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
            <div class="row align-items-center mt-4 mb-4">
              <div class="col-md-8 col-lg-6">
                {/* <p class="lighGrey mb-0">
                  Copyright @2021 NLTM. All Rights Reserved.
                  <br /> NLTM: National Language Translation Mission
                </p> */}
              </div>
              <div class="col-md-4 col-lg-6">
                <ul class="d-lg-flex link justify-content-end ">
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
                      href="https://bhashini.gov.in/en/privacy-policy"
                      target="_self"
                      class="footer-anchor-redirect-links"
                    >
                      Privacy Policy
                    </a>
                  </li>
                  <li>
                    <a
                      href="https://bhashini.gov.in/en/terms-conditions"
                      target="_self"
                      class="footer-anchor-redirect-links"
                    >
                      {" "}
                      Terms of Use
                    </a>
                  </li>
                </ul>
              </div>
            </div>
            <div class="row align-items-center justify-content-between mt-5">
              <div class="col-md-7">
                <a href="" class="tdl-logo">
                  <img
                    src={`${process.env.PUBLIC_URL}/img/meity_logo.png`}
                    alt="TDIL logo"
                  />
                </a>
              </div>
              <div class="col-md-3 text-right">
                <div class="caption mb-3"><span class="lighGrey text-center ft-14">Designed, Developed &amp; Hosted by</span><a href=""> Digital India Corporation(DIC)</a></div>
                <a href="" class="dg-india-logo ">
                  <img
                    src={`${process.env.PUBLIC_URL}/img/dg-india.png`}
                    alt="dg-india logo"
                  />
                </a>
              </div>
            </div>
            <div class="row mt-4 pt-4">
              <div class="col-md-5">
                <p class="lighGrey text-center ft-14">
                  Technology Development for Indian Languages Programme
                </p>
              </div>
              <div class="col-md-4">
                {/* <p class="lighGrey text-center ft-14 ">
                  JavaScript must be enabled to access this site. Supports :
                  Firefox, Google Chrome, Internet Explorer 10.0+, Safari
                </p> */}
              </div>
              <div class="col-md-4">
                {/* <p class="lighGrey text-center ft-14">
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

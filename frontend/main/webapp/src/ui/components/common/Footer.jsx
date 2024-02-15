// import 'bootstrap/dist/css/bootstrap.min.css';
// import "../../styles/css/style.css"
import email from "../../styles/img/email-id.png";
import web from "../../styles/img/web.png";
import location from "../../styles/img/location.png";
import facebook from "../../styles/img/facebook.png";
import tw from "../../styles/img/tw.png";
import insta from "../../styles/img/insta.png";
import tdil from "../../styles/img/tdil.png";
import dg from "../../styles/img/dg-india.png";
import { Grid, Link, Divider, Typography } from "@material-ui/core";
import { withStyles } from "@material-ui/core";
import FooterStyles from "../../styles/Footer";

import "../../styles/css/style.css";
import { translate } from "../../../assets/localisation";
const Footer = (props) => {
  const { classes } = props;
  return (
    <footer>
      <Grid container>
        <Grid
          item
          xs={12}
          sm={4}
          md={4}
          lg={4}
          xl={4}
          className={classes.grid}
          style={{ width: "100vw" }}
        >
          <img src={web} alt="" className={classes.image} />
          <div>
            <Typography variant="body2">{translate("label.web")}</Typography>
            <Typography variant="body1">
              <Link
                className={classes.link}
                color="#16337B"
                // href="https://www.meity.gov.in"
                href="https://www.bhashini.gov.in"
                target="_self"
                rel="noopener noreferrer"
              >
                {translate("link.meity")}
              </Link>
            </Typography>
          </div>
        </Grid>
        <Grid item xs={0} sm={4} md={4} lg={4} xl={4} className={classes.grid2}>
          <img src={email} alt="" className={classes.image} />
          <div className="">
            <Typography variant="body2">{translate("label.mail")}</Typography>
            <Typography variant="body1">
              <Link
                className={classes.link}
                href="mailto:ceo-dibd@digitalindia.gov.in"
              >
                {translate("link.contactUs")}
              </Link>
            </Typography>
          </div>
        </Grid>
        <Grid
          item
          xs={12}
          sm={4}
          md={4}
          lg={4}
          xl={4}
          className={classes.grid3}
        >
          <img src={location} alt="" className={classes.image} />
          <div className="">
            <Typography variant="body2">
              {translate("label.address")}
            </Typography>
            <Typography variant="body1" className={classes.link} href="">
              {translate("label.addressInfo")}
            </Typography>
          </div>
        </Grid>
      </Grid>
      <div className="section primary-color">
        <div className={classes.parentDiv}>
          <Grid container className={classes.container}>
            <Grid container style={{ alignItems: "center" }}>
              <Grid item xs={12} sm={12} md={6} lg={4} xl={6}>
                <ul className={classes.bhasini}>
                  {/* <Typography variant="body2">
                    <li>
                      <Link color="while" href="https://bhashini.gov.in/images/Bhashini_-_Whitepaper.pdf">
                        {translate("link.whitePaper")}
                      </Link>
                    </li>
                  </Typography> */}

                  <Typography style={{ fontSize: "1rem" }}>
                    <li>
                      <Link
                        color="while"
                        href="https://bhashini.gov.in/ecosystem"
                      >
                        {translate("link.ecosystem")}
                      </Link>
                    </li>
                  </Typography>
                  <Typography variant="body1" style={{ fontSize: "1rem" }}>
                    <li>
                      <Link
                        color="while"
                        href="http://bhashini.gov.in/bhashadaan"
                      >
                        {translate("link.joinBhashaDaan")}{" "}
                      </Link>
                    </li>
                  </Typography>
                  {/* <Typography variant="body1" >
                    <li>
                      <Link color="while" href="">
                        <div className="join">
                          <Link
                            className="bh-btn-primary"
                            color="while"
                            href="https://bhashini.gov.in/bhashadaan"
                            target="_self"
                            rel="noopener noreferrer"
                          >
                            {" "}
                            {translate("link.joinBhashaDaan")}{" "}
                            
                          </Link>
                        </div>
                      </Link>
                    </li>
                  </Typography> */}
                </ul>
              </Grid>
              <Grid item xs={12} sm={12} md={6} lg={8} xl={6}>
                <ul className={classes.social}>
                  <Typography variant="body1">
                    <li>
                      <Link
                        href="https://www.facebook.com/profile.php?id=100093281985246"
                        target="_self"
                      >
                        <img src={facebook} alt="facebook" />
                      </Link>
                    </li>
                  </Typography>
                  <Typography variant="body1">
                    <li>
                      <Link href="https://twitter.com/_BHASHINI" target="_self">
                        <img src={tw} alt="twitter" />
                      </Link>
                    </li>
                  </Typography>
                  <Typography variant="body1">
                    <li>
                      <Link
                        href="https://www.instagram.com/_officialbhashini/"
                        target="_self"
                      >
                        {" "}
                        <img src={insta} alt="instagram" />
                      </Link>
                    </li>
                  </Typography>
                  <Typography variant="body1">
                    <li>
                      <Link
                        href="https://www.linkedin.com/company/96244597/admin/feed/posts/"
                        target="_self"
                      >
                        {" "}
                        <img
                          src={process.env.PUBLIC_URL + "/linkedin.png"}
                          style={{ borderRadius: "100%" }}
                          alt="linkedin"
                        />
                      </Link>
                    </li>
                  </Typography>
                  <Typography variant="body1">
                    <li>
                      <Link
                        href="https://www.kooapp.com/profile/_BHASHINI"
                        target="_self"
                      >
                        {" "}
                        <img
                          src={process.env.PUBLIC_URL + "/koo.svg"}
                          alt="instagram"
                        />
                      </Link>
                    </li>
                  </Typography>
                </ul>
              </Grid>
            </Grid>
            <Divider
              style={{
                width: "100%",
                marginTop: "20px",
                background: "#4E6079",
                marginBottom: "15px",
              }}
            />

            <Grid item xs={12} sm={6} md={6} lg={9} xl={6}>
              <br />
              {/* <Typography variant="body1" className="lighGrey mb-0">
                {translate("label.copyright")}
                <br />
                {translate("label.nltm")}
              </Typography> */}
            </Grid>
            <Grid item xs={12} sm={6} md={6} lg={3} xl={3}>
              <ul className={classes.info}>
                <Typography variant="body2">
                <li>
                    <Link
                      color="while"
                      href="https://bhashini.gov.in/privacy-policy"
                      target="_self"
                    >
                      {translate("link.privacyPolicy")}
                    </Link>
                  </li>
                  </Typography>
                  {" "}
                  {/* <li>
                    <Link
                      color="while"
                      href="https://bhashini.gov.in/privacy-policy"
                      target="_self"
                    >
                      {translate("link.privacyPolicy")}
                    </Link>
                  </li>
                </Typography>
                {/* <Typography variant="body1">
                  <li>
                    <Link
                      color="white"
                      href="https://bhashini.gov.in/privacy-policy"
                      target="_self"
                    >
                      {translate("link.privacyPolicy")}
                    </Link>
                  </li>
                </Typography> */}
                <Typography variant="body2">
                  <li>
                    <Link
                      color="white"
                      href="https://bhashini.gov.in/terms-of-use"
                      target="_self"
                    >
                      {" "}
                      {translate("link.termsAndConditions")}
                    </Link>
                  </li>
                </Typography>
              </ul>
            </Grid>

            <Grid item xs={12} sm={6} md={6} lg={9} xl={6}>
              <br />
              {/* <Typography variant="body1" className="lighGrey mb-0">
                {translate("label.copyright")}
                <br />
                {translate("label.nltm")}
              </Typography> */}
            </Grid>
            <Grid
              item
              xs={12}
              sm={6}
              md={6}
              lg={3}
              xl={3}
              className={classes.mobileDesigned}
            >
              <div className={classes.infoNew} style={{ display: "flex" }}>
                <Typography
                  variant="body2"
                  className="lighGrey mb-0"
                  style={{
                    display: "flex",
                    justifyContent: "flex-end",
                    lineHeight: "14px",
                  }}
                >
                  {" "}
                  Designed, Developed & Hosted by
                </Typography>
              </div>
              <div
                className={classes.infoNew}
                style={{ marginTop: "5px", display: "flex" }}
              >
                <Typography
                  variant="body2"
                  className="light mb-0"
                  style={{
                    color: "white",
                    display: "flex",
                    justifyContent: "flex-end",
                  }}
                >
                  {" "}
                  Digital India Bhashini Division (DIBD)
                </Typography>
              </div>
            </Grid>

            <Grid
              item
              xs={12}
              sm={6}
              md={6}
              lg={6}
              xl={6}
              style={{
                marginTop: "0rem",
                display: "flex",
                alignItems: "center",
              }}
            >
              <a href="" className={classes.tdlLogoSection}>
                <img
                  className={classes.tdlLogo}
                  src={process.env.PUBLIC_URL + "/meity_logo.png"}
                />
              </a>
            </Grid>
            <Grid
              item
              xs={12}
              sm={6}
              md={6}
              lg={6}
              xl={6}
              className={classes.FooterLogo}
              // style={{
              //   display: "flex",
              //   marginTop: "2rem",
              //   justifyContent: "flex-end",
              //   marginBottom:"3rem",
              // }}
            >
              <a href="" className="dg-india-logo ">
                <img src={dg} alt="dg-india logo" />
              </a>
            </Grid>
            <div className={classes.BottomText} style={{ display: "flex" }}>
              <Typography variant="body2" className="lighGrey mb-0 mt-3">
                {" "}
                Technology Development for Indian Languages Programme
              </Typography>
            </div>

            {/* <Grid
              item
              xs={12}
              sm={4}
              md={4}
              lg={4}
              xl={4}
              className={classes.textAlign}
            >
              <Typography variant="body1" className="lighGrey text-center">
                {translate("label.technologyDevelopment")}
              </Typography>
            </Grid>
            <Grid
              item
              xs={12}
              sm={4}
              md={4}
              lg={4}
              xl={4}
              className={classes.textAlign}
            >
              <Typography variant="body1" className="lighGrey text-center">
                {translate("label.jsNote")}
              </Typography>
            </Grid>
            <Grid
              item
              xs={12}
              sm={4}
              md={4}
              lg={4}
              xl={4}
              className={classes.textAlign}
            >
              <Typography variant="body1" className="lighGrey text-center">
                {translate("label.lastUpdated")}
              </Typography>
            </Grid> */}
          </Grid>
        </div>
      </div>
    </footer>
  );
};

export default withStyles(FooterStyles)(Footer);

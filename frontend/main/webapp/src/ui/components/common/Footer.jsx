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
        <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.grid}>
          <img src={web} alt="" className={classes.image} />
          <div>
            <Typography variant="body2">{translate("label.web")}</Typography>
            <Typography variant="body1">
              <Link
                className={classes.link}
                color="#16337B"
                href="https://www.meity.gov.in"
                target="_self"
                rel="noopener noreferrer"
              >
                {translate("link.meity")}
              </Link>
            </Typography>
          </div>
        </Grid>
        <Grid
          item
          xs={0}
          sm={4}
          md={4}
          lg={4}
          xl={4}
          className={classes.grid2}
        >
          <img src={email} alt="" className={classes.image} />
          <div className="">
            <Typography variant="body2">{translate("label.mail")}</Typography>
            <Typography variant="body1">
              <Link
                className={classes.link}
                href="mailto:contact@bhashini.gov.in"
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
            <Typography variant="body2">{translate("label.address")}</Typography>
            <Typography variant="body1" className={classes.link} href="">
              {translate("label.addressInfo")}
            </Typography>
          </div>
        </Grid>
      </Grid>
      <div className="section primary-color">
        <div className={classes.parentDiv}>
          <Grid container className={classes.container}>
            <Grid container>
              <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
                <ul className={classes.bhasini}>
                  <Typography variant="body1">
                    <li>
                      <Link color="while" href="https://bhashini.gov.in/images/Bhashini_-_Whitepaper.pdf">
                        {translate("link.whitePaper")}
                      </Link>
                    </li>
                  </Typography>

                  <Typography variant="body1">
                    <li>
                      <Link
                        color="while"
                        href="https://bhashini.gov.in/en/ecosystem"
                      >
                        {translate("link.ecosystem")}
                      </Link>
                    </li>
                  </Typography>
                  <Typography variant="body1" >
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
                  </Typography>
                </ul>
              </Grid>
              <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
                <ul className={classes.social}>
                  <Typography variant="body1">
                    <li>
                      <Link href="https://www.facebook.com/" target="_self">
                        <img src={facebook} alt="facebook" />
                      </Link>
                    </li>
                  </Typography>
                  <Typography variant="body1">
                    <li>
                      <Link href="https://twitter.com/" target="_self">
                        <img src={tw} alt="twitter" />
                      </Link>
                    </li>
                  </Typography>
                  <Typography variant="body1">
                    <li>
                      <Link href="https://www.instagram.com/" target="_self">
                        {" "}
                        <img src={insta} alt="instagram" />
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

            <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
              <br />
              <Typography variant="body1" className="lighGrey mb-0">
                {translate("label.copyright")}
                <br />
                {translate("label.nltm")}
              </Typography>
            </Grid>
            <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
              <ul className={classes.info}>
                <Typography variant="body1">
                  {" "}
                  <li>
                    <Link
                      color="while"
                      href="https://bhashini.gov.in/en/web-information-manager"
                      target="_self"
                    >
                      {translate("link.webInfo")}
                    </Link>
                  </li>
                </Typography>
                <Typography variant="body1">
                  <li>
                    <Link
                      color="white"
                      href="https://bhashini.gov.in/en/privacy-policy"
                      target="_self"
                    >
                      {translate("link.privacyPolicy")}
                    </Link>
                  </li>
                </Typography>
                <Typography variant="body1">
                  <li>
                    <Link
                      color="white"
                      href="https://bhashini.gov.in/en/terms-conditions"
                      target="_self"
                    >
                      {" "}
                      {translate('link.termsAndConditions')}
                    </Link>
                  </li>
                </Typography>
              </ul>
            </Grid>

            <Grid
              item
              xs={12}
              sm={6}
              md={6}
              lg={6}
              xl={6}
              style={{ marginTop: "3rem" }}
            >
              <a href="" className="tdl-logo">
                <img src={tdil} />
              </a>
            </Grid>
            <Grid
              item
              xs={12}
              sm={6}
              md={6}
              lg={6}
              xl={6}
              style={{
                display: "flex",
                marginTop: "3rem",
                justifyContent: "flex-end",
              }}
            >
              <a href="" className="dg-india-logo ">
                <img src={dg} alt="dg-india logo" />
              </a>
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
            </Grid>
          </Grid>
        </div>
      </div>
    </footer>
  );
};

export default withStyles(FooterStyles)(Footer);

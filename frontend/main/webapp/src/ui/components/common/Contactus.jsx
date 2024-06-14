import React from "react";
import { makeStyles } from "@material-ui/core/styles";

import ArrowRight from '../../../assets/arrow-white.svg';
import contactBanner from '../../../assets/contactBanner.jpg'
import { Col, Container, Row } from "react-bootstrap";

const useStyles = makeStyles((theme) => ({
  contactUsBanner: {
    backgroundImage: `url(${contactBanner})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    color: 'white',
    height: '205px',
    fontFamily: 'Noto-Regular',
    [theme.breakpoints.down('xs')]: {
      height: '350px',
    },
  },
  contactBannerContent: {
    background: 'transparent',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    textAlign: 'center',
    paddingLeft:"50px",
    [theme.breakpoints.up('sm')]: {
      textAlign: 'left',
    },
  },
  contactBannerHeading: {
    fontFamily: 'Noto-Bold',
    fontSize: '32px',
    fontWeight: 500,
    lineHeight: '48px',
  },
  contactBannerDesc: {
    fontFamily: 'Noto-Regular',
    fontSize: '20px',
    fontWeight: 400,
    lineHeight: '21.79px',
  },
  contactButton: {
    borderRadius: '4px',
    backgroundColor: '#2947A3',
    padding: '12px 28px',
    color: 'white',
    fontFamily: 'Noto-Regular',
    fontSize: '16px',
    fontWeight: 600,
    lineHeight: '24px',
    textDecoration: 'none',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    '&:hover': {
      textDecoration: 'none',
    },
  },
  buttonIcon: {
    marginLeft: theme.spacing(1),
  },
}));

const Contactus = () => {
  const classes = useStyles();

  return (
    // <Container fluid className="p-0">
    //   <Grid container className={classes.contactUsBanner}>
    //     <Grid item xs={12} md={6} className={classes.contactBannerContent}>
    //       <div className={classes.contactBannerHeading}>Need more information?</div>
    //       <p className={classes.contactBannerDesc}>Write your concern to us and we will get back to you.</p>
    //     </Grid>
    //     <Grid item xs={12} md={6} className={classes.contactBannerContent}>
    //       <a
    //         role="button"
    //         tabIndex={0}
    //         className={classes.contactButton}
    //         href="https://bhashini.gov.in/connect"
    //         target="_blank"
    //         rel="noopener noreferrer"
    //       >
    //         Contact Us <img src={ArrowRight} alt="arrow icon" className={classes.buttonIcon} />
    //       </a>
    //     </Grid>
    //   </Grid>
    // </Container>
    <Container fluid className="">
    <Row className="SliderSection">
      <Col md={12} className="p-0">
     
          <div className={classes.contactUsBanner}>
          <Container style={{height:"100%"}}>
              <Row style={{height:"100%"}}>
                  <Col md={6} className={classes.contactBannerContent} style={{display:"flex", justifyContent:"center"}}>
                

                      <div className={classes.contactBannerHeading}>Need more information?</div>
                      <p className={classes.contactBannerDesc}>Write your concern to us and we will get back to you.</p>
                      
                    
                  </Col>
                  <Col md={6} className="d-flex justify-content-center align-items-center Contact__bannerImg">
                     {/* <a href="#" className="ContactUs__contactButton">
                      <button className="ContactUs__contactButtonText">Contact us <img src={ArrowRight} className="ml-2"/></button>
                     </a> */}
                     <a
    role="button"
    tabIndex={0}
    className="theme-btn btn btn-primary"
    href="https://bhashini.gov.in/connect"
    target="_blank"
    rel="noopener noreferrer"
    style={{ borderRadius: "4px", backgroundColor: "#2947A3", padding: "12px 28px" }}
  >
    Contact Us <img src={ArrowRight} className="ml-2"/>
  </a>
                  </Col>
              </Row>
          </Container>
          </div>
        
        {/* </Slider> */}
      </Col>
    </Row>
  </Container>
  );
}

export default Contactus;

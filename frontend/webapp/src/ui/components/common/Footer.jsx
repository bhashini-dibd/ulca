// import 'bootstrap/dist/css/bootstrap.min.css';
// import "../../styles/css/style.css"
import email from "../../styles/img/email-id.png";
import web from "../../styles/img/web.png";
import location from "../../styles/img/location.png";
import facebook from "../../styles/img/facebook.png";
import tw from "../../styles/img/tw.png";
import insta from "../../styles/img/insta.png";
import dg from "../../styles/img/dg-india.png";
import { Grid } from "@material-ui/core";


const Footer = (props) => {
return (
    <footer >
    <Grid container>
            
            <Grid item xs={12} sm={4} md={4} lg={4} xl={4}>
            
            <img src={web} alt="" className=""/>
            <div className="">Web<br/> 
                <a className="font-weight-bold" href="https://www.meity.gov.in" target="_blank" rel="noopener noreferrer">www.meity.gov.in</a>
                
            </div>

        </Grid>
        <Grid item xs={12} sm={4} md={4} lg={4} xl={4}>
            
            <img src={email} alt="" className=""/>.
            <div className="">Mail<br/> 
                <a className="font-weight-bold" href="mailto:contact@bhashini.gov.in">contact@bhashini.gov.in</a>
                
            </div>
            </Grid>
            <Grid item xs={12} sm={4} md={4} lg={4} xl={4}>
            
            <img src={location} alt="" className=""/>
            <div className="">Address<br/> 
                <p className="font-weight-bold" href="">
Electronics Niketan, 6, CGO Complex,
Lodhi Road, New Delhi - 110003</p>
                
            </div>
       </Grid>
   
    </Grid>
    <div className="section primary-color">
    <Grid container>
            <div className="row align-items-center">
            <Grid item xs={12} sm={4} md={4} lg={4} xl={4}>
             
                        <ul className="d-lg-flex link">
                            <li><a href="#">Whitepaper</a></li>
                            
                            <li><a href="https://bhashini.uniteframework.io/en/ecosystem">Ecosystem</a></li>
                            <li>
                                <a href=""><div className="join">
        <a className="bh-btn-primary" href="https://uat.vakyansh.in/hi/home.html" target="_blank" rel="noopener noreferrer"> Join Bhasha Daan </a>
    </div></a>
                            </li>
                        </ul>
         
                </Grid>
                <Grid item xs={12} sm={4} md={4} lg={4} xl={4}>
              
                        <ul className="socialLink link d-flex justify-content-end">
                            <li><a href="https://www.facebook.com/" target="_blank"><img src={facebook} alt="facebook"/></a></li>
                            <li><a href="https://twitter.com/" target="_blank"><img src={tw} alt="twitter"/></a></li>
                            <li><a href="https://www.instagram.com/" target="_blank"> <img src={insta} alt="instagram"/></a></li>
                        </ul>
  
                </Grid>

            </div>
            <hr/>
            <div className="row align-items-center mt-4 mb-4">
                <div className="col-md-8 col-lg-6">
             
                        <p className="lighGrey mb-0">Copyright @2021 NLTM. All Rights Reserved.<br/>
NLTM: National Language Translation Mission</p>
         
                </div>
                <div className="col-md-4 col-lg-6">
                    <ul className="d-lg-flex link justify-content-end ">
                        <li><a href="https://bhashini.uniteframework.io/en/web-information-manager" target="_blank">Web Information Manager</a></li>
                        <li><a href="https://bhashini.uniteframework.io/en/privacy-policy" target="_blank">Privacy Policy</a></li>
                        <li><a href="https://bhashini.uniteframework.io/en/terms-conditions" target="_blank"> Terms of Use</a></li>
                    </ul>
                </div>

            </div>
             <div className="row align-items-center justify-content-between mt-5">
                <div className="col-md-3">
                    <a href="" className="tdl-logo"><img src={email}/></a>
                </div>
                <div className="col-md-3 text-right">
                     <a href="" className="dg-india-logo "><img src={dg} alt="dg-india logo"/></a>
                </div>

            </div>
            <div className="row mt-4 pt-4">
                <div className="col-md-4">
                   <p className="lighGrey text-center">Technology Development for Indian Languages Programme</p>
                </div>
                <div className="col-md-4">
                   <p className="lighGrey text-center">JavaScript must be enabled to access this site.
Supports : Firefox, Google Chrome, Internet Explorer 10.0+, Safari</p>
                </div>
                <div className="col-md-4">
                   <p className="lighGrey text-center">Last reviewed and updated on:16–Jun-2021</p>
                </div>
                

            </div>
        </Grid>
    </div>
</footer>
)
}

export default (Footer);
// import 'bootstrap/dist/css/bootstrap.min.css';
// import "../../styles/css/style.css"
import email from "../../styles/img/email-id.png";
import web from "../../styles/img/web.png";
import location from "../../styles/img/location.png";
import facebook from "../../styles/img/facebook.png";
import tw from "../../styles/img/tw.png";
import insta from "../../styles/img/insta.png";
import tdil from "../../styles/img/tdil.png"
import dg from "../../styles/img/dg-india.png";
import { Grid , Link, Divider} from "@material-ui/core";
import { withStyles } from "@material-ui/core";
import FooterStyles from "../../styles/Footer";

import "../../styles/css/style.css"
const Footer = (props) => {
    const {classes} = props;
return (
    <footer >
    <Grid container>
            
            <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.grid} >
            
            <img src={web} alt="" className={classes.image}/>
            <div >Web<br/>
                <Link className={classes.link} href="https://www.meity.gov.in" target="_blank" rel="noopener noreferrer">www.meity.gov.in</Link>
                
            </div>

        </Grid>
        <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.grid2}>
            
            <img src={email} alt="" className={classes.image}/>.
            <div className="">Mail<br/> 
                <Link className={classes.link} href="mailto:contact@bhashini.gov.in">contact@bhashini.gov.in</Link>
                
            </div>
            </Grid>
            <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.grid3}>
            
            <img src={location} alt="" className={classes.image}/>
            <div className="">Address<br/> 
                <p className={classes.link} href="">
Electronics Niketan, 6, CGO Complex,
Lodhi Road, New Delhi - 110003</p>
                
            </div>
       </Grid>
   
    </Grid>
    <div className="section primary-color">
        <div className={classes.parentDiv}>
        
    <Grid container className={classes.container}>
    <Grid container>
            
            <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
             
                        <ul className= {classes.bhasini}>
                            <li><Link color="secondary" href="#">Whitepaper</Link></li>
                            
                            <li><Link color="secondary" href="https://bhashini.uniteframework.io/en/ecosystem">Ecosystem</Link></li>
                            <li>
                                <Link color="secondary" href=""><div className="join">
        <Link className="bh-btn-primary" color="secondary" href="https://uat.vakyansh.in/hi/home.html" target="_blank" rel="noopener noreferrer"> Join Bhasha Daan </Link>
    </div></Link>
                            </li>
                        </ul>
         
                </Grid>
                <Grid item xs={12} sm={6} md={6} lg={6} xl={6} >
              
                        <ul className={classes.social}>
                            <li><Link  href="https://www.facebook.com/" target="_blank"><img src={facebook} alt="facebook"/></Link></li>
                            <li><Link href="https://twitter.com/" target="_blank"><img src={tw} alt="twitter"/></Link></li>
                            <li><Link href="https://www.instagram.com/" target="_blank"> <img src={insta} alt="instagram"/></Link></li>
                        </ul>
  
                </Grid>

            </Grid>
            <div style={{marginTop:"1rem",marginBottom:"1rem",border:"0px",borderTop:"1px solid white"}}/>
            
            <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
                            <br/>
                        <p className="lighGrey mb-0">Copyright @2021 NLTM. All Rights Reserved.<br/>
NLTM: National Language Translation Mission</p>
         
                </Grid>
                <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
                    <ul className={classes.info}>
                        <li><Link color="secondary"href="https://bhashini.uniteframework.io/en/web-information-manager" target="_blank">Web Information Manager</Link></li>
                        <li><Link  color="secondary" href="https://bhashini.uniteframework.io/en/privacy-policy" target="_blank">Privacy Policy</Link></li>
                        <li><Link color="secondary" href="https://bhashini.uniteframework.io/en/terms-conditions" target="_blank"> Terms of Use</Link></li>
                    </ul>
               </Grid>

           
               <Grid item xs={12} sm={6} md={6} lg={6} xl={6}style={{marginTop:"3rem"}}>
                    <a href="" className="tdl-logo"><img src={tdil}/></a>
               </Grid>
               <Grid item xs={12} sm={6} md={6} lg={6} xl={6} style={{display:"flex",marginTop:"3rem",justifyContent:"flex-end"}}>
                     <a href="" className="dg-india-logo "><img src={dg} alt="dg-india logo"/></a>
                </Grid>
                
                <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.textAlign}>
                   <p className="lighGrey text-center">Technology Development for Indian Languages Programme</p>
                </Grid>
                <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.textAlign}>
                   <p className="lighGrey text-center">JavaScript must be enabled to access this site.
Supports : Firefox, Google Chrome, Internet Explorer 10.0+, Safari</p>
                </Grid>
                <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.textAlign}>
                   <p className="lighGrey text-center">Last reviewed and updated on:16–Jun-2021</p>
                </Grid>
               
                

            
       </Grid>
       </div>
    </div>
</footer>
)
}

export default withStyles(FooterStyles)(Footer);
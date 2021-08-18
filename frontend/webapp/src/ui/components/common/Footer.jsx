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
import { Grid , Link, Divider, Typography} from "@material-ui/core";
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
            <div ><Typography variant="body2">Web</Typography>
                <Typography variant="body1"><Link  className={classes.link} color="#16337B" href="https://www.meity.gov.in" target="_self" rel="noopener noreferrer">www.meity.gov.in</Link></Typography>
                
            </div>

        </Grid>
        <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.grid2}>
            
            <img src={email} alt="" className={classes.image}/>
            <div className=""><Typography variant="body2">Mail</Typography>
            <Typography variant="body1"><Link className={classes.link} href="mailto:contact@bhashini.gov.in">contact@bhashini.gov.in</Link></Typography>
                
            </div>
            </Grid>
            <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.grid3}>
            
            <img src={location} alt="" className={classes.image}/>
            <div className=""><Typography variant="body2">Address</Typography>
            <Typography variant="body1"className={classes.link} href="">
Electronics Niketan, 6, CGO Complex,
Lodhi Road, New Delhi - 110003</Typography>
                
            </div>
       </Grid>
   
    </Grid>
    <div className="section primary-color">
        <div className={classes.parentDiv}>
        
    <Grid container className={classes.container}>
    <Grid container>
            
            <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
             
                        <ul className= {classes.bhasini}>
                        <Typography variant="body1"><li><Link color="while" href="#">Whitepaper</Link></li></Typography>
                            
                        <Typography variant="body1"><li><Link color="while" href="https://bhashini.uniteframework.io/en/ecosystem">Ecosystem</Link></li></Typography>
                        <Typography variant="body1"><li>
                                <Link color="while" href=""><div className="join">
        <Link className="bh-btn-primary" color="while" href="https://uat.vakyansh.in/hi/home.html" target="_self" rel="noopener noreferrer"> Join Bhasha Daan </Link>
    </div></Link>
                            </li></Typography>
                        </ul>
         
                </Grid>
                <Grid item xs={12} sm={6} md={6} lg={6} xl={6} >
              
                        <ul className={classes.social}>
                        <Typography variant="body1"><li><Link  href="https://www.facebook.com/" target="_self"><img src={facebook} alt="facebook"/></Link></li></Typography>
                        <Typography variant="body1"><li><Link href="https://twitter.com/" target="_self"><img src={tw} alt="twitter"/></Link></li></Typography>
                        <Typography variant="body1"><li><Link href="https://www.instagram.com/" target="_self"> <img src={insta} alt="instagram"/></Link></li></Typography>
                        </ul>
  
                </Grid>

            </Grid>
            <Divider style={{width:"100%",marginTop:"20px",background:"#4E6079",marginBottom:"15px"}}/>
            
            <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
                            <br/>
                            <Typography variant="body1"className="lighGrey mb-0">Copyright @2021 NLTM. All Rights Reserved.<br/>
NLTM: National Language Translation Mission</Typography>
         
                </Grid>
                <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
                    <ul className={classes.info}>
                    <Typography variant="body1"> <li><Link color="while"href="https://bhashini.uniteframework.io/en/web-information-manager" target="_self">Web Information Manager</Link></li></Typography>
                    <Typography variant="body1"><li><Link  color="white" href="https://bhashini.uniteframework.io/en/privacy-policy" target="_self">Privacy Policy</Link></li></Typography>
                    <Typography variant="body1"><li><Link color="white" href="https://bhashini.uniteframework.io/en/terms-conditions" target="_self"> Terms of Use</Link></li></Typography>
                    </ul>
               </Grid>

           
               <Grid item xs={12} sm={6} md={6} lg={6} xl={6}style={{marginTop:"3rem"}}>
               <a href="" className="tdl-logo"><img src={tdil}/></a>
               </Grid>
               <Grid item xs={12} sm={6} md={6} lg={6} xl={6} style={{display:"flex",marginTop:"3rem",justifyContent:"flex-end"}}>
               <a href="" className="dg-india-logo "><img src={dg} alt="dg-india logo"/></a>
                </Grid>
                
                <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.textAlign}>
                <Typography variant="body1"className="lighGrey text-center">Technology Development for Indian Languages Programme</Typography>
                </Grid>
                <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.textAlign}>
                <Typography variant="body1" className="lighGrey text-center">JavaScript must be enabled to access this site.
Supports : Firefox, Google Chrome, Internet Explorer 10.0+, Safari</Typography>
                </Grid>
                <Grid item xs={12} sm={4} md={4} lg={4} xl={4} className={classes.textAlign}>
                <Typography variant="body1" className="lighGrey text-center">Last reviewed and updated on:16–Jun-2021</Typography>
                </Grid>
               
                

            
       </Grid>
       </div>
    </div>
</footer>
)
}

export default withStyles(FooterStyles)(Footer);
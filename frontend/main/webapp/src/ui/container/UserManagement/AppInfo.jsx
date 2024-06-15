import {
  Box,
  Grid,
  Hidden,
  Typography,
  withStyles,
} from "@material-ui/core";
import LoginStyles from "../../styles/Login";
import {  useHistory } from "react-router-dom";
import { translate } from "../../../assets/localisation";
import logo from '../../../assets/BhashiniFooterlogo.png'
function AppInfo(props) {
  const { classes } = props;
  const history = useHistory();
  return (
    
      <Grid item xs={12} sm={4} md={3} lg={3} color = {"primary"}className={classes.appInfo} style={{display:"flex", flexDirection:"column", justifyContent:"space-between", backgroundColor:"#13296C"}}>
        <img src={logo} style={{width:"200px"}} className={classes.title}/>
        {/* <Typography className={classes.title} variant={"h2"} onClick={() => { history.push(`${process.env.PUBLIC_URL}/dashboard`)}}></Typography> */}
        {/* <Hidden only="xs"> */}
          <Box>
        <Typography variant={"h3"} className={classes.subTitle} style={{margin:"0px 0px 0px 39px"}}>
          {/* {translate("label.ulcaFullForm")} */}
          Bhashini Udyat
        </Typography>
       
        <Typography variant={"body1"} className={classes.body}>
        {/* {translate("label.ulcaInfo")} */}
        Udyat is an open-sourced
API and data platform to
collect, curate and discover
datasets in Indian
languages.
        </Typography>
        </Box>
        {/* </Hidden> */}
      </Grid>
     
  );
}

export default withStyles(LoginStyles)(AppInfo);

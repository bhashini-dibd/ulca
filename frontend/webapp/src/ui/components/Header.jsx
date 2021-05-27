import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { withStyles} from "@material-ui/core";
import HeaderStyles from "../styles/HeaderStyles"

 function Header(props){
    const {title, classes} = props;
    
    return (
        <div>
          <AppBar className={classes.appBar}>

            <Toolbar className={classes.toolbar}>
              <Typography variant="h5">
                {title}
              </Typography>
              
            </Toolbar>
          </AppBar>


        </div>
    )
}

export default withStyles(HeaderStyles)(Header);
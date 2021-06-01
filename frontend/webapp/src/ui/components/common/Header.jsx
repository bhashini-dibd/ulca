import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { Grid, withStyles, Button, Menu, MenuItem } from "@material-ui/core";
import DownIcon from '@material-ui/icons/ArrowDropDown';
import Avatar from '@material-ui/core/Avatar';
import HeaderStyles from "../../styles/HeaderStyles"


const StyledMenu = withStyles({
  paper: {
    border: '1px solid #d3d4d5',
  },
})((props) => (
  <Menu
    elevation={0}
    getContentAnchorEl={null}
    anchorOrigin={{
      vertical: 'bottom',
      horizontal: 'center',
    }}
    transformOrigin={{
      vertical: 'top',
      horizontal: 'center',
    }}
    {...props}
  />
));

function Header(props) {
  const { classes } = props;

  return (
    <div>
      <AppBar className={classes.appBar}>
        <Toolbar className={classes.toolbar}>
          <div className={classes.menu}>
            <div className={classes.title}>
              <Typography component="h2" variant="b">
                {"U L C A"}
              </Typography>
            </div>
            <div className={classes.home}>
              <div>
                <Button className={classes.menuBtn} variant="text">
                  Home
                </Button>

                <StyledMenu
                  id="menu-appbar"
                >
                  <MenuItem
                    style={{ borderTop: "1px solid #D6D6D6" }}
                  >
                    As TXT
                    </MenuItem>
                </StyledMenu>
              </div>
            </div>
            <div className={classes.options}>
              <div>
                <Button className={classes.menuBtn} variant="text">
                  Dataset
                    <DownIcon />
                </Button>

                <StyledMenu
                  id="menu-appbar"
                >
                  <MenuItem
                    style={{ borderTop: "1px solid #D6D6D6" }}
                  >
                    As TXT
                    </MenuItem>
                </StyledMenu>
              </div>
            </div>
            <div className={classes.options}>
              <Button className={classes.menuBtn} variant="text" >
                Model
                    <DownIcon />
              </Button>

              <StyledMenu
                id="menu-appbar"
              >
                <MenuItem
                  style={{ borderTop: "1px solid #D6D6D6" }}
                >
                  As TXT
                    </MenuItem>
              </StyledMenu>
            </div>
            <div className={classes.profile}>
              <Button className={classes.menuBtn} variant="text">
                <Avatar >RS</Avatar>
                  Roshan Shah
                    <DownIcon />
              </Button>

              <StyledMenu
                id="menu-appbar"
              >
                <MenuItem
                  style={{ borderTop: "1px solid #D6D6D6" }}
                >
                  As TXT
                    </MenuItem>
              </StyledMenu>
            </div>
          </div>
        </Toolbar>
      </AppBar>
    </div >
  )
}

export default withStyles(HeaderStyles)(Header);
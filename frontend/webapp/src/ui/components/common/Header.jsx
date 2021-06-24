import React, { useState } from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { withStyles, Button, Menu, MenuItem, MuiThemeProvider } from "@material-ui/core";
import DownIcon from '@material-ui/icons/ArrowDropDown';
import Avatar from '@material-ui/core/Avatar';
import HeaderStyles from "../../styles/HeaderStyles"
import { useHistory } from 'react-router-dom';
import GroupIcon from '@material-ui/icons/Group';
import GroupAddIcon from '@material-ui/icons/GroupAdd';
import authenticate from '../../../configs/authenticate';
import Theme from "../../theme/theme-default";
import MenuItems from "../../components/common/MenuItem";
import {menuItems} from '../../../configs/menuItems';

const StyledMenu = withStyles({
 
})((props) => (
  <Menu
     elevation={0}
    getContentAnchorEl={null}
    anchorOrigin={{
      vertical: 'bottom',
      horizontal: 'left',
    }}
    transformOrigin={{
      vertical: 'top',
      horizontal: 'left',
    }}
    {...props}
  />
));

const Header = (props) => {
  const { classes } = props;
  const [anchorEl, setAnchorEl] = useState(null)
  const [logout, setAnchorElLogout] = useState(null)
  const history = useHistory();
 
  const {firstName,lastName} = authenticate() ? JSON.parse(localStorage.getItem('userDetails')) : {firstName:"",lastName:""}
  const handleClose = (e) => {
    setAnchorEl(null)
    setAnchorElLogout(null)
  }

  const handleOpenMenu = (e) => {
    setAnchorEl(e.currentTarget)
  }

  const handleLogoutOption = (e) => {
    setAnchorElLogout(e.currentTarget)
  }

  const handleMenuItemClick = (url) => {
    
    history.push(`${process.env.PUBLIC_URL}${url}`)
    handleClose();
  }


  return (
    <MuiThemeProvider theme={Theme}>
      <AppBar color="primary">
        <Toolbar className={classes.toolbar}>
          <div className={classes.menu}>

              <Typography variant="h6" onClick={() => handleMenuItemClick('/private-dashboard')}>
                ULCA
              </Typography>

            {
              <>
                {/* <div className={classes.home}>
                  <Button
                    className={classes.menuBtn}
                    variant="text"
                    onClick={() => handleMenuItemClick('/dashboard')}
                  >
                    Home
                </Button>
                </div>

                <div className={classes.homeBtn}>
                  <Button
                    className={classes.menuBtn}
                    variant="text"
                    onClick={() => handleMenuItemClick('/dashboard')}
                  >
                    <HomeIcon fontSize="large" />
                  </Button>
                </div> */}
                <div className={classes.datasetOption}>
                  <div>
                    <Button className={classes.menuBtn}
                      onClick={(e) => handleOpenMenu(e)}
                      variant="text"
                    >
                      Dataset
                    <DownIcon />
                    </Button>
                  </div>
                  
                  <MenuItems
                    id={"dataset-menu"}
                    anchorEl={anchorEl}
                    handleClose={handleClose}
                    menuOptions={menuItems.dataset}
                    handleMenuItemClick={handleMenuItemClick}
                  />
                  {/* <StyledMenu id="data-set"
                    anchorEl={anchorEl}
                    open={Boolean(anchorEl)}
                    onClose={(e) => handleClose(e)}
                     className={classes.styledMenu1}
                  >
                    <MenuItem
                      className={classes.styledMenu}
                      onClick={() => handleMenuItemClick('/my-contribution')}
                    >
                      My Contributon
                    </MenuItem>
                    <MenuItem className={classes.styledMenu}
                      onClick={() => handleMenuItemClick('/my-searches')}
                    >
                      My Searches
                    </MenuItem>
                    <MenuItem className={classes.styledMenu}
                      onClick={() => handleMenuItemClick('/search-and-download-rec/initiate/-1')}
                    >
                      Search & Download Records
                    </MenuItem>
                    <MenuItem className={classes.styledMenu}
                     onClick={() => handleMenuItemClick('/readymade-dataset')}>
                      Explore Readymade Datasets
                    </MenuItem>
                    <MenuItem className={classes.styledMenu}
                      onClick={() => handleMenuItemClick('/submit-dataset/upload')}
                    >
                      Submit Dataset
                    </MenuItem>
                  </StyledMenu> */}
                </div>
                {/* <div className={classes.options}>
                  <div className={classes.model}>
                    <Button className={classes.menuBtn} variant="text" onClick={() => alert("Still in progress")}>
                      Model
                    <DownIcon />
                    </Button>
                  </div>
                 
                </div> */}
              </>
            }
            {
              authenticate() ?
                <div className={classes.profile}>
                  <Button  onClick={(e) => handleLogoutOption(e)} className={classes.menuBtn} variant="text">
                    <Avatar variant="contained" color= "transparent">{`${firstName[0].toUpperCase()}`}</Avatar>
                    <p className={classes.profileName}>{`${firstName}`}</p>
                    <DownIcon />
                  </Button>
                  <StyledMenu id="data-set"
                    anchorEl={logout}
                    open={Boolean(logout)}
                    onClose={(e) => handleClose(e)}
                    className={classes.styledMenu1}
                  >
                     <MenuItem
                     className={classes.styledMenu}
                
                    >
                      Change Password
                    </MenuItem>
                    <MenuItem
                     className={classes.styledMenu}
                     
                    >
                      Feedback
                    </MenuItem>
                    <MenuItem
                     className={classes.styledMenu}
                      onClick={() => {
                        localStorage.removeItem('userInfo')
                        handleMenuItemClick('/user/login')}}
                    >
                      Log out
                    </MenuItem>
                   
                  </StyledMenu>
                </div>
                :
                <div className={classes.profile}>
                  <div className={classes.desktopAuth}>
                    <Button
                      className={classes.menuBtn}
                      color = "primary"
                      onClick={() => history.push(`${process.env.PUBLIC_URL}/user/login`)}
                      variant="text"
                    >
                      Sign In
                    </Button>
                    <Button
                      className={classes.menuBtn2}
                      color="primary"
                      variant="text"
                      onClick={() => history.push(`${process.env.PUBLIC_URL}/user/register`)}
                    >
                      Sign Up</Button>
                  </div>
                  <div className={classes.mobileAuth}>
                    <Button
                      className={classes.menuBtn}
                      onClick={() => history.push(`${process.env.PUBLIC_URL}/user/login`)}
                      variant="text"
                    >
                      <GroupIcon />
                    </Button>
                    <Button
                      className={classes.menuBtn}
                      variant="text"
                      onClick={() => history.push(`${process.env.PUBLIC_URL}/user/register`)}
                    >
                      <GroupAddIcon />
                    </Button>
                  </div>
                </div>
            }
          </div>
        </Toolbar>
      </AppBar>
    </MuiThemeProvider>
  )
}

export default withStyles(HeaderStyles)(Header);
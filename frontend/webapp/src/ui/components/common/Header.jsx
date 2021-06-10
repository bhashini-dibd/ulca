import React, { useState } from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { withStyles, Button, Menu, MenuItem } from "@material-ui/core";
import DownIcon from '@material-ui/icons/ArrowDropDown';
import Avatar from '@material-ui/core/Avatar';
import HeaderStyles from "../../styles/HeaderStyles"
import HomeIcon from '@material-ui/icons/Home';
import DescriptionIcon from '@material-ui/icons/Description';
import ChromeReaderModeIcon from '@material-ui/icons/ChromeReaderMode';
import { useHistory } from 'react-router-dom';
import GroupIcon from '@material-ui/icons/Group';
import GroupAddIcon from '@material-ui/icons/GroupAdd';
import authenticate from '../../../configs/authenticate';

const StyledMenu = withStyles({
  paper: {
    border: '1px solid #d3d4d5',
  },
})((props) => (
  <Menu
    elevation={4}
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

const Header = (props) => {
  const { classes } = props;
  const [anchorEl, setAnchorEl] = useState(null)
  const [logout, setAnchorElLogout] = useState(null)
  const history = useHistory();
  
  const {firstName,lastName} = JSON.parse(localStorage.getItem('userProfile')).userDetails
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
    handleClose();
    history.push(`${process.env.PUBLIC_URL}${url}`)
  }
  return (
    <div>
      <AppBar color="primary">
        <Toolbar className={classes.toolbar}>
          <div className={classes.menu}>
            <Button className={classes.title}
              onClick={() => handleMenuItemClick('/dashboard')}
            >
              <Typography variant="h5">
                <strong>{"U L C A"}</strong>
              </Typography>
            </Button>
            {
              authenticate() &&
              <>
                <div className={classes.home}>
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
                </div>
                <div className={classes.options}>
                  <div className={classes.dataset}>
                    <Button className={classes.menuBtn}
                      onClick={(e) => handleOpenMenu(e)}
                      variant="text"
                    >
                      Dataset
                    <DownIcon />
                    </Button>
                  </div>
                  <div className={classes.datasetMobile}>
                    <Button className={classes.menuBtn}
                      onClick={(e) => handleOpenMenu(e)}
                      variant="text"
                    >
                      <DescriptionIcon fontSize="large" />
                      <DownIcon />
                    </Button>
                  </div>
                  <StyledMenu id="data-set"
                    anchorEl={anchorEl}
                    open={Boolean(anchorEl)}
                    onClose={(e) => handleClose(e)}
                    className={classes.styledMenu}
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
                      onClick={() => handleMenuItemClick('/search-and-download-rec/initiate')}
                    >
                      Search & Download Records
                    </MenuItem>
                    <MenuItem className={classes.styledMenu}>
                      Explore Readymade Datasets
                    </MenuItem>
                    <MenuItem className={classes.styledMenu}
                      onClick={() => handleMenuItemClick('/submit-dataset/upload')}
                    >
                      Submit Dataset
                    </MenuItem>
                  </StyledMenu>
                </div>
                <div className={classes.options}>
                  <div className={classes.model}>
                    <Button className={classes.menuBtn} variant="text">
                      Model
                    <DownIcon />
                    </Button>
                  </div>
                  <div className={classes.modelMobile}>
                    <Button className={classes.menuBtn} variant="text">
                      <ChromeReaderModeIcon fontSize="large" />
                      <DownIcon />
                    </Button>
                  </div>
                </div>
              </>
            }
            {
              authenticate() ?
                <div className={classes.profile}>
                  <Button onClick={(e) => handleLogoutOption(e)} className={classes.menuBtn} variant="text">
                    <Avatar >{`${firstName[0]}${lastName[0]}`}</Avatar>
                    <p className={classes.profileName}>{`${firstName} ${lastName}`}</p>
                    <DownIcon />
                  </Button>
                  <StyledMenu id="data-set"
                    anchorEl={logout}
                    open={Boolean(logout)}
                    onClose={(e) => handleClose(e)}
                    className={classes.styledMenu}
                  >
                    <MenuItem
                      className={classes.styledMenu}
                      onClick={() => handleMenuItemClick('/user/login')}
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
                      onClick={() => history.push(`${process.env.PUBLIC_URL}/user/login`)}
                      variant="text"
                    >
                      Sign In
                    </Button>
                    <Button
                      className={classes.menuBtn}
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
    </div >
  )
}

export default withStyles(HeaderStyles)(Header);
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
  const history = useHistory();

  const handleClose = (e) => {
    setAnchorEl(null)
  }

  const handleOpenMenu = (e) => {
    setAnchorEl(null)
    setAnchorEl(e.currentTarget)
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
                <MenuItem className={classes.styledMenu}>
                  My Searches
                    </MenuItem>
                <MenuItem className={classes.styledMenu}>
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
            <div className={classes.profile}>
              <Button className={classes.menuBtn} variant="text">
                <Avatar >UU</Avatar>
                <p className={classes.profileName}>Ulca User</p>
                <DownIcon />
              </Button>
            </div>
          </div>
        </Toolbar>
      </AppBar>
    </div >
  )
}

export default withStyles(HeaderStyles)(Header);
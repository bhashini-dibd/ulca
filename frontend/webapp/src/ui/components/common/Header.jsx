import React, { useState } from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { withStyles, Button, Menu, MenuItem, Hidden } from "@material-ui/core";
import DownIcon from '@material-ui/icons/ArrowDropDown';
import Avatar from '@material-ui/core/Avatar';
import HeaderStyles from "../../styles/HeaderStyles"
import HomeIcon from '@material-ui/icons/Home';
import DescriptionIcon from '@material-ui/icons/Description';
import ChromeReaderModeIcon from '@material-ui/icons/ChromeReaderMode';

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

const Header = (props) => {
  const { classes } = props;
  const [anchorEl, setAnchorEl] = useState(null)

  const handleClose = (e) => {
    setAnchorEl(null)
  }

  const handleOpenMenu = (e) => {
    setAnchorEl(null)
    setAnchorEl(e.currentTarget)
  }

  return (
    <div>
      <AppBar color="primary">
        <Toolbar className={classes.toolbar}>
          <div className={classes.menu}>
            <Button className={classes.title}>
              <Typography component="h2" variant="b">
                {"U L C A"}
              </Typography>
            </Button>
            <div className={classes.home}>
              <Button
                className={classes.menuBtn}
                variant="text"
              >
                Home
                </Button>
            </div>
            <div className={classes.homeBtn}>
              
              <Button
                className={classes.menuBtn}
                variant="text"
              >
                <HomeIcon fontSize="large" />
              </Button>
            </div>
            <div className={classes.options}>
              <div className={classes.dataset}>
                <Button className={classes.menuBtn}
                  className={classes.menuBtn}
                  onClick={(e) => handleOpenMenu(e)}
                  variant="text"
                >
                  Dataset
                    <DownIcon />
                </Button>
              </div>
              <div className={classes.datasetMobile}>
                <Button className={classes.menuBtn}
                  className={classes.menuBtn}
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
                <MenuItem className={classes.styledMenu}>
                  My Contrributon
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
                <MenuItem className={classes.styledMenu}>
                  Submit Dataset
                    </MenuItem>
              </StyledMenu>
            </div>
            <div className={classes.options}>
              <div className={classes.model}>
                <Button className={classes.menuBtn} variant="text"
                  className={classes.menuBtn}
                  variant="text"
                >
                  Model
                    <DownIcon />
                </Button>
              </div>
              <div className={classes.modelMobile}>
                <Button className={classes.menuBtn} variant="text"
                  className={classes.menuBtn}
                  variant="text"
                >
                  <ChromeReaderModeIcon fontSize="large" />
                  <DownIcon />
                </Button>
              </div>
            </div>
            <div className={classes.profile}>
              <Button className={classes.menuBtn} variant="text">
                <Avatar >RS</Avatar>
                <p className={classes.profileName}>Roshan Shah</p>
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
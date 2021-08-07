import React, { useState } from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import { withStyles, Button, Menu, MenuItem, MuiThemeProvider, Divider, Grid } from "@material-ui/core";
import DownIcon from '@material-ui/icons/ArrowDropDown';
import Avatar from '@material-ui/core/Avatar';
import HeaderStyles from "../../styles/HeaderStyles"
import { useHistory } from 'react-router-dom';
import GroupIcon from '@material-ui/icons/Group';
import GroupAddIcon from '@material-ui/icons/GroupAdd';
import authenticate from '../../../configs/authenticate';
import Theme from "../../theme/theme-default";
import MenuItems from "../../components/common/MenuItem";
import { menuItems } from '../../../configs/menuItems';
import Dialog from "./Dialog";
import { useDispatch } from "react-redux";
import { initialSearchFilter } from '../../../redux/actions/api/Model/ModelSearch/Benchmark';
import bhashiniLogo from '../../../assets/bhashiniogo.png';

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
  const [anchorModel, setAnchorModel] = useState(null)
  const [urlLink, setUrlLink] = useState(null)
  const [open, setOpen] = useState(false)
  const [logout, setAnchorElLogout] = useState(null)
  const history = useHistory();

  const { firstName, lastName } = authenticate() ? JSON.parse(localStorage.getItem('userDetails')) : { firstName: "", lastName: "" }
  const handleClose = (e) => {
    setAnchorEl(null)
    setAnchorElLogout(null)
    setAnchorModel(null)
  }

  const handleOpenMenu = (e) => {
    setAnchorEl(e.currentTarget)
  }

  const handleOpenModel = (e) => {
    authenticate() ? setAnchorModel(e.currentTarget) : history.push(`${process.env.PUBLIC_URL}/benchmark/initiate`)
  }
  const dispatch = useDispatch();

  const handleLogoutOption = (e) => {
    setAnchorElLogout(e.currentTarget)
  }

  const handleLogOut = (url) => {
    history.push(`${process.env.PUBLIC_URL}${url ? url : urlLink}`)
    handleClose();
  }

  const handleMenuItemClick = (url) => {
    if (authenticate() || url === "/benchmark/initiate" || url.includes()) {
      dispatch(initialSearchFilter());
      history.push(`${process.env.PUBLIC_URL}${url}`)
      handleClose();
    }
    else {
      handleClose();
      setUrlLink(url)
      setOpen(true)
    }

  }




  return (
    <MuiThemeProvider theme={Theme}>
      <AppBar color="inherit">
        <Toolbar className={classes.toolbar}>
          <div className={classes.menu}>
            <img className={classes.bhashiniLogo}
              src={bhashiniLogo}
              alt="Bhashini Logo"
            />
            {/* <Divider orientation="vertical" color="primary"/> */}
            <Typography variant="h5" onClick={() => history.push(`${process.env.PUBLIC_URL}/dashboard`)}>
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
                      <Typography variant={"body1"} >
                        Dataset
                      </Typography>
                      {authenticate() && <DownIcon color="action" />}
                    </Button>
                  </div>
                  {authenticate() &&
                    <MenuItems
                      id={"dataset-menu"}
                      anchorEl={anchorEl}
                      handleClose={handleClose}
                      menuOptions={menuItems.dataset}
                      handleMenuItemClick={handleMenuItemClick}
                    />
                  }
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
                <div className={classes.options}>
                  <div className={classes.model}>
                    <Button className={classes.menuBtn} variant="text" onClick={(e) => handleOpenModel(e)}>
                      <Typography variant={"body1"}>
                        Model
                      </Typography>
                      {authenticate() && <DownIcon color="action" />}
                    </Button>
                  </div>
                  {authenticate() && <MenuItems
                    id={"dataset-menu"}
                    anchorEl={anchorModel}
                    handleClose={handleClose}
                    menuOptions={menuItems.models}
                    handleMenuItemClick={handleMenuItemClick}
                  />}
                </div>
              </>
            }
            {
              authenticate() ?
                <div className={classes.profile}>
                  <Button onClick={(e) => handleLogoutOption(e)} className={classes.menuBtn}>
                    <Avatar className={classes.avatar} variant="contained">{`${firstName[0].toUpperCase()}`}</Avatar>
                    <Typography variant="body1" color="textPrimary" className={classes.profileName}>{`${firstName}`}</Typography>
                    <DownIcon color="action" />
                  </Button>
                  <StyledMenu id="data-set"
                    anchorEl={logout}
                    open={Boolean(logout)}
                    onClose={(e) => handleClose(e)}
                    className={classes.styledMenu1}
                  >
                    {/* <MenuItem
                     className={classes.styledMenu}
                
                    >
                      Change Password
                    </MenuItem>
                    <MenuItem
                     className={classes.styledMenu}
                     
                    >
                      Feedback
                    </MenuItem> */}
                    <MenuItem
                      className={classes.styledMenu}
                      onClick={() => {
                        localStorage.removeItem('userInfo')
                        handleLogOut('/user/login')
                      }}
                    >
                      Log out
                    </MenuItem>

                  </StyledMenu>
                </div>
                :
                <div className={classes.profile}>
                  <div className={classes.desktopAuth}>
                    <Grid container spacing={2}>
                      <Grid item>
                        <Button
                          size="small"
                          // className={classes.menuBtn}
                          color="default"
                          onClick={() => history.push(`${process.env.PUBLIC_URL}/user/login`)}
                          variant="outlined"
                        >
                          Sign In
                        </Button>
                      </Grid>
                      <Grid item>
                        <Button
                          // className={classes.menuBtn2}
                          color="primary"
                          variant="contained"
                          size="small"
                          onClick={() => history.push(`${process.env.PUBLIC_URL}/user/register`)}
                        >
                          Sign Up
                        </Button>
                      </Grid>
                    </Grid>
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

      {open && <Dialog
        title={"Not Signed In"}
        open={open}
        handleClose={() => setOpen(false)}
        message={"Please sign in to continue."}
        handleSubmit={handleLogOut}
        actionButton={"Close"}
        actionButton2={"Sign In"}
      />}
    </MuiThemeProvider>
  )
}

export default withStyles(HeaderStyles)(Header);
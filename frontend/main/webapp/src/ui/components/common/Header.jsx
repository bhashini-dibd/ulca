import React, { useState } from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import {
  withStyles,
  Button,
  Menu,
  MenuItem,
  MuiThemeProvider,
  Divider,
  Grid,
  Box,
} from "@material-ui/core";
import DownIcon from "@material-ui/icons/ArrowDropDown";
import Avatar from "@material-ui/core/Avatar";
import HeaderStyles from "../../styles/HeaderStyles";
import { useHistory } from "react-router-dom";
import GroupIcon from "@material-ui/icons/Group";
import GroupAddIcon from "@material-ui/icons/GroupAdd";
import authenticate from "../../../configs/authenticate";
import Theme from "../../theme/theme-default";
import MenuItems from "../../components/common/MenuItem";
import { menuItems } from "../../../configs/menuItems";
import Dialog from "./Dialog";
import { useDispatch, useSelector } from "react-redux";
import { initialSearchFilter } from "../../../redux/actions/api/Model/ModelSearch/Benchmark";
import bhashiniLogo from "../../../assets/BhashiniHeaderLogo.png";
import { Link } from "@material-ui/core";
import SubHeader from "./SubHeader";
import getMenuType from "../../../redux/actions/api/Common/getMenuType";
import getMenuOption from "../../../redux/actions/api/Common/getMenuOption";
import { translate } from "../../../assets/localisation";
import person from '../../../assets/person.svg'
import logoutIcon from '../../../assets/logout.svg'
const StyledMenu = withStyles({
  paper: {
    "@media (max-width:650px)": {
      width:"120px"
    
     
    },
   },
})((props) => (
  <Menu
    elevation={0}
    getContentAnchorEl={null}
    anchorOrigin={{
      vertical: "bottom",
      horizontal: "left",
    }}
    transformOrigin={{
      vertical: "top",
      horizontal: "left",
    }}
    {...props}
  />
));

const Header = (props) => {
  const { classes, type, index } = props;
  const [anchorEl, setAnchorEl] = useState(null);
  const [anchorModel, setAnchorModel] = useState(null);
  const [urlLink, setUrlLink] = useState(null);
  const [open, setOpen] = useState(false);
  const [logout, setAnchorElLogout] = useState(null);
  const history = useHistory();
  const menuType = useSelector((state) => state.getMenuInfo.type);
  const value = useSelector((state) => state.getMenuInfo.optionSelected);
  const { firstName, lastName } = authenticate()
    ? JSON.parse(localStorage.getItem("userDetails"))
    : { firstName: "", lastName: "" };
  const handleClose = (e) => {
    setAnchorEl(null);
    setAnchorElLogout(null);
    setAnchorModel(null);
  };

  const roles = localStorage.getItem("userDetails")
    ? localStorage.getItem("userDetails")
    : [];

  const handleOpenMenu = (e) => {
    setAnchorEl(e.currentTarget);
  };

  const handleOpenModel = (e) => {
    authenticate()
      ? setAnchorModel(e.currentTarget)
      : history.push(`${process.env.PUBLIC_URL}/model/explore-models`);
  };
  const dispatch = useDispatch();

  const handleLogoutOption = (e) => {
    setAnchorElLogout(e.currentTarget);
  };

  const handleLogOut = (url) => {
    history.push(`${process.env.PUBLIC_URL}${url ? url : urlLink}`);
    handleClose();
  };
  const handleRedirection= (url) => {
    console.log(`${process.env.PUBLIC_URL}${url}`)
    history.push(`${process.env.PUBLIC_URL}${url}`);
    handleClose();
  };

  const handleChange = (event, newValue) => {
    dispatch(getMenuOption(newValue));
  };

  const handleMenuItemClick = (url) => {
    if (authenticate() || url === "/benchmark/initiate" || url.includes()) {
      dispatch(initialSearchFilter());
      history.push(`${process.env.PUBLIC_URL}${url}`);
      handleClose();
    } else {
      handleClose();
      setUrlLink(url);
      setOpen(true);
    }
  };

  const handleMenuTypeClick = (type) => {
    if (type === "models") {
      history.push(`${process.env.PUBLIC_URL}/model/explore-models`);
      dispatch(getMenuOption(1));
    } else if (type === "admin") {
      history.push(`${process.env.PUBLIC_URL}/admin/view-user-details`);
      dispatch(getMenuOption(1));
    } else {
      history.push(
        `${process.env.PUBLIC_URL}/search-and-download-rec/initiate/-1`
      );
      dispatch(getMenuOption(2));
    }
    dispatch(getMenuType(type));
  };
  return (
    <MuiThemeProvider theme={Theme}>
      <AppBar color="inherit" position="static" >
        <Toolbar className={classes.toolbar}>
          <div className={classes.menu} style={{margin:"10px 0px"}}>
            <Link href="https://bhashini.gov.in/">
              <img
                className={classes.bhashiniLogo}
                src={bhashiniLogo}
                alt="Bhashini Logo"
              />
            </Link>
            {/* <Divider orientation="vertical" color="primary"/> */}
            <Typography
              variant="h4"
              onClick={() => {
                dispatch(getMenuType(""));
                dispatch(getMenuOption(""));
                window.location = `${process.env.REACT_APP_INTRO_URL}/ulca/intro`;
              }}
            >
              { authenticate() ? 
               <Box>
                <Box>Bhashini Udyat</Box>
                <Box><Typography variant="body2">API Consumption Portal</Typography></Box>
              </Box>
              :  translate("label.ulca")
              }
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
                { authenticate() &&
                  roles.indexOf("EXTERNAL-CONSORTIUM-MEMBER") === -1 && (
                    <div
                      className={classes.datasetOption}
                      style={
                        type === "dataset" ? { background: "#f5f5f5" } : {}
                      }
                    >
                      <Button
                        className={classes.menuBtn}
                        onClick={(e) => handleMenuTypeClick("dataset")}
                        variant="text"
                      >
                        {translate("label.dataset")}
                        {/* {authenticate() && <DownIcon color="action" />} */}
                      </Button>
                      {/* {authenticate() &&
                      <MenuItems
                        id={"dataset-menu"}
                        anchorEl={anchorEl}
                        handleClose={handleClose}
                        menuOptions={menuItems.dataset}
                        handleMenuItemClick={handleMenuItemClick}
                      />
                    } */}
                    </div>
                  )}

                <div
                  className={
                    authenticate() ? classes.options : classes.datasetOption
                  }
                >
                  <div
                    className={classes.model}
                    style={type === "models" ? { background: "#f5f5f5" } : {}}
                  >
                    <Button
                      className={classes.menuBtn}
                      variant="text"
                      onClick={(e) => handleMenuTypeClick("models")}
                    >
                      {translate("label.model")}
                      {/* {authenticate() && <DownIcon color="action" />} */}
                    </Button>
                  </div>
                  {/* 
                    <MenuItems
                      id={"dataset-menu"}
                      anchorEl={anchorModel}
                      handleClose={handleClose}
                      menuOptions={menuItems.models}
                      handleMenuItemClick={handleMenuItemClick}
                    /> */}
                </div>
                {roles.indexOf("ADMIN") !== -1 && (
                  <div
                    className={
                      authenticate() ? classes.options : classes.datasetOption
                    }
                  >
                    <div
                      className={classes.model}
                      style={type === "models" ? { background: "#f5f5f5" } : {}}
                    >
                      <Button
                        className={classes.menuBtn}
                        variant="text"
                        onClick={(e) => handleMenuTypeClick("admin")}
                      >
                        {/* {translate("label.a")} */}
                        Admin
                        {/* {authenticate() && <DownIcon color="action" />} */}
                      </Button>
                    </div>
                    {/* 
                    <MenuItems
                      id={"dataset-menu"}
                      anchorEl={anchorModel}
                      handleClose={handleClose}
                      menuOptions={menuItems.models}
                      handleMenuItemClick={handleMenuItemClick}
                    /> */}
                  </div>
                )}
              </>
            }
            {authenticate() ? (
              <div className={classes.profile}>
                <Button
                  onClick={(e) => handleLogoutOption(e)}
                  className={classes.menuBtn}
                >
                  <Avatar
                    className={classes.avatar}
                    variant="contained"
                  >{`${firstName[0].toUpperCase()}`}</Avatar>
                  <Typography
                    variant="body1"
                    color="textPrimary"
                    className={classes.profileName}
                  >{`${firstName}`}</Typography>
                  <DownIcon color="action" />
                </Button>
                <StyledMenu
                  id="data-set"
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
                      handleRedirection("/profile");
                    }}
                  >
                   <img src={person} className="me-2"/> {translate("label.myProfile")}
                  </MenuItem>
                  <MenuItem
                    className={classes.styledMenu}
                    onClick={() => {
                      localStorage.removeItem("userInfo");
                      localStorage.removeItem("userDetails");
                      handleLogOut("/user/login");
                    }}
                  >
                   <img src={logoutIcon} className="me-2"/> {translate("label.logOut")}
                  </MenuItem>
                </StyledMenu>
              </div>
            ) : (
              <div className={classes.profile}>
                <div className={classes.desktopAuth}>
                  <Grid container spacing={2}>
                    <Grid item>
                      {/* <Button
                          size="small"
                          // className={classes.menuBtn}
                          color="default"
                          onClick={() => history.push(`${process.env.PUBLIC_URL}/user/login`)}
                          variant="outlined"
                        >
                          Sign In
                        </Button> */}
                    </Grid>
                    <Grid item>
                    </Grid>
                  </Grid>
                </div>
                <div className={classes.mobileAuth}>
                  {authenticate() && (
                    <Button
                      className={classes.menuBtn}
                      onClick={() =>
                        history.push(`${process.env.PUBLIC_URL}/user/login`)
                      }
                      variant="text"
                    >
                      <GroupIcon />
                    </Button>
                  )}
                </div>
              </div>
            )}
          </div>
        </Toolbar>
      </AppBar>
      {type && (
        <SubHeader
          tabs={menuItems[type]}
          value={index}
          handleChange={handleChange}
        />
      )}
      {open && (
        <Dialog
          title={"Not Signed In"}
          open={open}
          handleClose={() => setOpen(false)}
          message={"Please sign in to continue."}
          handleSubmit={handleLogOut}
          actionButton={"Close"}
          actionButton2={"Sign In"}
        />
      )}
    </MuiThemeProvider>
  );
};

export default withStyles(HeaderStyles)(Header);

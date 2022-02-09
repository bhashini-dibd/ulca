import { Typography } from "@material-ui/core";
import { withStyles } from "@material-ui/styles";
import Modal from "../../components/common/Modal";
import AdminPanelStyle from "../../styles/AdminPanel";
import React, { useState } from "react";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";
import Box from "@material-ui/core/Box";
import EditInfo from "./EditInfo";
import EditProfile from "./EditProfile";
import EditAccount from "./EditAccount";

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`vertical-tabpanel-${index}`}
      aria-labelledby={`vertical-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={0}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `vertical-tab-${index}`,
    "aria-controls": `vertical-tabpanel-${index}`,
    style: {
      width: "5rem",
    },
  };
}

const useStyles = makeStyles((theme) => ({
  root: {
    width: 750,
    backgroundColor: theme.palette.background.paper,
  },
}));

const UpdateUserInfo = (props) => {
  //fetching props
  const {
    open,
    handleClose,
    info,
    handleRoleChange,
    handleOrgChange,
    handleTextFieldChange,
    handleSubmit,
    checkboxState,
    handleCheckBoxClick,
  } = props;

  //declaring and initializing constants
  const classes = useStyles();
  const [value, setValue] = React.useState(0);

  //event handler
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Modal open={open} handleClose={handleClose}>
      <div className={classes.root}>
        <Tabs
          // orientation="vertical"
          value={value}
          onChange={handleChange}
          aria-label="Vertical tabs example"
        >
          <Tab label="Profile" {...a11yProps(0)} />
          <Tab label="Security" {...a11yProps(1)} />
        </Tabs>
        <TabPanel value={value} index={0}>
          <EditInfo handleClose={handleClose} handleSubmit={handleSubmit}>
            <EditProfile
              info={info}
              handleRoleChange={handleRoleChange}
              handleOrgChange={handleOrgChange}
              handleTextFieldChange={handleTextFieldChange}
            />
          </EditInfo>
        </TabPanel>
        <TabPanel value={value} index={1}>
          <EditInfo handleClose={handleClose} handleSubmit={handleSubmit}>
            <EditAccount
              checked={checkboxState}
              handleChange={handleCheckBoxClick}
              handleTextFieldChange={handleTextFieldChange}
            />
          </EditInfo>
        </TabPanel>
      </div>
    </Modal>
  );
};

export default withStyles(AdminPanelStyle)(UpdateUserInfo);

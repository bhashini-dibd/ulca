import MUIDataTable from "mui-datatables";
import EditIcon from "@material-ui/icons/Edit";
import {
  Grid,
  IconButton,
  Tooltip,
  Typography,
  FormGroup,
  FormControlLabel,
  Checkbox,
} from "@material-ui/core";
import { withStyles } from "@material-ui/styles";
import AdminPanelStyle from "../../styles/AdminPanel";
import { useSelector, useDispatch } from "react-redux";
import APITransport from "../../../redux/actions/apitransport/apitransport";
import { useEffect, useState, useRef } from "react";
import UserDetailsAPI from "../../../redux/actions/api/Admin/UserDetails";
import UpdateUserInfo from "./UpdateUserInfo";
import { Switch } from "@material-ui/core";
import UpdateUserDetails from "../../../redux/actions/api/Admin/UpdateUserDetails";
import Snackbar from "../../components/common/Snackbar";
import Search from "../../components/Datasets&Model/Search";
import Filter from "../../components/common/Filter";
import UpdateUserStatus from "../../../redux/actions/api/Admin/UpdateUserStatus";
import searchUserDetails from "../../../redux/actions/api/Admin/SearchUserDetails";

const ViewUserDetail = (props) => {
  //destructuring of props
  const { classes } = props;

  // reducer and action dispatcher intialization
  const data = useSelector((state) => state.getUserDetails.filteredUserDetails);
  const status = useSelector((state) => state.getUserDetails.status);
  const dispatch = useDispatch();
  const filters = useSelector((state) => state.getUserDetails.filters);
  const selectedFilter = useSelector(
    (state) => state.getUserDetails.selectedFilter
  );

  //state initialization
  const [anchorEl, setAnchorEl] = useState(null);
  const popoverOpen = Boolean(anchorEl);
  const id = popoverOpen ? "simple-popover" : undefined;
  const [openModal, setOpenModal] = useState(false);
  const [snackbar, setSnackbar] = useState({
    open: false,
    message: "",
    variant: "success",
    timeOut: 3000,
  });
  const [info, setInfo] = useState({
    userName: "",
    fullName: "",
    role: [],
    orgValue: "",
    pwd: "",
    confirmPwd: "",
  });
  const [checkboxState, setCheckBoxState] = useState(false);
  const [searchState, setSearchState] = useState("");
  const refHook = useRef(false);
  const userDetails = JSON.parse(localStorage.getItem("userDetails"));
  //useEffect when the component is mounted
  useEffect(() => {
    if (status === "Started") {
      makeUserDetailsAPICall();
    }
  }, []);

  useEffect(() => {
    if (!refHook.current) {
      makeUserDetailsAPICall();
      refHook.current = true;
    }
  });

  useEffect(() => {
    return () => {
      refHook.current = false;
    };
  }, []);

  //API Call for fetching user details

  const makeUserDetailsAPICall = () => {
    const objUserDetails = new UserDetailsAPI();
    dispatch(APITransport(objUserDetails));
  };

  //click events
  const handleOpen = (tableData) => {
    setInfo({
      userName: tableData[1],
      fullName: tableData[2],
      role: tableData[3].split(",").map((data) => {
        return { label: data, value: data };
      }),
      orgValue: { label: tableData[4], value: tableData[4] },
    });
    setOpenModal(true);
  };

  const handleRoleChange = (data, val) => {
    setInfo({
      ...info,
      role: data,
    });
  };

  const handleOrgChange = (data, val) => {
    setInfo({
      ...info,
      orgValue: data,
    });
  };

  const handleClose = () => {
    setCheckBoxState(false);
    setOpenModal(false);
  };

  const handleChange = (email, status) => {
    const obj = new UpdateUserStatus(email, !status);
    fetch(obj.apiEndPoint(), {
      method: "post",
      headers: obj.getHeaders().headers,
      body: JSON.stringify(obj.getBody()),
    }).then(async (res) => {
      let rsp_data = await res.json();
      if (!res.ok) {
        setSnackbar({
          message: rsp_data.message,
          open: true,
          variant: "error",
        });
      } else {
        const objUserDetails = new UserDetailsAPI();
        fetch(objUserDetails.apiEndPoint(), {
          method: "post",
          headers: objUserDetails.getHeaders().headers,
          body: JSON.stringify(objUserDetails.getBody()),
        }).then(async (res) => {
          let rsp_data = await res.json();
          if (res.ok) {
            dispatch({
              type: "TOGGLE_USER_STATUS",
              payload: {
                data: rsp_data.data,
                searchState,
              },
            });
          } else {
            setSnackbar({
              message: rsp_data.message,
              open: true,
              variant: "error",
            });
          }
        });
        setSnackbar({
          message: rsp_data.message,
          open: true,
          variant: "success",
        });
      }
    });
  };

  const handleTextFieldChange = (value, prop) => {
    setInfo({
      ...info,
      [prop]: value,
    });
  };

  const handleCheckBoxClick = (e) => {
    setCheckBoxState(e.target.checked);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!info.fullName.trim() || !info.orgValue || !info.role.length) {
      setSnackbar({
        ...snackbar,
        variant: "error",
        message: "Profile details are mandatory",
        open: true,
      });
    } else {
      if (checkboxState) {
        if (!info.pwd || !info.confirmPwd) {
          setSnackbar({
            ...snackbar,
            variant: "error",
            message: "Please fill password",
            open: true,
          });
        } else if (info.pwd !== info.confirmPwd) {
          setSnackbar({
            ...snackbar,
            variant: "error",
            message: "Password and Confirm Password is different",
            open: true,
          });
        } else {
          updateUserDetailAPI();
          handleClose();
        }
      } else {
        updateUserDetailAPI();
        handleClose();
      }
    }
  };

  const handleSnackbarClose = () => {
    setSnackbar({
      ...snackbar,
      message: "",
      open: false,
    });
  };

  const handleSearch = (event) => {
    setSearchState(event.target.value);
    dispatch(searchUserDetails(event.target.value));
  };

  const handleShowFilter = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleFilterClose = () => {
    setAnchorEl(null);
  };

  //API Call for updating user details
  const updateUserDetailAPI = () => {
    let userInfo = {
      firstName: info.fullName,
      email: info.userName,
      roles: info.role.map((val) => val.label),
    };
    if (checkboxState) {
      userInfo.password = info.confirmPwd;
    }
    const obj = new UpdateUserDetails(userInfo);
    fetch(obj.apiEndPoint(), {
      method: "post",
      headers: obj.getHeaders().headers,
      body: JSON.stringify(obj.getBody()),
    }).then(async (res) => {
      let rsp_data = await res.json();
      if (res.ok) {
        makeUserDetailsAPICall();
        setSnackbar({
          ...snackbar,
          variant: "success",
          message: rsp_data.message,
          open: true,
        });
      } else {
        setSnackbar({
          ...snackbar,
          variant: "error",
          message: rsp_data.message,
          open: true,
        });
      }
      setTimeout(() => {
        setSnackbar({
          ...snackbar,
          open: false,
        });
      }, 3000);
    });
  };

  //function to render the action button in the table
  const renderActions = (tableData) => {
    return (
      <Grid container spacing={1}>
        <Grid
          item
          xs={12}
          sm={6}
          md={6}
          lg={6}
          xl={6}
          className={classes.switchGrid}
        >
          <Tooltip placement="left" title="Active/Inactive">
            <Switch
              checked={tableData[6]}
              onChange={() => handleChange(tableData[1], tableData[6])}
              color="primary"
              name="checkedB"
              inputProps={{ "aria-label": "primary checkbox" }}
            />
          </Tooltip>
        </Grid>
        <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
          <IconButton
            onClick={() => handleOpen(tableData)}
            disabled={!tableData[6]}
          >
            <Tooltip placement="right" title="Edit Details">
              <EditIcon fontSize="medium" />
            </Tooltip>
          </IconButton>
        </Grid>
      </Grid>
    );
  };

  const isChecked = (type, property) => {
    return selectedFilter[property].indexOf(type) > -1 ? false : true;
  };

  const isDisabled = () => {
    const keys = Object.keys(selectedFilter);
    for (let i = 0; i < keys.length; i++) {
      if (selectedFilter[keys[i]].length > 0) {
        return false;
      }
    }
    return true;
  };

  //render filter options
  const renderFilterOptions = (property) => {
    return (
      <FormGroup>
        {filters[property].map((type) => {
          return (
            <FormControlLabel
              control={
                <Checkbox
                  name={type}
                  color="primary"
                  onChange={() => {
                    dispatch({
                      type: "SELECT_ADMIN_FILTER",
                      payload: {
                        type: property,
                        value: type,
                      },
                    });
                  }}
                />
              }
              label={type}
            />
          );
        })}
      </FormGroup>
    );
  };

  //render Custom Toolbar
  const renderToolbar = () => {
    return (
      <Grid container>
        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
          <Search searchValue={searchState} handleSearch={handleSearch} />
        </Grid>
        {/* <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
          <Button
            color={"default"}
            size="medium"
            variant="outlined"
            style={{ borderRadius: "20px" }}
            onClick={handleShowFilter}
          >
            {" "}
            <FilterListIcon className={classes.iconStyle} />
            Filter
          </Button>
        </Grid> */}
      </Grid>
    );
  };

  const convertDate = (date) => {
    return date
      .toLocaleString("en-IN", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
        hour: "numeric",
        minute: "numeric",
        second: "numeric",
        hour12: false,
      })
      .toUpperCase();
  };

  const handleClear = () => {
    dispatch({
      type: "CLEAR_ADMIN_FILTER",
    });
  };

  //columns to be displayed in the table
  const columns = [
    { name: "uId", label: "UID", options: { display: "excluded" } },
    {
      name: "userId",
      label: "User ID",
      options: {
        sort: false,
        customBodyRender: (rowData) => {
          return (
            <Typography variant="body2" className={classes.userIdTypo}>
              {rowData}
            </Typography>
          );
        },
      },
    },
    { name: "name", label: "Name", options: { sort: false } },
    { name: "role", label: "Role", options: { sort: false } },
    { name: "org", label: "Organization", options: { sort: false } },
    {
      name: "createdOn",
      label: "Timestamp",
      options: {
        sort: false,
        customBodyRender: (rowData) => {
          return <div>{convertDate(rowData)}</div>;
        },
        sortDirection: "desc",
      },
    },
    {
      name: "isActive",
      label: "Active/Inactive",
      options: { display: "excluded" },
    },
    {
      name: "action",
      label: "Action",
      options: {
        sort: false,
        customBodyRender: (value, tableMeta, updateValue) => {
          return userDetails && tableMeta.rowData[1] !== userDetails.email ? (
            renderActions(tableMeta.rowData)
          ) : (
            <></>
          );
        },
      },
    },
  ];

  //options to customize table rendering
  const options = {
    print: false,
    download: false,
    filter: false,
    viewColumns: false,
    selectableRows: false,
    search: false,
    customToolbar: renderToolbar,
  };

  return (
    <>
      <MUIDataTable
        title="User Details"
        columns={columns}
        data={data}
        options={options}
      />
      {openModal && (
        <UpdateUserInfo
          open={openModal}
          handleClose={handleClose}
          info={info}
          handleTextFieldChange={handleTextFieldChange}
          handleRoleChange={handleRoleChange}
          handleOrgChange={handleOrgChange}
          handleSubmit={handleSubmit}
          checkboxState={checkboxState}
          handleCheckBoxClick={handleCheckBoxClick}
          handleSnackbarClose={handleSnackbarClose}
        />
      )}
      {snackbar && (
        <Snackbar
          open={snackbar.open}
          message={snackbar.message}
          variant={snackbar.variant}
          hide={snackbar.timeOut}
          handleClose={handleSnackbarClose}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
        />
      )}
      {popoverOpen && (
        <Filter
          id={id}
          open={popoverOpen}
          anchorEl={anchorEl}
          handleClose={handleFilterClose}
          selectedFilter={selectedFilter}
          handleClear={handleClear}
          isDisabled={isDisabled()}
        >
          <Grid container className={classes.filterContainer}>
            <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
              <Typography variant="h6">Role</Typography>
              {renderFilterOptions("roles")}
            </Grid>
            <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
              <Typography variant="h6">Organisation</Typography>
              {renderFilterOptions("org")}
            </Grid>
          </Grid>
        </Filter>
      )}
    </>
  );
};

export default withStyles(AdminPanelStyle)(ViewUserDetail);

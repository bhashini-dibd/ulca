import MUIDataTable from "mui-datatables";
import EditIcon from "@material-ui/icons/Edit";
import { Grid, IconButton, Tooltip, Typography } from "@material-ui/core";
import { withStyles } from "@material-ui/styles";
import AdminPanelStyle from "../../styles/AdminPanel";
import { useSelector, useDispatch } from "react-redux";
import APITransport from "../../../redux/actions/apitransport/apitransport";
import { useEffect, useState } from "react";
import UserDetailsAPI from "../../../redux/actions/api/Admin/UserDetails";
import UpdateUserInfo from "./UpdateUserInfo";
import { Switch } from "@material-ui/core";
import { roles } from "../../../configs/AdminConfig";

const ViewUserDetail = (props) => {
  //destructuring of props
  const { classes } = props;

  // reducer and action dispatcher intialization
  const data = useSelector((state) => state.getUserDetails.userDetails);
  const status = useSelector((state) => state.getUserDetails.status);
  const dispatch = useDispatch();

  //state initialization
  const [openModal, setOpenModal] = useState(false);
  const [info, setInfo] = useState({
    userName: "",
    fullName: "",
    role: [],
    orgValue: "",
    pwd: "",
    confirmPwd: "",
  });
  //useEffect when the component is mounted
  useEffect(() => {
    if (status === "Started") {
      const objUserDetails = new UserDetailsAPI();
      dispatch(APITransport(objUserDetails));
    }
  }, []);

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
    setOpenModal(false);
  };

  const handleTextFieldChange = (value, prop) => {
    setInfo({
      ...info,
      [prop]: value,
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
              // onChange={handleChange}
              color="primary"
              name="checkedB"
              inputProps={{ "aria-label": "primary checkbox" }}
            />
          </Tooltip>
        </Grid>
        <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
          <IconButton onClick={() => handleOpen(tableData)}>
            <Tooltip placement="right" title="Edit Details">
              <EditIcon fontSize="medium" />
            </Tooltip>
          </IconButton>
        </Grid>
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
          return renderActions(tableMeta.rowData);
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
        />
      )}
    </>
  );
};

export default withStyles(AdminPanelStyle)(ViewUserDetail);

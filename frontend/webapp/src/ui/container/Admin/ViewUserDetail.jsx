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

const ViewUserDetail = (props) => {
  //destructuring of props
  const { classes } = props;

  // reducer and action dispatcher intialization
  const data = useSelector((state) => state.getUserDetails.userDetails);
  const status = useSelector((state) => state.getUserDetails.status);
  const dispatch = useDispatch();

  //state initialization
  const [openModal, setOpenModal] = useState(false);

  //useEffect when the component is mounted
  useEffect(() => {
    if (status === "Started") {
      const objUserDetails = new UserDetailsAPI();
      dispatch(APITransport(objUserDetails));
    }
  }, []);

  //click events

  const handleOpen = () => {
    setOpenModal(true);
  };

  const handleClose = () => {
    setOpenModal(false);
  };

  //function to render the action button in the table
  const renderActions = (isActive) => {
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
              checked={isActive}
              // onChange={handleChange}
              color="primary"
              name="checkedB"
              inputProps={{ "aria-label": "primary checkbox" }}
            />
          </Tooltip>
        </Grid>
        <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
          <IconButton onClick={handleOpen}>
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
          return <Typography variant="body2" className={classes.userIdTypo}>{rowData}</Typography>;
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
          return renderActions(tableMeta.rowData[6]);
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
        <UpdateUserInfo open={openModal} handleClose={handleClose} />
      )}
    </>
  );
};

export default withStyles(AdminPanelStyle)(ViewUserDetail);

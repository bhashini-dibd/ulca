import MUIDataTable from "mui-datatables";
import EditIcon from "@material-ui/icons/Edit";
import { Grid, IconButton, Tooltip } from "@material-ui/core";
import { withStyles } from "@material-ui/styles";
import AdminPanelStyle from "../../styles/AdminPanel";
import { useSelector, useDispatch } from "react-redux";
import APITransport from "../../../redux/actions/apitransport/apitransport";
import { useEffect } from "react";
import UserDetailsAPI from "../../../redux/actions/api/Admin/UserDetails";

const ViewUserDetail = (props) => {
  // reducer and action dispatcher intialization
  const data = useSelector((state) => state.getUserDetails.userDetails);
  const dispatch = useDispatch();

  //useEffect when the component is mounted
  useEffect(() => {
    const objUserDetails = new UserDetailsAPI();
    dispatch(APITransport(objUserDetails));
  }, []);

  //function to render the action button in the table
  const renderActions = () => {
    return (
      <Grid container spacing={1}>
        <Grid item xs={12} sm={6} md={6} lg={6} xl={6}>
          <IconButton>
            <Tooltip placement="right" title="Edit Details">
              <EditIcon fontSize="small" />
            </Tooltip>
          </IconButton>
        </Grid>
      </Grid>
    );
  };

  //columns to be displayed in the table
  const columns = [
    { name: "uId", label: "UID", options: { display: "excluded" } },
    { name: "userId", label: "User ID", options: { sort: false } },
    { name: "name", label: "Name", options: { sort: false } },
    { name: "role", label: "Role", options: { sort: false } },
    { name: "org", label: "Organization", options: { sort: false } },
    { name: "createdOn", label: "Timestamp", options: { sort: false } },
    {
      name: "action",
      label: "Action",
      options: {
        sort: false,
        customBodyRender: (rowData) => {
          return renderActions();
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
    <MUIDataTable
      title="User Details"
      columns={columns}
      data={data}
      options={options}
    />
  );
};

export default withStyles(AdminPanelStyle)(ViewUserDetail);

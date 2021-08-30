import MUIDataTable from "mui-datatables";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core/styles";
import CloseIcon from "@material-ui/icons/Close";
import { IconButton } from "@material-ui/core";

const BenchmarkModal = (props) => {
  const getMuiTheme = () =>
    createMuiTheme({
      overrides: {
        MUIDataTable: {
          paper: {
            padding: "17px 21px 21px 20px",
            width: "64.375rem",
          },
          responsiveBase: {
            minHeight: "35rem",
          },
        },
        MuiTableCell: {
          head: {
            padding: ".6rem .5rem .6rem 1.5rem",
            backgroundColor: "#F8F8FA !important",
            marginLeft: "25px",
            letterSpacing: "0.74",
            fontWeight: "bold",
            minHeight: "700px",
          },
        },
        MuiToolbar: {
          root: {
            marginTop: "18px",
          },
          gutters: {
            padding: "0",
            "@media (min-width:600px)": {
              paddingLeft: "0",
              paddingRight: "0",
            },
          },
        },
      },
    });

  return (
    <div
      style={{
        position: "absolute",
        left: "20%",
        top: "10%",
        width: "1030px",
      }}
    >
      <IconButton
        style={{ position: "inherit", left: "98%" }}
        onClick={props.handleCloseModal}
      >
        <CloseIcon color="action" />
      </IconButton>
      <MuiThemeProvider theme={getMuiTheme()}>
        <MUIDataTable
          columns={props.columns}
          options={props.options}
          title={"Select Benchmark Dataset and Metric \n "}
        ></MUIDataTable>
      </MuiThemeProvider>
    </div>
  );
};

export default BenchmarkModal;

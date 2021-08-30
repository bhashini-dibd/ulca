import MUIDataTable from "mui-datatables";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core/styles";

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
      <MuiThemeProvider theme={getMuiTheme()}>
        <MUIDataTable
          columns={props.columns}
          options={props.options}
          title={"Select Benchmark Dataset and Metric"}
        ></MUIDataTable>
      </MuiThemeProvider>
    </div>
  );
};

export default BenchmarkModal;

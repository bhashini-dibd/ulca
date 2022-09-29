import { ThemeProvider } from "@material-ui/core";
import MUIDataTable from "mui-datatables"
import themeDefault from "../../theme/theme-default";

const DataTable = (props) => <ThemeProvider theme={themeDefault}>
    <MUIDataTable {...props} />
</ThemeProvider>

export default DataTable;
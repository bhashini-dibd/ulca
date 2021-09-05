import { withStyles, Typography, Card, Grid,ButtonBase } from "@material-ui/core";
import DataSet from "../../../styles/Dataset";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

const ExpandTable = (props) => {

    const { classes,data,handleCardClick } = props;
    const renderTable = () =>{
        return (
            <TableRow>
            <TableCell>
             
            </TableCell>
            <TableCell>
             
             </TableCell>
            <TableCell>
              Custom expandable
            </TableCell>
            <TableCell>
              Custom 
            </TableCell>
            <TableCell>
              Custom 
            </TableCell>
            <TableCell>
              Custom 
            </TableCell>
            <TableCell>
              Custom 
            </TableCell>
            <TableCell>
             
            </TableCell>
            
          </TableRow>
        )
    }

    return (
        <>
       {renderTable()}
      </>
            
            
    );
};

export default withStyles(DataSet)(ExpandTable);

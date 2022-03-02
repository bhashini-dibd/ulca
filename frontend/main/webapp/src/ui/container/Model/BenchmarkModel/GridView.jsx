import { withStyles, Grid } from "@material-ui/core";
import DataSet from "../../../styles/Dataset";
import CardComponent from "../../../components/common/CardComponent";
import TablePagination from "@material-ui/core/TablePagination";

const GridView = (props) => {
  const {
    classes,
    data,
    page,
    handleRowsPerPageChange,
    rowsPerPage,
    handleCardClick,
    onPageChange
  } = props;
  const renderGrid = () => {
    return (
      <Grid container>
        {data.filteredData.map((element, i) => {
          if (i >= page * rowsPerPage && i < page * rowsPerPage + rowsPerPage) {
            return (
              <Grid item xs={12} sm={12} md={4} lg={4} xl={4} key={i}>
                <CardComponent
                  index={i}
                  data={element}
                  onClick={handleCardClick}
                />
              </Grid>
            );
          }
        })}
      </Grid>
    );
  };

  return (
    <>
      <div className={classes.gridHeader}></div>
      {data.filteredData.length > 0 && renderGrid()}
    <TablePagination
        component="div"
        count={data.filteredData.length}
        page={page}
        onRowsPerPageChange={handleRowsPerPageChange}
        rowsPerPage={rowsPerPage}
        rowsPerPageOptions={[9, 12, 18]}
        onPageChange={onPageChange}
      />
    </>
  );
};

export default withStyles(DataSet)(GridView);

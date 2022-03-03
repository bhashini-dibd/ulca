import { withStyles, Typography, Card, Grid } from "@material-ui/core";
import DataSet from "../../../styles/Dataset";
import { Paper, Button } from "@material-ui/core";
import { Cached, DeleteOutline, FilterList, GridOn, ViewList } from '@material-ui/icons';
import ContributionCard from "./ContributionCard"
import TablePagination from '@material-ui/core/TablePagination';

const GridView = (props) => {

    const { classes, data, handleViewChange, rowChange, handleShowFilter, MyContributionListApi, view, page, handleChangePage, rowsPerPage, count, handleCardClick } = props;
    const renderGrid = () => {
        return data.map((element, i) => {
            if (i >= page * rowsPerPage && i < page * rowsPerPage + rowsPerPage) {
                return <ContributionCard key={i} data={element} handleCardClick={handleCardClick}></ContributionCard>
            }

        })
    }

    const fetchHeaderButton = () => {
        return <>

            {/* <Button color={"default"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={handleShowFilter}> <FilterList className={classes.iconStyle} />Filter</Button> */}
            <Button color={"primary"} size="medium" variant="outlined" className={classes.ButtonRefresh} onClick={MyContributionListApi}><Cached className={classes.iconStyle} />Refresh</Button>
            <Button color={"default"} size="medium" variant="default" className={classes.buttonStyle} onClick={handleViewChange}> {view ? <ViewList size={"large"} /> : <GridOn />}</Button>


        </>
    }
   
    return (
        <Paper className={classes.paper}>
            <div className={classes.gridHeader}>
                <Typography variant={'h6'} className={classes.gridTypo}>My Contribution</Typography>
                {fetchHeaderButton()}
            </div>
            <div className={classes.gridData}>
                {data.length > 0 && renderGrid()}
            </div>
            <TablePagination
                component="div"
                count={data.length}
                page={page}
                onChangePage={handleChangePage}
                rowsPerPage={rowsPerPage}
                onChangeRowsPerPage={rowChange}
            />
        </Paper>

    );
};

export default withStyles(DataSet)(GridView);
import FindInPageIcon from '@material-ui/icons/FindInPage';
import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../styles/Dataset';
import {
    Typography
} from '@material-ui/core';
const SearchResult = (props) => {
    const {classes}=props;
    return (
        <div className={classes.searchResult}>
            <FindInPageIcon className={classes.FindInPageIcon} color="primary" />
            <Typography color="primary" variant="h5">Your search result will appear here  </Typography>
        </div>
    )
}

export default withStyles(DatasetStyle)(SearchResult);
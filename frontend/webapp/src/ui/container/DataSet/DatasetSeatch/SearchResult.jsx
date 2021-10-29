
import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../styles/Dataset';
import searchIcon from "../../../../assets/Group 10143.svg"
import {
    Typography
} from '@material-ui/core';
import { translate } from '../../../../assets/localisation';
const SearchResult = (props) => {
    const { classes } = props;
    return (
        <div className={classes.searchResult}>
            <div>
                <img
                    src={searchIcon}
                    alt="Search Icon"
                />
                {/* <SearchRoundedIcon className={classes.FindInPageIcon} color="primary" /> */}
                <Typography variant="h5" color="textSecondary">{translate("label.searchResultHere")}</Typography>
            </div>
        </div>
    )
}

export default withStyles(DatasetStyle)(SearchResult);
import {
    Grid,
    Typography,
    Button,
    Paper
} from '@material-ui/core';
import { withStyles, MuiThemeProvider } from '@material-ui/core/styles';
import DatasetStyle from '../../../styles/Dataset';
import Header from '../../../components/common/Header';
import aunthenticate from '../../../../configs/authenticate';
import NewSearchModel from './NewSearchModel';
import Theme from '../../../theme/theme-default';
const ExploreModels = (props) => {
    const { classes } = props;
    return (
        <MuiThemeProvider theme={Theme}>
            <><Header style={{ marginBottom: "10px" }} /><br /><br /><br /> </>
            <div className={classes.parentPaper}>
                <Paper elevation={0} className={classes.mainPaper}>
                    <Typography variant="h5">Explore Models</Typography>
                    <NewSearchModel />
                </Paper>
            </div>
        </MuiThemeProvider>
    )


}

export default withStyles(DatasetStyle)(ExploreModels);



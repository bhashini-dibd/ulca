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
import Footer from "../../../components/common/Footer";
const ExploreModels = (props) => {
    const { classes } = props;
    return (
        <MuiThemeProvider theme={Theme}>
            <Header style={{ marginBottom: "10px" }} />
            <div className={classes.parentPaper}>
                <Paper elevation={0} className={classes.mainPaper}>
                    {!aunthenticate() && <Typography variant="h3">Explore Models</Typography>}
                    <NewSearchModel />
                </Paper>
            </div>
            <Footer/>
        </MuiThemeProvider>
    )


}

export default withStyles(DatasetStyle)(ExploreModels);



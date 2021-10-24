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
    import {translate} from '../../../../assets/localisation';

    const ReadymadeDataset = (props) => {
        const { classes } = props;
        return (
            
                <div className={classes.parentPaper}>
                    <Paper elevation={0} className={classes.mainPaper}>
                        {!aunthenticate() && <Typography variant="h3">{translate("label.readymadeDataset")}</Typography>}
                        <NewSearchModel />
                    </Paper>
                </div>
               
        )
    
    
    }
    
    export default withStyles(DatasetStyle)(ReadymadeDataset);
    
    
    
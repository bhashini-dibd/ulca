import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { useHistory, useParams } from 'react-router';
import {
    Grid,
    Typography
} from '@material-ui/core';

const ModelDescription = (props) => {
    const { classes, title, para } = props;
    const history = useHistory();

    return (
        <div style={{maxWidth:'624px'}}>
            <Typography variant="h6" className={classes.modelTitle}>{title}</Typography>
            <Typography className={classes.gridCompute}>{para}</Typography>

        </div>
    )
}
export default withStyles(DatasetStyle)(ModelDescription);
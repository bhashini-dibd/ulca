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
            <Typography>{title}</Typography>
            {para.map(paragraph => <Typography>{paragraph}</Typography>)}

        </div>
    )
}
export default withStyles(DatasetStyle)(ModelDescription);
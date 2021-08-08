import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { useHistory, useParams } from 'react-router';
import {
    Grid,
    Link,
    Typography
} from '@material-ui/core';

const ModelDescription = (props) => {
    const { classes, title, para } = props;
    const history = useHistory();
    return (
        <div>
            <Typography variant="h6" className={classes.modelTitle}>{title}</Typography>
            {title !=="Source URL" || para==="NA"?
            <Typography className={classes.modelPara}>{para}</Typography>:
            <Link href={para}>
            {para}</Link>}

        </div>
    )
}
export default withStyles(DatasetStyle)(ModelDescription);
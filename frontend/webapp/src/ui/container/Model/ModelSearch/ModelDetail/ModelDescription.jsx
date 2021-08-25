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
            {title !== "Source URL" || para === "NA" ?
                <Typography style={{ fontSize: '20px', fontFamily: 'Roboto', textAlign: "justify" }} className={classes.modelPara}>{para}</Typography> :
                <Typography style={{ marginTop: '15px' }}><Link style={{ color: "#3f51b5", fontSize: '20px', }} variant="body2" href={para}>
                    {para}</Link></Typography>}

        </div>
    )
}
export default withStyles(DatasetStyle)(ModelDescription);
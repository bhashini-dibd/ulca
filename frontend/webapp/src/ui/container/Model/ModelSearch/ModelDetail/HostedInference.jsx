import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { useHistory } from 'react-router';
import InfoOutlinedIcon from '@material-ui/icons/InfoOutlined';
import {
    Grid,
    Typography,
    TextField,
    Button
} from '@material-ui/core';

const HostedInference = (props) => {
    const { classes, title, para } = props;
    const history = useHistory();

    return (
        <div>
            <Typography className={classes.hosted}>Hosted inference API {< InfoOutlinedIcon className={classes.buttonStyle} fontSize="small" color="disabled" />}</Typography>
            <Grid container spacing={2}>
                <Grid className={classes.gridCompute} item xl={8} lg={8} md={8} sm={8} xs={8}>
                    <TextField fullWidth
                        color="primary"
                        label="Enter Text"
                    // value={model.modelName}
                    // error={error.name ? true : false}
                    // helperText={error.name}
                    // onChange={(e) => {
                    //     setModelInfo({ ...model, modelName: e.target.value })
                    //     setError({ ...error, name: false })
                    // }}
                    />
                </Grid>
                <Grid item xl={4} lg={4} md={4} sm={4} xs={4} className={classes.computeGrid}>
                    <Button
                        color="primary"
                        className={classes.computeBtn}
                        variant="contained"
                        size={'small'}

                    // onClick={handleCompute}
                    >
                        Compute
                    </Button>
                </Grid>
            </Grid>
        </div>

    )
}
export default withStyles(DatasetStyle)(HostedInference);
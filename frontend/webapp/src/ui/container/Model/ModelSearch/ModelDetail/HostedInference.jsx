import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { useHistory } from 'react-router';
import InfoOutlinedIcon from '@material-ui/icons/InfoOutlined';
import {
    Grid,
    Typography,
    TextField,
    Button,
    CardContent,Card
} from '@material-ui/core';
import { useState } from 'react';

const HostedInference = (props) => {
    const { classes, title, para } = props;
    const history = useHistory();
    const [translation, setTranslationState] = useState(false)
    const handleCompute = ()=>setTranslationState(true);
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

                    onClick={handleCompute}
                    >
                        Compute
                    </Button>
                </Grid>

                {translation &&
                    // <Grid item xl={11} lg={11} md={12} sm={12} xs={12}>
                        <Card style={{backgroundColor:'#139D601A',color:'black',heigth:'50px',width:'440px'}}>
                            <CardContent style={{paddingBottom:'16px'}}>
                                This is my translated text
                            </CardContent>
                        </Card>
                    // </Grid>
                }
            </Grid>
        </div>

    )
}
export default withStyles(DatasetStyle)(HostedInference);
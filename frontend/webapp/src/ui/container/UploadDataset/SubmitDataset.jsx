import { Grid, Container, Card, CardContent, Typography, Divider, FormControl, Button, TextField, Hidden } from '@material-ui/core'
import BreadCrum from '../../components/common/Breadcrum';
import { makeStyles } from '@material-ui/core/styles';
import { RadioButton, RadioGroup } from 'react-radio-buttons';

const useStyles = makeStyles((theme) => ({
    root: {
        padding: 0,
        margin: '0rem 23rem 0rem 17rem',
        maxWidth: '72.5rem'
    },
    paper: {
        marginTop: '1vh',
        // padding: '1vw'
    },
    divStyle: {
        padding: '3vh 3vw 5vw 6vw'
    },
    title: {
        marginBottom: '3vh'
    },
    form: {
        marginTop: '1vh',
        width: '100%',
    },
    radioGroup: {
        marginTop: '1vh',
        paddingRight: '2vw'
    },

    updateBtn: {
        backgroundColor: "#F8F8F8",
        border: '1px solid black',
        display: 'block',
        marginLeft: 'auto',
    },
    submitBtn: {
        marginTop: '6vh',
        color: 'white',
        backgroundColor: 'black'
    }
}));

const SubmitDataset = () => {
    const classes = useStyles();
    return (
        <div className={classes.root}>
            <BreadCrum links={["Dataset"]} activeLink="Submit Dataset" />
            <Card className={classes.paper}>
                <CardContent>
                    <div className={classes.divStyle}>
                        <Grid container className={classes.title}>
                            <Grid item>
                                <Typography variant="b" component="h2">Submit Dataset</Typography>
                            </Grid>
                        </Grid>
                        <Grid container spacing={5}>
                            <Grid item xl={5} lg={5} md={5} sm={5} xs={12}>
                                <Typography color="textSecondary" variant="subtitle1">STEP-1</Typography>
                                <FormControl className={classes.form}>
                                    <Typography className={classes.typography} variant="b" component="h4">Select Dataset Type</Typography>
                                    <RadioGroup className={classes.radioGroup} vertical>
                                        <RadioButton rootColor="black" pointColor="black" value="apple">
                                            Parallel Dataset
                                    </RadioButton>
                                        <RadioButton rootColor="black" pointColor="black" value="orange">
                                            Monolingual Dataset
                                    </RadioButton>
                                        <RadioButton rootColor="black" pointColor="black" value="melon">
                                            ASR/TTS Dataset
                                    </RadioButton>
                                        <RadioButton rootColor="black" pointColor="black" value="melon">
                                            OCR Dataset
                                    </RadioButton>
                                    </RadioGroup>
                                </FormControl>
                            </Grid>
                            <Hidden only="xs">
                            <Grid item xl={1} lg={1} md={1} sm={1} xs={1}>
                                <Divider orientation="vertical" />
                            </Grid>
                            </Hidden>
                            <Grid item xl={6} lg={6} md={6} sm={6} xs={12}>
                                <Typography color="textSecondary" variant="subtitle1">STEP-2</Typography>
                                <FormControl className={classes.form}>
                                    <Grid container spacing={3}>
                                        <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                            <Grid container spacing={5}>
                                                <Grid item xl={4} lg={4} md={4} sm={4} xs={4}>
                                                    <Typography variant="b" component="h4">Parallel Dataset</Typography>
                                                </Grid>
                                                <Grid item xl={8} lg={8} md={8} sm={8} xs={8}>
                                                    <Button className={classes.updateBtn} color="inherit" variant="contained">Update an existing dataset</Button>
                                                </Grid>
                                            </Grid>
                                        </Grid>
                                        <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                            <Grid container spacing={3}>
                                                <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                                    <TextField fullWidth variant="outlined" color="secondary" label="Dataset name" />
                                                </Grid>
                                                <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                                    <TextField fullWidth variant="outlined" color="secondary" label="Paste the URL of the public repository" />
                                                </Grid>
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                    <Button className={classes.submitBtn} fullWidth variant="contained">Submit</Button>
                                </FormControl>
                            </Grid>
                        </Grid>
                    </div>
                </CardContent>
            </Card>
        </div>
    )
}

export default SubmitDataset;
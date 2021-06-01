import { Grid, Paper, Typography, Divider, FormControl, Button, TextField, Hidden } from '@material-ui/core'
import BreadCrum from '../../../components/common/Breadcrum';
import { withStyles } from '@material-ui/core/styles';
import { RadioButton, RadioGroup } from 'react-radio-buttons';
import DatasetStyle from '../../../styles/Dataset';
import { useState } from 'react';

const SubmitDataset = (props) => {
    const { classes } = props;
    const [anchorEl, setAnchorEl] = useState(null);
    return (
        <div className={classes.root}>
            <div className={classes.breadcrum}>
                <BreadCrum links={["Dataset"]} activeLink="Submit Dataset" />
            </div>
            <Paper elevation={3} className={classes.paper}>
                <Grid container className={classes.title}>
                    <Grid item>
                        <Typography variant="b" component="h2">Submit Dataset</Typography>
                    </Grid>
                </Grid>
                <Grid container spacing={5}>
                    <Grid item xs={12} sm={12} md={5} lg={5} xl={5}>
                        <Typography color="textSecondary" variant="subtitle1">STEP-1</Typography>
                        <FormControl className={classes.form}>
                            <Typography className={classes.typography} variant="b" component="h4">Select Dataset Type</Typography>
                            <RadioGroup className={classes.radioGroup} vertical>
                                <RadioButton rootColor="grey" pointColor="black" value="apple">
                                    Parallel Dataset
                                    </RadioButton>
                                <RadioButton rootColor="grey" pointColor="black" value="orange">
                                    Monolingual Dataset
                                    </RadioButton>
                                <RadioButton rootColor="grey" pointColor="black" value="melon">
                                    ASR/TTS Dataset
                                    </RadioButton>
                                <RadioButton rootColor="grey" pointColor="black" value="melon">
                                    OCR Dataset
                                    </RadioButton>
                            </RadioGroup>
                        </FormControl>
                    </Grid>
                    <Hidden>
                        <Grid item xl={1} lg={1} md={1} sm={1} xs={1}>
                            <Divider orientation="vertical" />
                        </Grid>
                    </Hidden>
                    <Grid item xs={12} sm={12} md={6} lg={6} xl={6}>
                        <Typography color="textSecondary" variant="subtitle1">STEP-2</Typography>
                        <FormControl className={classes.form}>
                            <Grid container spacing={3}>
                                <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                    <Grid container spacing={5}>
                                        <Grid item xl={4} lg={4} md={4} sm={4} xs={4}>
                                            <Typography variant="b" component="h4">Parallel Dataset</Typography>
                                        </Grid>
                                        <Grid item xl={8} lg={8} md={8} sm={8} xs={8}>
                                            <Button
                                                className={classes.updateBtn}
                                                color="inherit"
                                                variant="contained"
                                                onClick={(e) => setAnchorEl(e.currentTarget)}
                                            >
                                                Update an existing dataset
                                            </Button>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                    <Grid container spacing={3}>
                                        <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                            <TextField fullWidth variant="outlined" color="primary" label="Dataset name" />
                                        </Grid>
                                        <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                                            <TextField fullWidth variant="outlined" color="primary" label="Paste the URL of the public repository" />
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Button className={classes.submitBtn} variant="contained" color="primary">Submit</Button>
                        </FormControl>
                    </Grid>
                </Grid>
            </Paper>
        </div>
    )
}

export default withStyles(DatasetStyle)(SubmitDataset);
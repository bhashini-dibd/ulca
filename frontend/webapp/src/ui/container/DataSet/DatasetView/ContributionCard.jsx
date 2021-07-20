import { withStyles, Typography, Card, Grid } from "@material-ui/core";
import DataSet from "../../../styles/Dataset";

const ContributionCard = (props) => {

    const { classes } = props;
    return (
        <div>
            <Card className={classes.contriCard}>
                <Grid container className={classes.container}>
                    <Grid item xs={12} sm={12} md={12} lg={12} xl={12} >
                        <Typography variant="body2" className={classes.countTypo}>{props.sid}</Typography>
                    </Grid>
                    <Grid item xs={12} sm={12} md={12} lg={12} xl={12} >
                        <Typography variant="body2" className={classes.nameTypo}>{props.name}</Typography>
                    </Grid>
                    <Grid item xs={4} sm={4} md={4} lg={4} xl={4} >
                        <Typography variant="body2" className={classes.dateTypo}>{props.date}</Typography>
                    </Grid>
                    <Grid item xs={8} sm={8} md={8} lg={8} xl={8}>
                        <Typography variant="body2" className={classes.dateTypo}>{props.status}</Typography>
                    </Grid>
                </Grid>
            </Card>
        </div>

    );
};

export default withStyles(DataSet)(ContributionCard);

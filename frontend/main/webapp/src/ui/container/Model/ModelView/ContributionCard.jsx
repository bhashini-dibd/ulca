import { withStyles, Typography, Card, Grid,ButtonBase } from "@material-ui/core";
import DataSet from "../../../styles/Dataset";

const ContributionCard = (props) => {

    const { classes,data,handleCardClick } = props;
    

    return (
        <div>
            {data && data.hasOwnProperty("modelName")&&
            
            <Card className={classes.contriCard} id = {data.submitRefNumber} style={{cursor:"pointer"}} onClick ={handleCardClick}>
                 
                <Grid container className={classes.container} >
                <Grid item xs={12} sm={6} md={6} lg={6} xl={6} >
                        <Typography value = {data} variant="body2" className={classes.typeTypo}>{data.task}</Typography>
                    </Grid>
                    
                    <Grid item xs={12} sm={12} md={12} lg={12} xl={12} >
                        <Typography value = {data} variant="body2" className={classes.typeTypo}>{data.type}</Typography>
                    </Grid>
                    <Grid item xs={12} sm={12} md={12} lg={12} xl={12} >
                        <Typography value = {data} variant="body2" className={classes.typeTypo}>{data.type}</Typography>
                    </Grid>
                    <Grid item xs={12} sm={12} md={12} lg={12} xl={12} >
                        <Typography value = {data} variant="body2" className={classes.nameTypo}>{data.modelName}</Typography>
                    </Grid>
                    <Grid item xs={12} sm={6} md={6} lg={6} xl={6} >
                        <Typography value = {data} variant="body2" className={classes.typeTypo}>{data.domain}</Typography>
                    </Grid>
                    <Grid item xs={5} sm={5} md={5} lg={5} xl={5}>
                        <Typography value = {"1"} variant="body2" style={{textAlign:"end", color:data.color}} className={classes.Typo}>{data.status}</Typography>
                    </Grid>
                </Grid>
                
            </Card>
    }
        </div>

    );
};

export default withStyles(DataSet)(ContributionCard);

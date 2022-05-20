import React from 'react'
import {
  Grid,
  Typography,
  TextField,
  Button,
  CircularProgress,
  CardContent,
  Card,
  CardActions,
  CardHeader,
} from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../../styles/Dataset";
import { translate } from "../../../../../assets/localisation";


 function HostedInferTransliteration(props) {
  const { classes } = props;
  return (
    <Grid
    className={classes.gridCompute}
    item
    xl={12}
    lg={12}
    md={12}
    sm={12}
    xs={12}
  >

    <Card className={classes.hostedCard}>
      <CardContent className={classes.translateCard}>
        <Grid container className={classes.cardHeader}>
          <Grid
            item
            xs={4}
            sm={4}
            md={4}
            lg={4}
            xl={4}
            className={classes.headerContent}
          >
           
          </Grid>
          <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
           
            <Typography variant="h6" className={classes.hosted}>
              
            </Typography>
          </Grid>
        </Grid>
      </CardContent>
      <CardContent>
        <textarea
        
          rows={5}
          // cols={40}
          className={classes.textArea}
          placeholder="Enter Text"
        
          
        />
      </CardContent>

      <CardActions className={classes.actionButtons}>
        <Grid container spacing={2}>
          <Grid item>
            <Button
              // disabled={sourceText ? false : true}
              // size="small"
              variant="outlined"
             
            >
              {translate("button.clearAll")}
            </Button>
          </Grid>
          <Grid item>
            <Button
              color="primary"
              //  className={classes.computeBtn}
              variant="contained"
              size={"small"}
             
              // disabled={sourceText ? false : true}
            >
              {translate("button.translate")}
            </Button>
          </Grid>
        </Grid>
      </CardActions>
    </Card>
    
  
  
   


  </Grid>

  //  </Grid>

  //   </div>
);
};
export default withStyles(DatasetStyle)(HostedInferTransliteration);
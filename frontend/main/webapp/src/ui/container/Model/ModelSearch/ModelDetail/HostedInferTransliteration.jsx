import React, { useState } from "react";
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
import { Autocomplete } from "@material-ui/lab";
import GetTransliterationText from "../../../../../redux/actions/api/Model/ModelSearch/GetTransliterationText";
import { useDispatch } from "react-redux";
import APITransport from "../../../../../redux/actions/apitransport/apitransport";

function HostedInferTransliteration(props) {
  const { classes, target } = props;
  const [transliterationText, setTransliterationText] = useState("");
  const dispatch = useDispatch();

  const setTransliterateValues = (e) => {
    console.log("inside set");
    const transliterationObj = new GetTransliterationText(
      target,
      e.target.value
    );
    dispatch(APITransport(transliterationObj));
    setTransliterationText(e.target.value);
  };

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
            ></Grid>
            <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
              <Typography variant="h6" className={classes.hosted}></Typography>
            </Grid>
          </Grid>
        </CardContent>
        <CardContent>
          <Autocomplete
            options={[]}
            freeSolo
            // groupBy={(option) => option.firstLetter}
            // getOptionLabel={(option) => option?.title}
            sx={{ width: 300 }}
            renderInput={(params) => (
              <textarea
                {...params}
                rows={5}
                onChange={setTransliterateValues}
                className={classes.textArea}
                placeholder="Enter Text"
              />
            )}
            value={transliterationText}
          />
        </CardContent>
      </Card>
    </Grid>
  );
}
export default withStyles(DatasetStyle)(HostedInferTransliteration);

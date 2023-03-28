import Record from "../../../assets/record.svg";
import { Grid, Typography, withStyles } from "@material-ui/core";
import CommonStyles from "../../styles/Styles";
import {
  getLanguageName,
  getTaskName,
  FilterByDomain,
} from "../../../utils/getLabel";
import React from "react";
import {  useLocation } from 'react-router-dom';
import { getCamelCase } from "../../../utils/util";
const CardComponent = (props) => {
  const { classes, data, index } = props;
  const location = useLocation();
 // console.log("location", location)
  const renderPublishedOn = (data) => {
    if (data.publishedOn)
      return (
        <Grid item>
          <Typography
            variant="caption"
            style={{ color: "#ffffff", opacity: "0.6" }}
            gutterBottom
          >
            Published On
          </Typography>
          <Typography variant="body2" style={{ color: "#ffffff" }}>
            {data.publishedOn.split(",")[0]}
          </Typography>
        </Grid>
      );
    return <></>;
  };

  const renderSubmitterName = (data) => {
    if (data.publishedOn)
      return (
        <Grid item xs={3} sm={3} md={3} lg={4} xl={4}>
          <Typography
            variant="caption"
            style={{ color: "#ffffff", opacity: "0.6" }}
            gutterBottom
          >
            Submitter
          </Typography>
          <Typography variant="body2" style={{ color: "#ffffff" }}>
            {data.submitter}
          </Typography>
        </Grid>
      );
    return (
      <Grid>
        <Typography
          variant="caption"
          style={{ color: "#ffffff", opacity: "0.6" }}
          gutterBottom
        >
          Submitter
        </Typography>
        <Typography variant="body2" style={{ color: "#ffffff" }}>
          {data.submitter}
        </Typography>
      </Grid>
    );
  };

  const renderDomain = (data) => {
    return (
      <Grid item xs={3} sm={3} md={3} lg={4} xl={4}>
        <Typography
          variant="caption"
          style={{ color: "#ffffff", opacity: "0.6" }}
          gutterBottom
        >
          Domain
        </Typography>
        <Typography variant="body2" style={{ color: "#ffffff" }}>
          {FilterByDomain([data.domain])[0].label}
        </Typography>
      </Grid>
    );
  };

  const renderSourceLanguage = (data) => {
    if (data.source) {
      return (
        <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
          <Typography
            variant="caption"
            style={{ color: "#ffffff", opacity: "0.6" }}
            gutterBottom
          >
            {data.task === "translation" ? "Source" : "Source"}
          </Typography>
          <Typography variant="body2" style={{ color: "#ffffff" }}>
            {getLanguageName(data.source)}
          </Typography>
        </Grid>
      );
    }
  };

  const renderTargetLanguage = (data) => {
    if (data.task === "translation" || data.task === "transliteration")
      return (
        <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
          <Typography
            variant="caption"
            style={{ color: "#ffffff", opacity: "0.6" }}
            gutterBottom
          >
            Target
          </Typography>
          <Typography variant="body2" style={{ color: "#ffffff" }}>
            {getLanguageName(data.target)}
          </Typography>
        </Grid>
      );
    return <></>;
  };

  const renderProcessingType = (data) => {
   
    if ((data.task === "asr" || data.task === "tts")&& !location.pathname.includes('benchmark-datasets')) {
      const {
        inferenceEndPoint: {
          schema: {
            modelProcessingType: { type },
          },
        },
      } = data;
      return (
        <Grid Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
          <Typography
            variant="caption"
            style={{ color: "#ffffff", opacity: "0.6" }}
            gutterBottom
          >
            Type
          </Typography>
          <Typography variant="body2" style={{ color: "#ffffff" }}>
          {getCamelCase(type)}
          </Typography>
        </Grid>
      );
    }
    return <></>;
  };

  const renderLanguage = (data) => {
    return (
      <Grid className={classes.cardGrid} container>
        {renderSourceLanguage(data)}
        {renderTargetLanguage(data)}
        {renderProcessingType(data)}
      </Grid>
    );
  };

  const renderMetaData = (data) => {
    return (
      <Grid style={{ marginTop: "20px", color: "#ffffff" }} container>
        {renderDomain(data)}
        {renderSubmitterName(data)}
        {renderPublishedOn(data)}
      </Grid>
    );
  };

  const renderTaskName = (data) => {
    return (
      <Typography className={classes.typeTypo} variant="body2">
        {getTaskName(data.task)}
      </Typography>
    );
  };

  const renderModelName = (data) => {
    return (
      <Typography variant="h6" className={classes.modelname}>
        {data.modelName}
      </Typography>
    );
  };

  const renderModelInfo = (data) => {
    return (
      <div
        onClick={() => props.onClick(data)}
        style={{
          padding: "10px 20px",
          boxSizing: "border-box",
          cursor: "pointer",
        }}
      >
        {renderTaskName(data)}
        {renderModelName(data)}
        {renderLanguage(data)}
        {renderMetaData(data)}
      </div>
    );
  };

  const renderCardGrid = () => {
    return (
      <Grid
        item
        xs={12}
        sm={12}
        md={12}
        lg={12}
        xl={12}
        className={index % 2 === 0 ? classes.card : classes.card2}
      >
        {renderModelInfo(data)}
      </Grid>
    );
  };

  const renderCardComp = () => {
    if (data)
      return (
        <Grid container spacing={2} className={classes.cardGrid}>
          {renderCardGrid()}
        </Grid>
      );

    return <div style={{ background: `url(${Record}) no-repeat` }}></div>;
  };
  return <>{renderCardComp()}</>;
};

export default withStyles(CommonStyles)(CardComponent);

import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../../styles/Dataset";
import { ArrowBack } from "@material-ui/icons";
import { useHistory, useParams } from "react-router";
import ModelDescription from "./ModelDescription";
import HostedInference from "./HostedInference";
import { useLocation } from "react-router-dom";
import React, { useEffect, useState } from "react";
import Header from "../../../../components/common/Header";
import AudioRecord from "./VoiceRecorder";
import Footer from "../../../../components/common/Footer";
import Theme from "../../../../theme/theme-default";
import { MuiThemeProvider } from "@material-ui/core/styles";
import { Grid, Typography, Button, Divider, Card } from "@material-ui/core";
import HostedInferASR from "./HostedInferASR";
import HostedInferOCR from "./HostedInferOCR";
import BenchmarkTable from "./BenchmarkTable";
import { translate } from "../../../../../assets/localisation";
import { StreamingClient } from "@project-sunbird/open-speech-streaming-client";

const SearchModelDetail = (props) => {
  const { classes } = props;
  const history = useHistory();
  const [data, setData] = useState("");
  const [modelTry, setModelTry] = useState(false);
  const [streaming, setStreaming] = useState(new StreamingClient());
  const location = useLocation();
  const params = useParams();
  useEffect(() => {
    setData(location.state);
  }, [location]);

  useEffect(() => {
    return () => {
      setStreaming(null);
    };
  }, []);

  const description = [
    // {
    //   title: "Version",
    //   para: data.version,
    // }, ,
    // {
    //   title: "Description",
    //   para: data.description,
    // },
    {
      title: "Source URL",
      para: data.refUrl,
    },
    {
      title: "Task",
      para: data.task,
    },

    {
      title: "Languages",
      para: data.language,
    },
    {
      title: "Domain",
      para: data.domain,
    },
    {
      title: "Submitter",
      para: data.submitter,
    },
    {
      title: "Published On",
      para: data.publishedOn,
    },
    {
      title: "Training Dataset",
      para:
        data.trainingDataset !== undefined
          ? data.trainingDataset["description"]
          : "NA",
      // para:"Trained on the datasets curated as part of Anuvaad project.Trained on the datasets curated as part of Anuvaad project."
    },
  ];
  const { prevUrl } = location.state;
  const handleCardNavigation = () => {
    if (data.task === "asr" && streaming.isStreaming === true) {
      streaming.stopStreaming((blob) => {
        clearTimeout();
      });
    }
    // const { prevUrl } = location.state
    if (prevUrl === "explore-models") {
      history.push(`${process.env.PUBLIC_URL}/model/explore-models`);
    } else {
      history.push(`${process.env.PUBLIC_URL}/model/my-contribution`);
    }
  };

  const handleClick = () => {
    history.push({
      pathname: `${process.env.PUBLIC_URL}/search-model/${params.srno}/model`,
      state: data,
    });
  };

  const renderHostedInfer = (task) => {
    if (data) {
      switch (task) {
        case "asr":
          return (
            <HostedInferASR
              task={data.task}
              source={data.source}
              language={data.language}
              inferenceEndPoint={data.inferenceEndPoint}
              modelId={params.srno}
              streaming={streaming}
            />
          );
        case "ocr":
          return (
            <HostedInferOCR
              task={data.task}
              source={data.source}
              inferenceEndPoint={data.inferenceEndPoint}
              modelId={params.srno}
            />
          );
        default:
          return (
            <HostedInference
              task={data.task}
              modelId={params.srno}
              source={data.source}
              target={data.target}
            />
          );
      }
    }
  };

  return (
    <MuiThemeProvider theme={Theme}>
      <Header style={{ marginBottom: "10px" }} />
      {data && (
        <div className={classes.parentPaper}>
          <Button
            size="small"
            color="primary"
            className={classes.backButton}
            startIcon={<ArrowBack />}
            onClick={() => handleCardNavigation()}
          >
            {prevUrl === "explore-models"
              ? translate("label.backToModelList")
              : translate("label.backToMyContrib")}
          </Button>

          <div style={{ display: "flex", justifyContent: "space-between" }}>
            <Grid container>
              <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                <Card
                  style={{
                    height: "100px",
                    backgroundColor: "#0F2749",
                    borderRadius: "8px",
                    marginTop: "1%",
                  }}
                >
                  <Grid container>
                    <Grid item xs={10} sm={10} md={10} lg={10} xl={10}>
                      <Typography
                        variant="h4"
                        color="secondary"
                        className={classes.mainTitle}
                      >
                        {data.modelName} {data.version}
                      </Typography>
                    </Grid>
                    {!params.model && (
                      <Grid item xs={2} sm={2} md={2} lg={2} xl={2}>
                        <Button
                          color="primary"
                          className={classes.computeBtn}
                          variant="contained"
                          size={"small"}
                          onClick={() => handleClick()}
                        >
                          {translate("label.tryModel")}
                        </Button>
                      </Grid>
                    )}
                  </Grid>
                </Card>
              </Grid>
            </Grid>
          </div>
          {/* <hr style={{marginTop: "19px",opacity:'0.3' }}></hr> */}
          {/* <Divider className={classes.gridCompute} /> */}
          {params.model ? (
            <Grid container>
              <Grid
                className={classes.leftSection}
                item
                xs={12}
                sm={12}
                md={8}
                lg={8}
                xl={8}
              >
                {renderHostedInfer(data.task)}
              </Grid>
              <Grid
                item
                xs={12}
                sm={12}
                md={4}
                lg={4}
                xl={4}
                style={{ paddingLeft: "24px" }}
              >
                <Grid container spacing={2} style={{ marginTop: "2%" }}>
                  {/* <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                    <Typography variant="h6" className={classes.modelTitle}>Version</Typography>
                    <Typography style={{ fontSize: '20px', fontFamily: 'Roboto', textAlign: "justify" }} className={classes.modelPara}>{data.version}</Typography>
                  </Grid>
                  <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                    <Typography variant="h6" className={classes.modelTitle}>Description</Typography>
                    <Typography style={{ fontSize: '20px', fontFamily: 'Roboto', textAlign: "justify" }} className={classes.modelPara}>{data.description}</Typography>

                  </Grid> */}
                  <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                    <Grid container spacing={1}>
                      {description.map((des, i) => (
                        <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                          <ModelDescription
                            title={des.title}
                            para={des.para}
                            index={i}
                          />
                        </Grid>
                      ))}
                    </Grid>
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          ) : (
            <Grid container spacing={3}>
              {/* <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                <Typography variant="h6" className={classes.modelTitle}>Version</Typography>
                <Typography variant="body1" style={{ textAlign: "justify" }} className={classes.modelPara}>{data.version}</Typography>
              </Grid> */}
              <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                <Typography variant="h5" className={classes.modelTitle}>
                  {translate("label.description")}
                </Typography>
                <Typography
                  variant="body1"
                  style={{ textAlign: "justify", marginTop: "15px" }}
                >
                  {data.description}
                </Typography>
              </Grid>
              <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                <Grid container spacing={2}>
                  {description.map((des, i) => (
                    <Grid item xs={3} sm={3} md={3} lg={3} xl={3}>
                      <ModelDescription
                        title={des.title}
                        para={des.para}
                        index={i}
                      />
                    </Grid>
                  ))}
                </Grid>
              </Grid>
              <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                <BenchmarkTable modelId={params.srno} />
              </Grid>
            </Grid>
          )}
        </div>
      )}
      <Footer />
    </MuiThemeProvider>
  );
};

export default withStyles(DatasetStyle)(SearchModelDetail);

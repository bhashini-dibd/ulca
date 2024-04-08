import React from "react";
import clipboard1 from "../img/deatail-card-img/clipboard1.svg";
import dataClassification1 from "../img/deatail-card-img/dataClassification1.svg";
import dataCollection1 from "../img/deatail-card-img/dataCollection1.svg";
import data1 from "../img/deatail-card-img/data1.svg";
import efficiency1 from "../img/deatail-card-img/efficiency1.svg";
import management1 from "../img/deatail-card-img/management1.svg";
import serverCheck1 from "../img/deatail-card-img/serverCheck1.svg";
import standard1 from "../img/deatail-card-img/standard1.svg";
import training1 from "../img/deatail-card-img/training1.svg";
import {
  Grid,
  Card,
  CardContent,
  Typography,
  useMediaQuery,
  makeStyles,
} from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  gridItem: {
    width: "100%",
    [theme.breakpoints.up("sm")]: {
      width: "calc(50% - 40px)", // Two items per row on small screens and larger
    },
    [theme.breakpoints.up("md")]: {
      width: "calc(33.33% - 40px)", // Three items per row on medium screens and larger
    },
    [theme.breakpoints.up("lg")]: {
      width: "calc(25% - 40px)", // Four items per row on large screens and larger
    },
  },
  card: {
    minHeight: 250,
    display: "flex",
    flexDirection: "column",
    borderRadius: 8,
  },
  cardContent: {
    flex: 1,
    padding: "32px",
  },
}));

const cardData = [
  {
    icon: management1,
    title: "Establishing Dominance",
    description:
      "Aiming to Become the Leading Data Repository for Indian Language Resources.",
  },
  {
    icon: standard1,
    title: "Curate and Standardize",
    description:
      "Collect datasets for MT, ASR, TTS, OCR and various NLP tasks in standardized but extensible formats.",
  },
  {
    icon: dataCollection1,
    title: "Comprehensive metadata Collection",
    description:
      "Collect extensive metadata related to dataset for various analysis.",
  },
  {
    icon: dataClassification1,
    title: "Attributing Excellence",
    description:
      "Proper attribution for every contributor at the record level.",
  },
  {
    icon: efficiency1,
    title: "Efficiency Unleashed",
    description: "Deduplication capability built-in",
  },
  {
    icon: data1,
    title: "Seamless Exploration",
    description:
      "Simple interface to search and download datasets based on various filters.",
  },
  {
    icon: serverCheck1,
    title: "Elevating Standards",
    description: "Perform various quality checks on the submitted datasets.",
  },

  {
    icon: training1,
    title: "Precision Perfected",
    description: "Trained models for language specific tasks.",
  },
  {
    icon: clipboard1,
    title: "Task-Specific Excellence",
    description: "Multiple benchmarks defined for each model task.",
  },
];

export const WhyULCA = () => {
  const classes = useStyles();
  const isSmallScreen = useMediaQuery("(max-width:600px)");
  const isMediumScreen = useMediaQuery(
    "(min-width:801px) and (max-width:1200px)"
  );
  return (
    <Grid
      style={{ backgroundColor: "#F5F7FA", paddingTop: 40, display: "grid" }}
    >
      <Typography
        style={{
          textAlign: "center",
          letterSpacing: 1,
          marginBottom: 15,
          fontFamily: "Inter-Regular",
          fontWeight: 600,
          fontSize: "36px",
        }}
        variant="h4"
      >
        Why ULCA
      </Typography>
      <div
        style={{
          textAlign: "center",
          display: "flex",
          justifyContent: "center",
        }}
      >
        <Typography
          style={{
            width: isSmallScreen ? "80%" : "60%",
            fontFamily: "OS-Regular",
            fontSize: "16px",
            fontWeight: 400,
            marginBottom: isSmallScreen ? "25px" : "0px",
          }}
          variant="body2"
        >
          Your premier hub for Indian language resources, providing curated
          datasets and enhanced language-specific tasks for cutting-edge
          linguistic innovation and research.
        </Typography>
      </div>
      <Grid
        container
        spacing={5}
        direction="row"
        justifyContent="center"
        style={{
          padding: isSmallScreen ? 10 : 40,
          columnGap: 0.5,
          width: isSmallScreen ? "100%" : isMediumScreen ? "127%" : "100%",
          alignSelf: "center",
          justifySelf: "center",
        }}
      >
        {cardData.map((el, i) => (
          <Grid
            item
            key={i}
            xs={12}
            sm={6}
            md={3}
            lg={3}
            className={classes.gridItem}
          >
            <Card className={classes.card}>
              <CardContent className={classes.cardContent}>
                <img src={el.icon} alt={el.title} />
                <Typography
                  variant="h5"
                  style={{
                    marginTop: 16,
                    fontFamily: "Inter-Regular",
                    fontSize: 20,
                    fontWeight: "600",
                  }}
                >
                  {el.title}
                </Typography>
                <Typography
                  variant="body2"
                  style={{
                    marginTop: 16,
                    fontFamily: "OS-Regular",
                    fontSize: 16,
                  }}
                >
                  {/* {isSmallScreen
                    ? `${el.description.slice(0, 50)}...`
                    : el.description} */}
                    {el?.description}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Grid>
  );
};

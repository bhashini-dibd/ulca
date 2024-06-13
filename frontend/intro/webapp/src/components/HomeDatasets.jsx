import React, { useEffect, useRef, useState } from "react";
import info from "../img/info.svg";
import {
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Typography,
  useMediaQuery,
} from "@material-ui/core";
import Loader from "./Loader";
import { Card, Col, Container, Dropdown, Form, Row } from "react-bootstrap";
import CardImg1 from "../assets/analytics.svg";
import CardImg2 from "../assets/modeling.svg";
import CardImg3 from "../assets/benchmark.svg";
import DatasetArrow from '../assets/datasetCardArrow.svg'
import DatasetArrowDisabled from '../assets/Arrow-gray.svg'
const HomeDatasets = () => {
  const [selectedValue, setSelectedDataset] = useState("parallel-corpus");
  const [apiValue, setApiValue] = useState("parallel-corpus");

  const [totalValue, setTotalValue] = useState("");
  const [totalValue2, setTotalValue2] = useState("");
  const [totalValue3, setTotalValue3] = useState("");
  const isDesktopScreen = useMediaQuery("(max-width:2000px)");
  const isMobileScreen = useMediaQuery("(max-width:500px)");
  const isTabScreen = useMediaQuery("(max-width:900px) and (min-width:600px)");
  const [isLoading, setIsLoading] = useState(false);
  const [isLoadingDrop, setIsLoadingDrop] = useState(false);

  const [dropdownVisible, setDropdownVisible] = useState(false);
  // const [dataset, setDataset] = useState("parallel-corpus");

  // const handleChange = (event) => {
  //   setDataset(event.target.value);
  // };

  const handleDatasetClick = () => {
    setDropdownVisible(!dropdownVisible);
  };

  const handleDatasetChange = (event) => {
    setIsLoadingDrop(true);
    setSelectedDataset(event.target.value);
    setApiValue(event.target.value);
    setDropdownVisible(false); // Close the dropdown after selection
  };

  const criterions = [{ field: "sourceLanguage", value: "en" }];

  useEffect(() => {
    setIsLoading(true);
    setIsLoadingDrop(true);
    const fetchData = async () => {
      try {

        const response1 = await fetchChartData(apiValue, "", criterions);
        setTotalValue(response1?.count);
        const response2 = await fetchChartData("model", "", "");
        setTotalValue2(response2?.count);
        const response3 = await fetchChartData("benchmark", "", "");
        setTotalValue3(response3?.count);
        setIsLoading(false);
        setIsLoadingDrop(false);

      } catch (error) {
        console.error("Error fetching data:", error);
        setIsLoading(false);
        setIsLoadingDrop(false);
      }
    };

    fetchData();
  }, []);
  console.log("3");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response1 = await fetchChartData(apiValue, "", criterions);
        setTotalValue(response1?.count);
        setIsLoadingDrop(false);
      } catch (error) {
        console.error("Error handling API response:", error);
      }
    };
    fetchData();

    // if (apiValue !== "parallel-corpus") {
    //   fetchData();
    // }
  }, [apiValue]);


  const fetchChartData = async (dataType, value, criterions) => {
    try {
      window.REACT_APP_APIGW_BASE_URL =
        process.env.REACT_APP_APIGW_BASE_URL ||
        "https://dev-auth.ulcacontrib.org";
      const endpoint = `${window.REACT_APP_APIGW_BASE_URL}/ulca/data-metric/v0/store/search`;
      const request = {
        type: dataType,
        criterions: criterions ? criterions : null,
        groupby: value ? [{ field: value, value: null }] : null,
      };
      const headers = {
        "Content-Type": "application/json",
      };
      const resp = await fetch(endpoint, {
        method: "post",
        body: JSON.stringify(request),
        headers: headers,
      });
      const resp_data = await resp.json();
      if (resp.ok) {
        return resp_data;
      }
    } catch (error) {
      console.error("Error fetching chart data:", error);
      throw error;
    }
  };
  return (
    <>
    
      {/* <div
      className={`${
        isDesktopScreen ? "container" : ""
      } datasetResponsiveContainer  text-left elements  `}
      style={{ marginBottom: "80px" }}
    >
      <div className="row">
        <div className="col DatasetCard">
          <p
            className="h5"
            style={{ fontSize: "20px", fontWeight: 600, lineHeight: "28px",fontFamily: "Roboto-Regular", }}
          >
            Datasets
          </p>
          <div className="DatasetContainer">
            <div
              style={{
                display: "flex",
                flexDirection: "row",
                justifyContent: "space-between",
                height: "2rem",
              }}
            >
              <div className="wrapper" style={{ height: "20px" }}>
                <div
                  className="select_wrap"
                  style={{ height: "50px", margin: "-35px 0px 0px -13px" }}
                >
                  <ul className="default_option">
                    <li>
                      <div className="option" onClick={handleDatasetClick}>
                        <p id="parallel-corpus">
                          {selectedValue ? selectedValue : " Parallel Dataset"}
                        </p>
                      </div>
                    </li>
                  </ul>

                  {dropdownVisible === true ? (
                    <ul className="select_ul">
                      <li style={{ paddingTop: "25px" }}>
                        <div
                          className="option"
                          onClick={() =>
                            handleDatasetChange(
                              "Parallel Dataset",
                              "parallel-corpus"
                            )
                          }
                        >
                          <p id="parallel-corpus" style={{ margin: "0px" }}>
                            Parallel Dataset
                          </p>
                        </div>
                      </li>
                      <li>
                        <div
                          className="option"
                          onClick={() =>
                            handleDatasetChange(
                              "Monolingual Dataset",
                              "monolingual-corpus"
                            )
                          }
                        >
                          <p id="monolingual-corpus">Monolingual Dataset</p>
                        </div>
                      </li>
                      <li>
                        <div
                          className="option "
                          onClick={() =>
                            handleDatasetChange("ASR Dataset", "asr-corpus")
                          }
                        >
                          <p id="asr-corpus">ASR Dataset</p>
                        </div>
                      </li>
                      <li>
                        <div
                          className="option "
                          onClick={() =>
                            handleDatasetChange("TTS Dataset", "tts-corpus")
                          }
                        >
                          <p id="tts-corpus">TTS Dataset</p>
                        </div>
                      </li>
                      <li>
                        <div
                          className="option "
                          onClick={() =>
                            handleDatasetChange("OCR Dataset", "ocr-corpus")
                          }
                        >
                          <p id="ocr-corpus">OCR Dataset</p>
                        </div>
                      </li>
                      <li>
                        <div
                          className="option "
                          onClick={() =>
                            handleDatasetChange(
                              "ASR Unlabeled Dataset",
                              "asr-unlabeled-corpus"
                            )
                          }
                        >
                          <p id="asr-unlabeled-corpus">ASR Unlabeled Dataset</p>
                        </div>
                      </li>
                      <li>
                        <div
                          className="option"
                          onClick={() =>
                            handleDatasetChange(
                              "Transliteration Dataset",
                              "transliteration-corpus"
                            )
                          }
                        >
                          <p id="transliteration-corpus">
                            Transliteration Dataset
                          </p>
                        </div>
                      </li>
                      <li>
                        <div
                          className="option"
                          onClick={() =>
                            handleDatasetChange(
                              "Glossary Dataset",
                              "glossary-corpus"
                            )
                          }
                        >
                          <p id="glossary-corpus">Glossary Dataset</p>
                        </div>
                      </li>
                    </ul>
                  ) : null}
                </div>
              </div>
              <span
                style={{
                  alignItems: "center",
                  display: "flex",
                  cursor: "pointer",
                  margin: "20px 24px 0px 0px",
                }}
                data-tooltip="Datasets are collection of structured data that serve as a input for training machine learning models, enablings algorithms to learn patterns and perform tasks based on provided information"
              >
                <img
                  src={info}
                  className="w-100"
                  style={{ height: isMobileScreen ? "" : "24px" }}
                />
              </span>
            </div>
            <h6 id="totalValue">{isLoadingDrop ? <Loader /> : (totalValue ? (Number.isInteger(totalValue) ? totalValue : parseFloat(totalValue).toFixed(2)): 0)}</h6>
            <div
              style={{
                textAlign: "center",
                marginRight: "24px",
                marginLeft: "24px",
                fontSize: "13px",
              }}
              className="mobileButton mobileButton1"
            >
              <a
                href="https://bhashini.gov.in/ulca/dashboard"
                target="_blank"
                className="MobileButton__bigContainer"
              >
                <button
                  className="MobileButton__big"
                  style={{
                    borderRadius: "4px",
                    background: "var(--Primary-M_Blue, #0671E0)",
                    color: "#FFF",
                    border: "none",
                    padding: "6px 32px 6px 32px",
                    fontFamily: "Inter-Regular",
                  }}
                >
                  Go to Dashboard
                </button>
              </a>
            </div>
          </div>
        </div>
        <div className="col DatasetCard">
          <p
            className="h5"
            style={{ fontSize: "20px",fontFamily: "Roboto-Regular", fontWeight: 600, lineHeight: "28px" }}
          >
            Models
          </p>
          <div className="DatasetContainer">
            <div
              style={{
                display: "flex",
                flexDirection: "row",
                justifyContent: "space-between",
                height: "2rem",
              }}
            >
              <p className="DatasetTxt">Model Total Count</p>
              <span
                style={{
                  alignItems: "center",
                  display: "flex",
                  cursor: "pointer",
                  margin: "20px 24px 0px 0px",
                }}
                data-tooltip="Models are computational algorithms specifically designed for understanding and processing human language. They enable machines to analyze, interpret, and generate human-like text, facilitating applications such as language translation, sentiment analysis, and chatbot interactions."
              >
                <img src={info} className="w-100" style={{ height: "30px" }} />
              </span>
            </div>
            <h6 id="totalValue2">{isLoading ? <Loader /> : (totalValue2 ? totalValue2 : 0)}</h6>
            <div
              style={{
                display: "flex",
                marginRight: "24px",
                marginLeft: "24px",
                fontSize: isTabScreen ? "12px" : "13px",
              }}
              className="mobileButton"
            >
              <a
                style={{ color: "#0671E0", padding: "5px 5px" }}
                href="https://bhashini.gov.in/ulca/model/explore-models"
                className="desktopButton"
              >
                Explore Model
              </a>
              <button
                style={{
                  borderRadius: "4px",
                  marginLeft: "0.5rem",
                  border: "none",
                  backgroundColor: "#EEF5FC ",
                  color: "#ABBED1",
                  padding: "6px 32px 6px 32px",
                  cursor: "not-allowed",
                  fontFamily: "Inter-Regular",
                }}
                className="desktopButton"
              >
                Go to Dashboard
              </button>
            </div>
          </div>
        </div>
        <div className="col DatasetCard">
          <p
            className="h5"
            style={{ fontSize: "20px",fontFamily: "Roboto-Regular", fontWeight: 600, lineHeight: "28px" }}
          >
            Benchmarks
          </p>
          <div className="DatasetContainer DatasetContainer1">
            <div
              style={{
                display: "flex",
                flexDirection: "row",
                justifyContent: "space-between",
                height: "2rem",
              }}
            >
              <p className="DatasetTxt">Benchmark Total Count</p>
              <span
                style={{
                  alignItems: "center",
                  display: "flex",
                  cursor: "pointer",
                  margin: "20px 24px 0px 0px",
                }}
                data-tooltip="Benchmarking refers to the process of evaluating and comparing the performance of different algorithms or models against a standardized set of metrics and datasets to determine their relative effectiveness for a specific task or problem. This aids in selecting the most suitable model for a given application."
              >
                <img src={info} className="w-100" style={{ height: "30px" }} />
              </span>
            </div>
            <h6 id="totalValue3">{isLoading ? <Loader /> : (totalValue3 ? totalValue3 : 0)}</h6>
            <div
              style={{
                display: "flex",
                marginRight: "24px",
                marginLeft: "24px",
                fontSize: "13px",
              }}
              className="mobileButton tabButton "
            >
              <a
                style={{ color: "#0671E0", padding: "5px 5px" }}
                href="https://bhashini.gov.in/ulca/model/benchmark-datasets"
                className="mobileButtonextraSmall desktopButton"
              >
                Explore Benchmark
              </a>
              <button
                style={{
                  borderRadius: "4px",
                  marginLeft: "0.5rem",
                  border: "none",
                  backgroundColor: "#EEF5FC ",
                  color: "#ABBED1",
                  padding: "6px 21px 6px 21px",
                  cursor: "not-allowed",
                  fontFamily: "Inter-Regular",
                }}
                className="desktopButton"
              >
                Go to Dashboard
              </button>
            </div>
          </div>
        </div>
      </div>
    </div> */}

      <div style={{ backgroundColor: "#e6ebfa",paddingTop:"20px" }}>
        <Container className="p-4">
          <Row className="text-center">
            <Col xs={12} md={4} className="mb-4">
              <p
                className="h5"
                style={{
                  fontSize: "20px",
                  fontFamily: "Noto-Regular",
                  fontWeight: 600,
                  lineHeight: "28px",
                  display: "flex",
                }}
              >
                Dataset
              </p>
              <Card className="p-0">
                <Card.Body className="p-0">
                  <div
                    style={{
                      display: "flex",
                      justifyContent: "space-between",
                      alignItems: "flex-start",
                      padding:"30px 40px 0px 40px "
                    }}
                    
                  >
                    <img
                      src={CardImg1}
                      style={{ height: "65px", width: "65px" }}
                      className="mb-3"
                    />
                    <div data-tooltip="Datasets are collection of structured data that serve as a input for training machine learning models, enablings algorithms to learn patterns and perform tasks based on provided information">
                      <img
                        src={info}
                        className="w-100"
                        style={{ height: isMobileScreen ? "" : "24px" }}
                      />
                    </div>
                  </div>

                  <Card.Text className="text-start  ">
                    <FormControl
                      style={{
                        display: "flex",
                        justifyContent: "flex-start",
                        width: "70%",
                        // marginTop: "10px",
                        padding:"10px 40px",
                        margin:"0px"
                      }}
                    >
                      {/* <InputLabel id="demo-simple-select-label">Age</InputLabel> */}
                      <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={selectedValue}
                        displayEmpty
                        inputProps={{ "aria-label": "Without label" }}
                        onChange={handleDatasetChange}
                        className="DatasetTxt m-0 p-0"
                      >
                        <MenuItem
                          value="parallel-corpus"
                          style={{ display: "flex",color:"#150202",fontSize:"16px", fontWeight:400, fontFamily:"Noto-Regular" }}
                        >
                          Parallel Corpus
                        </MenuItem>
                        <MenuItem value="monolingual-corpus">Monolingual Dataset</MenuItem>
                        <MenuItem value="asr-corpus">ASR Dataset</MenuItem>
                        <MenuItem value="tts-corpus">TTS Dataset</MenuItem>
                        <MenuItem value="ocr-corpus">OCR Dataset</MenuItem>
                        <MenuItem value="asr-unlabeled-corpus">ASR Unlabeled Dataset</MenuItem>
                        <MenuItem value="tts-corpus">TTS Dataset</MenuItem>
                        <MenuItem value="transliteration-corpus">Transliteration Dataset</MenuItem>
                        <MenuItem value="glossary-corpus">Glossary Dataset</MenuItem>
                      </Select>
                    </FormControl>
                    <h6
                      id="totalValue"
                      style={{ display: "flex", marginLeft: "40px",marginTop:"0px" , fontFamily:"Noto-Bold"}}
                    >
                      {isLoadingDrop ? (
                        <Loader />
                      ) : totalValue ? (
                        Number.isInteger(totalValue) ? (
                          totalValue
                        ) : (
                          parseFloat(totalValue).toFixed(2)
                        )
                      ) : (
                        0
                      )}
                    </h6>                    
                    <hr style={{overflow:"hidden", width:"90%"}} />
                    <a href="https://bhashini.gov.in/ulca/dashboard" style={{color:"black", display:"flex", justifyContent:"flex-end", marginRight:"40px", marginBottom:"20px", color:"#2947A3", fontSize:"16px", fontWeight:600, fontFamily:"Noto-Bold"}}>Go to dashboard <img src={DatasetArrow} className="ml-2"/></a>
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
            <Col xs={12} md={4} className="mb-4">
              <p
                style={{
                  fontSize: "20px",
                  fontFamily: "Noto-Regular",
                  fontWeight: 600,
                  lineHeight: "28px",
                  display:"flex"
                }}
              >
                Models
              </p>
              <Card className="p-0">
                <Card.Body className="p-0">
                  <div
                    style={{
                      display: "flex",
                      justifyContent: "space-between",
                      alignItems: "flex-start",
                      padding:"30px 40px 0px 40px "
                    }}
                  >
                    <img
                      src={CardImg2}
                      style={{ height: "65px", width: "65px" }}
                      className="mb-3"
                    />
                    <div  data-tooltip="Models are computational algorithms specifically designed for understanding and processing human language. They enable machines to analyze, interpret, and generate human-like text, facilitating applications such as language translation, sentiment analysis, and chatbot interactions.">
                      <img
                        src={info}
                        className="w-100"
                        style={{ height: isMobileScreen ? "" : "24px" }}
                      />
                    </div>
                  </div>

                  <Card.Text className="text-start">
                    <p className="DatasetTxt"  style={{  marginLeft: "40px",marginTop:"0px",color:"#150202",fontSize:"16px", fontWeight:400, fontFamily:"Noto-Regular" }}>Total model count</p>
                    <h6
                      id="totalValue2"
                      style={{ display: "flex",  marginLeft: "40px",marginTop:"0px", fontFamily:"Noto-Bold" }}
                    >
                      {isLoading ? <Loader /> : totalValue2 ? totalValue2 : 0}
                    </h6>
                    <hr style={{overflow:"hidden", width:"90%"}} />
                    <a href="#dataset-dashboard" style={{color:"black", display:"flex", justifyContent:"flex-end", marginRight:"40px", marginBottom:"20px"}} className="ULCA__disabled">Go to dashboard <img src={DatasetArrowDisabled} style={{color:"white"}} className="ml-2"/></a>
                    
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
            <Col xs={12} md={4} className="mb-4">
              <p
                style={{
                  fontSize: "20px",
                  fontFamily: "Noto-Regular",
                  fontWeight: 600,
                  lineHeight: "28px",
                  display:"flex"
                }}
              >
                Benchmark
              </p>
              <Card className="p-0">
                <Card.Body className="p-0">
                  <div
                    style={{
                      display: "flex",
                      justifyContent: "space-between",
                      alignItems: "flex-start",
                      padding:"30px 40px 0px 40px "
                    }}
                  >
                    <img
                      src={CardImg3}
                      style={{ height: "65px", width: "65px" }}
                      className="mb-3"
                    />
                    <div data-tooltip="Benchmarking refers to the process of evaluating and comparing the performance of different algorithms or models against a standardized set of metrics and datasets to determine their relative effectiveness for a specific task or problem. This aids in selecting the most suitable model for a given application.">
                      <img
                        src={info}
                        className="w-100"
                        style={{ height: isMobileScreen ? "" : "24px" }}
                      />
                    </div>
                  </div>

                  <Card.Text className="text-start">
                    <p className="DatasetTxt"  style={{  marginLeft: "40px",marginTop:"0px",color:"#150202", fontSize:"16px", fontWeight:400, fontFamily:"Noto-Regular" }}>Total Benchmark count</p>
                    <h6
                      id="totalValue3"
                      style={{ display: "flex",  marginLeft: "40px",marginTop:"0px", fontFamily:"Noto-Bold" }}
                    >
                      {isLoading ? <Loader /> : (totalValue3 ? totalValue3 : 0)}
                      
                    </h6>
                    <hr style={{overflow:"hidden", width:"90%"}} />
                    <a href="#dataset-dashboard" style={{color:"black", display:"flex", justifyContent:"flex-end", marginRight:"40px", marginBottom:"20px"}} className="ULCA__disabled">Go to dashboard <img src={DatasetArrowDisabled} style={{color:"white"}} className="ml-2"/></a>
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      </div>
    </>
  );
};

export default HomeDatasets;
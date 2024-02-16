import React, { useEffect, useRef, useState } from "react";
import info from "../img/info.svg";
import { useMediaQuery } from "@material-ui/core";
const HomeDatasets = () => {
  const [selectedValue, setSelectedDataset] = useState("Parallel Dataset");
  const [apiValue, setApiValue] = useState("parallel-corpus");
  const [totalValue, setTotalValue] = useState("");
  const [totalValue2, setTotalValue2] = useState("");
  const [totalValue3, setTotalValue3] = useState("");
  const isDesktopScreen = useMediaQuery("(max-width:1700px)");

  const [dropdownVisible, setDropdownVisible] = useState(false);

  const handleDatasetClick = () => {
    setDropdownVisible(!dropdownVisible);
  };

  const handleDatasetChange = (dataset, apiName) => {
    setSelectedDataset(dataset);
    setApiValue(apiName);
    setDropdownVisible(false); // Close the dropdown after selection
  };

  const criterions = [{ field: "sourceLanguage", value: "en" }];

  useEffect(() => {
    const fetchData = async () => {
      try {
        // const [response1, response2, response3] = await Promise.all([
        //   fetchChartData(apiValue, "", criterions),
        //   fetchChartData("model", "", ""),
        //   fetchChartData("benchmark", "", ""),
        // ]);

        const response1 = await fetchChartData(apiValue, "", criterions);
        setTotalValue(response1?.count);
        const response2 = await fetchChartData("model", "", "");
        setTotalValue2(response2?.count);
        const response3 = await fetchChartData("benchmark", "", "");
        setTotalValue3(response3?.count);
        

        // setTotalValue(response1?.count || "");
        // console.log(response1?.count, response1, "1");
        // console.log(response2?.count, response2, "2");
        // console.log(response3?.count, response3, "3");
        // console.log("1");

        // setTotalValue2(response2?.count || "");
        // setTotalValue3(response3?.count || "");
      } catch (error) {
        console.error("Error fetching data:", error);
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
      } catch (error) {
        console.error("Error handling API response:", error);
      }
    };

    if (apiValue !== "parallel-corpus") {
      fetchData();
    }
  }, [apiValue]);

  console.log(totalValue, totalValue2, totalValue3, "change");

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
    <div
      className={`${
        isDesktopScreen ? "container" : ""
      } datasetResponsiveContainer  text-left elements  `}
      style={{ marginBottom: "80px" }}
    >
      <div className="row">
        <div className="col DatasetCard">
          <p
            className="h5"
            style={{ fontSize: "20px", fontWeight: 600, lineHeight: "28px" }}
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
                <img src={info} className="w-75" />
              </span>
            </div>
            <h6 id="totalValue">{totalValue ? totalValue : 0}</h6>
            <div
              style={{
                textAlign: "center",
                marginRight: "24px",
                fontSize: "14px",
              }}
              className="mobileButton mobileButton1"
            >
              <a href="https://bhashini.gov.in/ulca/dashboard" target="_blank">
                <button
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
            style={{ fontSize: "20px", fontWeight: 600, lineHeight: "28px" }}
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
                <img src={info} />
              </span>
            </div>
            <h6 id="totalValue2">{totalValue2 ? totalValue2 : 0}</h6>
            <div
              style={{
                display: "flex",
                marginRight: "24px",
                marginLeft: "24px",
                fontSize: "14px",
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
            style={{ fontSize: "20px", fontWeight: 600, lineHeight: "28px" }}
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
                <img src={info} />
              </span>
            </div>
            <h6 id="totalValue3">{totalValue3 ? totalValue3 : 0}</h6>
            <div
              style={{
                display: "flex",
                marginRight: "24px",
                marginLeft: "24px",
                fontSize: "14px",
              }}
              className="mobileButton tabButton "
            >
              <a
                style={{ color: "#0671E0", padding: "5px 5px" }}
                href="https://bhashini.gov.in/ulca/model/explore-models"
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
    </div>
  );
};

export default HomeDatasets;

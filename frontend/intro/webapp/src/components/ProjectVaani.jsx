import { Button, Typography, withStyles } from "@material-ui/core";
import HeaderStyles from "../styles/HeaderStyles";

const ProjectVaani = (props) => {
  const { classes } = props;

  const downloadZip = () => {
    const link = document.createElement("a");
    link.href =
      "https://ulcaproduction.blob.core.windows.net/ulca-prod-container/datasets/2278536770.zip";
    // link.setAttribute("download", `VAANI.zip`);
    document.body.appendChild(link);
    link.click();
    link.parentNode.removeChild(link);
  };

  return (
    <>
      <div class="section primary-color" style={{ padding: 0 }}>
        {/* <div
          style={{
            backgroundColor: "white",
            transform: `skewY(-45deg)`,
            // transform: `skewX(20deg)`,
            maxWidth: "8%",
            // width:'25%',
            // alignContent: "flex-end",
            // justifyContent:'center',
            // display: "flex",
            fontSize:"25px",
            paddingLeft:"2%"
          }}
        >
          New!
        </div> */}
        <div
          style={{
            display: "flex",
            flexDirection: "row",
          }}
        >
          <img
            style={{ width: "100px", height: "100px", position: "absolute" }}
            src={`${process.env.PUBLIC_URL}/img/5a5a6d2414d8c4188e0b088d.png`}
            alt="shapes"
          />
          <div class="container">
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                flexDirection: "column",
                alignItems: "center",
                padding: "30px 0px",
              }}
            >
              <Typography
                variant="h3"
                style={{
                  //   color: "#f59042",
                  color: "#f58d42",
                  textAlign: "center",
                }}
              >
                PROJECT VAANI
              </Typography>
              <Typography
                variant="h4"
                style={{
                  color: "#cce6ff",
                  // color: "#f58d42",
                  textAlign: "center",
                }}
              >
                Contributor : IISc, Bangalore (Funded by Google)
              </Typography>
              <Typography
                variant="h5"
                style={{
                  color: "#DEECFF",
                  textAlign: "center",
                  padding: "15px",
                  lineHeight: "2.6rem",
                }}
              >
                This open source project aims to capture the speech diversity in
                India by collecting 154,600 hours of speech data from across the
                773 districts in India (on average 200 hours per district). The
                collected speech samples will cover linguistic, educational,
                urban-rural, and gender diversity in the population. 10% of the
                collected samples will also be transcribed (total 15,460 hours
                transcribed). Although outside the scope of this project, this
                dataset will enable learning speech representations and ASR
                systems for all 130+ Indic languages with 100K+ speakers as per
                2011 census. The dataset will be open sourced through Bhashini,
                India's national language mission.
              </Typography>
              <Button
                variant="contained"
                style={{
                  backgroundColor: "#cce6ff",
                  margin: "20px",
                  justifyContent: "center",
                  borderRadius: "20px",
                  width: "20%",
                }}
                onClick={downloadZip}
              >
                Download
              </Button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default withStyles(HeaderStyles)(ProjectVaani);

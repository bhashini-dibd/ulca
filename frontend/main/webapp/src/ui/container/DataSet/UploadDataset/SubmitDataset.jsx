import {
  Grid,
  Paper,
  Typography,
  Divider,
  FormControl,
  Button,
  TextField,
  Link,
  Hidden,
  FormControlLabel,
  Checkbox,
  Menu,
  MenuItem,
  InputLabel,
  Select,
} from "@material-ui/core";
import DownIcon from "@material-ui/icons/ArrowDropDown";
import BreadCrum from "../../../components/common/Breadcrum";
import { withStyles } from "@material-ui/core/styles";
import { RadioButton, RadioGroup } from "react-radio-buttons";
import DatasetStyle from "../../../styles/Dataset";
import { useState } from "react";
import { useHistory } from "react-router-dom";
import Snackbar from "../../../components/common/Snackbar";
import UrlConfig from "../../../../configs/internalurlmapping";
import SubmitDatasetApi from "../../../../redux/actions/api/DataSet/UploadDataset/SubmitDataset";
import DatasetItems from "../../../../configs/DatasetItems";
import getTitleName from "../../../../utils/getDataset";
import C from "../../../../redux/actions/constants";
import { useDispatch } from "react-redux";
import { PageChange } from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import { translate } from "../../../../assets/localisation";

const SubmitDataset = (props) => {
  const { classes } = props;
  const [anchorEl, setAnchorEl] = useState(null);
  const [dataset, setDatasetInfo] = useState({ name: "", url: "" });
  const [title, setTitle] = useState("Parallel Dataset");
  const dispatch = useDispatch();
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
  const [error, setError] = useState({ name: "", url: "", type: false });
  const [search, setSearch] = useState(false);
  const [isChecked, setIsChecked] = useState(false);
  const history = useHistory();
  const [selectedOption, setOptionLabel] = useState("");
  const { roles } = JSON.parse(localStorage.getItem("userDetails"));
  // const handleClick = (event) => {
  //     setAnchorEl(event.currentTarget)
  // };

  const handleClose = () => {
    setAnchorEl(null);
  };

  // const handleDone = () => {
  //     if (dataset.filteredName) {
  //         setDatasetInfo({ ...dataset, datasetName: dataset.filteredName })
  //     }
  //     handleClose();
  // }

  // const renderUpdateDatasetSearch = () => {
  //     return (
  //         <div>
  //             <div className={classes.updateDataset}>
  //                 <Grid container spacing={1}>
  //                     <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
  //                         <Autocomplete
  //                             id="tags-outlined"
  //                             options={[]}
  //                             getOptionLabel={(option) => option.name}
  //                             filterSelectedOptions
  //                             open={search}
  //                             onChange={(e, value) => {
  //                                 setDatasetInfo({ ...dataset, datasetName: value.name})
  //                                 handleClose();
  //                             }}
  //                             onOpen={() => {
  //                                 setTimeout(() => setSearch(true), 200)
  //                             }}
  //                             onClose={() => {
  //                                 setSearch(false)
  //                             }}
  //                             openOnFocus
  //                             renderInput={(params) => (
  //                                 <TextField
  //                                     id="search-dataset"
  //                                     variant="outlined"
  //                                     placeholder="Search Dataset"
  //                                     autoFocus={true}
  //                                     {...params}
  //                                 />
  //                             )}
  //                         />
  //                     </Grid>
  //                 </Grid>
  //             </div>

  //         </div>
  //     )
  // }

  const handleApicall = async () => {
    let apiObj = new SubmitDatasetApi(dataset, isChecked);
    fetch(apiObj.apiEndPoint(), {
      method: "post",
      body: JSON.stringify(apiObj.getBody()),
      headers: apiObj.getHeaders().headers,
    })
      .then(async (response) => {
        const rsp_data = await response.json();
        if (!response.ok) {
          setSnackbarInfo({
            ...snackbar,
            open: true,
            message: rsp_data.message
              ? rsp_data.message
              : "Something went wrong. Please try again.",
            timeOut: 40000,
            variant: "error",
          });
          if (response.status === 401) {
            setTimeout(
              () => history.push(`${process.env.PUBLIC_URL}/user/login`),
              3000
            );
          }
        } else {
          dispatch(PageChange(0, C.PAGE_CHANGE));
          history.push(
            `${process.env.PUBLIC_URL}/dataset/submission/${rsp_data.data.serviceRequestNumber}`
          );
          //           return true;
        }
      })
      .catch((error) => {
        setSnackbarInfo({
          ...snackbar,
          open: true,
          message: "Something went wrong. Please try again.",
          timeOut: 40000,
          variant: "error",
        });
      });
  };

  const renderInstrructions = () => {
    return (
      <div className={classes.list}>
        <ul>
          <li>
            <Typography className={classes.marginValue} variant="body2">
              {translate("label.datasetName")}
            </Typography>
          </li>
          <li>
            <Typography className={classes.marginValue} variant="body2">
              {translate("label.datasetStoredMsg")}
            </Typography>
          </li>
          <li>
            <Typography className={classes.marginValue} variant="body2">
              {translate("label.urlDownload")}
            </Typography>
          </li>
          <li>
            <Typography className={classes.marginValue} variant="body2">
              If your dataset is stored in Google Drive, use
              <Link
                id="newaccount"
                href="https://sites.google.com/site/gdocs2direct/home"
              >
                {" "}
                {translate("link.googleDriveLink")}{" "}
              </Link>
              to generate a direct download link.
            </Typography>
          </li>
          <li>
            <Typography className={classes.marginValue} variant="body2">
              {translate("label.zipFormatMsg")}
            </Typography>
          </li>
        </ul>
      </div>
    );
  };

  const validURL = (str) => {
    var pattern = new RegExp(
      "^((ft|htt)ps?:\\/\\/)?" + // protocol
        "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|" + // domain name and extension
        "((\\d{1,3}\\.){3}\\d{1,3}))" + // OR ip (v4) address
        "(\\:\\d+)?" + // port
        "(\\/[-a-z\\d%@_.~+&:]*)*" + // path
        "(\\?[;&a-z\\d%@_.,~+&:=-]*)?" + // query string
        "(\\#[-a-z\\d_]*)?$",
      "i"
    ); // fragment locator
    return pattern.test(str.replace(/\s\s+/g, ""));
  };

  const spaceInBetween = (str) => {
    const pattern = new RegExp(/\s+/, "g");
    return pattern.test(str);
  };

  const handleSubmitDataset = (e) => {
    if (dataset.name.trim() === "" || dataset.url.trim() === "") {
      setError({
        ...error,
        name: !dataset.name.trim() ? "Name cannot be empty" : "",
        url: !dataset.url.trim() ? "URL cannot be empty" : "",
      });
    } else if (dataset.name.length > 256) {
      setError({ ...error, name: "Max 256 characters allowed" });
    } else if (spaceInBetween(dataset.url.trim())) {
      setError({ ...error, url: "URL contains space in between" });
    } else if (!validURL(dataset.url)) {
      setError({ ...error, url: "Invalid URL" });
    } else {
      handleApicall();
      setSnackbarInfo({
        ...snackbar,
        open: true,
        message: "Please wait while we process your request...",
        variant: "info",
      });
    }
  };

  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false });
  };
  const url = UrlConfig.dataset;

  return (
    <div>
      <div>
        {/* <div className={classes.breadcrum}>
                    <BreadCrum links={[url]} activeLink="Submit Dataset" />
                </div> */}
        <Paper elevation={3} className={classes.divStyle}>
          <Grid container spacing={5}>
            <Grid item xs={12} sm={12} md={12} lg={4} xl={4}>
              <FormControl className={classes.form}>
                <Typography className={classes.typography} variant="subtitle1">
                  {translate("label.howToSubmit")}
                </Typography>
                {renderInstrructions()}
              </FormControl>
            </Grid>
            <Hidden>
              <Grid item xl={1} lg={1} md={1} sm={1} xs={1}>
                <Divider orientation="vertical" />
              </Grid>
            </Hidden>
            <Grid item xs={12} sm={12} md={12} lg={7} xl={7}>
              <FormControl className={classes.form}>
                <Grid container spacing={6}>
                  <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                    <Grid container spacing={5}>
                      <Grid item xl={5} lg={5} md={5} sm={12} xs={12}>
                        <Typography
                          className={classes.typography}
                          variant="subtitle1"
                        >
                          {translate("button.submitDataset")}
                        </Typography>
                      </Grid>
                      {/* <Grid item xl={7} lg={7} md={7} sm={12} xs={12}>
                                                <div>
                                                <Button
                                                    size = "medium"
                                                    className={classes.updateBtn}
                                                    color="primary"
                                                    variant="outlined"
                                                    onClick={(e) => handleClick(e)}
                                                >
                                                    
                                                    Update an existing dataset
                                            </Button>
                                                <Popover
                                                    className={classes.popOver}
                                                    id={"update-dataset"}
                                                    open={Boolean(anchorEl)}
                                                    anchorEl={anchorEl}
                                                    onClose={handleClose}
                                                    anchorOrigin={{
                                                        vertical: 'bottom',
                                                        horizontal: 'left',
                                                    }}
                                                    transformOrigin={{
                                                        vertical: 'top',
                                                        horizontal: "center",
                                                    }}
                                                    children={renderUpdateDatasetSearch()}
                                                />
                                                </div>
                                            </Grid> */}
                    </Grid>
                  </Grid>
                  <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                    <Grid container spacing={3}>
                      <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                        <TextField
                          fullWidth
                          color="primary"
                          label="Dataset name"
                          value={dataset.name}
                          error={error.name ? true : false}
                          helperText={error.name}
                          onChange={(e) => {
                            setDatasetInfo({
                              ...dataset,
                              name: e.target.value,
                            });
                            setError({ ...error, name: false });
                          }}
                        />
                      </Grid>
                      <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                        <TextField
                           className={classes.textField}
                          fullWidth
                          color="primary"
                          label="Paste the URL of the public repository"
                          value={dataset.url}
                          error={error.url ? true : false}
                          helperText={error.url}
                          onChange={(e) => {
                            setDatasetInfo({ ...dataset, url: e.target.value });
                            setError({ ...error, url: false });
                          }}
                        />
                      </Grid>
                      {roles[0] === "BENCHMARK-DATASET-CONTRIBUTOR" && (
                        <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                          <FormControlLabel
                            control={
                              <Checkbox
                                color="primary"
                                checked={isChecked}
                                onChange={() => {
                                  setIsChecked(!isChecked);
                                  setOptionLabel("");
                                }}
                              />
                            }
                            label="It is a Benchmark Dataset"
                          />
                        </Grid>
                      )}
                      {/* {isChecked && (
                        <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                          <Typography
                            className={classes.typography}
                            variant="subtitle1"
                          >
                            Task
                          </Typography>
                          <Select
                            fullWidth
                            labelId="demo-simple-select-label"
                            id="demo-simple-select"
                            value={selectedOption}
                            onChange={(e) => setOptionLabel(e.target.value)}
                          >
                            {ModelTask.map((menu) => (
                              <MenuItem value={menu.value}>
                                {menu.label}
                              </MenuItem>
                            ))}
                          </Select>
                        </Grid>
                      )} */}
                    </Grid>
                  </Grid>
                </Grid>
                <Button
                  color="primary"
                  className={classes.submitBtn}
                  variant="contained"
                  size={"large"}
                  onClick={handleSubmitDataset}
                >
                  {translate("button.submit")}
                </Button>
              </FormControl>
            </Grid>
          </Grid>
        </Paper>
      </div>
      {snackbar.open && (
        <Snackbar
          open={snackbar.open}
          handleClose={handleSnackbarClose}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          message={snackbar.message}
          variant={snackbar.variant}
        />
      )}
    </div>
  );
};

export default withStyles(DatasetStyle)(SubmitDataset);

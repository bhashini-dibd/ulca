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
  InputAdornment,
  IconButton,
} from "@material-ui/core";
// import Autocomplete from '@material-ui/lab/Autocomplete';
import BreadCrum from "../../../components/common/Breadcrum";
import { withStyles } from "@material-ui/core/styles";
import { RadioButton, RadioGroup } from "react-radio-buttons";
import DatasetStyle from "../../../styles/Dataset";
import { useState, useRef, useEffect } from "react";
import { useHistory } from "react-router-dom";
import Snackbar from "../../../components/common/Snackbar";
import UrlConfig from "../../../../configs/internalurlmapping";
import SubmitModelApi from "../../../../redux/actions/api/Model/UploadModel/SubmitModel";
import C from "../../../../redux/actions/constants";
import { useDispatch, useSelector, shallowEqual } from "react-redux";
import { usePrevious } from "react-hanger";
import { PageChange } from "../../../../redux/actions/api/DataSet/DatasetView/DatasetAction";
import APITransport from "../../../../redux/actions/apitransport/apitransport";
import Files from "react-files";
import { translate } from "../../../../assets/localisation";

const SubmitModel = (props) => {
  const { classes } = props;
  const [anchorEl, setAnchorEl] = useState(null);
  const [label, setLabel] = useState("");
  const [model, setModelInfo] = useState({ file: "" });
  const dispatch = useDispatch();
  const [snackbar, setSnackbarInfo] = useState({
    open: false,
    message: "",
    variant: "success",
  });
  const submitStatus = useSelector(
    (state) => state.modelStatus.modelId,
    shallowEqual
  );
  const [error, setError] = useState({ file: "", type: false });
  const [search, setSearch] = useState(false);
  const history = useHistory();
  const modelIdStatus = usePrevious(submitStatus);
  // const handleClick = (event) => {
  //     setAnchorEl(event.currentTarget)
  // };

  useEffect(() => {
    if (submitStatus && modelIdStatus !== submitStatus) {
      history.push(
        `${process.env.PUBLIC_URL}/model/submission/${submitStatus}`
      );
    }
  }, [submitStatus]);

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
    let apiObj = new SubmitModelApi(model);
    fetch(apiObj.apiEndPoint(), {
      method: "post",
      body: apiObj.getFormData(),
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
            `${process.env.PUBLIC_URL}/model/submission/${rsp_data.data.modelId}`
          );
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

  const renderInstructions = () => {
    return (
      <div className={classes.list}>
        <ul>
          <li>
            <Typography className={classes.marginValue} variant="body2">
              {translate("label.instructions1")}
            </Typography>
          </li>
          <li>
            <Typography className={classes.marginValue} variant="body2">
              {translate("label.instructions2")}
            </Typography>
          </li>
          <li>
            <Typography className={classes.marginValue} variant="body2">
              {translate("label.instructions3")}
            </Typography>
          </li>
        </ul>
      </div>
    );
  };

  const handleSubmitModel = (e) => {
    if (!model.file) {
      setError({
        ...error,
        file: !model.file ? "URL cannot be empty" : "",
      });
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

  const handleChange = (files) => {
    if (files.length > 0) {
      setModelInfo({ ...model, file: files[0] });
      setLabel(files[0].name);
    }
  };

  const handleSnackbarClose = () => {
    setSnackbarInfo({ ...snackbar, open: false });
  };

  return (
    <div>
      <div>
        {/* <div className={classes.breadcrum}>
                    <BreadCrum links={[UrlConfig.model]} activeLink="Submit Model" />
                </div> */}
        <Paper elevation={3} className={classes.divStyle}>
          <Grid container spacing={5}>
            <Grid item xs={12} sm={12} md={4} lg={4} xl={4}>
              <FormControl className={classes.form}>
                <Typography className={classes.typography} variant="subtitle1">
                 {translate("label.howToSubmitModel")}
                </Typography>
            
                {renderInstructions()}
              </FormControl>
            </Grid>
            <Hidden>
              <Grid item xl={1} lg={1} md={1} sm={1} xs={1}>
                <Divider orientation="vertical" />
              </Grid>
            </Hidden>
            <Grid item xs={12} sm={12} md={7} lg={7} xl={7}>
              <FormControl className={classes.form}>
                <Grid container spacing={6}>
                  <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                    <Grid container spacing={5}>
                      <Grid item xl={5} lg={5} md={5} sm={12} xs={12}>
                        <Typography
                          className={classes.typography}
                          variant="subtitle1"
                        >
                          {translate("label.submitModel")}
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
                  <Grid
                    item
                    xl={12}
                    lg={12}
                    md={12}
                    sm={12}
                    xs={12}
                    style={{ paddingTop: "0px" }}
                  >
                    <Grid container spacing={3}>
                      {/* <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                        <TextField
                          fullWidth
                          color="primary"
                          label="Model name"
                          value={model.modelName}
                          error={error.name ? true : false}
                          helperText={error.name}
                          onChange={(e) => {
                            setModelInfo({
                              ...model,
                              modelName: e.target.value,
                            });
                            setError({ ...error, name: false });
                          }}
                        />
                      </Grid> */}
                      <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                        <div ondrop={handleChange}>
                          <TextField
                            fullWidth
                            color="primary"
                            label="Upload the file"
                            value={label}
                            error={error.file ? true : false}
                            helperText={error.file}
                            InputProps={{
                              endAdornment: (
                                <InputAdornment>
                                  <Files
                                    onChange={handleChange}
                                    //   onError={this.onFilesError}
                                    accepts={[".json", "audio/*"]}
                                    multiple
                                    clickable
                                  >
                                    <Button
                                      color="primary"
                                      className={classes.browseBtn}
                                      variant="outlined"
                                    >
                                      Browse
                                      <input type="file" hidden />
                                    </Button>
                                  </Files>
                                </InputAdornment>
                              ),
                            }}
                          />
                        </div>
                      </Grid>
                    </Grid>
                  </Grid>
                </Grid>
                <Button
                  color="primary"
                  className={classes.submitBtn}
                  variant="contained"
                  size={"small"}
                  onClick={handleSubmitModel}
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

export default withStyles(DatasetStyle)(SubmitModel);

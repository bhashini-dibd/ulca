import { withStyles } from "@material-ui/core/styles";
import DatasetStyle from "../../../styles/Dataset";
import { useHistory, useParams } from "react-router";
import {
  Grid,
  Typography,
  Card,
  CardMedia,
  CardContent,
} from "@material-ui/core";

const SearchDescription = (props) => {
  const { classes, title, para, index, color, image } = props;
  const history = useHistory();
  return (
    // <>
    //     <Typography variant="h6" className={classes.modelTitle}>{title}</Typography>

    //     <Typography style={{ fontSize: '20px', fontFamily: 'Roboto', textAlign: "justify" }} className={classes.modelPara}>{para}</Typography>
    // </>
    <Card
      sx={{ display: "flex" }}
      style={{
        minHeight: "100px",
        maxHeight: "100px",
        marginBottom: "5px",
        backgroundColor: color,
      }}
    >
      <Grid container>
        <Grid
          item
          xs={3}
          sm={3}
          md={3}
          lg={3}
          xl={3}
          style={{
            display: "flex",
            marginTop: "21px",
            justifyContent: "center",
          }}
        >
          <CardMedia
            component="img"
            style={{ width: "48px", height: "48px" }}
            image={image}
          />
        </Grid>
        <Grid
          item
          xs={9}
          sm={9}
          md={9}
          lg={9}
          xl={9}
          style={{ display: "flex", marginTop: "5px" }}
        >
          {/* <Box sx={{ display: 'flex', flexDirection: 'row' }}> */}
          <CardContent>
            <Typography
              component="div"
              variant="subtitle2"
              style={{ marginBottom: "0px" }}
            >
              {title}
            </Typography>
            {title !== "Source URL" || para === "NA" ? (
              <Typography
                variant="body2"
                color="text.secondary"
                className={classes.modelPara}
              >
                {para[0] !== undefined && para
                  ? para.replace(para[0], para[0].toUpperCase())
                  : typeof para === 'number' ? para : ""}
              </Typography>
            ) : (
              <Typography
                style={{
                  overflowWrap: "anywhere",
                  display: "-webkit-box",
                  "-webkit-line-clamp": "2",
                  "-webkit-box-orient": "vertical",
                  overflow: "hidden",
                }}
              ></Typography>
            )}
          </CardContent>
          {/* </Box> */}
        </Grid>
      </Grid>
    </Card>
  );
};
export default withStyles(DatasetStyle)(SearchDescription);

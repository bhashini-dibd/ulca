import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { useHistory, useParams } from 'react-router';
import {
    Grid,
    Link,
    Typography,
    Card,
    Box,
    CardMedia,
    CardContent
} from '@material-ui/core';
import ImageArray from '../../../../../utils/getModelIcons';

const ModelDescription = (props) => {
    const { classes, title, para, index } = props;
    const history = useHistory();
    return (
        /* {/* <Typography variant="h6" className={classes.modelTitle}>{title}</Typography>
             {title !== "Source URL" || para === "NA" ?
                 <Typography style={{ fontSize: '20px', fontFamily: 'Roboto', textAlign: "justify" }} className={title !== "Version" && classes.modelPara}>{para}</Typography> :
                 <Typography style={{ marginTop: '15px' }}><Link style={{ color: "#3f51b5", fontSize: '20px', }} variant="body2" href={para}>
                     {para}</Link></Typography>} */
        <Card sx={{ display: 'flex' }} style={{ minHeight: '110px', maxHeight: '110px', backgroundColor: ImageArray[index].color }}>
            <Grid container>
                <Grid item xs={3} sm={3} md={3} lg={3} xl={3} style={{ display: 'flex', marginTop: "21px", justifyContent: 'center' }}>
                    <CardMedia
                        component="img"
                        style={{ width: '48px', height: '48px' }}
                        image={ImageArray[index].imageUrl}
                    />
                </Grid>
                <Grid item xs={9} sm={9} md={9} lg={9} xl={9} style={{ display: 'flex', marginTop: "5px" }}>
                    {/* <Box sx={{ display: 'flex', flexDirection: 'row' }}> */}
                    <CardContent>
                        <Typography component="div" variant="subtitle2" style={{ marginBottom: '0px' }}>
                            {title}
                        </Typography>
                        {title !== 'Source URL' || para === "NA" ?
                            <Typography variant="body2" color="text.secondary" className={classes.modelPara} >
                                {para}
                            </Typography> :
                            <Typography style={{ overflowWrap: "anywhere" }}>
                                <Link style={{ color: "#3f51b5", fontSize: '14px' }} variant="body2" href={para}>
                                    {para}
                                </Link>
                            </Typography>
                        }
                    </CardContent>
                    {/* </Box> */}
                </Grid>
            </Grid>
        </Card>
    )
}
export default withStyles(DatasetStyle)(ModelDescription);
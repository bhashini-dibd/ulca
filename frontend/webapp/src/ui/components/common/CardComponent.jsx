import BlueCard from '../../../assets/card.svg';
import GreenCard from '../../../assets/card2.svg';
import Record from "../../../assets/record.svg"
import { Grid, Typography, withStyles } from '@material-ui/core';
import CommonStyles from '../../styles/Styles';
import { getLanguageName, getTaskName, FilterByDomain } from '../../../utils/getLabel';

const CardComponent = (props) => {
    const { value, classes } = props;
    return (
        <Grid container spacing={2} className={classes.cardGrid}>{
            value.filteredData.map((data, i) => {
                return (
                    <Grid item xs={12} sm={6} md={5} lg={4} xl={4}
                        className={classes.card}
                        style={{ background: `url(${i % 2 === 0 ? BlueCard : GreenCard}) no-repeat` }}>
                        <div onClick={()=>props.onClick(data)} style={{ padding: '10px 20px', boxSizing: "border-box",cursor:"pointer" }}>
                            <Typography className={classes.typeTypo} variant="body2">{getTaskName(data.task)}</Typography>
                            <Typography variant="h6" className={classes.modelname}>{data.modelName}</Typography>
                            <Grid className={classes.cardGrid} container>
                                <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                                    <Typography variant="caption" style={{ color: "#ffffff", opacity: '0.6' }} gutterBottom>{data.task === 'translation' ? 'Source' : 'Language'}</Typography>
                                    <Typography variant="body2" style={{ color: "#ffffff" }}>{getLanguageName(data.source)}</Typography>
                                </Grid>
                                {data.task === 'translation' && <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                                    <Typography variant="caption" style={{ color: "#ffffff", opacity: '0.6' }} gutterBottom>Target</Typography>
                                    <Typography variant="body2" style={{ color: "#ffffff" }}>{getLanguageName(data.target)}</Typography>
                                </Grid>}
                            </Grid>
                            <Grid style={{ marginTop: '20px', color: "#ffffff" }} container>
                                <Grid item xs={3} sm={3} md={3} lg={4} xl={4}>
                                    <Typography variant="caption" style={{ color: "#ffffff", opacity: '0.6' }} gutterBottom>Domain</Typography>
                                    <Typography variant="body2" style={{ color: "#ffffff" }}>{FilterByDomain([data.domain])[0].label}</Typography>
                                </Grid>
                                <Grid item xs={3} sm={3} md={3} lg={4} xl={4}>
                                    <Typography variant="caption" style={{ color: "#ffffff", opacity: '0.6' }} gutterBottom>Submitter</Typography>
                                    <Typography variant="body2" style={{ color: "#ffffff" }}>{data.submitter}</Typography>
                                </Grid>
                                <Grid item>
                                    <Typography variant="caption" style={{ color: "#ffffff", opacity: '0.6' }} gutterBottom>Published On</Typography>
                                    <Typography variant="body2" style={{ color: "#ffffff" }}>{data.publishedOn.split(",")[0]}</Typography>
                                </Grid>
                            </Grid>
                        </div>
                    </Grid>
                )
            })
        }
        </Grid>
    )
}

export default withStyles(CommonStyles)(CardComponent);
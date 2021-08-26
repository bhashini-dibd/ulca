import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { ArrowBack } from '@material-ui/icons';
import { useHistory, useParams } from 'react-router';
import ModelDescription from "./ModelDescription";
import HostedInference from "./HostedInference";
import { useLocation } from "react-router-dom";
import React, { useEffect, useState } from "react";
import Header from '../../../../components/common/Header';
import AudioRecord from './VoiceRecorder';
import Footer from "../../../../components/common/Footer"
import Theme from '../../../../theme/theme-default';
import { MuiThemeProvider } from '@material-ui/core/styles';
import {
    Grid,
    Typography,
    Button,
    Divider
} from '@material-ui/core';
import HostedInferASR from './HostedInferASR';

const SearchModelDetail = (props) => {
    const { classes } = props;
    const history = useHistory();
    const [data, setData] = useState("")
    const [modelTry, setModelTry] = useState(false)
    const location = useLocation();
    const params = useParams();
    useEffect(() => {

        setData(location.state)
    }, [location]);
    const description = [
        {
            title: "Description",
            para: data.description
        },
        {
            title: "Source URL",
            para: data.refUrl
        },
        {
            title: "Task",
            para: data.task
        },

        {
            title: "Languages",
            para: data.language
        },
        {
            title: "Domain",
            para: data.domain
        },
        {
            title: "Submitter",
            para: data.submitter
        },
        {
            title: "Published On",
            para: data.publishedOn
        }
    ]
    const { prevUrl } = location.state
    const handleCardNavigation = () => {
       // const { prevUrl } = location.state
        if (prevUrl === 'explore-models') {
            history.push(`${process.env.PUBLIC_URL}/model/explore-models`)
        } else {
            history.push(`${process.env.PUBLIC_URL}/model/my-contribution`)
        }
    }

    const handleClick = () => {
        history.push({
            pathname: `${process.env.PUBLIC_URL}/search-model/${params.srno}/model`,
            state: data
        })

    }

    return (
        <MuiThemeProvider theme={Theme}>
            <Header style={{ marginBottom: "10px" }} />
            {data && <div className={classes.parentPaper}>
                <Button size="small" color="primary" className={classes.backButton} startIcon={<ArrowBack />} onClick={() => handleCardNavigation()}>{prevUrl === 'explore-models'? 'Back to Model List' : 'Back to My Contribution'}</Button>

                <div style={{ display: "flex", justifyContent: "space-between" }}>
                    <Typography variant="h5" className={classes.mainTitle}>{data.modelName}</Typography>
                    {!params.model && <Button
                        color="primary"
                        className={classes.computeBtn}
                        variant="contained"
                        size={'small'}
                        onClick={() => handleClick()}
                    >
                        Try Model
                    </Button>}
                </div>
                {/* <hr style={{marginTop: "19px",opacity:'0.3' }}></hr> */}
                <Divider className={classes.gridCompute} />
                {params.model ?
                    <Grid container>
                        <Grid className={classes.leftSection} item xs={12} sm={12} md={8} lg={8} xl={8}>

                            {data.task !== 'asr' ? <HostedInference task={data.task} modelId={params.srno} source={data.source} target={data.target} /> : <HostedInferASR task={data.task} source={data.source} inferenceEndPoint={data.inferenceEndPoint} modelId={params.srno} />}
                        </Grid>
                        <Grid item xs={12} sm={12} md={4} lg={4} xl={4} style={{ paddingLeft: '24px' }}>
                            {description.map(des => <ModelDescription title={des.title} para={des.para} />)}

                        </Grid>


                    </Grid> :
                    <Grid container>

                        <Grid item xs={12} sm={12} md={9} lg={9} xl={9} >
                            {description.map(des => <ModelDescription title={des.title} para={des.para} />)}

                        </Grid>


                    </Grid>

                }
            </div>}
            <Footer />
        </MuiThemeProvider>
    )
}

export default withStyles(DatasetStyle)(SearchModelDetail);
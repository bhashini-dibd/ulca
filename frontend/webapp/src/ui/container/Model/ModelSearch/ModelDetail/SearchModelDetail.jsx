import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { ArrowBack } from '@material-ui/icons';
import { useHistory, useParams } from 'react-router';
import ModelDescription from "./ModelDescription";
import HostedInference from "./HostedInference";
import { useLocation } from "react-router-dom";
import React, { useEffect, useState } from "react";
import Header from '../../../../components/common/Header';

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
    const location = useLocation();
    const params = useParams();
    useEffect(() => {

        setData(location.state)
    }, [location]);
    console.log('data', data)
    const description = [
        {
            title: "Description",
            para: data.description
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
        }
    ]
    const handleCardNavigation = () => {
        // history.push(`${process.env.PUBLIC_URL}/benchmark/initiate`)
        history.goBack()
    }

    return (
        <>
            <><Header style={{ marginBottom: "10px" }} /><br /><br /><br /> </>
            {data && <div className={classes.parentPaper}>
                <Button size="small" color="primary" className={classes.backButton} startIcon={<ArrowBack />} onClick={() => handleCardNavigation()}>Back to model list</Button>
                <Typography variant="h6" className={classes.mainTitle}>{data.modelName}</Typography>
                {/* <hr style={{marginTop: "19px",opacity:'0.3' }}></hr> */}
                <Divider className={classes.gridCompute} />
                <Grid container>
                    <Grid className={classes.leftSection} item xs={12} sm={7} md={8} lg={8} xl={8}>

                        {data.task !== 'asr' ? <HostedInference task={data.task} modelId={params.srno} /> : <HostedInferASR task={data.task} modelId={params.srno} />}
                    </Grid>
                    <Grid item xs={4} sm={4} md={4} lg={4} xl={4} style={{ paddingLeft: '24px' }}>
                        {description.map(des => <ModelDescription title={des.title} para={des.para} />)}

                    </Grid>


                </Grid>
            </div>}
        </>
    )
}

export default withStyles(DatasetStyle)(SearchModelDetail);
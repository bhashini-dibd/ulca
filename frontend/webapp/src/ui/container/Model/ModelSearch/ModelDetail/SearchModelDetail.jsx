import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { ArrowBack } from '@material-ui/icons';
import { useHistory, useParams } from 'react-router';
import ModelDescription from "./ModelDescription";
import HostedInference from "./HostedInference";
import { useLocation } from "react-router-dom";
import React, { useEffect,useState } from "react";
import {
    Grid,
    Typography,
    Button,
    Divider
} from '@material-ui/core';

const SearchModelDetail = (props) => {
    const { classes } = props;
    const history = useHistory();
    const [data,setData] = useState("")
    const location = useLocation();

    useEffect(() => {
      
        setData(location.state)
    }, [location]);

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
        history.push(`${process.env.PUBLIC_URL}/benchmark/initiate/-1`)
    }

    console.log(data)
    return (
        <>
        {data && <div>
                    
            
            <Typography className={classes.mainTitle}>{data.modelName}</Typography>
            {/* <hr style={{marginTop: "19px",opacity:'0.3' }}></hr> */}
            <Divider className={classes.gridCompute} />
            <Grid container>
                <Grid className={classes.leftSection} item xs={7} sm={7} md={7} lg={7} xl={7}>
                    {description.map(des => <ModelDescription title={des.title} para={des.para} />)}

                </Grid>

                <Grid item xs={5} sm={5} md={5} lg={5} xl={5} style={{ paddingLeft: '24px' }}>
                    <HostedInference />
                </Grid>
            </Grid>
        </div>}
        </>
    )
}

export default withStyles(DatasetStyle)(SearchModelDetail);
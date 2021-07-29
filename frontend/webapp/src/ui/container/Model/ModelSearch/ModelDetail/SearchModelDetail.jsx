import { withStyles } from '@material-ui/core/styles';
import DatasetStyle from '../../../../styles/Dataset';
import { ArrowBack } from '@material-ui/icons';
import { useHistory, useParams } from 'react-router';
import ModelDescription from "./ModelDescription";
import HostedInference from "./HostedInference";
import {
    Grid,
    Typography,
    Button,
    Divider
} from '@material-ui/core';

const SearchModelDetail = (props) => {
    const { classes } = props;
    const history = useHistory();
    const description = [
        {
            title: "Description",
            para: ["Pretrained model on English language using a masked language modeling (MLM) objective. It was introduced in this paper and first released in this repository. This model is uncased: it does not make a difference between English and English. Disclaimer: The team releasing BERT did not write a model card for this model so this model card has been written by the Hugging Face team."]
        },
        {
            title: "Task",
            para: ["Pretrained model on English language using a masked language modeling (MLM) objective. It was introduced in this paper and first released in this repository. This model is uncased: it does not make a difference between English and English."]
        },
        {
            title: "Languages",
            para: ["Pretrained model on English language using a masked language modeling (MLM) objective. It was introduced in this paper and first released in this repository."]
        },
        {
            title: "Domain",
            para: ["Pretrained model on English language using a masked language modeling (MLM) objective. It was introduced in this paper and first released in this repository."]
        },
        {
            title: "Submitter",
            para: ["Pretrained model on English language using a masked language modeling (MLM) objective. It was introduced in this paper and first released in this repository. This model is uncased: it does not make a difference between English and English."]
        },
        {
            title: "Training Dataset",
            para: ["ABC", "XYZ"]
        }
    ]
    const handleCardNavigation = () => {
        history.push(`${process.env.PUBLIC_URL}/benchmark/initiate/-1`)
    }
    return (
        <div>

            <Button size="small" color="primary" className={classes.backButton} startIcon={<ArrowBack />} onClick={() => handleCardNavigation()}>Back to model list</Button>
            <Typography className={classes.mainTitle}>Reliiance_Jio_AICOE</Typography>
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
        </div>
    )
}

export default withStyles(DatasetStyle)(SearchModelDetail);
import { Grid } from "@material-ui/core";
import SingleAutoComplete from "./SingleAutoComplete";

const AdvanceFilter = (props) => {
    const { filters } = props;
    return <Grid container>
        {
            filters.map((filter,index) => <Grid style={{marginTop:"20px"}} item xs={12} sm={12} md={12} lg={12} xl={12}>
                <SingleAutoComplete placeholder={filter.placeholder} />
            </Grid>)
        }
    </Grid>
}

export default AdvanceFilter;
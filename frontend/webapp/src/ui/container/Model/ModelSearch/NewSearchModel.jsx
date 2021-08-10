import Tab from '../../../components/common/Tab';
import DatasetItems, { ModelTask } from '../../../../configs/DatasetItems';
import { useState } from 'react';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import CardComponent from '../../../components/common/CardComponent';
import { useEffect } from 'react';
import { useDispatch, useSelector, } from "react-redux";
import SearchModel from '../../../../redux/actions/api/Model/ModelSearch/SearchModel';
import updateFilter from '../../../../redux/actions/api/Model/ModelSearch/Benchmark';
import APITransport from '../../../../redux/actions/apitransport/apitransport';
import { FilterModel, clearFilterModel} from "../../../../redux/actions/api/Model/ModelView/DatasetAction"
import Record from "../../../../assets/no-record.svg";
import { useHistory } from "react-router-dom";
import C from "../../../../redux/actions/constants";
import FilterList from "./ModelDetail/Filter"

function TabPanel(props) {
    const { children, value, index, ...other } = props;
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box p={3}>
                    {children}
                </Box>
            )}
        </div>
    );
}

const NewSearchModel =() => {
    const filter = useSelector(state => state.searchFilter);
    const type = ModelTask.map(task => task.value);
    const [value, setValue] = useState(type.indexOf(filter.type))
    const [anchorEl, setAnchorEl] = useState(null);
   
    const popoverOpen = Boolean(anchorEl);
        const id = popoverOpen ? 'simple-popover' : undefined;

    const handleChange = (event, newValue) => {
        setValue(newValue);
        makeModelSearchAPICall(ModelTask[newValue].value);
    }
    console.log(filter)
    const dispatch = useDispatch();
    const searchModelResult = useSelector(state => state.searchModel);
    const history = useHistory();
    useEffect(() => {
        makeModelSearchAPICall(filter.type);
    }, [])

    const makeModelSearchAPICall = (type) => {
        const apiObj = new SearchModel(type, "", "")
        dispatch(APITransport(apiObj));
    }

    const handleShowFilter = (event) => {
        setAnchorEl(event.currentTarget);
}
const handleClose = () => {
        setAnchorEl(null);
};
const clearAll = (data) => {
        dispatch(clearFilterModel(data, C.CLEAR_FILTER_MODEL))
}
const apply = (data) => {
        handleClose()
        dispatch(FilterModel(data, C.SEARCH_FILTER))
}

    const handleClick = (data) => {
        dispatch(updateFilter({ source: "", filter: "", type: data.task }));
        history.push({
            pathname: `${process.env.PUBLIC_URL}/search-model/${data.submitRefNumber}`,
            state: data
        })
    }
console.log(searchModelResult)
    return (
        <Tab handleChange={handleChange} handleShowFilter={handleShowFilter} value={value} tabs={ModelTask} >
            <TabPanel value={value} index={value}>
                {searchModelResult.filteredData.length ?
                    <CardComponent onClick={handleClick} value={searchModelResult} /> :

                    <div style={{ background: `url(${Record}) no-repeat center center`, height: '287px', marginTop: '20vh' }}>
                        {/* //     <strong style={{
                    //         position: "absolute",
                    //         top: "65%"
                    //     }}>No record found!</strong>
                        // <div > */}
                        {/* <img
                            style={{ position: 'absolute', top: '45%', left: '38%', right: '38%' }}
                            src={Record}
                            alt="No records Icon"
                        /> */}
                    </div>
                    // {/* <span style={{ position: 'absolute', top: '70%', left: '42%', right: '38%' }}>No records found</span> */}
                    // </div>
                }
            </TabPanel>

            {popoverOpen && <FilterList
                                id={id}
                                open={popoverOpen}
                                anchorEl={anchorEl}
                                handleClose={handleClose}
                                filter={searchModelResult.filter}
                                selectedFilter={searchModelResult.selectedFilter}
                                clearAll={clearAll}
                                apply={apply}
                        />
                        }
        </Tab>
    )
}

export default NewSearchModel;
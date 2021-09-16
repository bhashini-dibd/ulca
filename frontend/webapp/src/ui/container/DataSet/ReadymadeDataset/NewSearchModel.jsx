import Tab from '../../../components/common/Tab';
import DatasetItems, { DatasetReadymade } from '../../../../configs/DatasetItems';
import { useState } from 'react';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import CardComponent from '../../../components/common/CardComponent';
import { useEffect } from 'react';
import { useDispatch, useSelector, } from "react-redux";
import SearchReadymade from '../../../../redux/actions/api/DataSet/Readymade/SearchReadymade';
import updateFilter from '../../../../redux/actions/api/Model/ModelSearch/Benchmark';
import APITransport from '../../../../redux/actions/apitransport/apitransport';
import { FilterModel, clearFilterModel} from "../../../../redux/actions/api/Model/ModelView/DatasetAction"
import Record from "../../../../assets/no-record.svg";
import { useHistory } from "react-router-dom";
import SearchList from '../../../../redux/actions/api/Model/ModelSearch/SearchList';

import C from "../../../../redux/actions/constants";
import FilterList from "./Filter"

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
    
    const filter = useSelector(state => state.searchReadymade);
    const type = DatasetReadymade.map(task => task.value);
    const [value, setValue] = useState(type.indexOf(filter.type))
    const [searchValue,setSearchValue] = useState("");
    const [anchorEl, setAnchorEl] = useState(null);
   
    const popoverOpen = Boolean(anchorEl);
        const id = popoverOpen ? 'simple-popover' : undefined;

    const handleChange = (event, newValue) => {
        setValue(newValue);
        makeModelSearchAPICall(DatasetReadymade[newValue].value);
        setSearchValue("");
        // dispatch(SearchList(searchValue))

    }
    const dispatch = useDispatch();
    const searchModelResult = useSelector(state => state.SearchReadymadeDataset);
    const history = useHistory();
    useEffect(() => {
        console.log(filter.type)
        makeModelSearchAPICall(filter.type);
    }, [])

    const makeModelSearchAPICall = (type) => {
        const apiObj = new SearchReadymade(type, "", "")
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


    const handleSearch=(event)=>{
        setSearchValue(event.target.value);
        dispatch(SearchList(event.target.value))
    }
    return (
        <Tab handleSearch={handleSearch} handleShowFilter={handleShowFilter} searchValue={searchValue} handleChange={handleChange} value={value} tabs={DatasetReadymade} >
            <TabPanel value={value} index={value}>
                {searchModelResult.filteredData.length ?
                    <CardComponent  value={searchModelResult} /> :
                    <div style={{ background: `url(${Record}) no-repeat center center`, height: '287px', marginTop: '20vh' }}>
                    </div>
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
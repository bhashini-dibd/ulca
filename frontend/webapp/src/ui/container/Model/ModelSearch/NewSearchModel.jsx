import Tab from '../../../components/common/Tab';
import DatasetItems, { ModelTask } from '../../../../configs/DatasetItems';
import { useState } from 'react';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import CardComponent from '../../../components/common/CardComponent';
import { useEffect } from 'react';
import { useDispatch, useSelector } from "react-redux";
import SearchModel from '../../../../redux/actions/api/Model/ModelSearch/SearchModel';
import APITransport from '../../../../redux/actions/apitransport/apitransport';
import Record from "../../../../assets/record.svg";

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

export default () => {
    const [value, setValue] = useState(0)
    const handleChange = (event, newValue) => {
        setValue(newValue);
        makeModelSearchAPICall(ModelTask[newValue].value);
    }
    const dispatch = useDispatch();
    const searchModelResult = useSelector(state => state.searchModel);

    useEffect(() => {
        makeModelSearchAPICall(ModelTask[0].value);
    }, [])

    const makeModelSearchAPICall = (type) => {
        const apiObj = new SearchModel(type, "", "")
        dispatch(APITransport(apiObj));
    }

    const handleClick = () => {
        console.log('Card Clicked!!!!')
    }

    return (
        <Tab handleChange={handleChange} value={value} tabs={ModelTask} >
            <TabPanel value={value} index={value}>
                {searchModelResult.responseData.length ?
                    <CardComponent onClick={handleClick} value={searchModelResult} /> :

                    <div style={{ background: `url(${Record}) no-repeat`, position: 'absolute',top:'45%',left:'40%',height:'287px',width:'287px',display:'flex',justifyContent:'center' }}>
                        <strong style={{
                            position: "absolute",
                            top: "65%"
                        }}>No record found!</strong>
                        {/* <img
                            style={{ position: 'absolute', top: '45%', left: '38%', right: '38%' }}
                            src={Record}
                            alt="No records Icon"
                        /> */}
                        {/* <span style={{ position: 'absolute', top: '70%', left: '42%', right: '38%' }}>No records found</span> */}
                    </div>
                }
            </TabPanel>
        </Tab>
    )
}
import Tab from '../../../components/common/Tab';
import DatasetItems, { ModelTask } from '../../../../configs/DatasetItems';
import { useState } from 'react';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';

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
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

export default () => {
    const [value, setValue] = useState(0)
    const handleChange = (event, newValue) => {
        setValue(newValue);
    }
    return (
        <Tab handleChange={handleChange} value={value} tabs={ModelTask} >
            <TabPanel value={value} index={value}>
            </TabPanel>
        </Tab>
    )
}
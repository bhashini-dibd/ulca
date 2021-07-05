import React, { useState } from 'react';
import DataSet from "../../../styles/Dataset";
import { withStyles, Button, Divider, Grid, Typography, Popover, FormGroup, Checkbox, FormControlLabel } from "@material-ui/core";

const FilterList = (props) => {
    const classes = props;
    // const [anchorEl, setAnchorEl] = React.useState(null);
    // const handleChange = (event) => {
    //     setState({ ...state, [event.target.name]: event.target.checked });
    // };
    // const handleClick = (event) => {
    //     setAnchorEl(event.currentTarget);
    // };

    // const handleClose = () => {
    //     setAnchorEl(null);
    // };
    // const open = Boolean(anchorEl);
    // const id = open ? 'simple-popover' : undefined;
    const data = {
        datasetType: [{ name: 'Parallel', state: true }, { name: 'Monolingual', state: true }, { name: 'ASR', state: true }, { name: 'OCR', state: false }],
        status: [{ name: 'Pending', state: true }, { name: 'In-Progress', state: true }, { name: 'Completed', state: true }, { name: 'Failed', state: false }]
    };
    const count = 0
    const { filter, selectedFilter, clearAll, apply } = props

    const [selectedType, setSelectedType] = useState(selectedFilter.datasetType)
    const [selectedStatus, setSelectedStatus] = useState(selectedFilter.status)

    const handleDatasetChange = (e) => {
        if (e.target.checked)
            setSelectedType([...selectedType, e.target.name])
        else {
            const selected = Object.assign([], selectedType)
            const index = selected.indexOf(e.target.name);

            if (index > -1) {
                selected.splice(index, 1);
                setSelectedType(selected)
            }
        }

    }
    const handleStatusChange = (e) => {
        if (e.target.checked)
            setSelectedStatus([...selectedStatus, e.target.name])
        else {
            const selected = Object.assign([], selectedStatus)
            const index = selected.indexOf(e.target.name);

            if (index > -1) {
                selected.splice(index, 1);
                setSelectedStatus(selected)
            }
        }
    }
    const handleClearAll = () => {
        setSelectedStatus([])
        setSelectedType([])
        clearAll({ datasetType: [], status: [] })
    }
    const isChecked = (type, param) => {
        const index = param === 'status' ? selectedStatus.indexOf(type) : selectedType.indexOf(type);
        if (index > -1)
            return true
        return false
    }

    console.log('helloi', selectedType, selectedStatus)
    return (
        <div>
            <Popover
                // style={{ width: '399px', minHeight: '246px' }}
                id={props.id}
                open={props.open}
                anchorEl={props.anchorEl}
                onClose={props.handleClose}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'right',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
            >
                <Button
                    onClick={handleClearAll}
                    color="primary" size="small" style={{ float: "right", margin: '9px 16px 0px auto', padding: '0' }}> Clear All
                </Button>
                <Grid container style={{ borderBottom: '1px solid #00000029', paddingLeft: '18.5px' }}>
                    <Grid item xs={5} sm={5} md={5} lg={5} xl={5}>
                        <Typography style={{ marginBottom: '9px' }}>Dataset Type</Typography>
                        <FormGroup>
                            {filter.datasetType.map((type) => {
                                return (
                                    <FormControlLabel
                                        control={
                                            <Checkbox
                                                checked={isChecked(type, 'dataset')}
                                                onChange={(e) => handleDatasetChange(e)}
                                                name={type}
                                                color="primary"
                                            />
                                        }
                                        label={type}
                                    />)
                            })}
                        </FormGroup>
                    </Grid>
                    <Grid item xs={1} sm={1} md={1} lg={1} xl={1}>
                        <Divider orientation="vertical"></Divider>
                    </Grid>
                    <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
                        <Typography style={{ marginBottom: '9px' }}>Status</Typography>
                        <FormGroup>
                            {filter.status.map((type) => {
                                return (
                                    <FormControlLabel
                                        control={
                                            <Checkbox
                                                checked={isChecked(type, 'status')}
                                                onChange={(e) => handleStatusChange(e)}
                                                name={type}
                                                color="primary"
                                            />
                                        }
                                        label={type}
                                    />)
                            })}
                        </FormGroup>
                    </Grid>
                </Grid>
                <Button
                    disabled={!(selectedType.length || selectedStatus.length)}
                    onClick={() => apply({ datasetType: selectedType, status: selectedStatus })}
                    color="primary" size="small" variant="contained" style={{ float: "right", margin: '5px', borderRadius: '4px', margin: '9px 16px 9px auto' }}> Apply
                </Button>

            </Popover>
        </div >
    );
}
export default withStyles(DataSet)(FilterList);
import React, { useState } from 'react';
import DataSet from "../../../styles/Dataset";
import { withStyles, Button, Divider, Grid, Typography, Popover, FormGroup, Checkbox, FormControlLabel } from "@material-ui/core";

const FilterList = (props) => {
    const {classes} = props;
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
                    color="primary" size="small" className={classes.clearAllBtn}> Clear All
                </Button>
                <Grid container className={classes.filterContainer}>
                    <Grid item xs={6} sm={6} md={6} lg={6} xl={6}>
                        <Typography variant="body2" className={classes.filterTypo}>Dataset Type</Typography>
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
                    <Grid item xs={5} sm={5} md={5} lg={5} xl={5}>
                        <Typography variant="body2" className={classes.filterTypo}>Status</Typography>
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
                    color="primary" size="small" variant="contained" className={classes.applyBtn}> Apply
                </Button>

            </Popover>
        </div >
    );
}
export default withStyles(DataSet)(FilterList);
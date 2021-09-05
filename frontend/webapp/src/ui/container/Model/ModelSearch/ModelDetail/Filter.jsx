import React, { useState } from 'react';
import DataSet from "../../../../styles/Dataset";
import { withStyles, Button, Divider, Grid, Typography, Popover, FormGroup, Checkbox, FormControlLabel } from "@material-ui/core";

const FilterList = (props) => {
    const { classes } = props;
    const { filter, selectedFilter, clearAll, apply } = props
    const [selectedDomain, setSelectedDomain] = useState(selectedFilter.domainFilter)
    const [selectedLanguage, setSelectedLanguage] = useState(selectedFilter.language)
    const [selectedSubmitter, setSelectedSubmitter] = useState(selectedFilter.submitter)
    const handleDatasetChange = (e) => {
        if (e.target.checked)
            setSelectedDomain([...selectedDomain, e.target.name])
        else {
            const selected = Object.assign([], selectedDomain)
            const index = selected.indexOf(e.target.name);

            if (index > -1) {
                selected.splice(index, 1);
                setSelectedDomain(selected)
            }
        }

    }
    const handleStatusChange = (e) => {
        if (e.target.checked)
            setSelectedLanguage([...selectedLanguage, e.target.name])
        else {
            const selected = Object.assign([], selectedLanguage)
            const index = selected.indexOf(e.target.name);

            if (index > -1) {
                selected.splice(index, 1);
                setSelectedLanguage(selected)
            }
        }
    }
    const handleSubmitterChange = (e) => {
        if (e.target.checked)
            setSelectedSubmitter([...selectedSubmitter, e.target.name])
        else {
            const selected = Object.assign([], selectedSubmitter)
            const index = selected.indexOf(e.target.name);

            if (index > -1) {
                selected.splice(index, 1);
                setSelectedSubmitter(selected)
            }
        }
    }
    const handleClearAll = () => {
        setSelectedDomain([])
        setSelectedLanguage([])
        setSelectedSubmitter([])
        clearAll({ modelType: [], status: [] })
    }
    const isChecked = (type, param) => {
        const index = param === 'domainFilter' ? selectedDomain.indexOf(type) : param === 'language' ? selectedLanguage.indexOf(type) : selectedSubmitter.indexOf(type);
        if (index > -1)
            return true
        return false
    }


    return (
        <div>
            <Popover
                // style={{ width: '600px', minHeight: '246px' }}
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

                <Grid container className={classes.filterContainer}>
                    <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                        <Typography variant="h6" className={classes.filterTypo}>Domain</Typography>
                        <FormGroup>
                            {filter.domainFilter.map((type) => {
                                return (
                                    <FormControlLabel
                                        control={
                                            <Checkbox
                                                checked={isChecked(type, 'domainFilter')}
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
                    {/* <Grid item xs={1} sm={1} md={1} lg={1} xl={1}>
                        <Divider orientation="vertical"></Divider>
                    </Grid> */}
                    <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                        <Typography variant="h6" className={classes.filterTypo}>Language</Typography>
                        <FormGroup>
                            {filter.language.map((type) => {
                                return (
                                    <FormControlLabel
                                        control={
                                            <Checkbox
                                                checked={isChecked(type, 'language')}
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

                    {/* <Grid item xs={1} sm={1} md={1} lg={1} xl={1}>
                        <Divider orientation="vertical"></Divider>
                    </Grid> */}
                    <Grid item xs={4} sm={4} md={4} lg={4} xl={4}>
                        <Typography variant="h6" className={classes.filterTypo}>Submitter</Typography>
                        <FormGroup>
                            {filter.submitter.map((type) => {
                                return (
                                    <FormControlLabel
                                        control={
                                            <Checkbox
                                                checked={isChecked(type, 'submitter')}
                                                onChange={(e) => handleSubmitterChange(e)}
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
                    disabled={!(selectedDomain.length || selectedLanguage.length || selectedSubmitter.length)}
                    onClick={() => apply({ domainFilter: selectedDomain, language: selectedLanguage, submitter: selectedSubmitter })}
                    color="primary" size="small" variant="contained" className={classes.applyBtn}> Apply
                </Button>
                <Button
                    disabled={!(selectedDomain.length || selectedLanguage.length || selectedSubmitter.length)}
                    onClick={handleClearAll}
                    size="small" variant="outlined" className={classes.clrBtn}> Clear All
                </Button>



            </Popover>
        </div >
    );
}
export default withStyles(DataSet)(FilterList);
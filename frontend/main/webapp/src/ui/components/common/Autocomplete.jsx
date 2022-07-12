
import React from 'react';
import Checkbox from '@material-ui/core/Checkbox';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import CheckBoxOutlineBlankIcon from '@material-ui/icons/CheckBoxOutlineBlank';
import CheckBoxIcon from '@material-ui/icons/CheckBox';

const icon = <CheckBoxOutlineBlankIcon fontSize="small" />;
const checkedIcon = <CheckBoxIcon fontSize="small" />;

export default function CheckboxesTags(props) {
  return (
    <Autocomplete
      // multiple
      disabled={props.disabled}
      multiple={!props.single}
      id={props.id}
      options={props.options}
      disableCloseOnSelect
      value={props.value}
      getOptionLabel={(option) => option.label ? option.label : ""}
      onChange={(event, value, reason) => props.handleOnChange(value, props.filter)}
      getOptionSelected={(option, value) => {
        return option.label === value.label
      }}
      renderOption={(option, { selected }) => (
        <React.Fragment>
          <Checkbox
            icon={icon}
            checkedIcon={checkedIcon}
            checked={selected}
            color="primary"
          />
          {option.label}
        </React.Fragment>
      )}
      renderInput={(params) => (
        <TextField {...params} variant="standard" label={props.label} error={props.error} helperText={props.error && props.helperText} />
      )}
    />
  );
}


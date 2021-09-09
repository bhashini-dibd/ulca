import Autocomplete from "@material-ui/lab/Autocomplete";
import { TextField } from "@material-ui/core";

const SingleAutoComplete = (props) => {
    const { value, id, labels, placeholder, error, handleChange } = props
    return (<Autocomplete
        value={value}
        id={id}
        options={labels}
        // onChange={(event, data) => handleChange(data, id)}
        renderInput={(params) => <TextField fullWidth {...params} label={placeholder} variant="standard"
            // error={error}
            // helperText={error && "This field is mandatory"}
        />}
    />)
}

export default SingleAutoComplete;
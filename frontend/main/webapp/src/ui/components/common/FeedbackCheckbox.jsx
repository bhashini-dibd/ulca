import {useState} from "react";
import FormControl from "@material-ui/core/FormControl";
import FormGroup from "@material-ui/core/FormGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
    root: {
      display: "flex"
    },
    formControl: {
      margin: theme.spacing(2)
    }
  }));

export default function CheckboxesGroup() {
    const classes = useStyles();
    const [state, setState] = useState({
      Options1: false,
      Options2: false,
      Options3: false,
      Options4: false
    });
  
    const handleChange = (event) => {
      setState({ ...state, [event.target.name]: event.target.checked });
    };
  
    const { Options1,Options2,Options3,Options4 } = state;
   
    Object.keys(state).forEach((element,i) => {
          console.log(element,"fjhbjd",i);    
    });
    return (
      <div className={classes.root}>
        <FormControl component="fieldset" className={classes.formControl}>
          
          <FormGroup>
           <FormControlLabel
              control={
                <Checkbox  color= 'primary' checked={Options1} onChange={handleChange} name="Options1" />
              }
              label="Options 1"
            />
            <FormControlLabel
              control={
                <Checkbox  color= 'primary' checked={Options2} onChange={handleChange} name="Options2" />
              }
              label="Options 2"
            />
            <FormControlLabel
              control={
                <Checkbox
                color= 'primary'
                  checked={Options3}
                  onChange={handleChange}
                  name="Options3"
                />
              }
              label="Options 3"
            />
            <FormControlLabel
              control={
                <Checkbox
                color= 'primary'
                  checked={Options4}
                  onChange={handleChange}
                  name="Options4"
                />
              }
              label="Options 4"
            />
          </FormGroup>
         
        </FormControl>
        </div>
    )}
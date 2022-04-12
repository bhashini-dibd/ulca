import { useState } from "react";
import FormControl from "@material-ui/core/FormControl";
import FormGroup from "@material-ui/core/FormGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import { makeStyles } from "@material-ui/core/styles";
import { pink, red } from '@material-ui/core/colors';

const useStyles = makeStyles((theme) => ({

  root: {
    display: "flex",

  },
  formControl: {
    margin: theme.spacing(2)
  },
  MuiIconButtonroot: {
    '&.Mui-checked': {
      color: pink[600],
    },
  },
  MuiSvgIcon: {
    root: {
      fill: "white",
    }
  },

}));

export default function CheckboxesGroup({ questions }) {
  const classes = useStyles();

  const generateState = ()=>{
    const obj = {}
     questions.forEach((q,i)=>{
      obj[i] = false;
    });
    return obj
  }

  const [state, setState] = useState(generateState());

  const handleChange = (event) => {
    setState({ ...state, [event.target.name]: event.target.checked });
  };


  return (
    <div className={classes.root}>
      <FormControl component="fieldset" className={classes.formControl}>
        <FormGroup>
          {
            questions.map((question, i) => (<FormControlLabel key={i}
              control={
                <Checkbox color='primary' checked={state[i]} onChange={handleChange} name={i}
                />
              }
              label={question.question}
            />))
          }
        </FormGroup>
      </FormControl>
    </div>
  )
}
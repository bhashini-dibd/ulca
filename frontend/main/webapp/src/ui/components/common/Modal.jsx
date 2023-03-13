import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Modal from "@material-ui/core/Modal";

function getModalStyle() {
  const top = 50;
  const left = 50;

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`,
  };
}

const useStyles = makeStyles((theme) => ({
  paper: {
    borderRadius: "20px",
    position: "absolute",
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
  },
}));

export default function SimpleModal(props) {
  const classes = useStyles();
  const { handleClose, open } = props;
  const [modalStyle] = React.useState(getModalStyle);

  return (
    <Modal open={open} onClose={handleClose} disableEscapeKeyDown={true}>
      <div style={modalStyle} className={classes.paper}>
        {props.children}
      </div>
    </Modal>
  );
}

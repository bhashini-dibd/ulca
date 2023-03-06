import {
  Accordion,
  AccordionSummary,
  Typography,
  AccordionDetails,
} from "@material-ui/core";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";

const MyAccordion = (props) => {
  const { label, children, color } = props;
  return (
    <Accordion style={{ backgroundColor: color }}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon />}
        aria-controls="panel1a-content"
        id="panel1a-header"
      >
        <Typography variant="h6">{label}</Typography>
      </AccordionSummary>
      <AccordionDetails style={{ backgroundColor: "white" }}>
        {children}
      </AccordionDetails>
    </Accordion>
  );
};

export default MyAccordion;

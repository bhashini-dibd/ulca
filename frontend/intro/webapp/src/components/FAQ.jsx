import React from 'react';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import {
  ExpansionPanel,
  ExpansionPanelSummary,
  ExpansionPanelDetails,
  Typography,
  useMediaQuery,
} from '@material-ui/core';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '62%', // Adjust the width for larger screens
    margin: '15px auto', // Center the content horizontally
    [theme.breakpoints.down('sm')]: {
      width: '90%', // Adjust the width for smaller screens
    },
  },
  heading: {
    fontSize: theme.typography.pxToRem(16),
    width: '60%', // Adjust the width for larger screens
    fontWeight: theme.typography.fontWeightBold,
    [theme.breakpoints.down('sm')]: {
      width: '90%', // Adjust the width for smaller screens
    },
  },
}));

const faqData = [
    {
        question: "What is ULCA?",
        answer: ""
    },
    {
        question: "How can I access ULCA?",
        answer: ""
    },
    {
        question: "How can I contribute data or models to ULCA?",
        answer: ""
    },
    {
        question: "How can I discover or download data or models from ULCA?",
        answer: ""
    },
    {
        question: "How can I evaluate the performance of my model on ULCA?",
        answer: ""
    },
    {
        question: "How can I contact ULCA or get help?",
        answer: ""
    },
]

const ExpandableCard = ({ question, answer }) => {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <ExpansionPanel style={{ backgroundColor: '#DBEDFF' }}>
        <ExpansionPanelSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel1a-content"
          id="panel1a-header"
        >
          <Typography style={{fontFamily: 'Inter'}} className={classes.heading}>{question}</Typography>
        </ExpansionPanelSummary>
        <ExpansionPanelDetails>
          <Typography style={{fontFamily: 'Inter'}}>{answer}</Typography>
        </ExpansionPanelDetails>
      </ExpansionPanel>
    </div>
  );
};

const FAQ = () => {
  const theme = useTheme();
  const isSmallScreen = useMediaQuery(theme.breakpoints.down('sm'));

  return (
    <div style={{ marginBottom: 40, paddingTop: 40, display: 'flex', flexDirection: 'column', justifySelf: 'center' }}>
      <Typography
        style={{ textAlign: 'center', letterSpacing: 1, margin: 20, fontFamily: 'Inter', fontWeight: 600, fontSize: '36px' }}
        variant="h4"
      >
        Frequently Asked Questions
      </Typography>
      {faqData.map((el, i) => (
        <ExpandableCard
          key={i}
          question={el.question}
          answer={el.answer}
          isSmallScreen={isSmallScreen}
        />
      ))}
    </div>
  );
};

export default FAQ;

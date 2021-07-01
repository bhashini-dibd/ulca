import React from 'react';
import Button from '@material-ui/core/Button';
import {Dialog,Typography,Divider} from '@material-ui/core';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import Slide from '@material-ui/core/Slide';

import Theme from "../../theme/theme-default";
import "../../../assets/appInfo.css"

export default function ResponsiveDialog(props) {
  

    const Transition = React.forwardRef(function Transition(props, ref) {
        return <Slide direction="left" ref={ref} {...props} />;
      });
  const getMuiTheme = () => createMuiTheme({
    overrides: {
        MuiButton: {
            label: {
              textTransform: "capitalize",
              fontFamily: '"Lato"',
              fontSize:"14px",
              fontWeight: "600",
              lineHeight: "1.14",
              letterSpacing: "0.14px",
              textAlign: "center",
              height: "26px",
            },
            sizeLarge: {
              height: "48px",
            },
            sizeSmall: {
              height: "36px",
            },
          
        },
         
        
        MuiDialog:{
            paper:{minWidth:"52.4%", minHeight:"739px"}
          },
   
    }
});

  

  const { open, handleClose} = props;
  
  return (
    
    <MuiThemeProvider theme={getMuiTheme()}> 

      <Dialog
        open={open}
        // TransitionComponent={Transition}
        // onClose ={() =>{handleClose()}}
        
      >
          <div class="IntroSection">
		<div class="close"><Button onClick ={() =>handleClose()} color= "primary" variant="outlined"><CloseIcon size={"small"}/>Close</Button></div>
		<div class="introdetails">
			<h4>ULCA - Universal Language Contribution APIs</h4>
			<h6>A MeitY initiative.</h6>
			<p>ULCA is an open-sourced scalable data platform, supporting various types of dataset for Indic languages, along with a user interface for interacting with the datasets.</p>
			<div class="features">
				<div class="featureColumn">
					<div class="featureDetail naviBlueColor">
						<h6 class="mb0">Dataset</h6>
						<p>Language datasets</p>
						<ul>
							<li>Parallel corpus</li>
							<li>Monolingual corpus</li>
							<li>ASR / TTS corpus</li>
							<li>OCR corpus</li>
						</ul>
					</div>
					<div class="arrow-pointer naviBlueColor">Open sourced</div>
					
				</div>
				<div class="featureColumn">
					<div class="featureDetail skyBlueColor">
						<h6 class="mb0">Model</h6>
						<p>Language specific tasks</p>
						<ul>
							<li>Translation</li>
							<li>Speech recognition</li>
							<li>Text to speech</li>
							<li>Optical Character Recognition</li>
						</ul>
					</div>
					<div class="arrow-pointer skyBlueColor">Transparent</div>
					
				</div>
				<div class="featureColumn">
					<div class="featureDetail purpleColor">
						<h6 class="mb0">Benchmark</h6>
						<p>Open benchmarking</p>
						<ul>
							<li>Large, diverse task specific benchmarks</li>
							<li>Research community approved metric system</li>
							
						</ul>
					</div>
					<div class="arrow-pointer purpleColor">Inclusive</div>
					
				</div>
			</div>
			<div class="why mt16">
				<h6>Why ULCA ?</h6>
				<ul class="mb0">
					<li>Consolidate & share all the knowledge wealth related to Indic languages with various NLP projects/initiatives.</li>
					<li>Single stop for working with multiple dataset types.</li>
					<li>Streamlined data format for each of the dataset types.</li>
					<li>Collect all possible metadata along with the datasets.</li>
					<li>Attribute proper credits to every contributor of every record in the dataset.</li>
					<li>Handle canonical checks and deduplication for each submitted dataset.</li>
					<li>Provides simple user interface to enable anyone to search & download based on various filters.</li>
					<li>Open sourced for building a best ecosystem for Indian languages.</li>
					<li>Quality check of the submitted datasets.</li>

				</ul>
			</div>

		</div>
	</div>
        
      </Dialog>
    </MuiThemeProvider>
  );
}
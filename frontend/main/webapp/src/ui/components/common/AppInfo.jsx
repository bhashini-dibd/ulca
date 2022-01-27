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
	const { open, handleClose} = props;
    const Transition = React.forwardRef(function Transition(props, ref) {
        return <Slide direction="right" ref={ref} {...props} />;
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
          
        },
		MuiTypography:{
			colorTextSecondary:{
				color:"black"
			},
			h4 : {
				fontSize: "1.5rem",
				// letterSpacing: "1.98px",
				fontFamily: '"Poppins","lato" ,sans-serif',
				fontWeight: "500",
				color:"#292576",
				margin: "0 0 .8rem"
			  },
			  h5 : {
				fontSize: "1.3125rem",
				padding:"10px 0 15px 0",
				fontFamily: '"Poppins","lato" ,sans-serif',
				fontWeight: "500",
				color:"black"
			  },
			  h6 : {
				fontSize: "1.125rem",
				fontFamily: '"Poppins","lato" ,sans-serif',
				fontWeight: "500",
				paddingTop:"4px",
				color:"black",
				margin: "0 0 .8rem"
			  },
			  body1 : {
				fontSize: "1rem",
				fontFamily: '"lato" ,sans-serif',
				fontWeight: "400",
				color:"black",
				padding:"10px 0 15px 0"
			  
			  },
			  body2 :{
				fontSize: "0.875rem",
				fontFamily: '"lato" ,sans-serif',
				fontWeight: "400",
				color:"black",
				padding:"10px 0 15px 0"
			  }
		},
         
        
        MuiDialog:{
            paper:{
				minWidth:"955px",
				border: "4px solid #292576",
	boxShadow: "0 0 2px rgba(0,0,0.5),0 0 8px rgba(0,0,0,.5)"
				
		},

          },
   
    }
});


  return (
    
    <MuiThemeProvider theme={getMuiTheme()}> 

      <Dialog
        open={open}
        // TransitionComponent={Transition}
        // onClose ={() =>{handleClose()}}
        
      >
		  <DialogContent>
          <DialogContentText id="alert-dialog-description">
           
         
         
		
		<div class="introdetails">
			<div class = "titlerow"><Typography variant={"h4"}>ULCA - Universal Language Contribution APIs</Typography> <Button onClick ={() =>handleClose()} style={{margin:"auto", marginRight:"0"}} color= "default"><CloseIcon size={"small"}/></Button></div>
			<Typography variant={"h6"}>A MeitY initiative.</Typography>
			<Typography variant={"body1"}>ULCA is an open-sourced scalable data platform, supporting various types of dataset for Indic languages, along with a user interface for interacting with the datasets.</Typography>
			<div class="features">
				<div class="featureColumn">
					<div class="featureDetail naviBlueColor">
					<Typography variant={"h6"}>Dataset</Typography>
						<p>Language datasets</p>
						<ul>
							<Typography style={{color:"white", marginLeft:"1.5rem", paddingTop:"-5px"}}>
							Parallel corpus <br/>
							Monolingual corpus <br/>
							ASR / TTS corpus <br/>
							OCR corpus<br/>
							</Typography>
							
						</ul>
					</div>
					<div class="arrow-pointer naviBlueColor">Open sourced</div>
					
				</div>
				<div class="featureColumn">
					<div class="featureDetail skyBlueColor">
					<Typography variant={"h6"}>Model</Typography>
						<p>Language specific tasks</p>
						<ul>
						<Typography style={{color:"white", marginLeft:"1.5rem", paddingTop:"-5px"}}>
						Translation <br/>
						Speech recognition <br/>
						Text to speech <br/>
						Optical Character Recognition<br/>
							</Typography>
						</ul>
					</div>
					<div class="arrow-pointer skyBlueColor">Transparent</div>
					
				</div>
				<div class="featureColumn">
					<div class="featureDetail purpleColor">
					<Typography variant={"h6"}>Benchmark</Typography>
					<p>Open benchmarking</p>
						<ul>
						<Typography style={{color:"white", marginLeft:"1.5rem", paddingTop:"-5px"}}>
						Large, diverse task specific benchmarks <br/>
						Research community approved metric system <br/>
						</Typography>
							
						</ul>
					</div>
					<div class="arrow-pointer purpleColor">Inclusive</div>
					
				</div>
			</div>
			<div class="why mt16">
			<Typography variant={"h6"}>Why ULCA ?</Typography>
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
	
	</DialogContentText>
        </DialogContent>
        
      </Dialog>
    </MuiThemeProvider>
  );
}
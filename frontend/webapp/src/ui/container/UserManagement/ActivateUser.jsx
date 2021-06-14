
import { withStyles, Typography,Link, MuiThemeProvider, createMuiTheme,Button } from "@material-ui/core";

import React, { useEffect, useState } from "react";
import {  useHistory } from "react-router-dom";
import Dialog from "../../components/common/Dialog"
import Snackbar from '../../components/common/Snackbar';
import UrlConfig from '../../../configs/internalurlmapping';
import ActivateUserId from "../../../redux/actions/api/UserManagement/ActivateUser"
import { useParams } from "react-router";

const ContributionList = (props) => {

        const history                 = useHistory();
       
        const [open, setOpen]         = useState(false)
        const [message, setMessage]   = useState("Do you want to delete")
        const [title, setTitle]       = useState("Delete")
        const {email, userId}                 = useParams()
        const [loading, setLoading] = useState(true);
        const [snackbar, setSnackbarInfo] = useState({
            open: false,
            message: '',
            variant: 'success'
        })
        
        useEffect(()                  => {
                 UserActivate()
        }, []);

        
  
        const  UserActivate  = () =>{
            let apiObj = new ActivateUserId(email, userId)
            var rsp_data =[]
              fetch(apiObj.apiEndPoint(), {
                method: 'post',
                body: JSON.stringify(apiObj.getBody()),
                headers: apiObj.getHeaders().headers
              }).then(async response => {
                rsp_data = await response.json();
                debugger
                setLoading(false)
                if (!response.ok) {
                  
                  return Promise.reject('');
                } else {
                  debugger
                  
                  setTimeout(() => {
                    history.push(`${process.env.PUBLIC_URL}/user/login`)
                }, 4000)
                 
                }
              }).catch((error) => {
                setLoading(false)
                
                  setSnackbarInfo({
                                  ...snackbar,
                                  open: true,
                                  message: rsp_data.message ? rsp_data.message : "Verification failed. please try again",
                                  variant: 'error'
                              })
              });
            
              }
            
        

        


              const handleSnackbarClose = () => {
                setSnackbarInfo({ ...snackbar, open: false })
            }


    debugger
        const { classes } = props;
        return (
                <div >
                       
                        
                       {snackbar.open &&
      <Snackbar
          open={snackbar.open}
          handleClose={handleSnackbarClose}
          anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
          message={snackbar.message}
          variant={snackbar.variant}
      />}
                </div>
        );
};

export default (ContributionList);

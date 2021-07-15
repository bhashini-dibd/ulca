

import React, { useEffect, useState } from "react";
import {  useHistory } from "react-router-dom";
import Snackbar from '../../components/common/Snackbar';
import ActivateUserId from "../../../redux/actions/api/UserManagement/ActivateUser";
import ActiveUser from "./ActiveUser";
import { useParams } from "react-router";

const ContributionList = (props) => {

        const history                 = useHistory();
       
        const [open, setOpen]         = useState(false)
        // const [message, setMessage]   = useState("Do you want to delete")
        //const [title, setTitle]       = useState("Delete")
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
                setLoading(false)
                if (!response.ok) {
                  
                  return Promise.reject('');
                } else {
                    setOpen(true)
                 
                }
              }).catch((error) => {
                setLoading(false)
                
                  setSnackbarInfo({
                                  ...snackbar,
                                  open: true,
                                  message: rsp_data.message ? rsp_data.message : "Something went wrong. please try again",
                                  variant: 'error'
                              })
              });

              setTimeout(() => {
                history.push(`${process.env.PUBLIC_URL}/user/login`)
            }, 4000)
            
              }
              const handleSnackbarClose = () => {
                setSnackbarInfo({ ...snackbar, open: false })
            }
        return (
                <div >
                       {open && <ActiveUser/>}
                        
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

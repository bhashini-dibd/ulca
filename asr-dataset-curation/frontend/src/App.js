
import React from "react";
import Form from "./Components/Form";
import Content from './Components/Content';
import {
  BrowserRouter as Router,
  Switch,
  Route,
 
} from "react-router-dom";

function App() {

  return (
  
<div>
<>
    
      <Router>
        <Switch>
         
          <Route exact path="/">  < Form/> </Route>
            
         
          <Route  path="/Content" > <Content/> </Route>
            
          
            
          
        </Switch>
      </Router>
    </>
    
{/* <Youtube/> */}

</div>
  
  );
}

export default App;

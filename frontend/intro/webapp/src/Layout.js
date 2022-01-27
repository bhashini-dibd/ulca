import React from "react";
import Dataset from "./components/Chart"
import Model from "./components/ModelChart"
import Benchmark from "./components/BenchmarkChart"
function App(props) {
  
  return (
      <div>
          <Dataset />
          <Model/>
          <Benchmark/>
          
          
          
      </div>
    
  );
}
export default (App);

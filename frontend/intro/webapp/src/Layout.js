import React, { useState } from "react";
import Dataset from "./components/Chart";
import Model from "./components/ModelChart";
import Benchmark from "./components/BenchmarkChart";

function App(props) {
  const componentObj = [
    { component: <Dataset /> },
    { component: <Model /> },
    { component: <Benchmark /> },
  ];
  const [data, setData] = useState([]);

  window.addEventListener("scroll", (e) => {
    if (window.pageYOffset > 300) {
      const comps = componentObj.map((elem) => elem.component);
      setData(comps);
    }
  });

  return <div>{data}</div>;
}
export default App;

import React, { useState } from "react";
import Footer from "./components/Footer";
import { WhyULCA } from "./components/WhyULCA";
import FAQ from "./components/FAQ";

function App(props) {
  return (
    <>
    <WhyULCA /> 
    <FAQ />
    <Footer />
    </>
    
  );
}
export default App;

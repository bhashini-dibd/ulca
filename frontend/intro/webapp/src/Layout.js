import React, { useState } from "react";
import Footer from "./components/Footer";
import { WhyULCA } from "./components/WhyULCA";
import FAQ from "./components/FAQ";
import HomeBanner from "./components/HomeBanner";
import HomeDatasets from "./components/HomeDatasets";
import VideoSection from "./components/VideoSection";
import Header from "./components/Header";
import DownloadApp from "./components/DownloadApp";
import { FooterNewDesign } from "./components/FooterNewDesign";

function App(props) {
  return (
    <>
    <Header />
      <HomeBanner />
      <HomeDatasets />
      <VideoSection />
      <WhyULCA />
      {/* <FAQ /> */}
      <DownloadApp />
      <FooterNewDesign />
      {/* <Footer /> */}
    </>
  );
}
export default App;
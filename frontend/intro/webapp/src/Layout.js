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
import TopContent from "./components/TopContent";
import AppContextProvider from "./context/ContextAPI";
import Contactus from "./components/Contactus";
import Clients from "./components/Clients";

function App(props) {
  return (
    <>
    <AppContextProvider>
      <TopContent />
      <Header />
      <HomeBanner />
      <HomeDatasets />
      <VideoSection />
      {/* <WhyULCA /> */}
      {/* <FAQ /> */}
      <DownloadApp />
      <Contactus />
      <Clients />
      <FooterNewDesign />
    </AppContextProvider>
      {/* <Footer /> */}
    </>
  );
}
export default App;
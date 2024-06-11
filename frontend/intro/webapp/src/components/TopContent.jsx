import React, { useState, useContext, useEffect } from "react";
import { LANGUAGE_LIST } from "../constants/MenuConstants";
import "./TopContent.css";
import { useTranslation } from "react-i18next";
import { AppContext } from "../context/ContextAPI";
import { CoffeeIcon, HomeIcon, LaptopIcon, FontIcon, BookReaderIcon, LanguageIcon } from "./FontAwesomeComponent";
import useMedia from "../hooks/useMedia";
export default function TopContent(props) {
  const {
    updateFont,
    // getDefaultFontSize,
    // fontIncrease,
    // setFontIncrease,
    // setFontDefault,
    // setFontDecrease,
  } = useContext(AppContext);
  const { t, i18n } = useTranslation();
  const getInitialState = () => {
    const value = { languageCode: "en", languageName: "English" };
    return value;
  };
  const [getSelectedLanguage, setSelectedLanguage] = useState(getInitialState);
  const [currentDate, setCurrentDate] = useState('');
  const [currentTime, setCurrentTime] = useState('');
  const isMobile = useMedia("(max-width:800px)");
  const classArray = [
    "en",
    "hi",
    "ta",
    "te",
    "as",
    "bn",
    "gu",
    "kn",
    "ml",
    "mr",
    "pa",
    "or",
  ];

  function getCurrentDateFormatted() {
    const months = [
      "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December"
    ];
  
    const date = new Date();
    const day = date.getDate();
    const month = months[date.getMonth()];
    const year = date.getFullYear();
  
    return `${day} ${month}, ${year}`;
  }

  function getCurrentTimeFormatted() {
    const date = new Date();
    let hours = date.getHours();
    const minutes = date.getMinutes();
    const seconds = date.getSeconds();
    const ampm = hours >= 12 ? 'PM' : 'AM';
  
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    const strMinutes = minutes < 10 ? '0' + minutes : minutes;
    const strSeconds = seconds < 10 ? '0' + seconds : seconds;
  
    return `${hours} : ${strMinutes} : ${strSeconds} ${ampm}`;
  }


  console.log(getCurrentDateFormatted());
  const handleChange = (e) => {
    const selectedLanguage = LANGUAGE_LIST.find(
      (item) => item.languageName === e.target.value
    );
    setSelectedLanguage(selectedLanguage);
    i18n.changeLanguage(selectedLanguage.languageCode);
    let cln = document.getElementById("bodyId").className;
    console.log(cln);
    // const selectedActiveLanguage = classArray.filter((item) => {
    //   item === selectedLanguage.languageCode
    //     ? document.body.classList.replace(cln, item)
    //     : "";
    // });
    // console.log("selectedActiveLanguage", selectedActiveLanguage);
  };
  const handleFontSize = (value) => {
    updateFont(value);
  };

  useEffect(() => {
    // Function to update date and time
    const updateDateTime = () => {
      setCurrentDate(getCurrentDateFormatted());
      setCurrentTime(getCurrentTimeFormatted());
    };

    // Initial update
    updateDateTime();

    // Update time every second
    const intervalId = setInterval(updateDateTime, 1000);

    // Cleanup interval on component unmount
    return () => clearInterval(intervalId);
  }, []);

  return (
    <div className="top__head">
      <div className="container">
        <div className="row">
          <div className="col-12">
            <div className="top__head-container">
              <div className="top__head-item">
               {currentDate} <span className="mx-2">|</span>  {currentTime} 
              </div>
              <div className="top__head-item">
                <ul className="top__listing"> 
              {isMobile ? <></> :  <>  <li className="top__listing-item">
                    <a href="https://bhashini.gov.in/#mainPage" className="top__listing-link" target="_blank">
                      {/* <span className="fa-solid fa-laptop u-icon"> 
                      <LaptopIcon />
                      </span> */}
                      <span className="u-text">{t("skipToMainContent")}</span>
                    </a>
                  </li>
                  <li className="top__listing-item">
                    <div className="top__listing-link">
                    <div
                        className="sub-link"
                      >
                        |
                      </div>
                      {/* <span className="u-icon">
                      <FontIcon/>
                      </span>                      */}
                      <a
                        href="#;"
                        className="sub-link"
                        onClick={() =>{handleFontSize('decrease')}}
                      >
                        -A
                      </a>
                      <a
                        href="#;"
                        className="sub-link"
                        onClick={() =>{handleFontSize('')}}
                      >
                        A
                      </a>
                      <a
                        href="#;"
                        className="sub-link"
                        onClick={() =>{handleFontSize('increase')}}
                      >
                        +A
                      </a>
                      <div
                        className="sub-link"
                      >
                        |
                      </div>
                    </div>
                  </li> </>}
                  {/* <li className="top__listing-item">
                    <a
                      className="top__listing-link"
                      href={'https://bhashini.gov.in/screen-reader'}
                      target="_blank"
                    >
                      <span className="u-icon"><BookReaderIcon /></span>
                      <span className="u-text">{t("screenReader")}</span>
                    </a>
                  </li> */}
                  
                  <li className="top__listing-item">
                    <a href="#" className="top__listing-link">
                      <span className="u-icon"><LanguageIcon /></span>
                      <select
                        className="lang"
                        value={getSelectedLanguage.languageName}
                        onChange={handleChange}
                        disabled
                      >
                        {LANGUAGE_LIST.map((item) => {
                          return item.isVisible ? (
                            <option
                              key={item.languageCode}
                            >{`${item.languageName}`}</option>
                          ) : (
                            ""
                          );
                        })}
                      </select>
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
import React, { useContext, useEffect, useRef, useState } from "react";
import "../styles/Home.css";
import useMedia from "../hooks/useMedia";
import downArrow from "../img/arrowDown.svg";
import { AppContext } from "../context/ContextAPI";
import { useTranslation } from "react-i18next";
import Arrow from '../assets/arrow_nav.svg'
import Logo from '../assets/bhashini-ulcaLogo.png'
const MobileHeader = () => {
  const [isOpen, setIsOpen] = useState(false);
  const { t } = useTranslation();
  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  

  const toggleSubMenu1 = (e) => {
    e.stopPropagation(); // Prevents the parent menu from closing
    e.target.parentElement.classList.toggle("submenu-open1");
  };

  const toggleSubMenu2 = (e) => {
    e.stopPropagation(); // Prevents the parent menu from closing
    e.target.parentElement.classList.toggle("submenu-open2");
  };
  return (
    <div className="navbar">
      <div className="hamburger-menu">
        <button className="hamburger-button" onClick={toggleMenu}>
          ☰
        </button>
        {isOpen && (
          <div className="menu">
            <ul>
              <li>
                <a className="nav-color" href="https://bhashini.gov.in/">
                  {t('home')}
                </a>
              </li>
              <li /* onClick={toggleSubMenu} */>
                <a
                  className="nav-color"
                  href="https://bhashini.gov.in/about-bhashini"
                >
                  {t('aboutBhashini')}
                </a>
                {/* <span className="submenu-arrow">▶</span> */}
                {/* <ul className="sub-menu">
                  <li className="sub-item">About Bhashini</li>
                  <li className="sub-item">Team</li>
                </ul> */}
              </li>
              {/* <li>
                {" "}
                <a
                  className="nav-color"
                  href="https://bhashini.gov.in/ecosystem"
                >
                  Ecosystem
                </a>
              </li> */}
              {/* <li>
                {" "}
                <a
                  className="nav-color"
                  href="https://anuvaad.bhashini.gov.in/"
                >
                  Anuvaad
                </a>
              </li> */}
              <li style={{ position: "relative" }} >
                <a href="https://bhashini.gov.in/prayog" className="nav-color">Prayog{" "}</a>
                {/* <img
                  className="submenu-arrow1"
                  style={{ height: "16px" }}
                  src={downArrow}
                ></img>
                <ul className="sub-menu2">
                  <li className="sub-item">
                    {" "}
                    <a
                      className="nav-color"
                      href="https://anuvaad.bhashini.gov.in/"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Anuvaad
                    </a>
                  </li>
                  <li className="sub-item">
                    {" "}
                    <a
                      className="nav-color"
                      href="https://chitraanuvaad.bhashini.co.in/"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Chitraanuvaad
                    </a>
                  </li>
                </ul> */}
              </li>
              <li style={{ position: "relative" }}>
                <a href="https://bhashini.gov.in/sahyogi" className="nav-color">Sahyogi{" "}</a>
                {/* <img
                  className="submenu-arrow"
                  style={{ height: "16px" }}
                  src={downArrow}
                ></img>
                <ul className="sub-menu1">
                  <li className="sub-item">
                    {" "}
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/sahyogi"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Empanelled Agencies
                    </a>
                  </li>
                  <li className="sub-item">
                    {" "}
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/career"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Career
                    </a>
                  </li>
                </ul> */}
              </li>
              <li>
                {" "}
                <a className="nav-color" href="https://bhashini.gov.in/sanchalak">
                  Sanchalak
                </a>
                </li>
              <li>
                {" "}
                <a
                  className="nav-color"
                  href="https://bhashini.gov.in/pravakta"
                >
                  Pravakta
                </a>
              </li>
              <li>
                {" "}
                <a className="nav-color" href="https://bhashini.gov.in/tender">
                  Tender's/EOI's
                </a>
              </li>
            </ul>
          </div>
        )}
      </div>
      <div className="logo">
        {" "}
        <div className="join">
          <a
            role="button"
            tabIndex={0}
            className="theme-btn btn btn-primary"
            href="https://bhashini.gov.in/bhashadaan/en/home"
            target="_blank"
            rel="noopener noreferrer"
            style={{borderRadius: "4px"}}
           >
              Bhashadaan
          </a>
        </div>
      </div>
    </div>
  );
};




const Dropdown = ({ label, items }) => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const handleClickOutside = (event) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      setIsDropdownOpen(false);
    }
  };

  useEffect(() => {
    if (isDropdownOpen) {
      document.addEventListener('click', handleClickOutside);
    } else {
      document.removeEventListener('click', handleClickOutside);
    }

    return () => {
      document.removeEventListener('click', handleClickOutside);
    };
  }, [isDropdownOpen]);

  return (
    <li className="nav-item dropdown" ref={dropdownRef}>
      <a
        className="nav-link dropdown-toggle"
        href="#"
        id="dropdownMenuLink"
        onClick={toggleDropdown}
        aria-expanded={isDropdownOpen}
      >
        {label} <img src={Arrow} alt="Dropdown Arrow" style={{marginLeft: '10px', width:"15px"}} />
      </a>
      <ul className={`dropdown-menu${isDropdownOpen ? ' show' : ''}`} aria-labelledby="dropdownMenuLink">
        {items.map((item, index) => (
          <li key={index}>
            <a
              className="dropdown-item nav-item"
              href={item.href}
              target="_self"
              rel="noopener noreferrer"
            >
              {item.label}
            </a>
          </li>
        ))}
      </ul>
    </li>
  );
};



function Header() {
  const isMobile = useMedia("(max-width:900px)");
  const {getdefaultFontSize} = useContext(AppContext);
  

  const dropdownItems1 = [
    { label: 'About Bhashini', href: 'https://bhashini.gov.in/about-bhashini' },
    { label: 'Team', href: 'https://bhashini.gov.in/teams' },
  ];

  const dropdownItems2 = [
    { label: 'About Arpan', href: 'https://bhashini.gov.in/about-bhashini' },
    { label: 'Team', href: 'https://bhashini.gov.in/teams' },
  ];


  const { t } = useTranslation();
  return (
    <>
      {isMobile ? (
        <div className="w-100 py-2">
          <div
            className="d-flex justify-content-center mx-3"
            style={{ height: "60px" }}
          >
            <img
              src={process.env.PUBLIC_URL + "/img/gov-logo-1.png"}
              alt="NLTM logo"
              style={{ objectFit: "contain" }}
            />
          </div>
        </div>
      ) : (
        <header>
          <div className="container">
            <div className="row align-items-center">
              <div className="col-3 col-md-5">
                <img
                  src={process.env.PUBLIC_URL + "/img/gov-logo-1.png"}
                  alt="NLTM logo"
                />
              </div>
              <div
                className="col-9 col-md-7"
                style={{ display: "flex", justifyContent: "flex-end" }}
              >
                <img
                  src={Logo}
                  alt="bhashini logo"
                  className="img-fluid"
                  style={{height:"60px"}}
                />
              </div>
            </div>
          </div>
        </header>
      )}
      {/* navbar */}
      <nav className="navbar navbar-expand navbar-light bg-light">
        {isMobile ? (
          <div className="d-flex flex-column w-100">
            <div className="logo" style={{ backgroundColor: "#fff" }}>
              {" "}
              <img
                src={process.env.PUBLIC_URL + "/img/Bhashini_en.png"}
                alt="bhashini logo"
                style={{ height:"60px", marginLeft:"1rem" }}
                className="img-fluid"
              />
            </div>
            <div className="w-100 py-2">
              <div
                // className="d-flex justify-content-center mx-3"
                style={{ height: "60px" }}
              >
                <MobileHeader />
              </div>
            </div>
          </div>
        ) : (
          <div className="container custom-container">
            <button
              className="navbar-toggler"
              type="button"
              data-toggle="collapse"
              data-target="#navbarsExample07"
              aria-controls="navbarsExample07"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarsExample07">
              <ul className="navbar-nav mr-auto navbarScroll TabNavbar">
                <li className="nav-item">
                  <a className="nav-link" href="https://bhashini.gov.in/">
                    {t('home')} <span className="sr-only">(current)</span>
                  </a>
                </li>
                {/* <li className="nav-item">
                  <a
                    className="nav-link"
                    href="https://bhashini.gov.in/about-bhashini"
                  >
                    {t('aboutBhashini')}
                  </a>
                </li> */}
                <Dropdown label="About" items={dropdownItems1} />
                <Dropdown label="Arpan" items={dropdownItems2} />
                <Dropdown label="Prayog" items={dropdownItems1} />
                <Dropdown label="Sagyogi" items={dropdownItems1} />
                <Dropdown label="Sanchalak" items={dropdownItems1} />
                <Dropdown label="Pravakta" items={dropdownItems1} />
                {/* <li className="dropdown" ref={dropdownRef}>
                  <a
                    className="dropdown-toggle nav-link"
                    href="#"
                    id="dropdownMenuLink"
                    // data-toggle="dropdown"
                    // aria-haspopup="true"
                    // aria-expanded="false"
                    onClick={toggleDropdown}
                    aria-expanded={isDropdownOpen}
                  >
                    Arpan
                    <img src={Arrow} alt="Dropdown Arrow" style={{marginLeft: '10px', width:"15px"}} />
                  </a>
                  <div
                    className={`dropdown-menu ${isDropdownOpen ? ' show' : ''}`}
                    data-bs-popper="static"
                    aria-labelledby="dropdownMenuLink"
                  >
                    <a
                      data-rr-ui-dropdown-item=""
                      className="dropdown-item nav-item"
                      href="https://bhashini.gov.in/about-bhashini"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      About Bhashini
                    </a>
                    <a
                      data-rr-ui-dropdown-item=""
                      className="dropdown-item nav-item"
                      href="https://bhashini.gov.in/teams"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Team
                    </a>
                  </div>
                </li> */}
             
             
             
              
                
                
              
              </ul>
            </div>
            <div className="join">
              <a
                role="button"
                tabIndex={0}
                className="theme-btn btn btn-primary"
                href="https://bhashini.gov.in/bhashadaan/en/home"
                target="_blank"
                rel="noopener noreferrer"
                style={{borderRadius: "4px", marginRight:"10px"}}
              >
                Bhashadaan
              </a>
              <a
                role="button"
                tabIndex={0}
                className="theme-btn btn btn-primary"
                href="https://bhashini.gov.in/bhashadaan/en/home"
                target="_blank"
                rel="noopener noreferrer"
                style={{borderRadius: "4px"}}
              >
                Bhashadaan
              </a>
            </div>
          </div>
        )}
      </nav>
    </>
  );
}

export default Header;

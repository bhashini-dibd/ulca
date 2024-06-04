import React, { useContext, useEffect, useRef, useState } from "react";
import "../styles/Home.css";
import useMedia from "../hooks/useMedia";
import downArrow from "../img/arrowDown.svg";
import { AppContext } from "../context/ContextAPI";
import { useTranslation } from "react-i18next";
import Arrow from '../assets/arrow_nav.svg'
import Logo from '../assets/bhashini-ulcaLogo.png'
import { withStyles, makeStyles } from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';
const MobileHeader = () => {
  const [isOpen, setIsOpen] = useState(false);
  const isSmallMobile = useMedia("(max-width:350px)");
  const { t } = useTranslation();
  const menuRef = useRef(null);
  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  const toggleSubMenu = (e, submenuClass) => {
    e.stopPropagation(); // Prevents the parent menu from closing
    e.target.closest('li').classList.toggle(submenuClass);
  };

  const handleClickOutside = (event) => {
    if (menuRef.current && !menuRef.current.contains(event.target)) {
      setIsOpen(false);
      // setOpenDropdown(null);
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <div className="navbar">
      <div className="hamburger-menu" ref={menuRef}>
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

              <li style={{ position: "relative" }}>
                <div onClick={(e) => toggleSubMenu(e, 'submenu-open2')} className="nav-color">
                  Prayog{" "}
                </div>
                <img
                  className="submenu-arrow"
                  style={{ height: "16px" }}
                  src={downArrow}
                  onClick={(e) => toggleSubMenu(e, 'submenu-open2')}
                />
                <ul className="sub-menu2">
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      About us
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Vision & Mission
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Our Objectives
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Bhashini Ecosystem
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      CEO & Our Team
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Our Journey
                    </a>
                  </li>
                </ul>
              </li>

              <li style={{ position: "relative" }}>
                <div  onClick={(e) => toggleSubMenu(e, 'submenu-open1')} className="nav-color">
                  Arpan{" "}
                </div>
                <img
                  className="submenu-arrow"
                  style={{ height: "16px" }}
                  src={downArrow}
                  onClick={(e) => toggleSubMenu(e, 'submenu-open1')}
                />
                <ul className="sub-menu1">
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                     Our Services
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                     Our Products
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                     Careers
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                     Tenders/EOI
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                     On - Premise
                    </a>
                  </li>
                </ul>
              </li>

              <li style={{ position: "relative" }}>
                <div className="nav-color" onClick={(e) => toggleSubMenu(e, 'submenu-open3')}>
                  Prayog
                </div>
                <img
                  className="submenu-arrow1"
                  style={{ height: "16px" }}
                  src={downArrow}
                  onClick={(e) => toggleSubMenu(e, 'submenu-open3')}
                />
                <ul className="sub-menu3">
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Parikshan App
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Model Vatika
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Data Vatika
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Abhiyantrikee Prayogshala
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Avinya Prayogshala
                    </a>
                  </li>
                </ul>
              </li>

              <li>
                <a className="nav-color" href="#">
                  Sahyogi
                </a>
              </li>

              <li>
                <a className="nav-color" href="#">
                  Sanchalak
                </a>
              </li>

             

              <li style={{ position: "relative" }}>
                <div  onClick={(e) => toggleSubMenu(e, 'submenu-open4')} className="nav-color" >
                  Pravakta
                </div>
                <img
                  className="submenu-arrow1"
                  style={{ height: "16px" }}
                  src={downArrow}
                  onClick={(e) => toggleSubMenu(e, 'submenu-open4')}
                />
                <ul className="sub-menu4">
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://link3.com"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      CEO Speaks
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      News & Events
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Awards & Achievement
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Videos & Gallery
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Industry Association
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="#"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Academician/ Competitions/Hackathons/GICs
                    </a>
                  </li>
                </ul>
              </li>

              {/* <li style={{ position: "relative" }}>
                <div className="nav-color" onClick={(e) => toggleSubMenu(e, 'submenu-open5')}>
                  Tender's/EOI's
                </div>
                <img
                  className="submenu-arrow1"
                  style={{ height: "16px" }}
                  src={downArrow}
                  onClick={(e) => toggleSubMenu(e, 'submenu-open5')}
                />
                <ul className="sub-menu5">
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://link5.com"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Link5
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://link6.com"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Link6
                    </a>
                  </li>
                </ul>
              </li>

              <li style={{ position: "relative" }}>
                <div className="nav-color"  onClick={(e) => toggleSubMenu(e, 'submenu-open6')}>
                  Contact
                </div>
                <img
                  className="submenu-arrow1"
                  style={{ height: "16px" }}
                  src={downArrow}
                  onClick={(e) => toggleSubMenu(e, 'submenu-open6')}
                />
                <ul className="sub-menu6">
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://link7.com"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Link7
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://link8.com"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Link8
                    </a>
                  </li>
                </ul>
              </li> */}
            </ul>
          </div>
        )}
      </div>
      <div className="logo">
<div className="join">
  {/* <a
    role="button"
    tabIndex={0}
    className="theme-btn btn btn-primary"
    href="https://bhashini.gov.in/bhashadaan/en/home"
    target="_blank"
    rel="noopener noreferrer"
    style={{ borderRadius: "4px",fontSize:isSmallMobile ? "9px" :"12px", marginRight:"5px",backgroundColor:"white", border:"1px solid #2947A3", color:"#2947A3" }}
  >
    Bhashadaan
  </a> */}
  <a
    role="button"
    tabIndex={0}
    className="theme-btn btn btn-primary"
    href="https://bhashini.gov.in/bhashadaan/en/home"
    target="_blank"
    rel="noopener noreferrer"
    style={{ borderRadius: "4px",fontSize:isSmallMobile ? "11px" :"14px" }}
  >
    Bhashadaan
  </a>
</div>
</div>
    </div>
  );
};




const useStylesBootstrap = makeStyles((theme) => ({
  arrow: {
    color: theme.palette.common.black,
  },
  tooltip: {
    backgroundColor: theme.palette.common.black,
    padding:"10px",
    fontSize:"12px"
  },
}));

function BootstrapTooltip(props) {
  const classes = useStylesBootstrap();

  return <Tooltip arrow classes={classes} {...props} />;
}

const Dropdown = ({ label, items,tooltipValue }) => {
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
       <BootstrapTooltip title={tooltipValue} placement="top">
      <a
        className="nav-link dropdown-toggle"
        href="#"
        id="dropdownMenuLink"
        onClick={toggleDropdown}
        aria-expanded={isDropdownOpen}
      >
        {label} <img src={Arrow} alt="Dropdown Arrow" style={{marginLeft: '10px', width:"15px"}} />
      </a>
      </BootstrapTooltip>
      <ul className={`dropdown-menu${isDropdownOpen ? ' show' : ''}`} aria-labelledby="dropdownMenuLink">
        {items.map((item, index) => (
          <li key={index}>
            <a
              className="dropdown-item nav-item"
              href={item.href}
              target="_blank"
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
    { label: 'About us', href: 'https://uat-bhashini.bhashini.co.in/about' },
    { label: 'Vision & Mission', href: '#' },
    { label: 'Our Objectives', href: '#' },
    { label: 'Bhashini Ecosystem', href: '#' },
    { label: 'CEO & Our Team', href: '#' },
    { label: 'Our Journey', href: '#' },
   
  ];

  const dropdownItems2 = [
    { label: 'Our Services', href: '#' },
    { label: 'Our Products', href: '#' },
    { label: 'Careers', href: '#' },
    { label: 'Tenders/EOI', href: '#' },
    { label: 'On - Premise', href: '#' },
    { label: 'Team', href: '#' },
  ];

  const dropdownItems3 = [
   
    { label: 'Parikshan App', href: '#' },
    { label: 'Model Vatika', href: '#' },
    { label: 'Data Vatika', href: '#' },
    { label: 'Abhiyantrikee Prayogshala', href: '#' },
    { label: 'Avinya Prayogshala', href: '#' },
   
  ];

  const dropdownItems4 = [
   
    { label: 'CEO Speaks', href: 'https://bhashini.gov.in/teams' },
    { label: 'News & events', href: 'https://bhashini.gov.in/teams' },
    { label: 'Awards & Achievements', href: 'https://bhashini.gov.in/teams' },
    { label: 'Videos & Gallery', href: 'https://bhashini.gov.in/teams' },
    { label: 'Industry Association', href: 'https://bhashini.gov.in/teams' },
    { label: 'Academician/Competitions/Hackathons/GICs', href: 'https://bhashini.gov.in/teams' },
   
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
            <div className="logo" style={{ backgroundColor: "#fff", display:"flex", justifyContent:"center" }}>
              {" "}
              <img
                src={Logo}
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
                  <a className="nav-link" href="https://uat-bhashini.bhashini.co.in/">
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
                <Dropdown label="About" items={dropdownItems1} tooltipValue="About Bhashini" />
                <Dropdown label="Arpan" items={dropdownItems2} tooltipValue="About Arpan"/>
                <Dropdown label="Prayog" items={dropdownItems3} tooltipValue="Explore our reference applications"/>
                <li className="nav-item">
                  <a className="nav-link" href="https://uat-bhashini.bhashini.co.in/sahyogi">
                   Sahyogi
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="https://uat-bhashini.bhashini.co.in/sanchalak">
                   Sanchalak
                  </a>
                </li>
                {/* <Dropdown label="Sagyogi" items={dropdownItems1} /> */}
                {/* <Dropdown label="Sanchalak" items={dropdownItems1} /> */}
                <Dropdown label="Pravakta" items={dropdownItems4} tooltipValue="Awareness and outreach"/>
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

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
    <div className="navbar navbar-row">
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

              <li style={{ position: "relative" }}>
                <div onClick={(e) => toggleSubMenu(e, 'submenu-open2')} className="nav-color">
                  About{" "}
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
                      href="https://bhashini.gov.in/about-bhashini"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      About us
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/vision-mission"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Vision & Mission
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/our-objectives"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Our Objectives
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/bhashini-ecosystem"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Bhashini Ecosystem
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/our-team"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      CEO & Our Team
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/bhashini-at-work"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Bhashini At Work
                    </a>
                  </li>
                </ul>
              </li>

              {/* <li style={{ position: "relative" }}>
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
              </li> */}

              <li style={{ position: "relative" }}>
                <div className="nav-color" onClick={(e) => toggleSubMenu(e, 'submenu-open3')}>
                  Arpan
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
                      href="https://bhashini.gov.in/services"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                       Services
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/product"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Our Product
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/career"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Careers
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/tender"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Tender/EOI
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/on-premise"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      On-Premise
                    </a>
                  </li>
                </ul>
              </li>

              <li style={{ position: "relative" }}>
                <div  onClick={(e) => toggleSubMenu(e, 'submenu-open4')} className="nav-color" >
                  Sahyogi
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
                      href="https://bhashini.gov.in/sahyogi"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                       About Sahyogi
                    </a>
                  </li>
                  <li className="sub-item">
                  <a
                      className="nav-color"
                      href="https://bhashini.gov.in/samudaye"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                     Bhashini Samudaye
                    </a>
                  </li>
                  <li className="sub-item">
                  <a
                      className="nav-color"
                      href="https://bhashini.gov.in/be-our-sahyogi"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                     Be our Sahyogi
                    </a>
                  </li>
                  <li className="sub-item">
                  <a
                      className="nav-color"
                      href="https://bhashini.gov.in/sahyogi/startup"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Startups
                    </a>
                  </li>
                  <li className="sub-item">
                  <a
                      className="nav-color"
                      href="https://bhashini.gov.in/sahyogi/anushandhan-mitra"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Anusandhan Mitra
                    </a>
                  </li>
                  <li className="sub-item">
                  <a
                      className="nav-color"
                      href="https://bhashini.gov.in/sahyogi-projects"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Sahyogi Projects
                    </a>
                  </li>
                </ul>
              </li>

              {/* <li>
                <a className="nav-color" href="https://bhashini.gov.in/sahyogi">
                  Sahyogi
                </a>
              </li> */}

              <li>
                <a className="nav-color" href="https://bhashini.gov.in/sanchalak">
                  Sanchalak
                </a>
              </li>

              <li>
                <a className="nav-color" href="https://bhashini.gov.in/parikshan-app">
                  Prayog
                </a>
              </li>

              {/* <li>
                <a className="nav-color" href="https://bhashini.gov.in/pravakta">
                  Pravakta
                </a>
              </li> */}

             

            

              <li style={{ position: "relative" }}>
                <div className="nav-color" onClick={(e) => toggleSubMenu(e, 'submenu-open5')}>
                  Pravakta
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
                      href="https://bhashini.gov.in/featured"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Featured
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/utsav"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Bhashini's Utsav
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/ministers-insights"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Minister's Insights
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/leadership-corner"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Leadership Corner
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/global-voices"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Global Voices
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/awards-recognition"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                      Awards & Recognition
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/bhashini-in-news"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                     Bhashini in News
                    </a>
                  </li>
                  <li className="sub-item">
                    <a
                      className="nav-color"
                      href="https://bhashini.gov.in/workshops"
                      target="_self"
                      rel="noopener noreferrer"
                    >
                     Workshops
                    </a>
                  </li>
                </ul>
              </li>
              <li>
                <a className="nav-color" href="https://bhashini.gov.in/bhashadaan/en/home">
                  BhashaDaan
                </a>
              </li>
              <li>
                <a className="nav-color" href="https://bhashini.gov.in/bhashadaan/en/home" style={{fontSize:"17px",fontWeight:"bold", color:"#2947a3"}}>
                  Login/SignUp
                </a>
              </li>
              <hr />

              <li>
            <a
              className="dropdown-item nav-item"
              href=""
              target="_blank"
              rel="noopener noreferrer"
              style={{padding:"0px"}}
            >
            <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="22"
            viewBox="0 0 18 22"
            fill="none"
          >
            <path
              d="M8.3919 10.5078L0.0546875 20.1764C0.145765 20.5421 0.312534 20.88 0.542112 21.164C0.77169 21.4481 1.05793 21.6706 1.37873 21.8145C1.69953 21.9584 2.04629 22.0198 2.39224 21.9939C2.73819 21.968 3.07406 21.8555 3.37392 21.6652L12.768 15.7442L8.3919 10.5078Z"
              fill="#EA4335"
            />
            <path
              d="M16.8221 8.86375L12.767 6.29688L8.21094 10.7376L12.7827 15.743L16.8143 13.1761C17.1727 12.9663 17.4723 12.6542 17.6811 12.273C17.89 11.8917 18.0003 11.4557 18.0003 11.0114C18.0003 10.567 17.89 10.131 17.6811 9.74978C17.4723 9.36856 17.1727 9.05642 16.8143 8.84664L16.8221 8.86375Z"
              fill="#FBBC04"
            />
            <path
              d="M0.0556016 1.82812C0.0142027 2.03023 -0.00420258 2.23714 0.000803294 2.44417V19.5567C0.00216955 19.7705 0.0284766 19.9833 0.0790873 20.1899L8.69029 10.778L0.0556016 1.82812Z"
              fill="#4285F4"
            />
            <path
              d="M8.45457 11.0033L12.768 6.28885L3.37396 0.350806C3.02103 0.121499 2.61811 0.000319712 2.20754 0C1.71228 0.00155129 1.23118 0.180681 0.838047 0.509898C0.444918 0.839115 0.161488 1.30023 0.03125 1.82248L8.45457 11.0033Z"
              fill="#34A853"
            />
          </svg>
         <span className="ml-3">Bhashini Mobile App</span> 
            </a>
          </li>

              <li>
            <a
              className="dropdown-item nav-item"
              href=""
              target="_blank"
              rel="noopener noreferrer"
              style={{padding:"0px"}}
            >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="22"
            viewBox="0 0 18 22"
            fill="none"
          >
            <path
              d="M15.0341 11.6983C15.045 10.8549 15.2702 10.028 15.6888 9.29449C16.1075 8.56098 16.7058 7.94469 17.4283 7.50296C16.9694 6.85108 16.3639 6.31461 15.66 5.93616C14.956 5.5577 14.1731 5.34769 13.3733 5.32281C11.6672 5.14471 10.0132 6.3381 9.1438 6.3381C8.25758 6.3381 6.919 5.34049 5.47757 5.36998C4.54521 5.39994 3.63656 5.66957 2.84014 6.15261C2.04372 6.63565 1.3867 7.31562 0.933086 8.12626C-1.03186 11.5096 0.433813 16.4819 2.31606 19.2167C3.25779 20.5558 4.3584 22.0517 5.79853 21.9986C7.20778 21.9405 7.73411 21.1049 9.4352 21.1049C11.1205 21.1049 11.6143 21.9986 13.0837 21.9649C14.5958 21.9405 15.5486 20.6198 16.4573 19.268C17.1339 18.3138 17.6546 17.2592 18 16.1433C17.1215 15.7737 16.371 15.1891 15.8145 14.4364C15.2581 13.6838 14.9135 12.7842 14.8137 11.8372C14.7937 11.7906 14.7937 11.744 14.8137 11.6983H15.0341Z"
              fill="black"
            />
            <path
              d="M12.4195 4.45555C12.8191 3.9891 13.137 3.4658 13.3583 2.90755C13.5796 2.34931 13.7002 1.76393 13.7152 1.17264C13.1185 1.25992 12.5357 1.42345 11.9856 1.65504C11.4355 1.88662 10.9269 2.18267 10.4766 2.53326C10.0536 2.87565 9.68479 3.27077 9.3828 3.70745C9.0808 4.14412 8.84987 4.61724 8.69945 5.11108C9.28793 5.18886 9.88513 5.13891 10.4573 4.96336C11.0294 4.78782 11.5641 4.48903 12.0311 4.08677L12.4195 4.45555Z"
              fill="black"
            />
          </svg>
          <span className="ml-3">Bhashini Mobile App</span> 
            </a>
          </li>

              {/* <li style={{ position: "relative" }}>
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

const Dropdown = ({ label, items,tooltipValue,link }) => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  const handleMouseEnter = () => {
    setIsDropdownOpen(true);
  };

  const handleMouseLeave = () => {
    setIsDropdownOpen(false);
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
    <li className="nav-item dropdown" ref={dropdownRef}  onMouseEnter={handleMouseEnter} 
    onMouseLeave={handleMouseLeave}>
       <BootstrapTooltip title={tooltipValue} placement="top">
      <a
        className="nav-link dropdown-toggle"
        href={link}
        id="dropdownMenuLink"
        // onClick={toggleDropdown}
        aria-expanded={isDropdownOpen}
      >
        {label} <img src={Arrow} alt="Dropdown Arrow" style={{marginLeft: '10px', width:"15px"}} />
      </a>
      </BootstrapTooltip>
      <ul className={`dropdown-menu${isDropdownOpen ? ' show' : ''}`} aria-labelledby="dropdownMenuLink" style={{top:"93%"}}>
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
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const {getdefaultFontSize} = useContext(AppContext);
  const dropdownRef = useRef(null);
  let timeoutId;
  const handleMouseEnter = () => {
    clearTimeout(timeoutId);
    setIsDropdownOpen(true);
  };

  const handleMouseLeave = () => {
    timeoutId = setTimeout(() => setIsDropdownOpen(false), 200);
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

  const dropdownItems1 = [
    { label: 'About us', href: 'https://bhashini.gov.in/about-bhashini' },
    { label: 'Vision & Mission', href: 'https://bhashini.gov.in/vision-mission' },
    { label: 'Our Objectives', href: 'https://bhashini.gov.in/our-objectives' },
    { label: 'CEO & Our Team', href: 'https://bhashini.gov.in/our-team' },
    { label: 'Bhashini Ecosystem', href: 'https://bhashini.gov.in/bhashini-ecosystem' },
    { label: 'Bhashini At Work', href: 'https://bhashini.gov.in/bhashini-at-work' },
   
  ];

  const dropdownItems2 = [
    { label: 'Our Services', href: 'https://bhashini.gov.in/services' },
    { label: 'Our Products', href: 'https://bhashini.gov.in/product' },
    { label: 'Careers', href: 'https://bhashini.gov.in/career' },
    { label: 'Tenders/EOI', href: 'https://bhashini.gov.in/tender' },
    { label: 'On - Premise', href: 'https://bhashini.gov.in/on-premise' },
    // { label: 'Team', href: '#' },
  ];

  const dropdownItems3 = [
   
    { label: 'About Sahyogi', href: 'https://bhashini.gov.in/sahyogi' },
    { label: 'Bhashini Samudaye', href: 'https://bhashini.gov.in/samudaye' },
    { label: 'Be our Sahyogi', href: 'https://bhashini.gov.in/be-our-sahyogi' },
    { label: 'Startups', href: 'https://bhashini.gov.in/sahyogi/startup' },
    { label: 'Anusandhan Mitra', href: 'https://bhashini.gov.in/sahyogi/anushandhan-mitra' },
    { label: 'Sahyogi Projects', href: 'https://bhashini.gov.in/sahyogi-projects' },
   
  ];

  const dropdownItems4 = [
   
    { label: 'Featured', href: 'https://bhashini.gov.in/featured' },
    { label: "Bhashini's Utsav", href: 'https://bhashini.gov.in/utsav' },
    { label: "Minister's Insights", href: 'https://bhashini.gov.in/ministers-insights' },
    { label: 'Leadership Corner', href: 'https://bhashini.gov.in/leadership-corner' },
    { label: 'Global Voices', href: 'https://bhashini.gov.in/global-voices' },
    { label: 'Awards & Recognition', href: 'https://bhashini.gov.in/awards-recognition' },
    { label: 'Bhashini in News', href: 'https://bhashini.gov.in/bhashini-in-news' },
    { label: 'Workshops', href: 'https://bhashini.gov.in/workshops' },
   
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
                style={{ display: "flex", justifyContent: "flex-end", paddingRight: "0" }}
              >
                <img
                  src={process.env.PUBLIC_URL + "/img/Bhashini_new_en.png"}
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
                src={process.env.PUBLIC_URL + "/img/Bhashini_new_en.png"}
                alt="bhashini logo"
                style={{ height:"60px", marginLeft:"-13px" }}
                className="img-fluid"
              />
            </div>
            <div className="mainNav w-100 py-2">
              <div
                // className="d-flex justify-content-center mx-3"
                style={{ height: "60px" }}
              >
                <MobileHeader />
              </div>
            </div>
          </div>
        ) : (
          <div className="container custom-container" style={{fontSize:"17px"}}>
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
                  <a className="nav-link" href="https://bhashini.gov.in/" style={{marginLeft:"0px"}}>
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
                <Dropdown label="About" items={dropdownItems1} tooltipValue="About Bhashini"  link="https://bhashini.gov.in/about-bhashini"/>
                <Dropdown label="Arpan" items={dropdownItems2} tooltipValue="About Arpan" link="https://bhashini.gov.in/services"/>
                {/* <Dropdown label="Prayog" items={dropdownItems3} tooltipValue="Explore our reference applications"/> */}
                {/* <li className="nav-item">
                  <a className="nav-link" href="https://bhashini.gov.in/sahyogi">
                   Sahyogi
                  </a>
                </li> */}
                <Dropdown label="Sahyogi" items={dropdownItems3} tooltipValue="About Sahyogi" link="https://bhashini.gov.in/sahyogi"/>

                <li className="nav-item">
                  <a className="nav-link" href="https://bhashini.gov.in/sanchalak">
                   Sanchalak
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="https://bhashini.gov.in/parikshan-app">
                   Prayog
                  </a>
                </li>
                {/* <li className="nav-item">
                <BootstrapTooltip title="Awareness and outreach" placement="top">
                  <a className="nav-link" href="https://bhashini.gov.in/pravakta">
                   Pravakta
                  </a>
                  </BootstrapTooltip>
                </li> */}
                <Dropdown label="Pravakta" items={dropdownItems4} tooltipValue="About Pravakta" link="https://bhashini.gov.in/featured"/>
                <li className="nav-item">
                  <a className="nav-link" href="https://bhashini.gov.in/bhashadaan/en/home">
                   Bhashadaan
                  </a>
                </li>
                
                {/* <Dropdown label="Sagyogi" items={dropdownItems1} /> */}
                {/* <Dropdown label="Sanchalak" items={dropdownItems1} /> */}
                {/* <Dropdown label="Pravakta" items={dropdownItems4} tooltipValue="Awareness and outreach"/> */}
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
            <div className="join" style={{display:"flex"}}  ref={dropdownRef}
  onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}>
            
              {/* <a
                role="button"
                tabIndex={0}
                className="theme-btn btn btn-primary"
                href="https://bhashini.gov.in/bhashadaan/en/home"
                target="_blank"
                rel="noopener noreferrer"
                style={{borderRadius: "4px"}}
              >
                Bhashadaan
              </a> */}
                <div className="relative inline-block group">
      <button
  className="buttonSmall"
  style={{
    paddingRight: "0.5rem",
    fontSize: "0.875rem",
    color: "white",
    backgroundColor: "#2947a3", // Replace with actual primary color
    border: "1px solid #2947a3", // Replace with actual primary color
    fontWeight: "600",
    borderRadius: "0.5rem",
    display: "flex",
    alignItems: "center",
    padding:"8px 20px"
  }}
 
  id="dropdownMenuLink1"  
  aria-expanded={isDropdownOpen}
>
        Bhashini Mobile App
        <div>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="25"
            viewBox="0 0 24 25"
            fill="none"
            // className="border-l border-[#5468A7] ml-2"
            style={{
              borderLeft: "1px solid #5468A7",
              marginLeft: "0.5rem"
            }}
          >
            <path
              d="M16.9997 9.6736C16.8123 9.48735 16.5589 9.38281 16.2947 9.38281C16.0305 9.38281 15.7771 9.48735 15.5897 9.6736L11.9997 13.2136L8.4597 9.6736C8.27234 9.48735 8.01889 9.38281 7.7547 9.38281C7.49052 9.38281 7.23707 9.48735 7.0497 9.6736C6.95598 9.76657 6.88158 9.87717 6.83081 9.99903C6.78004 10.1209 6.75391 10.2516 6.75391 10.3836C6.75391 10.5156 6.78004 10.6463 6.83081 10.7682C6.88158 10.89 6.95598 11.0006 7.0497 11.0936L11.2897 15.3336C11.3827 15.4273 11.4933 15.5017 11.6151 15.5525C11.737 15.6033 11.8677 15.6294 11.9997 15.6294C12.1317 15.6294 12.2624 15.6033 12.3843 15.5525C12.5061 15.5017 12.6167 15.4273 12.7097 15.3336L16.9997 11.0936C17.0934 11.0006 17.1678 10.89 17.2186 10.7682C17.2694 10.6463 17.2955 10.5156 17.2955 10.3836C17.2955 10.2516 17.2694 10.1209 17.2186 9.99903C17.1678 9.87717 17.0934 9.76657 16.9997 9.6736Z"
              fill="white"
            />
          </svg>
        </div>
      </button>
      <ul className={`dropdown-menu${isDropdownOpen ? ' show' : ''}`} aria-labelledby="dropdownMenuLink1"  style={{position:"absolute", right:"260px", left:"auto", marginTop:"0px", top:"95%"}}>
      
          <li>
            <a
              className="dropdown-item nav-item"
              href="https://play.google.com/store/apps/details?id=com.dibd.bhashini"
              target="_blank"
              rel="noopener noreferrer"
            >
            <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="22"
            viewBox="0 0 18 22"
            fill="none"
          >
            <path
              d="M8.3919 10.5078L0.0546875 20.1764C0.145765 20.5421 0.312534 20.88 0.542112 21.164C0.77169 21.4481 1.05793 21.6706 1.37873 21.8145C1.69953 21.9584 2.04629 22.0198 2.39224 21.9939C2.73819 21.968 3.07406 21.8555 3.37392 21.6652L12.768 15.7442L8.3919 10.5078Z"
              fill="#EA4335"
            />
            <path
              d="M16.8221 8.86375L12.767 6.29688L8.21094 10.7376L12.7827 15.743L16.8143 13.1761C17.1727 12.9663 17.4723 12.6542 17.6811 12.273C17.89 11.8917 18.0003 11.4557 18.0003 11.0114C18.0003 10.567 17.89 10.131 17.6811 9.74978C17.4723 9.36856 17.1727 9.05642 16.8143 8.84664L16.8221 8.86375Z"
              fill="#FBBC04"
            />
            <path
              d="M0.0556016 1.82812C0.0142027 2.03023 -0.00420258 2.23714 0.000803294 2.44417V19.5567C0.00216955 19.7705 0.0284766 19.9833 0.0790873 20.1899L8.69029 10.778L0.0556016 1.82812Z"
              fill="#4285F4"
            />
            <path
              d="M8.45457 11.0033L12.768 6.28885L3.37396 0.350806C3.02103 0.121499 2.61811 0.000319712 2.20754 0C1.71228 0.00155129 1.23118 0.180681 0.838047 0.509898C0.444918 0.839115 0.161488 1.30023 0.03125 1.82248L8.45457 11.0033Z"
              fill="#34A853"
            />
          </svg>
         <span className="ml-3">Bhashini Mobile App</span> 
            </a>
          </li>

          <li>
            <a
              className="dropdown-item nav-item"
              href="https://apps.apple.com/in/app/bhashini/id6446089978"
              target="_blank"
              rel="noopener noreferrer"
            >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="22"
            viewBox="0 0 18 22"
            fill="none"
          >
            <path
              d="M15.0341 11.6983C15.045 10.8549 15.2702 10.028 15.6888 9.29449C16.1075 8.56098 16.7058 7.94469 17.4283 7.50296C16.9694 6.85108 16.3639 6.31461 15.66 5.93616C14.956 5.5577 14.1731 5.34769 13.3733 5.32281C11.6672 5.14471 10.0132 6.3381 9.1438 6.3381C8.25758 6.3381 6.919 5.34049 5.47757 5.36998C4.54521 5.39994 3.63656 5.66957 2.84014 6.15261C2.04372 6.63565 1.3867 7.31562 0.933086 8.12626C-1.03186 11.5096 0.433813 16.4819 2.31606 19.2167C3.25779 20.5558 4.3584 22.0517 5.79853 21.9986C7.20778 21.9405 7.73411 21.1049 9.4352 21.1049C11.1205 21.1049 11.6143 21.9986 13.0837 21.9649C14.5958 21.9405 15.5486 20.6198 16.4573 19.268C17.1339 18.3138 17.6546 17.2592 18 16.1433C17.1215 15.7737 16.371 15.1891 15.8145 14.4364C15.2581 13.6838 14.9135 12.7842 14.8137 11.8372C14.7937 11.7906 14.7937 11.744 14.8137 11.6983H15.0341Z"
              fill="black"
            />
            <path
              d="M12.4195 4.45555C12.8191 3.9891 13.137 3.4658 13.3583 2.90755C13.5796 2.34931 13.7002 1.76393 13.7152 1.17264C13.1185 1.25992 12.5357 1.42345 11.9856 1.65504C11.4355 1.88662 10.9269 2.18267 10.4766 2.53326C10.0536 2.87565 9.68479 3.27077 9.3828 3.70745C9.0808 4.14412 8.84987 4.61724 8.69945 5.11108C9.28793 5.18886 9.88513 5.13891 10.4573 4.96336C11.0294 4.78782 11.5641 4.48903 12.0311 4.08677L12.4195 4.45555Z"
              fill="black"
            />
          </svg>
          <span className="ml-3">Bhashini Mobile App</span> 
            </a>
          </li>
     
      </ul>
    </div>
    <div>
    <a
                role="button"
                tabIndex={0}
                className="theme-btn btn btn-primary"
                href="https://dashboard.bhashini.co.in/user/login"
                target="_blank"
                rel="noopener noreferrer"
                style={{borderRadius: "4px", marginLeft:"10px", padding:"10px 20px", backgroundColor:"white",border:"1px solid #2947a3", color:"#2947a3", fontSize:"17px"}}
              >
                Login/SignUp
              </a>
    </div>           
            </div>
          </div>
        )}
      </nav>
    </>
  );
}

export default Header;

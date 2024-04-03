import AppImg from "../img/DownloadApp/AppNew1.svg";
import playStore from '../img/DownloadApp/googlePlay.png';
import appStore from '../img/DownloadApp/appStore.png'
import playStoreQR from '../img/DownloadApp/PlaystoreQRCode.jpg'
import appStoreQR from '../img/DownloadApp/AppleQRCode.jpg'
import "./DownloadApp.css";
import { Container } from "react-bootstrap";
import useMedia from "../hooks/useMedia";
const DownloadApp = () => {
  const isMobile = useMedia("(max-width:600px)");
  return (
    <>
      <div className="section">
        <div className="content-left1">
          <Container>
            <div className="DownloadAppLeft">
              <div className="DownloadAppLeftContent">
                <div className="DownloadAppLeftHeading">
                  Breaking Language Barriers Across India
                </div>
                <div className="DownloadAppLeftText">
                  {" "}
                  Speak Your Language, All of India Understands!
                </div>
              </div>
              {isMobile ? <></> : <div>
                {" "}
                <div className="DownloadAppDownloadTxt">Download Bhashini App</div>
                <div className="DownloadAppleftButtonContainer">
                  <a href="https://play.google.com/store/apps/details?id=com.dibd.bhashini&hl=en&gl=US" target="_blank">

                    <img className="DownloadImage" src={playStore} />
                  </a>
                  <a href="https://apps.apple.com/in/app/bhashini/id6446089978" target="_blank">

                    <img className="DownloadImage" src={appStore} />
                  </a>
                </div>
              </div>}
            </div>
          </Container>
        </div>
        <div className="content-left2">
          <Container className="content-left2Container">

            {isMobile ?
              <div>
                {" "}
                <div className="DownloadAppDownloadTxtMobile">Download Bhashini App</div>
                <div className="DownloadAppleftButtonContainerMobile">
                  <a href="https://play.google.com/store/apps/details?id=com.dibd.bhashini&hl=en&gl=US" target="_blank">

                    <img className="DownloadImage" src={playStore} />
                  </a>
                  <a href="https://apps.apple.com/in/app/bhashini/id6446089978" target="_blank">

                    <img className="DownloadImage" src={appStore} />
                  </a>
                </div>
              </div>
              :
              <>
                <img src={playStoreQR} alt="playstore qrcode" className="content-left2ContainerImg" />
                <div className="QrCodeSeparator"></div>
                <img src={appStoreQR} alt="appstore qrcode" className="content-left2ContainerImg" />
              </>}

          </Container>
        </div>
        <div className="image-right">
          <img src={AppImg} alt="Background Image" className="background-image" />
        </div>

      </div>

    </>
  );
};

export default DownloadApp;

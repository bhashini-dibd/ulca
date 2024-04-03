import XLogo from "../img/Footer/XLogo.svg";
import fbLogo from "../img/Footer/fbLogo.svg";
import instaLogo from "../img/Footer/instagramLogo.svg";
import linkedinLogo from "../img/Footer/linkedinLogo.svg";


const FootersData = [
  {
    id : 1,
    title : "About",
    size:3,
    links : [
      {
        id:1,
        text:"About us",
        link:"/about-bhashini",
        icon:""
      },
      {
        id:2,
        text:"Career",
        link:"/career",
        icon:""
      },
      {
        id:3,
        text:"Contact us",
        link:"/ContactUs",
        icon:""
      },
      {
        id:4,
        text:"Terms & Conditions",
        link:"/terms-of-use",
        icon:""
      },
      {
        id:5,
        text:"Policy",
        link:"/privacy-policy",
        icon:""
      },
    ]
  },
  {
    id : 2,
    title : "Prayog",
    tooltipData:"Explore our reference applications",
    size:2,
    links : [
      {
        id:1,
        text:"Chitraanuvaad",
        link:"https://chitraanuvaad.bhashini.co.in/",
        icon:""
      },
      {
        id:2,
        text:"Anuvaad",
        link:"https://anuvaad.bhashini.gov.in/",
        icon:"",
      },
      {
        id:3,
        text:"Vanianuvaad",
        link:"/prayog?tab=vaanianuvaad",
        icon:"",
      },
      {
        id:4,
        text:"Lekhaanuvaad",
        link:"/prayog?tab=lekhaanuvaad",
        icon:"",
      },
      
    ]
  },
  {
    id : 3,
    title : "Sahyogi",
    tooltipData:"Discover our Associates",
    size:3,
    links : [
      {
        id:1,
        text:"Translation service provider",
        link:"/translation-service-providers",
        icon:"",
      },
      {
        id:2,
        text:"Start up",
        link:"/startup",
        icon:""
      },
      {
        id:3,
        text:"State gov",
        link:"/sahyogi?tab=stategov",
        icon:""
      },
      {
        id:4,
        text:"Mitra",
        link:"/sahyogi?tab=mitra",
        icon:""
      },
      {
        id:5,
        text:"Udayat",
        link:"/sahyogi?tab=udayat",
        icon:""
      },
      
    ]
  },
  {
    id : 4,
    title : "Sanchalak",
    tooltipData:"Drivers, Services and Offerings",
    size:2,
    links : [
      {
        id:1,
        text:"ULCA",
        link:"https://bhashini.gov.in/ulca",
       

      },
      {
        id:2,
        text:"NHLT",
        link:"/nhlt",
        
      },
      
    ]
  },
  {
    id : 5,
    title : "Other",
    size:2,
    links : [
      {
        id:1,
        text:"Pravakta",
        link:"/pravakta",
        icon:"",
      },
      {
        id:2,
        text:"Tenders",
        link:"/tender",
        icon:""
      },
      {
        id:3,
        text:"Ecosystem",
        link:"/ecosystem",
        icon:""
      },
      
    ]
  },
]

const socialMedia = [
  {
      id:1,
      name:"X",
      image:XLogo,
      link:"https://twitter.com/_BHASHINI"
  },
  {
      id:2,
      name:"Instagram",
      image:instaLogo,
      link:"https://www.instagram.com/_officialbhashini/"
  },
  {
      id:3,
      name:"LinkedIn",
      image:linkedinLogo,
      link:"https://www.linkedin.com/company/96244597/admin/feed/posts/"
  },
  {
      id:4,
      name:"Facebook",
      image:fbLogo,
      link:"https://www.facebook.com/profile.php?id=100093281985246"
  },
]

export { FootersData,socialMedia };

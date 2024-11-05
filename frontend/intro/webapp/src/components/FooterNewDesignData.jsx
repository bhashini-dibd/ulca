import XLogo from "../img/Footer/XLogo.svg";
import fbLogo from "../img/Footer/fbLogo.svg";
import instaLogo from "../img/Footer/instagramLogo.svg";
import linkedinLogo from "../img/Footer/linkedinLogo.svg";


const FootersData = [
  {
    id : 1,
    title : "About",
    size:2,
    sizeMob:6,
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
        text:"Terms & Conditions",
        link:"/terms-of-use",
        icon:""
      },
      {
        id:4,
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
    sizeMob:6,
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
    size:2,
    sizeMob:6,
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
    sizeMob:6,
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
    sizeMob:12,
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
   {
    id : 5,
    title : "Contact Us",
    size:2,
    sizeMob:12,
    links : [
      {
        id:1,
        text:"<p style='margin-top:10px;'>Email <br /> <a href='mailto:ceo-dibd@digitalgov.co.in' style='pointer-events: auto;text-decoration:underline;color:#4D4D4D;'>ceo-dibd@digitalgov.co.in </a></p>",
        link:"",
        icon:"",
      },
      {
        id:2,
        text:"<p>Phone <br /> <a href='callto:011 24301361' style='width:100%;pointer-events: auto;text-decoration:underline; color:#4D4D4D;'>011-24301361</a> <hr style='color:black;opacity:0.25;' /></p> ",
        link:"",
        icon:""
      },
      {
        id:3,
        text:"<a href='https:/bhashini.gov.in/ContactUs' style='color:#0671E0;'><p style='text-align:start;pointer-events: auto;font-size:16px;font-weight:600'>Write to us </p></a>",
        link:"",
        icon:"check"
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

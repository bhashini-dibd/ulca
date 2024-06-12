import React from 'react';
import Slider from 'react-slick';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import '../styles/Clients.css'
import Client1 from '../assets/Clients/client1.svg';
import Client2 from '../assets/Clients/client2.svg';
import Client3 from '../assets/Clients/client3.svg';
import Client4 from '../assets/Clients/client4.svg';
import Client5 from '../assets/Clients/client5.svg';
const cardData = [
    { title: "Card 1", image: Client1 },
    { title: "Card 2", image: Client2 },
    { title: "Card 3", image: Client3 },
    { title: "Card 4", image: Client4 },
    { title: "Card 5", image: Client5 },
  ];

const Clients = () => {
    const settings = {
        dots: false,
        infinite: true,
        speed: 500,
        autoplay: true,
    autoplaySpeed: 1000,
        slidesToShow: 5,
        slidesToScroll: 1,
        responsive: [
          {
            breakpoint: 1024,
            settings: {
              slidesToShow: 2,
              slidesToScroll: 1,
              infinite: true,
              dots: true
            }
          },
          {
            breakpoint: 600,
            settings: {
              slidesToShow: 1,
              slidesToScroll: 1
            }
          }
        ]
      };
    
  return (
    <>
    <div className="carousel-container">
      <Slider {...settings}>
        {cardData.map((card, index) => (
          <div key={index} className="Clients_card">
            {/* <h3>{card.title}</h3> */}
            <img src={card.image} height="60px"/>
          </div>
        ))}
      </Slider>
    </div>
    </>
  )
}

export default Clients
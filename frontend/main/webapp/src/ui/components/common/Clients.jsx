import React from 'react';
import Slider from 'react-slick';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { makeStyles } from '@material-ui/core/styles';
import Client1 from '../../../assets/Clients/client1.svg';
import Client2 from '../../../assets/Clients/client2.svg';
import Client3 from '../../../assets/Clients/client3.svg';
import Client4 from '../../../assets/Clients/client4.svg';
import Client5 from '../../../assets/Clients/client5.svg';
import { useMediaQuery } from '@material-ui/core';

const cardData = [
  { title: "Card 1", image: Client1 },
  { title: "Card 2", image: Client2 },
  { title: "Card 3", image: Client3 },
  { title: "Card 4", image: Client4 },
  { title: "Card 5", image: Client5 },
];

const useStyles = makeStyles((theme) => ({
  carouselContainer: {
    width: '95%',
    margin: '0 auto',
  },
  clientsCard: {
    padding: '20px',
    textAlign: 'center',
    border: 'none',
    borderRadius: '4px',
    display: 'flex !important',
    justifyContent: 'center !important',
    alignItems: 'center !important',
  },
}));

const Clients = () => {
  const classes = useStyles();
  const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    autoplay:  true,
    autoplaySpeed: 1000,
    slidesToShow: 5,
    slidesToScroll: 1,
    arrows:false,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 1,
          infinite: true,
          dots: true,
        },
      },
      {
        breakpoint: 600,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
        },
      },
    ],
  };

  return (
    <div className={classes.carouselContainer}>
      <Slider {...settings}>
        {cardData.map((card, index) => (
          <div key={index} className={classes.clientsCard}>
            <img src={card.image} height="60px" alt={card.title} />
          </div>
        ))}
      </Slider>
    </div>
  );
};

export default Clients;

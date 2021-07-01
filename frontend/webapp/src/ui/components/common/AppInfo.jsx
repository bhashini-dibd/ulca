import React from 'react';

import { useHistory } from 'react-router-dom';
import UlcaImage from "../../../assets/"

export default function SimpleBreadcrumbs(props) {

  const handleClick = (event, url) => {
    event.preventDefault();
    history.push(`${process.env.PUBLIC_URL}${url}`)
  }

  const history = useHistory();
  return (
    <div class="intro">
		<div class="introCard">
			<div><img src="UlcaImage" alt="ULCA Information"/></div>
			<div class="close"><a href=""><img src="close@2x.png" alt="close"/></a></div>
		</div>

	</div>
  );
}
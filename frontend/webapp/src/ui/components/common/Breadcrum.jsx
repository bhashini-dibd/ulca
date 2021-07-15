import React from 'react';
import Typography from '@material-ui/core/Typography';
import Breadcrumbs from '@material-ui/core/Breadcrumbs';
import Link from '@material-ui/core/Link';
import { useHistory } from 'react-router-dom';


export default function SimpleBreadcrumbs(props) {

  const handleClick = (event, url) => {
    event.preventDefault();
    history.push(`${process.env.PUBLIC_URL}${url}`)
  }

  const history = useHistory();
  return (
    <Breadcrumbs separator=">" aria-label="breadcrumb">
      {
        props.links.map((link, i) => <Link key={i} color="inherit" href="/" onClick={(e) => handleClick(e, link.url)}>
          <Typography variant="body2">{link.name}</Typography>
        </Link>
        )}
      <Typography variant="body2" color="primary">{props.activeLink}</Typography>
    </Breadcrumbs>
  );
}
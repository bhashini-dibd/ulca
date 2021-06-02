import React from 'react';
import Typography from '@material-ui/core/Typography';
import Breadcrumbs from '@material-ui/core/Breadcrumbs';
import Link from '@material-ui/core/Link';

function handleClick(event) {
  event.preventDefault();
  console.info('You clicked a breadcrumb.');
}

export default function SimpleBreadcrumbs(props) {
  return (
    <Breadcrumbs separator=">" aria-label="breadcrumb">
      {
        props.links.map((link, i) => <Link key={i} color="inherit" href="/" onClick={handleClick}>
          <Typography variant="h6">{link}</Typography>
        </Link>
        )}
      <Typography variant="h6" color="textPrimary">{props.activeLink}</Typography>
    </Breadcrumbs>
  );
}
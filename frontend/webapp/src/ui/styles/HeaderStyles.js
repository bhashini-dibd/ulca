const HeaderStyles = (theme) => ({

  root: {
    background: "#eeeeee",
    height: window.innerHeight
  },
  drawerHeader: {
    display: "flex",
    flexDirection: "row",
  },
  toolbar: {
    minHeight: "56px",
    color: '#000000',
    padding: '0'
  },
  title: {
    color: 'white',
    "@media (max-width:670px)": {
      display: 'none'
    },
  },
  alignItems: {
    display: 'flex',
    alignItems: 'center',
  },
  iconButton: {
    color: 'black',
    borderRadius: 0,
    maxHeight: '100%'
  },
  menu: {
    margin: '0% 2%',
    width: '100%',
    display: 'flex'
  },
  home: {
    marginLeft: '8.4%',
    "@media (max-width:670px)": {
      marginLeft: '2%'
    },
    "@media (max-width:425px)": {
      display: 'none'
    },
  },
  options: {
    marginLeft: '1.875%'
  },
  profile: {
    marginLeft: 'auto',
    marginRight: '-1%'
  },
  menuBtn: {
    fontSize: '1rem',
    color: 'white'
  },
  styledMenu: {
    fontSize: '1rem',
    borderTop: "1px solid #D6D6D6"
  },
  profileName: {
    marginLeft: '0.5rem',
    "@media (max-width:800px)": {
      display: 'none'
    },
  },
  homeBtn: {
    display: 'none',
    "@media (max-width:425px)": {
      display: 'block'
    }
  },

  dataset: {
    "@media (max-width:425px)": {
      display: 'none'
    }
  },
  datasetMobile: {
    display: 'none',
    "@media (max-width:425px)": {
      display: 'block'
    }
  },
  model: {
    "@media (max-width:425px)": {
      display: 'none'
    }
  },
  modelMobile: {
    display: 'none',
    "@media (max-width:425px)": {
      display: 'block'
    }
  }
});
export default HeaderStyles;

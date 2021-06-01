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
    "@media (max-width:660px)": {
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
  headerGrid: {
    margin: '0vh 15vw'
  },
  menu: {
    margin: "0rem 2rem",
    width: '100%',
    display: 'flex'
  },
  home: {
    marginLeft: '9.375rem',
    "@media (max-width:660px)": {
      marginLeft: '0rem'
    },
    "@media (max-width:425px)": {
      display: 'none'
    },
  },
  options: {
    marginLeft: '1.875rem'
  },
  profile: {
    marginLeft: 'auto',
    marginRight: '-1rem'
  },
  menuBtn: {
    fontSize: '1rem',
    color: 'white'
  },
  styledMenu: {
    fontSize: '1rem'
  },
  profileName: {
    marginLeft: '0.5vw',
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

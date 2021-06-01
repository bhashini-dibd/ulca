const HeaderStyles = (theme) => ({

  root: {
    background: "#eeeeee",
    height: window.innerHeight
  },
  appBar: {
    boxShadow: "none",
    borderBottom: "1px solid #EEEEF0",
    padding: "0 3.125rem",
    backgroundColor: '#ffffff',
    padding: '0'
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
    marginTop: '0.5vh'
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
    margin: "0rem 17rem",
    width: '100%',
    display: 'flex'
  },
  home: {
    marginLeft: '9.375rem'
  },
  options: {
    marginLeft: '1.875rem'
  },
  profile: {
    marginLeft: 'auto'
  },
  menuBtn: {
    fontSize:'1rem'
  }
});
export default HeaderStyles;

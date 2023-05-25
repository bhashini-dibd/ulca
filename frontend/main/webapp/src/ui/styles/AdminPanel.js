const AdminPanel = (theme) => ({
  switchGrid: {
    display: "flex",
    alignItems: "center",
  },
  userIdTypo: {
    textTransform: "none",
    fontSize:"15px",
    "@media (max-width:960px)": {
      fontSize:"16px",
    },

  },
  filterContainer: {
    marginBottom: "20px",
  },
});

export default AdminPanel;

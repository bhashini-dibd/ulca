import C from "../../actions/constants";

const initialState = [];

const getUserDetails = () => {
  return [
    {
      userId: "roshan.shah@tarento.com",
      name: "Roshan",
      role: "Contributor",
      org: "IIT Mumbai",
      createdOn: new Date().toLocaleDateString(),
    },
    {
      userId: "alpana.majhi@tarento.com",
      name: "Alpana",
      role: "Contributor",
      org: "IIT Dhanbad",
      createdOn: new Date().toLocaleDateString(),
    },
    {
      userId: "roshan.shah@tarento.com",
      name: "Roshan",
      role: "Contributor",
      org: "IIT Mumbai",
      createdOn: new Date().toLocaleDateString(),
    },
    {
      userId: "alpana.majhi@tarento.com",
      name: "Alpana",
      role: "Contributor",
      org: "IIT Dhanbad",
      createdOn: new Date().toLocaleDateString(),
    },
    {
      userId: "roshan.shah@tarento.com",
      name: "Roshan",
      role: "Contributor",
      org: "IIT Mumbai",
      createdOn: new Date().toLocaleDateString(),
    },
    {
      userId: "alpana.majhi@tarento.com",
      name: "Alpana",
      role: "Contributor",
      org: "IIT Dhanbad",
      createdOn: new Date().toLocaleDateString(),
    },
    {
      userId: "roshan.shah@tarento.com",
      name: "Roshan",
      role: "Contributor",
      org: "IIT Mumbai",
      createdOn: new Date().toLocaleDateString(),
    },
    {
      userId: "alpana.majhi@tarento.com",
      name: "Alpana",
      role: "Contributor",
      org: "IIT Dhanbad",
      createdOn: new Date().toLocaleDateString(),
    },
  ];
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_USER_DETAILS:
      return {
        userDetails: getUserDetails(),
      };
    default:
      return {
        userDetails: initialState,
      };
  }
};

export default reducer;

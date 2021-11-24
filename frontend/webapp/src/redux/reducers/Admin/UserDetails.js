import C from "../../actions/constants";

const initialState = [];

const getUserDetails = () => {
  return [
    {
      userId: "roshan.shah@tarento.com",
      name: "Roshan",
      role: "Contributor",
      org: "IIT Mumbai",
      createdOn: new Date().toLocaleString(),
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

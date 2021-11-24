import C from "../../actions/constants";

const initialState = {
  userDetails: [],
  status: "Started",
};

const convertDate = (date) => {
  const mydate = new Date(date);
  return mydate;
};

const getUserDetails = (payload) => {
  return payload.map((item) => {
    return {
      uId: item.userID,
      userId: item.email,
      name: item.firstName,
      role: item.roles.join(","),
      org: item.orgID,
      createdOn: convertDate(item.activatedTime),
      isActive: item.isActive,
    };
  });
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case C.GET_USER_DETAILS:
      return {
        ...state,
        userDetails: getUserDetails(action.payload),
        status: "Completed",
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;

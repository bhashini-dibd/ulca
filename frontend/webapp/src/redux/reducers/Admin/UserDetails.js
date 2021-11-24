import C from "../../actions/constants";

const initialState = [];

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
        userDetails: getUserDetails(action.payload),
      };
    default:
      return {
        userDetails: initialState,
      };
  }
};

export default reducer;

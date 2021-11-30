import C from "../../actions/constants";

const initialState = {
  userDetails: [],
  filteredUserDetails: [],
  filters: {
    role: [],
    org: [],
  },
  selectedFilter: [],
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
      const data = getUserDetails(action.payload);
      let filters = {
        roles: [],
        org: [],
      };
      data.forEach((elem) => {
        filters.roles.push(elem.role);
        filters.org.push(elem.org);
      });
      const selectedFilter = [];
      filters.roles = [...new Set(filters.roles)];
      filters.org = [...new Set(filters.org)];

      return {
        ...state,
        userDetails: data,
        filteredUserDetails: data,
        status: "Completed",
        filters,
        selectedFilter,
      };
    default:
      return {
        ...state,
      };
  }
};

export default reducer;

import C from "../../actions/constants";

const initialState = {
  userDetails: [],
  filteredUserDetails: [],
  filters: {
    roles: [],
    org: [],
  },
  selectedFilter: {
    roles: [],
    org: [],
  },
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

const searchUserDetails = (searchedValue, userDetails) => {
  let filteredUserDetails = userDetails.filter((user) => {
    return (
      (user["name"] &&
        user["name"].toLowerCase().includes(searchedValue.toLowerCase())) ||
      (user["userId"] &&
        user["userId"].toLowerCase().includes(searchedValue.toLowerCase())) ||
      (user["role"] &&
        user["role"].toLowerCase().includes(searchedValue.toLowerCase())) ||
      (user["org"] &&
        user["org"].toLowerCase().includes(searchedValue.toLowerCase()))
    );
  });
  if (searchedValue !== "") {
    return {
      filteredUserDetails,
    };
  } else {
    return {
      filteredUserDetails: userDetails,
    };
  }
};

const updateSelectedFilter = (type, payload, prevState) => {
  let updatedFilterObj = prevState;
  if (updatedFilterObj[type].includes(payload)) {
    updatedFilterObj[type].splice(updatedFilterObj[type].indexOf(payload), 1);
  } else {
    updatedFilterObj[type].push(payload);
  }
  return updatedFilterObj;
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
      filters.roles = [...new Set(filters.roles)];
      filters.org = [...new Set(filters.org)];

      return {
        ...state,
        userDetails: data,
        filteredUserDetails: data,
        status: "Completed",
        filters,
      };

    case C.CLEAR_ADMIN_FILTER:
      return {
        ...state,
        filteredUserDetails: state.userDetails,
        selectedFilter: { roles: [], org: [] },
      };
    case C.SELECT_ADMIN_FILTER:
      return {
        ...state,
        selectedFilter: updateSelectedFilter(
          action.payload.type,
          action.payload.value,
          state.selectedFilter
        ),
      };

    case C.SEARCH_USER_DETAILS:
      return {
        ...state,
        ...searchUserDetails(action.payload, state.userDetails),
      };

    case C.TOGGLE_USER_STATUS:
      return {
        ...state,
        ...searchUserDetails(
          action.payload.searchState,
          getUserDetails(action.payload.data)
        ),
      };

    default:
      return {
        ...state,
      };
  }
};

export default reducer;

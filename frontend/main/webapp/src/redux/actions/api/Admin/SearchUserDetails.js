import C from "../../constants";

const action = (payload) => {
  return {
    type: C.SEARCH_USER_DETAILS,
    payload,
  };
};

export default action;

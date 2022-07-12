import C from "../../../constants";

const action = (payload) => {
  return {
    type: C.GET_SEARCHED_VALUES,
    payload,
  };
};

export default action;

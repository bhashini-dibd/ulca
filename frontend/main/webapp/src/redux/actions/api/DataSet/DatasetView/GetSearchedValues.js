import C from "../../../constants";

const action = (payload) => {
  return {
    type: C.GET_DATASET_SEARCH_VALUES,
    payload,
  };
};

export default action;

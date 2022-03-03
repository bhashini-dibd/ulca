import C from '../actions/constants';

const reducer = (state = {
  serviceWorkerUpdated: false
}, action) => {
  switch (action.type) {
    case C.UPDATE_SERVICEWORKER: {
      return {
        ...state,
        serviceWorkerUpdated: true
      }
    }
    default:
      return state
  }
}

export default reducer;

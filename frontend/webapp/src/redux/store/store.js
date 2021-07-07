import { createStore, compose, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import { createLogger } from 'redux-logger';
import { composeWithDevTools } from 'redux-devtools-extension';
import { persistCombineReducers } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import reducer from '../reducers';
import * as stateData from '../data/initialstate.json';

const middlewares = [];
const config = {
    key: 'root',
    storage
};

const appReducer = persistCombineReducers(config, reducer);
const rootReducer = (state, action) => {
    if (action.type === 'LOGOUT') {
      state = undefined
    }
  
    return appReducer(state, action)
  }

middlewares.push(thunk);
if (process.env.NODE_ENV === 'development') {
    middlewares.push(createLogger());
}


const store = createStore(
    rootReducer,
    stateData.default,
    (process.env.NODE_ENV === 'development') ? composeWithDevTools(applyMiddleware(...middlewares)) : compose(applyMiddleware(...middlewares))
);

export default store;
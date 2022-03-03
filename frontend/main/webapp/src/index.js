import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './web.routes';
import reportWebVitals from './reportWebVitals';
import { Provider, useDispatch } from "react-redux";
import store from "./redux/store/store"
import { updateServiceworker } from '../src/redux/actions/service-worker-redux';
import registerServiceWorker from './registerServiceWorker';


const OnUpdate = () => {
  const dispatch = useDispatch();
  dispatch(updateServiceworker());
}

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
registerServiceWorker(OnUpdate)

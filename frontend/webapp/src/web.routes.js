import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import history from "./web.history";
import Dashboard from "./Layout";
export default function App() {
  console.log(process.env.PUBLIC_URL);
  return (
    <Router history={history} basename="">
      <div>
        <Switch>
          <Route
            exact
            path={`${process.env.PUBLIC_URL}`}
            component={Dashboard}
          />
        </Switch>
      </div>
    </Router>
  );
}

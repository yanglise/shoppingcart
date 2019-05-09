import React from "react";
import {BrowserRouter as Router, Route} from "react-router-dom";
import Header from "./views/Header";
import ProductController from "./controllers/ProductController";

class APP extends React.Component {
  render() {
    return (
        <div>
          <Router>
            <div>
              <Header/>
              <Route exact path="/" component={ProductController}/>
            </div>
          </Router>
        </div>
    )
  }
}

export default APP;

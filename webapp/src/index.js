import React from "react";
import {render} from "react-dom";
import {browserHistory, Router} from "react-router";
import routes from "./routes";
// load favicon.ico
require('./favicon.ico');

render(
    <Router history={browserHistory} routes={routes}/>,
    document.getElementById('app')
);

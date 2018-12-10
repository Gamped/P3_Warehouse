import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter} from "react-router-dom";
import './style.css';
import {Provider} from "react-redux";
import redux from "./redux/Redux";
import routes from "./routes"

ReactDOM.render(
    <Provider store={redux}>
        <BrowserRouter>
            {routes()}
        </BrowserRouter>
    </Provider>
    , document.getElementById('root')
);

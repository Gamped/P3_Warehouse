import React from 'react';
import ReactDOM from 'react-dom';
import {Router, Route, IndexRoute, hashHistory} from "react-router";
import SignInBox from "./components/Login";
import Header from "./components/Header";
import './index.css';
import './style.css';

// TODO: Fix bug with white box not filling in proper when scrolling from small page

// Send components to HTML
ReactDOM.render(
    <div>
        <Header />
        <SignInBox />
    </div>
    , document.getElementById('root')
);

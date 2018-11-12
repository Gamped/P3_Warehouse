import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter} from "react-router-dom";
import SignInBox from "./components/Login";
import Header from "./components/Header";
import './index.css';
import './style.css';

// TODO: Fix bug with white box not filling in proper when scrolling from small page

// Send components to HTML
ReactDOM.render(
        <div>
            <Header title="P3 Warehouse"/>
            <SignInBox />
        </div>
    , document.getElementById('root')
);

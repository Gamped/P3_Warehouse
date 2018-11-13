import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route} from "react-router-dom";
import SignInBox from "./components/Login";
import Header from "./components/Header";
import AdminIndex from "./components/AdminIndex";
import './index.css';
import './style.css';

// TODO: Fix bug with white box not filling in proper when scrolling from small page

// Send components to HTML
ReactDOM.render(
    <BrowserRouter>
        <div>
            <Route exact path="/" component={Header}/>
            <Route exact path="/" component={SignInBox}/>        
            <Route path= "/AdminIndex" component={AdminIndex}/>
        </div>
    </BrowserRouter>
    , document.getElementById('root')
);

import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route} from "react-router-dom";
import SignInBox from "./components/Login";
import Header from "./components/Header";
import AdminIndex from "./components/AdminIndex";
import AdminOrders from "./components/pages/orders/AdminOrders"
import './index.css';
import './style.css';
import HomeAdmin from './components/pages/HomeAdmin';
import Users from "./components/pages/users/Users";
import Stock from "./components/pages/stock/Stock";
import Profile from "./components/pages/profile/Profile";

// Send components to HTML
ReactDOM.render(
    <BrowserRouter>
        <div>
            <Route exact path="/" component={SignInBox}/>        
            <Route path = "/AdminIndex" component={AdminIndex}/>
            <Route path = "/pages/orders/AdminOrders" component={AdminOrders} />
            <Route path = "/pages/users/Users" component={Users}/>
            <Route path = "/pages/stock/Stock" component={Stock}/>
            <Route path = "/pages/profile/Profile" component={Profile}/>

        </div>
    </BrowserRouter>
    , document.getElementById('root')
);

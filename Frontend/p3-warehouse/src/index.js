import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route} from "react-router-dom";
import SignInBox from "./components/Login";
import Header from "./components/Header";
import AdminIndex from "./components/AdminIndex";
import AdminOrders from "./components/pages/orders/AdminOrders"
import './index.css';
import './style.css';
import AdminOrders from './components/pages/orders/AdminOrders';

// TODO: Fix bug with white box not filling in proper when scrolling from small page

// Send components to HTML
ReactDOM.render(
    <BrowserRouter>
        <div>
<<<<<<< HEAD
<<<<<<< HEAD
        <Route exact path="/" component={Header}/>
        <Route exact path="/" component={SignInBox}/>        
        <Route path= "/AdminIndex" component={AdminIndex}/>
        <Route path = "/components/pages/orders/AdminOrders" component={AdminOrders} />
=======
            <Route exact path="/" component={Header}/>
=======
>>>>>>> cb5be6777005fc707ff408cc9cee7ac0c34daa75
            <Route exact path="/" component={SignInBox}/>        
            <Route path= "/AdminIndex" component={AdminIndex}/>
            <Route path= "/pages/orders/AdminOrders" component={AdminOrders}/>
>>>>>>> ac9353b4adb83cbffe58f8088aa8ace88aceb370
        </div>
    </BrowserRouter>
    , document.getElementById('root')
);

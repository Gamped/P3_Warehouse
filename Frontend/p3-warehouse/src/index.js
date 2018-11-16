import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route} from "react-router-dom";
import './style.css';
import loginMain from "./mainPages/loginMain";
import adminMain from "./mainPages/adminMain";
import userMain from "./mainPages/userMain";
import HomeAdmin from "./components/pages/Admin/HomeAdmin"
import AdminOrders from "./components/pages/Admin/orders/AdminOrders";
import AdminProfile from "./components/pages/Admin/AdminProfile";
import AdminStock from "./components/pages/Admin/AdminStock";
import AdminUsers from "./components/pages/Admin/AdminUsers";
import UserHome from "./components/pages/User/UserHome";
import PublisherClient from "./components/pages/User/PublisherClient";
import UserStock from "./components/pages/User/UserStock";
import UserProfile from "./components/pages/User/UserProfile"
import UserOrder from "./components/pages/User/UserOrder";

ReactDOM.render(
    <BrowserRouter>
        <div>
            <Route exact path="/" component={loginMain}/>

            <Route exact path="/Admin" component={adminMain}/>
            <Route exact path="/Admin" component={HomeAdmin}/>
            <Route exact path="/Admin/*" component={adminMain}/>
            <Route exact path="/Admin/Orders" component={AdminOrders}/>
            <Route exact path="/Admin/Profile" component={AdminProfile}/>
            <Route exact path="/Admin/Stock" component={AdminStock}/>
            <Route exact path="/Admin/Users" component={AdminUsers}/>

            <Route exact path="/User" component={userMain}/>
            <Route exact path="/User/" component={UserHome}/>
            <Route exact path="/User/*" component={userMain}/>
            <Route exact path="/User/Order" component={UserOrder}/>
            <Route exact path="/User/Stock" component={UserStock}/>
            <Route exact path="/User/Profile" component={UserProfile}/>
            <Route exact path="/User/Clients" component={PublisherClient}/>
         </div>
    </BrowserRouter>
    , document.getElementById('root')
);

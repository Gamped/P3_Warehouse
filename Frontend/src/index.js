import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route} from "react-router-dom";
import './style.css';
import loginMain from "./mainPages/loginMain";
import adminMain from "./mainPages/adminMain";
import userMain from "./mainPages/userMain";
import HomeAdmin from "./components/pages/Admin/HomeAdmin"
import AdminOrders from "./components/pages/Admin/AdminOrders";
import AdminProfile from "./components/pages/Admin/AdminProfile/AdminProfile";
import AdminAdd from "./components/pages/Admin/AdminProfile/AdminAdd";
import AdminRemove from "./components/pages/Admin/AdminProfile/AdminRemove";
import AdminProfileEdit from "./components/pages/Admin/AdminProfile/AdminProfileEdit";
import AdminStock from "./components/pages/Admin/AdminStock/AdminStock";
import NewWare from "./components/pages/Admin/AdminStock/NewWare";
import Edit from "./components/pages/Admin/AdminStock/Edit";
import RemoveWare from "./components/pages/Admin/AdminStock/RemoveWare";
import AdminUsers from "./components/pages/Admin/AdminUsers";
import UserHome from "./components/pages/User/UserHome";
import PublisherClient from "./components/pages/User/PublisherClients/PublisherClient";
import PublisherRequestClientChange from "./components/pages/User/PublisherClients/PublisherRequestClientChange";
import UserStock from "./components/pages/User/UserStock/UserStock";
import UserProfile from "./components/pages/User/UserProfile/UserProfile"
import UserProfileEdit from "./components/pages/User/UserProfile/UserProfileEdit"
import UserOrderHistory from "./components/pages/User/UserProfile/UserOrderHistory"
import UserOrder from "./components/pages/User/UserOrder/UserOrder";
import UserOrderCart from "./components/pages/User/UserOrder/UserOrderCart";
import UserCartConfirm from "./components/pages/User/UserOrder/UserCartConfirm";
import {Provider} from "react-redux";
import redux from "./redux/Redux";
import NewOrder from './components/pages/Admin/AdminOrders/NewOrder';
import EditOrder from './components/pages/Admin/AdminOrders/EditOrder';

ReactDOM.render(
    <Provider store={redux}>
        <BrowserRouter>
            <div>
                <Route exact path="/" component={loginMain}/>

                <Route exact path="/Admin" component={adminMain}/>
                <Route exact path="/Admin" component={HomeAdmin}/>
                <Route exact path="/Admin/*" component={adminMain}/>
                <Route exact path="/Admin/Orders" component={AdminOrders}/>
                <Route exact path="/Admin/Orders/New" component={NewOrder}/>
                <Route exact path="/Admin/Orders/Edit" component={EditOrder}/>
                <Route exact path="/Admin/Profile" component={AdminProfile}/>
                <Route exact path="/Admin/Profile/AddEmployee" component={AdminAdd}/>
                <Route exact path="/Admin/Profile/RemoveEmployee" component={AdminRemove}/>
                <Route exact path="/Admin/Profile/Edit" component={AdminProfileEdit}/>
                <Route exact path="/Admin/Stock" component={AdminStock}/>
                <Route exact path="/Admin/Stock/New" component={NewWare}/>
                <Route exact path="/Admin/Stock/Edit/:id" component={Edit}/>
                <Route exact path="/Admin/Stock/Remove" component={RemoveWare}/>
                <Route exact path="/Admin/Users" component={AdminUsers}/>

                <Route exact path="/User" component={userMain}/>
                <Route exact path="/User/" component={UserHome}/>
                <Route exact path="/User/*" component={userMain}/>
                <Route exact path="/User/Order" component={UserOrder}/>
                <Route exact path="/User/Order/Cart" component={UserOrderCart}/>
                <Route exact path="/User/Order/Cart/Confirm" component={UserCartConfirm}/>
                <Route exact path="/User/Stock" component={UserStock}/>
                <Route exact path="/User/Profile" component={UserProfile}/>
                <Route exact path="/User/Profile/Edit" component={UserProfileEdit}/>
                <Route exact path="/User/Profile/OrderHistory" component={UserOrderHistory}/>
                <Route exact path="/User/Clients" component={PublisherClient}/>
                <Route exact path="/User/Clients/Request" component={PublisherRequestClientChange}/>
            </div>
        </BrowserRouter>
    </Provider>
    , document.getElementById('root')
);

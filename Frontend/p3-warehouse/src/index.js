import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route} from "react-router-dom";
import SignInBox from "./components/Login";
import Header from "./components/Header";
import Products from "./components/product/Products";
import CreateProduct from "./components/product/CreateProduct";
import ShowProducts from "./components/product/ShowProducts";
import EditProduct from "./components/product/EditProduct";
import AdminIndex from "./components/AdminIndex";
import AdminOrders from "./components/pages/orders/AdminOrders"
import './index.css';
import './style.css';
import HomeAdmin from './components/pages/HomeAdmin';
import Users from "./components/pages/users/Users";
import Stock from "./components/pages/stock/Stock";
import Profile from "./components/pages/profile/Profile";

ReactDOM.render(
    <BrowserRouter>
        <div>
  
            <Route exact path="/" component={Header}/>
            <Route exact path="/" component={SignInBox}/>
           
            <Route exact path="/products" component={Products}/>
            <Route exact path= "/products/create" component={CreateProduct}/>
            <Route exact path= "/products/show/:id" component={ShowProducts}/>
            <Route exact path= "/products/edit/:id" component={EditProduct}/>
  
            <Route path= "/AdminIndex" component={AdminIndex}/>
  
  </div>
    </BrowserRouter>
    , document.getElementById('root')
);

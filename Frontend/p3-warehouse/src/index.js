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
import './index.css';
import './style.css';

// TODO: Fix bug with white box not filling in proper when scrolling from small page

//<Route exact path="/" component={Header}/>
//<Route exact path="/" component={SignInBox}/>

// Send components to HTML
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

import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter} from "react-router-dom";
import Navbar from "./Navbar"
import "./adminIndex.css"

// The Menu component
export default class Menu extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            homeText: "Home",
            ordersText: "Orders",
            clientsText: "Clients",
            stockText: "Stock",
        };
    }

    render(){
        let home = this.state.homeText;
        let orders = this.state.ordersText;
        let clients = this.state.clientsText;
        let stock = this.state.stockText;

        return(
            <div class="menuStyle">
                <button>{home}</button>
                <br/>
                <button>{orders}</button>
                <br/>
                <button>{clients}</button>
                <br/>
                <button>{stock}</button>
            </div>
        );
    }
}

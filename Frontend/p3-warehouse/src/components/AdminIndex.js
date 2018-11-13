import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter} from "react-router-dom";
import Navbar from "./MenuComponents/Navbar"
import "./adminIndex.css"
import Menu from "./MenuComponents/Menu"


export default class Admin extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            menuButtons : [
                {name: "Home",location: "./AdminIndex", id:"1"},
                {name: "Orders",location:"./components/pages/orders/AdminOrders", id:"2"},
                {name: "Clients",location:"./Clients",id:"3"}
            ]
        };
    }

    render(){
        console.log(this.props.location.pathname)
        return(
            <div className="menuStyle">
                <Menu buttons={this.state.menuButtons} current={"."+this.props.location.pathname}/>
            </div>
        );
    }
}

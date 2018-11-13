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
                {name: "Home",location: "../Home", id:"1"},
                {name: "Orders",location:"../Orders", id:"2"}
            ]
        };
    }
    
    render(){
        return(
            <div class="menuStyle">
                <Menu buttons={this.state.menuButtons}/>
            </div>
        );
    }
}

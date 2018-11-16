import React from 'react';
import "./adminIndex.css";
import Menu from "./MenuComponents/Menu";
import LandingPage from "./MenuComponents/LandingPage/LandingPage";
import Header from "./Header";


export default class Admin extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            menuButtons : [
                {name: "Home",location: "./AdminIndex", id:"1"},
                {name: "Orders",location:"./pages/orders/AdminOrders", id:"2"},
                {name: "Users",location:"./pages/users/Users",id:"3"},
                {name: "Stock",location:"./pages/stock/Stock",id:"4"},
                {name: "Profile",location:"./pages/profile/Profile",id:"5"}
            ],
            landingPageButtons:[
                {name:"Orders",location:"./pages/orders/AdminOrders",id:"1"},
                {name:"Users",location:"./pages/users/Users",id:"2"},
                {name:"Stock",location:"./pages/stock/Stock",id:"3"}
            ]
        };
    }

    render(){
        return(
            <div className="landingPageWrapper">
                <Header title="Warehouse - Employee landingpage"/>
                <div className="menuStyle">
                    <Menu buttons={this.state.menuButtons} current={"./AdminIndex"}/>
                </div>
                <div className="landingPageStyle">
                    <LandingPage buttons={this.state.landingPageButtons} name="Employee"/>
                </div>
            </div>

        );
    }
}

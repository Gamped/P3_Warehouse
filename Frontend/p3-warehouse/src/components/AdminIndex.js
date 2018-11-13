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
<<<<<<< HEAD
                {name: "Orders",location:"./components/pages/orders/AdminOrders", id:"2"},
                {name: "Clients",location:"./Clients",id:"3"}
=======
                {name: "Orders",location:"./pages/orders/AdminOrders", id:"2"},
                {name: "Users",location:"./Clients",id:"3"}
            ],
            landingPageButtons:[
                {name:"Orders",location:"./pages/orders/AdminOrders",id:"1"},
                {name:"Users",location:"./Clients",id:"2"},
                {name:"Stock",location:"./Stock",id:"3"}
>>>>>>> ac9353b4adb83cbffe58f8088aa8ace88aceb370
            ]
        };
    }

    render(){
        return(
            <div className="landingPageWrapper">
                <Header title="Warehouse - Employee landingpage"/>
                <div className="menuStyle">
                    <Menu buttons={this.state.menuButtons} current={"."+this.props.location.pathname}/>
                </div>
                <div className="landingPageStyle">
                    <LandingPage buttons={this.state.landingPageButtons} name="Employee"/>
                </div>
            </div>

        );
    }
}

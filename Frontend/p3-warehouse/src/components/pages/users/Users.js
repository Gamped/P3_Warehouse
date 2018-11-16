import React from "react";
import Menu from "../../MenuComponents/Menu";
import LandingPage from "../../MenuComponents/LandingPage/LandingPage";
import Header from "../../Header";
import BoxWithLongContent from "../../MenuComponents/ScrollTab";
import "./Users.css";


export default class Users extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            menuButtons : [
                {name: "Home",location: "../../AdminIndex", id:"1"},
                {name: "Orders",location:"../../pages/orders/AdminOrders", id:"2"},
                {name: "Users",location:"./Users",id:"3"},
                {name: "Stock",location:"../../pages/stock/Stock",id:"4"},
                {name: "Profile",location:"../../pages/profile/Profile",id:"5"}
            ],
            users: [
                {name: "Black Betty INC",id: "1"},
                {name:"Toys r us" , id:"2"},
                {name:"Fightclub" , id:"3"},
                {name:"I.C.U.P." , id:"4"},
                {name:"Aalborg Zoo" , id:"5"},
                {name:"Ådal og Søn" , id:"6"},
            ]
        };
    }
    
    render(){
        return(
            <div className="userPageWrapper">
                <Header title="Warehouse - Users"/>
                <div className="menuStyle">
                    <Menu buttons={this.state.menuButtons} current={"./Users"}/>
                </div>
                <div className="userPageStyle">
                    <BoxWithLongContent radios={this.state.users}/>
                </div>
            </div>

        );
    }
}

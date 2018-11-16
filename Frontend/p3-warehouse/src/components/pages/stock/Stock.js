import React from "react";
import Menu from "../../MenuComponents/Menu";
import LandingPage from "../../MenuComponents/LandingPage/LandingPage";
import Header from "../../Header";


export default class Stock extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            menuButtons : [
                {name: "Home",location: "../../../AdminIndex", id:"1"},
                {name: "Orders",location:"../../../pages/orders/AdminOrders", id:"2"},
                {name: "Users",location:"../../../pages/users/Users",id:"3"},
                {name: "Stock",location:"./Stock",id:"4"},
                {name: "Profile",location:"../../../pages/profile/Profile",id:"5"}
            ]
        };
    }

    render(){
        return(
            <div className="stockPageWrapper">
                <Header title="Warehouse - Stock"/>
                <div className="menuStyle">
                    <Menu 
                        buttons={this.state.menuButtons} 
                        current={"./Stock"}
                    />
                </div>
                <div className="landingPageStyle">
             
                </div>
            </div>

        );
    }
}
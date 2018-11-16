import React from 'react';
import "../Pages.css";

const AdminUsers = (props) => {


    return(
        <div className="PageStyle">
            <customText_b>You are on Admin users page</customText_b>
        </div>

    );
}
 
export default AdminUsers;




/* REMOVED, but left here if needed once this page will be built

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
                <div className="userPageStyle">
                    <BoxWithLongContent radios={this.state.users}/>
                </div>
            </div>

        );
    }
}*/
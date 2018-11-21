import React from 'react';
import LandingPage from "../../MenuComponents/LandingPage/LandingPage";
import "../Pages.css";

const UserHome = (props) => {
    var landingPageButtons = [
        {name:"Order",location:"./User/Order",id:"1"},
        {name:"Stock",location:"./User/Stock",id:"2"},
        {name:"Profile",location:"./User/Profile",id:"3"},
        {name:"Clients",location:"./User/Clients",id:"4"},
    ]

    return ( 
        <div className="PageStyle">
            <LandingPage buttons={landingPageButtons} name="User"/>
        </div>
    )
}
 
export default UserHome;
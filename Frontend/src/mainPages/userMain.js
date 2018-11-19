import React from "react";
import Base from "../components/pages/Base";
import "./mainPages.css";

const userMain = (props)=>{
    var menuButtons = [
        {name: "Home",location: "/User", id:"1"},
        {name: "Order wares",location:"/User/Order", id:"2"},
        {name: "Stock",location:"/User/Stock",id:"3"},
        {name: "Profile",location:"/User/Profile",id:"4"},
        {name: "Clients",location:"/User/Clients",id:"5"}
    ]

    return(
        <div>
            <Base title="User menu" buttons={menuButtons}/>
            <div className="ErrorBox">
                <customText_b className="ErrorText align-middle">ERROR: Page not found!</customText_b>
            </div>
        </div>
    )
}

export default userMain;
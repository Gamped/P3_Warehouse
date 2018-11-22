import React from "react";
import Base from "../components/pages/Base";
import "./mainPages.css";

const userMain = (props) => {
    var menuButtons = [
        {name: "Home",location: "/User", id:"1"},
        {name: "Order",location:"/User/Order", id:"2"},
        {name: "Stock",location:"/User/Stock",id:"3"},
        {name: "Profile",location:"/User/Profile",id:"4"},
        {name: "Clients",location:"/User/Clients",id:"5"}
    ]

    return(
        <div>
            <Base title="User menu" buttons={menuButtons}/>
            <div className="ErrorBox">
                <h1 className=" customText_b ErrorText align-middle">ERROR: Page not found!</h1>
            </div>
        </div>
    )
}

export default userMain;
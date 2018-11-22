import React from "react";
import Base from "../components/pages/Base";
import "./mainPages.css";
import { ObjectID } from 'bson';
import axios from 'axios';

const adminMain = (props) => {
    var menuButtons = [
        {name: "Home",location: "/Admin", id:"1"},
        {name: "Orders",location:"/Admin/Orders", id:"2"},
        {name: "Users",location:"/Admin/Users",id:"3"},
        {name: "Stock",location:"/Admin/Stock",id:"4"},
        {name: "Profile",location:"/Admin/Profile",id:"5"}
    ]

    return(
        <div>
            <Base title="Admin menu" buttons={menuButtons}/>>
            <div className="ErrorBox">
                <customText_b className="ErrorText align-middle">ERROR: Page not found!</customText_b>
            </div>
        </div>
    )
}

export default adminMain;

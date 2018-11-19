import React from "react";
import {Link} from "react-router-dom";
import Header from "../components/Header/Header";
import SignInBox from "../components/MenuComponents/Login/Login";

const loginMain = (props)=>{
    const {name,buttons}=props;

    return(
        <div>
            <Header title="Login to warehouse"/>
            <SignInBox/>
        </div>
    )
}

export default loginMain;
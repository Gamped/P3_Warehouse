import React from "react";
import Header from "../components/Header/Header";
import SignInBox from "../components/MenuComponents/Login/Login";

const loginMain = (props)=>{

    return(
        <div>
            <Header title="Login to warehouse"/>
            <SignInBox/>
        </div>
    )
}

export default loginMain;
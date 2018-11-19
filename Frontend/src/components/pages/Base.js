import React from "react";
import {Link} from "react-router-dom";
import Header from "../Header/Header"
import Menu from "../MenuComponents/Menu/Menu"

const base = (props)=>{
    const {title,buttons}=props;

    return(
        <div >
            <Header title={props.title}/>
            <Menu buttons={props.buttons}/>
        </div>
    )
}

export default base;
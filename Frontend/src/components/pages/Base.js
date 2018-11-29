import React from "react";
import Header from "../Header/Header"
import ButtonList from "../MenuComponents/ButtonList/ButtonList"

const base = (props)=>{

    return(
        <div >
            <Header title={props.title}/>
            <div className="menuStyle">
                <ButtonList buttons={props.buttons} color="primary" link={true}/>
                <img 
                 src={require('../../resources/4n_logo_mini.jpg')} 
                 className="menuLogo" 
                 alt="The logo of 4N"/>
            </div>
        </div>
    )
}

export default base;
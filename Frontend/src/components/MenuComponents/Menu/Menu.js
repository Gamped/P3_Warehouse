import React from 'react';
import "./Menu.css";
import {Link} from "react-router-dom";


// The header component
const Menu = (props) => {
    const {buttons, current}=props;
    const mappedButtonList = buttons.map(button =>{
        return (
            (current!==button.location)?
            <div className="Link" key={button.id}>
                <Link to={button.location} className=" button_1st btn btn-block" role="button">{button.name}</Link>
                <br/>
            </div>
            :
            <div className="Link" key={button.id}>
                <Link to={button.location} className="button_2nd btn btn-block" role="button">{button.name}</Link>
                <br/>
            </div>
        )
    })
    return(
        <div className="menuStyle">
           {mappedButtonList}
           <img 
           src={require('../../../resources/4n_logo_mini.jpg')} 
           className="menuLogo" 
           alt="The logo of 4N"/>
        </div>
    )
}

export default Menu
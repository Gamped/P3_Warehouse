import React from 'react';
import "../adminIndex.css";
import {Link} from "react-router-dom";


// The header component
const Menu = (props) => {
    const {buttons, current}=props;
    const mappedButtonList = buttons.map(button =>{
        return (
            (current!==button.location)?
            <div className="Link" key={button.id}>
                <Link to={button.location} className="btn btn-primary btn-block" role="button">{button.name}</Link>
                <br/>
            </div>
            :
            <div className="Link" key={button.id}>
                <Link to={button.location} className="btn btn-secondary btn-block" role="button">{button.name}</Link>
                <br/>
            </div>
        )
    })
    return(
        <div className="menuStyle">
           {mappedButtonList}
        </div>
    )
}

export default Menu
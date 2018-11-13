import React from 'react';
import "../adminIndex.css";
import {Link} from "react-router-dom";


// The header component
const Menu = (props) => {
    const {buttons}=props;
    const mappedButtonList = buttons.map(button =>{
        return (
            (props.current!==button.location)?
            <div className="Link" key={button.id}>
                <Link to={button.location} className="badge badge-primary">{button.name}</Link>
                <br/>
            </div>
            :
            <div className="Link" key={button.id}>
                <Link to={button.location} className="badge badge-secondary">{button.name}</Link>
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
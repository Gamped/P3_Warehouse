import React from 'react';
import "./ButtonList.css";
import {Link} from "react-router-dom";


// The header component
const Buttonlist = (props) => {
    const {buttons, current, link, color,action}=props;
    const mappedButtonList = buttons.map(button =>{
        return (
            (link)?
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
            :
            <div className="my-2" key={button.id}>
                <button type="button" className={"btn btn-block btn-"+ color} onClick={null}>{button.name}</button>
            </div>
        )
    })
    return(
        <div>
           {mappedButtonList}
        </div>
    )
}

export default Buttonlist
import React from 'react';
import {Link} from "react-router-dom";

// A stateless component. 
const Buttonlist = (props) => {

    const {buttons, current, link, color}=props;

    //To the array of buttons we map a div dependt on various variables
    const mappedButtonList = buttons.map(button =>{

        return (
            (link)?
                (current!==button.location)?
                <div className="Link" key={button.id}>
                    <Link to={button.location} className="std_BTN leftMenuButton btn-secondary btn btn-block" role="button">{button.name}</Link>
                    <br/>
                </div>
                :
                <div className="Link" key={button.id}>
                    <Link to={button.location} className="dark_BTN leftMenuButton btn-darker btn btn-block" role="button">{button.name}</Link>
                    <br/>
                </div>
            :
            <div className="my-2" key={button.id}>
                <button type="button" className={"std_BTN leftMenuButton btn btn-block btn-"+ color} onClick={()=> props.action(button.id)}>{button.name}</button>
            </div>
        )
    })

    //returns the mapped buttonlist wrapped in a div.
    return(
        <div>
           {mappedButtonList}
        </div>
    )
}

export default Buttonlist
import React from "react";
import {Link} from "react-router-dom";
import "./LandingPage.css";

const LandingPage = (props)=>{
    const {name,buttons}=props;
    var i = 0;
    const buttonList = buttons.map(button =>{
        i++;
        return (
           <li className="page-item" key={i}> 
            <Link 
                to={button.location} 
                key={button.id} 
                className="button_1st LandingPageButtons btn btn-primary btn-lg border border-secondary rounded" 
                role="button">{button.name}
            </Link>
           </li>
        )
    })
    return(
        <div>
            <div className="fixed-top LandingText">
                <h1 className="customText_b">Welcome {name}!</h1>
                <br/>
                <ul className="align-middle">
                    {buttonList}
                </ul>
            </div>
        </div>
    )
}

export default LandingPage;
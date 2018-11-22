import React from "react";
import {Link} from "react-router-dom";
import "./LandingPage.css";

const LandingPage = (props)=>{
    const {name,buttons}=props;
    const buttonList = buttons.map(button =>{
        return (
           <li class="page-item"> 
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
                <customText_b>Welcome {name}!</customText_b>
                <br/>
                <ul class="align-middle">
                    {buttonList}
                </ul>
            </div>
        </div>
    )
}

export default LandingPage;
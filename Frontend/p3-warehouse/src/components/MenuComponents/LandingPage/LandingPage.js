import React from "react";
import {Link} from "react-router-dom";
import "./LandingPage.css";

const LandingPage = (props)=>{
    const {name,buttons}=props;
    const buttonList = buttons.map(button =>{
        return (
           <li class="page-item"> 
           <Link to={button.location} key={button.id} className="LandingPageButtons btn btn-primary btn-lg border border-secondary rounded" role="button">{button.name}</Link>
           </li>
        )
    })
    return(
        <div className="LandingPage border border-primary rounded">
            <div className="fixed-top LandingText">
                <h1>Welcome to the landing page. <br/> You are currently logged in as {name}</h1>
                <br/>
                <ul class="pagination justify-content-center">
                    {buttonList}
                </ul>
            </div>
        </div>
    )
}

export default LandingPage;
import React from "react";
import {Link} from "react-router-dom";
import "./LandingPage.css";

const LandingPage = (props)=>{
    const {name,buttons}=props;
    var i = 0;
    const buttonList = buttons.map(button =>{
        i++;
        return (
            <div className="col">
                <Link 
                    to={button.location} 
                    key={button.id} 
                    className="button_1st LandingPageButtons btn btn-primary btn-lg block border border-secondary rounded" 
                    role="button">
                        {button.name}
                </Link>
            </div>
        )
    })
    return(
        <div className="container">
            <div className="fixed-top LandingText">
                <h1 className="customText_b">Welcome {name}!</h1>
                <br/>
                <div className="row mt-6">
                    {buttonList}
                </div>
            </div>
        </div>
    )
}

export default LandingPage;
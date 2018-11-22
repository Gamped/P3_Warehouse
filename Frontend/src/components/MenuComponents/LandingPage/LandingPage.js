import React from "react";
import {Link} from "react-router-dom";
import "./LandingPage.css";

const LandingPage = (props)=>{
    const {name,buttons}=props;
    var i = 0;
    const buttonList = buttons.map(button =>{
        i++;
        return (
<<<<<<< HEAD
=======
           <li className="page-item" key={i}> 
>>>>>>> Fixed all browser console errors for UI
            <Link 
                to={button.location} 
                key={button.id} 
                className="button_1st LandingPageButtons btn btn-primary btn-lg border border-secondary rounded" 
<<<<<<< HEAD
                role="button">
                    {button.name}
            </Link>
=======
                role="button">{button.name}
            </Link>
           </li>
>>>>>>> Fixed all browser console errors for UI
        )
    })
    return(
        <div>
            <div className="fixed-top LandingText">
                <h1 className="customText_b">Welcome {name}!</h1>
                <br/>
<<<<<<< HEAD
                <div className="row">
                    <div className="col-sm">
=======
                <ul className="align-middle">
>>>>>>> Fixed all browser console errors for UI
                    {buttonList}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LandingPage;
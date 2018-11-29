import React from "react";
import Header from "../components/Header/Header";
import "./mainPages.css";

const adminMain = (props) => {

    return(
        <div>
            <Header title="Admin menu"/>
            <div className="ErrorBox">
                <h1 className="ErrorText align-middle customText_b">ERROR: Page not found!</h1>
            </div>
        </div>
    )
}

export default adminMain;

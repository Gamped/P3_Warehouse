import React from "react";
import Header from "../components/Header/Header";
import "./mainPages.css";

const userMain = (props) => {
    return(
        <div>
            <Header title="User menu" />
            <div className="ErrorBox">
                <h1 className=" customText_b ErrorText align-middle">ERROR: Page not found!</h1>
            </div>
        </div>
    )
}

export default userMain;

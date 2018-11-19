import React from "react";

import "./mainPages.css";

const UnknownPage = (props)=>{

    return(
        <div>
            <div className="ErrorPage">
                <customText_b className="ErrorText align-middle">ERROR: Page not found!</customText_b>
            </div>
        </div>
    )
}

export default UnknownPage;
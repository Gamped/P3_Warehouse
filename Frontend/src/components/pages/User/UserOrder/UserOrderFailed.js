import React from 'react';
import {Link} from "react-router-dom";
import "../../Pages.css"

class UserOrderFailed extends React.Component {
    render() {
        return(
            <div className="PageStyle">
            <h1 className="title customText_b">Sorry, but the order was NOT sent to 4N :(</h1>
                <div className="orderInfoBox">
                    <Link to="/User/Order" className="green_BTN btn-block btn my-2">TRY AGAIN</Link>
                </div>
            </div>
        );
    }
}

export default UserOrderFailed;
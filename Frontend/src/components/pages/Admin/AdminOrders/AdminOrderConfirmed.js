import React from 'react';
import {Link} from "react-router-dom";
import "../../Pages.css"

class AdminOrderConfirmed extends React.Component {
    render() {
        return(
            <div className="PageStyle">
            <h1 className="title customText_b">Order has been sent to 4N!</h1>
                <div className="orderInfoBox">
                    <Link to="/Home" className="std_BTN btn-block btn my-2">To home</Link>
                    <Link to="/Admin/Orders" className="std_BTN btn-block btn my-2">To orders</Link>
                </div>
            </div>
        );
    }
}

export default AdminOrderConfirmed;
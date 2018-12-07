import React from 'react';
import "../../Pages.css";
import { Link } from "react-router-dom"

const PublisherRequestClientChange = (props) => {
    return(
        <div className="PageStyle rounded">
            <div className="col-md-6 offset-md-3">
                <h1 className="customText_b">Request change to client:</h1>
                <h1 className="pubInfoText customText_b">Please send an email to ???@???.dk to request change</h1>

                <Link to="/User/Clients" className="btn btn-info btn-block btn-lg">Back to clients</Link>
            </div>
        </div>
    );
}
 
export default PublisherRequestClientChange;
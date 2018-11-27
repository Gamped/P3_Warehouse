import React from 'react';
import "../../Pages.css";

const PublisherRequestClientChange = (props) => {
    return(
        <div className="PageStyle">
            <h1 className="customText_b">Request change to client:</h1>
            <h1 className="pubInfoText customText_b">Please send an email to ???@???.dk to request change</h1>
        
            <form action="/User/Clients" className="pubBackForm">
                <button  className="pubButton_f btn" >Back to clients</button>
            </form>
        </div>
    );
}
 
export default PublisherRequestClientChange;
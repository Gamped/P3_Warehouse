import React from 'react';
import "../../Pages.css";

const UserProfile = (props) => {
    var userName = "No username set";
    var name = "No name set";
    var email = "No@email.set";
    var phoneNumber = "No phone number set";
    var address = "No adress set";
    var cvr = "No CVR set";

    /*
    * FUNCTIONALITY TO RETRIEVE INFO FROM DB
    */

    return(
        <div className="PageStyle rounded">
            <h1 className="title customText_b_big">Profile information</h1>
            <div className="informationBox">
                <h1 className="infoText customText_b">User name: {userName}</h1>
                <h1 className="infoText customText_b">Name: {name}</h1>
                <h1 className="infoText customText_b">Email: {email}</h1>
                <h1 className="infoText customText_b">Phone number: {phoneNumber}</h1>
                <h1 className="infoText customText_b">Address: {address}</h1>
                <h1 className="infoText customText_b">CVR: {cvr}</h1>

                <form action="/User/Profile/Edit">
                    <button className="infoButton" >Edit</button>
                </form>
                <form action="/User/Profile/OrderHistory">
                    <button className="infoButton" >Order histroy</button>
                </form>
            </div>
        </div>
    );
}
 
export default UserProfile;
import React from 'react';
import "../../Pages.css";

const UserProfile = (props) => {
    var userID = props.ID;
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
        <div className="PageStyle">
            <customText_b_big className="title">Profile information</customText_b_big>
            <div className="informationBox">
                <customText_b className="infoText">User name: {userName}</customText_b>
                <br/>
                <customText_b className="infoText">Name: {name}</customText_b>
                <br/>
                <customText_b className="infoText">Email: {email}</customText_b>
                <br/>
                <customText_b className="infoText">Phone number: {phoneNumber}</customText_b>
                <br/>
                <customText_b className="infoText">Address: {address}</customText_b>
                <br/>
                <customText_b className="infoText">CVR: {cvr}</customText_b>
                <br/>

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
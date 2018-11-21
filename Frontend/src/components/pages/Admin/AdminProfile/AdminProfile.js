import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css"

const AdminProfile = (props) => {
    var userID = props.ID;
    var userName = "No username set";
    var name = "No name set";
    var email = "No@email.set";
    var phoneNumber = "No phone number set";
    var address = "No adress set";

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

                <form action="/Admin/Profile/Edit">
                    <button className="infoButton" >Edit</button>
                </form>
            </div>
        </div>
    );
}
 
export default AdminProfile;
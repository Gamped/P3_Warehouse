import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css"

const AdminProfile = (props) => {
    var userName = "No username set";
    var name = "No name set";
    var email = "No@email.set";
    var phoneNumber = "No phone number set";
    var address = "No adress set";

    /*
    * FUNCTIONALITY TO RETRIEVE INFO FROM DB
    */

    return(
        <div className="PageStyle rounded">
            <h1 className="title customText_b_big">Profile information</h1>
            <div className="informationBox">
                <h1 className="lead"><strong>User name: {userName}</strong></h1>
                <h1 className="lead"><strong>Name: {name}</strong></h1>
                <h1 className="lead"><strong>Email: {email}</strong></h1>
                <h1 className="lead"><strong>Phone number: {phoneNumber}</strong></h1>
                <h1 className="lead"><strong>Address: {address}</strong></h1>

                <form action="/Admin/Profile/Edit">
                    <button className="btn-block btn-dark btn my-3" >Edit</button>
                </form>
                <form action="/Admin/Profile/AddEmployee">
                    <button className="btn-block btn-dark btn my-3" >Add employee</button>
                </form>
                <form action="/Admin/Profile/RemoveEmployee">
                    <button className="btn-block btn-dark btn my-3" >Remove employee</button>
                </form>
            </div>
        </div>
    );
}
 
export default AdminProfile;
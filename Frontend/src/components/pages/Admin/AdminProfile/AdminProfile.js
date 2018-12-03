import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";
import axios from "axios";

export default class AdminProfile extends React.Component {
   
    componentDidMount(){
        //Todo: Sørg for at den henter det rigtige sted fra
        axios.get("http://localhost:8080/employee")
    }

    render(){
        let userName = "No username set";
        let name = "No name set";
        let email = "No@email.set";
        let phoneNumber = "No phone number set";
        let address = "No adress set";
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
}

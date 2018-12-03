import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";

export default class AdminProfileEdit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            userName: "",
            name: "",
            email: "",
            phoneNumber: "",
            address: "",
            passwordCurrent: "",
            passwordNew: "",
        };
    }

    /*
    * SOME FUNCTION TO RETRIEVE & SEND INFO FROM DB
    */

    handleUName = (event) => {
        this.setState({
            userName: event.target.value,
        });
    }

    handleName = (event) => {
        this.setState({
            name: event.target.value,
        });
    }

    handleEmail = (event) => {
        this.setState({
            email: event.target.value,
        });
    }

    handlePhoneNumber = (event) => {
        this.setState({
            phoneNumber: event.target.value,
        });
    }

    handleAddress = (event) => {
        this.setState({
            address: event.target.value,
        });
    }

    handleCurPass = (event) => {
        this.setState({
            passwordCurrent: event.target.value,
        });
    }

    handleNewPass = (event) => {
        this.setState({
            passwordNew: event.target.value,
        });
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <h1 className="title customText_b_big">Edit profile:</h1>
                    <form>
                        <input 
                            type="text" 
                            className="newForm" 
                            onChange={this.handleUName}
                            placeholder="User name"/>
                        <input 
                            type="text" 
                            className="newForm" 
                            onChange={this.handleName}
                            placeholder="Name"/>
                        <input 
                            type="email" 
                            className="newForm" 
                            onChange={this.handleEmail}
                            placeholder="Email"/>
                        <input 
                            type="tel" 
                            className="newForm" 
                            onChange={this.handlePhoneNumber}
                            placeholder="Phone Number"/>
                        <input 
                            type="text" 
                            className="newForm" 
                            onChange={this.handleAddress}
                            placeholder="Address"/>
                        <input 
                            type="password" 
                            className="newForm" 
                            onChange={this.handleCurPass}
                            placeholder="Current password"/>
                        <input 
                            type="password" 
                            className="newForm" 
                            onChange={this.handleNewPass}
                            placeholder="New password"/>
                    </form>
                    <form action="/Admin/Profile" className="newForm stockForm">
                        <button className="btn-block btn-lg my-2 btn-secondary btn">Back</button>
                    </form>
                    <form action="/Admin/Profile" className="newForm stockForm">
                        <button className="btn-block btn-lg my-2 btn-secondary btn">Edit profile</button>
                    </form>
            </div>
        );
    }
}
import React from 'react';
import "../../Pages.css";

export default class UserProfileEdit extends React.Component {
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
            cvr: "",
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

    handleCVR = (event) => {
        this.setState({
            cvr: event.target.value,
        });
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <h1 className="text-center">Edit profile:</h1>
                <form>
                    <input 
                        type="text" 
                        className="my-2 form-control" 
                        onChange={this.handleUName}
                        placeholder="User name"/>
                    <input 
                        type="text" 
                        className="my-2 form-control" 
                        onChange={this.handleName}
                        placeholder="Name"/>
                    <input 
                        type="email" 
                        className="my-2 form-control" 
                        onChange={this.handleEmail}
                        placeholder="Email"/>
                    <input 
                        type="tel" 
                        className="my-2 form-control" 
                        onChange={this.handlePhoneNumber}
                        placeholder="Phone Number"/>
                    <input 
                        type="text" 
                        className="my-2 form-control" 
                        onChange={this.handleAddress}
                        placeholder="Address"/>
                    <input 
                        type="text" 
                        className="my-2 form-control" 
                        onChange={this.handleCVR}
                        placeholder="CVR"/>
                    <input 
                        type="password" 
                        className="my-2 form-control" 
                        onChange={this.handleCurPass}
                        placeholder="Current password"/>
                    <input 
                        type="password" 
                        className="my-2 form-control" 
                        onChange={this.handleNewPass}
                        placeholder="New password"/>
                </form>
                <form action="/User/Profile" className="newForm stockForm">
                    <button className="btn-lg btn-block btn-warning my-2">Edit profile</button>
                </form>
                <form action="/User/Profile" className="newForm stockForm">
                    <button className="btn-info btn-lg btn-block btn my-2">Back</button>
                </form>

            </div>
        );
    }
}
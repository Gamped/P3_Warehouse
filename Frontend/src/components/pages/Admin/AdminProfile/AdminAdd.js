import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";

export default class AdminAdd extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            userName: "",
            name: "",
            email: "",
            phoneNumber: "",
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

    handleNewPass = (event) => {
        this.setState({
            passwordNew: event.target.value,
        });
    }

    render(){
        return(
            <div className="PageStyle">
                <h1 className="title customText_b_big">Add new employee:</h1>
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
                            type="password" 
                            className="newForm" 
                            onChange={this.handleNewPass}
                            placeholder="New password"/>
                    </form>
                    <form action="/Admin/Profile" className="newForm stockForm">
                        <button className="newButton stockButton_f btn">Back</button>
                    </form>
                    <form action="/Admin/Profile" className="newForm stockForm">
                        <button className="newButton stockButton_f btn">Add new employee</button>
                    </form>
            </div>
        );
    }
}
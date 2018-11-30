import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";
import axios from "axios";

export default class AdminAdd extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userName: "",
            nickName: "",
            email: "",
            phoneNumber: "",
            password: "",
        };
    }

    addEmployeeHandler = (event) =>{
        event.preventDefault();
        const {userName, name, email,phoneNumber,password} = this.state;

        setTimeout(function () {
            axios.post('http://localhost:8080/employee', {userName, name, email, phoneNumber, password}).then((result)=> {
                this.props.history.goBack();
            }).catch((err) => {
            console.log(err.response);
            });
        }, 1000);
        this.props.history.goBack();
    }

    handleUName = (event) => {
        this.setState({
            userName: event.target.value,
        });
    }

    handleName = (event) => {
        this.setState({
            nickName: event.target.value,
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
            password: event.target.value,
        });
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <div className="container col">
                    <h1 className="title customText_b_big">Add new employee:</h1>
                    <form>
                        <input 
                            type="text" 
                            className="newForm" 
                            onChange={this.handleUName}
                            placeholder="Username"/>
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
                        <button className="btn-lg btn-block btn-secondary my-2 btn">Back</button>
                    </form>
                    <form className="newForm stockForm">
                        <button onClick={this.addEmployeeHandler} className="btn-lg btn-block btn-secondary my-2 btn">Add new employee</button>
                    </form>
                </div>
            </div>
        );
    }
}
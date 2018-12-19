import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";
import {Link} from "react-router-dom"
import {post} from './../../../../handlers/requestHandlers.js';

export default class AdminAdd extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userName: "",
            nickname: "",
            password: "",
        };
    }

    addEmployeeHandler = (event) =>{
        event.preventDefault();
        const {userName, nickname, password} = this.state;

        post('employee/employees', {nickname, userName, password}, () => {
            this.props.history.goBack();
        });
    }

    onChange = (e) => {this.setState({[e.target.name]: e.target.value});}

    render() {
        return(
            <div className="PageStyle customText_b">
                <div className="container col mb-3">
                    <h1 className="customText_b_big text-center display-3">Add new employee:</h1>
                    <div className="container">
                        <form>
                            <input 
                                type="text" 
                                className="form-control mb-2" 
                                onChange={this.onChange}
                                name="userName"
                                placeholder="Username"/>
                            <input 
                                type="text" 
                                className="form-control mb-2" 
                                onChange={this.onChange}
                                name="nickname"
                                placeholder="Nickname"/>
                            <input 
                                type="password" 
                                className="form-control mb-2" 
                                onChange={this.onChange}
                                name="password"
                                placeholder="New password"/>
                        </form>
                        <form className="newForm stockForm">
                            <button onClick={this.addEmployeeHandler} className="green_BTN btn-lg btn-block btn my-2">Add new employee</button>
                        </form>
                        <Link to="/Admin/Profile" className="dark_BTN btn-lg btn-block btn my-2">Back</Link>
                    </div>
                </div>
            </div>
        );
    }
}
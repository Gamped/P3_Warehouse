import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";
import axios from "axios";
import {Link} from "react-router-dom"

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

        setTimeout(function () {
            axios.post('http://localhost:8080/api/employee/employees', {nickname, userName, password}).then((result)=> {
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
            nickname: event.target.value,
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
                <div className="container col mb-3">
                    <h1 className="text-center display-3">Add new employee:</h1>
                    <div className="container">
                        <form>
                            <input 
                                type="text" 
                                className="form-control mb-2" 
                                onChange={this.handleUName}
                                placeholder="Username"/>
                            <input 
                                type="text" 
                                className="form-control mb-2" 
                                onChange={this.handleName}
                                placeholder="nickname"/>
                            <input 
                                type="password" 
                                className="form-control mb-2" 
                                onChange={this.handleNewPass}
                                placeholder="New password"/>
                        </form>
                        <form className="newForm stockForm">
                            <button onClick={this.addEmployeeHandler} className="btn-success btn-lg btn-block btn my-2">Add new employee</button>
                        </form>
                        <Link to="/Admin/Profile" className="btn-info btn-lg btn-block btn my-2">Back</Link>
                    </div>
                </div>
            </div>
        );
    }
}
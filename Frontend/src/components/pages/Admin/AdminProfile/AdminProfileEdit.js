import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import {put, get} from './../../../../handlers/requestHandlers.js'
import {repeatedPasswordWarning} from './../../../../handlers/exceptions.js'

//TODO: Passwords has to match

class AdminProfileEdit extends React.Component {
   
    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.userId,
            nickname: "",
            userName: "",
            passwordRepeat: "",
            password: ""
        };
    }

    componentDidMount() {
        this.getEmployeeData();
    }

    getEmployeeData() {
        get("employee/employee/" + this.props.match.params.id, (data) => {
            this.setState({
                nickname: data.nickname,
                userName: data.userName });
    });
}

    confirmed = (event) =>{
        event.preventDefault();

        const {userName, nickname, password} = this.state;
        
        if (this.state.password === this.state.passwordRepeat){
            put("employee/edit/" + this.props.match.params.id, 
            {userName, nickname, password}, ()=>{
                this.props.history.push("/Admin/Profile")
            });
    } else {
        repeatedPasswordWarning();

    }
}

    onChange = (e) => {
        this.setState({[e.target.name]: e.target.value});
    }
    
    render(){
        return(
            <div className="PageStyle customText_b">
                <h1 className="text-center">Edit profile:</h1>
                <div className="row">
                    <div className ="col-md-4 offset-md-4">
                        <form>
                        <input 
                                type="text" 
                                name="userName"
                                className="my-2 form-control"
                                defaultValue={this.state.userName} 
                                onChange={this.onChange}
                                placeholder="Name"/>

                            <input 
                                type="text" 
                                name="nickname"
                                className="my-2 form-control"
                                defaultValue={this.state.nickname} 
                                onChange={this.onChange}
                                placeholder="Name"/>
                               
                            <input
                                type="text" 
                                name="password"
                                className="my-2 form-control"
                                onChange={this.onChange}
                                placeholder="New password"/>
                              
                            <input
                                type="password" 
                                name="passwordRepeat"
                                className="my-2 form-control" 
                                onChange={this.onChange}
                                placeholder="New password repeat"/>
                               
                        </form>

                        <form className="newForm stockForm">
                        <button className="green_BTN btn-lg btn-block btn my-2" onClick={this.confirmed}>Save profile</button>
                        </form>
                        <Link to="/Admin/Profile" className="dark_BTN btn-lg btn-block btn my-2">Back</Link>
                    </div>
                </div>
            </div>
        );
    }
}
    
    
    
const mapStateToProps = (state) => {
    return {
        userId: state.loginReducer.userId
    }
}

export default connect(mapStateToProps)(AdminProfileEdit)
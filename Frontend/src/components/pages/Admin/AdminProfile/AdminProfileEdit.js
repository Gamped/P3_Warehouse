import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import {put} from './../../../../handlers/requestHandlers.js'
import {repeatedPasswordWarning} from './../../../../handlers/exceptions.js'

//TODO: Passwords has to match

class AdminProfileEdit extends React.Component {
   
    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.userId,
            nickname: "",
            passwordRepeat: "",
            password: ""
        };
    }


    confirmed = (event) =>{
        event.preventDefault();

        const {nickName, password} = this.state;
        
        if (this.state.password === this.state.passwordRepeat){
            put("employee/edit/" + this.state.userId, 
            {nickName, password}, ()=>{
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
            <div className="PageStyle rounded">
                <h1 className="text-center">Edit profile:</h1>
                <div className="row">
                    <div className ="col-md-4 offset-md-4">
                        <form>
                            <input 
                                type="text" 
                                name="nickName"
                                className="my-2 form-control" 
                                onChange={this.onChange}
                                placeholder="Name"/>
                                {console.log(this.state.nickname)}
                            <input
                                type="text" 
                                name="password"
                                className="my-2 form-control" 
                                onChange={this.onChange}
                                placeholder="New password"/>
                                {console.log(this.state.password)}
                            <input
                                type="password" 
                                name="passwordRepeat"
                                className="my-2 form-control" 
                                onChange={this.onChange}
                                placeholder="New password repeat"/>
                                {console.log(this.state.passwordRepeat)}
                        </form>

                        <form className="newForm stockForm">
                        <button className="btn-success btn-lg btn-block btn my-2" onClick={this.confirmed}>Save profile</button>
                        </form>
                        <Link to="/Admin/Profile" className="btn-info btn-lg btn-block btn my-2">Back</Link>
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
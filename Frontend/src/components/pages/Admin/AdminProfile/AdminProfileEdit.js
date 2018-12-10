import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import Axios from 'axios';

class AdminProfileEdit extends React.Component {
   
    constructor(props) {
        super(props);
        this.state = {
            userId: props.userId,
            nickname: "",
            passwordRepeat: "",
            password: ""
        };
    }


    confirmed = (event) =>{
        event.preventDefault();
        const {nickname, password, passwordRepeat} = this.state;
        console.log(password);
        console.log(passwordRepeat);
        console.log(this.state.password);
        console.log(this.state.passwordRepeat);
        
        if (password===passwordRepeat){
            Axios.put("http://localhost:8080/api/employee/edit/" + this.state.userId, 
            {nickname: this.state.nickname, password: this.state.password});
            this.props.history.push("/Admin/Profile")
        }else{
            alert("Password has not been repeated correctly.")
        }
    }

    handleName = (event) => {
        this.setState({
            nickname: event.target.value
        });
    }
    
    handlePass = (event) => {
        this.setState({
            password: event.target.value
        });
    }
    
    handlePassRepeat = (event) => {
        this.setState({
            passwordRepeat: event.target.value
        });
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
                                className="my-2 form-control" 
                                onChange={this.handleName}
                                placeholder="Name"/>
                                {console.log(this.state.nickname)}
                            <input
                                type="text" 
                                className="my-2 form-control" 
                                onChange={this.handlePass}
                                placeholder="New password"/>
                                {console.log(this.state.password)}
                            <input
                                type="password" 
                                className="my-2 form-control" 
                                onChange={this.handlePassRepeat}
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
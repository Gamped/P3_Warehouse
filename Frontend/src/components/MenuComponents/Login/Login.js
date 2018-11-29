import React from 'react';
import "./Login.css";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import Axios from "axios"

// The box for sign-in to the system
class SignInBox extends React.Component{

    // Updates the username part of the state
    emailTypedHandler = (event) => {
        this.setState({
            username: event.target.value
        })
    }

    // Updates the password part of the state
    passwordTypedHandler = (event) => {
        this.setState({
            password: event.target.value
        })
    }

    // Logs in
    loginHandler = (event) => {
        event.preventDefault()

        

        //We want it to succede a check here. Then get name, id and type on th user.
        if (this.state.username.toLowerCase() ==="admin"){ //Temp work until connected to backend
            this.props.login({ userType:"admin", loggedIn:"true", name: "Generic Name", userid:"UserID"})
            console.log(this.props.user.name);
            this.props.history.push("./Admin")
        } else if(this.state.username.toLowerCase() ==="user"){
            this.props.login("publisher", "Generic Name","UserID2")
            this.props.history.push("./User")
        } else{
            alert("Email should be either: User or Admin")
        }
    }

    render(){
        console.log(this.props)
        return(
            //Functionality for responding to user input
            <div>
                <div className="signBox">
                    <img src={require('../../../resources/4n_logo_mini.jpg')} className="logoPic" alt="The logo of 4N"/>
                    <div className="form">
                        <form>
                            <input type="Email" placeholder="Email" onChange={this.emailTypedHandler} required></input>
                            <input type="Password" placeholder="Password" onChange={this.passwordTypedHandler} required></input> 
                            <button onClick={this.loginHandler} className="signButton" >Sign in</button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state)=>{
    return{
        user: state.user
    }
}

const mapDispatchToProps = (dispatch) =>{
    return {
        login: (user) => {dispatch({type: "LOGIN",user: user})}
    }
}

export default connect(mapStateToProps ,mapDispatchToProps)(withRouter(SignInBox))
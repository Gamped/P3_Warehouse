import React from 'react';
import "./Login.css";
import {withRouter} from "react-router-dom";

// The box for sign-in to the system
class SignInBox extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            password: "",
            username: "",
        };
    }

    // Updates the username part of the state
    emailTypedHandler = (event) =>{
      this.setState({
        username: event.target.value
      })
    }

    // Updates the password part of the state
    passwordTypedHandler = (event)=>{
      this.setState({
        password: event.target.value
      })
    }

    // Logs the state
    logTheStateHandler = (event) => {
        event.preventDefault()
        if (this.state.username.toLowerCase() ==="admin"){ //Temp work until connected to backend
            console.log("WTF I SHOULD BE CHANGING TO ADMIN")
            this.props.history.push("./Admin")
        } else if(this.state.username.toLowerCase() ==="user"){
            this.props.history.push("./User")
            console.log("WTF I SHOULD BE CHANGING TO USER")
        } else{
            alert("Email should be either: User or Admin")
        }
      console.log(this.state)
      console.log(this.props)
      
    }

    render(){
        return(
            //Functionality for responding to user input
            <div>
                <div className="signBox">
                    <img src={require('../../../resources/4n_logo_mini.jpg')} className="logoPic" alt="The logo of 4N"/>
                    <input type="Email" placeholder="Email" onChange={this.emailTypedHandler} required></input>
                    <input type="Password" placeholder="Password" onChange={this.passwordTypedHandler} required></input> 
                    <form>
                        <button onClick={this.logTheStateHandler} className="signButton" >Sign in</button>
                    </form>
                </div>
            </div>
        );
    }
}

export default withRouter(SignInBox)
import React from 'react';
import "./Login.css";

// The box for sign-in to the system
export default class SignInBox extends React.Component{
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
        //event.preventDefault()
        if (this.state.username.toLowerCase ==="admin"){ //Temp work until connected to backend
            
        }
      console.log(this.state)
    }

    render(){
        return(
            //Functionality for responding to user input
            <div>
                <div className="signBox">
                    <img src={require('../../../resources/4n_logo_mini.jpg')} className="logoPic" alt="The logo of 4N"/>
                    <input type="Email" placeholder="Email" onChange={this.emailTypedHandler}></input>
                    <input type="Password" placeholder="Password" onChange={this.passwordTypedHandler}></input> 
                    <form action="./Admin">
                        <button onClick={this.logTheStateHandler} className="signButton" >Sign in (Admin)</button>
                    </form>
                    <form action="./User">
                        <button onClick={this.logTheStateHandler} className="signButton" >Sign in (User)</button>
                    </form>
                </div>
            </div>
        );
    }
}
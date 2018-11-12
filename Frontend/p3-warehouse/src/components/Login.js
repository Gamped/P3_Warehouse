import React from 'react';

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
        if (this.state.username.toLowerCase ==="admin"){
            return "../adminIndex"
        }
        else{
            return "./login"
        }
    }

    render(){
        let signInText = this.state.signText;
        return(
            <div className="signBox">
                <img src={require('../resources/4n_logo_mini.jpg')} className="logoPic" alt="The logo of 4N"/>
                <input type="Email" placeholder="Email" onChange={this.emailTypedHandler}></input>
                <input type="Password" placeholder="Password" onChange={this.passwordTypedHandler}></input>
                <form action={this.logTheStateHandler}>
                    <button className="signButton">Sign in</button>
                </form>
            </div>
        );
    }
}
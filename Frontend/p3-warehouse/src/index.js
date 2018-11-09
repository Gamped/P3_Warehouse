import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './style.css';

// TODO: Fix bug with white box not filling in proper when scrolling from small page


// The header component
class Header extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            titleText: "P3 Warehouse",
        };
    }

    render(){
        let title = this.state.titleText;

        return(
            <div class="headerStyle">
                <titleText>{title}</titleText>
            </div>
        );
    }
}

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

    logTheStateHandler = () => {
      console.log(this.state)
    }

    render(){
        let signInText = this.state.signText;
        return(
            <div class="signBox">
                <img src={require('./resources/4n_logo_mini.jpg')} class="logoPic" alt="The logo of 4N"/>
                <input type="Email" placeholder="Email" onChange={this.emailTypedHandler}></input>
                <input type="Password" placeholder="Password" onChange={this.passwordTypedHandler}></input>
                <form action="/login">
                    <button onClick={this.logTheStateHandler} class="signButton" >Sign in</button>
                </form>
            </div>
        );
    }
}

// Send components to HTML
ReactDOM.render(
    <div>
        <Header />
        <SignInBox />
    </div>
    , document.getElementById('root')
);

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

    render(){
        let signInText = this.state.signText;
        return(
            <div class="signBox">
                <img src={require('./resources/4n_logo_mini.jpg')} class="logoPic"/>
                <input type="Email" placeholder="Email" ></input>
                <input type="Password" placeholder="Password" ></input>
                <form action="/login">
                    <button onClick={console.log("Sign in has been pressed")} class="signButton" >Sign in</button>
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

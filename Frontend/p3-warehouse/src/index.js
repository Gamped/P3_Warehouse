import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './style.css';

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
            <div>
                <div class="signBox">
                    <img src={require('./resources/4n_logo_mini.jpg')} class="logoPic"/> 
                    <input type="Username" placeholder="Username" ></input>
                    <input type="Password" placeholder="Password" ></input>
                    <button class="signButton">Sign in</button>
                </div>
                    <div class="textureBox"> 
                    <a href="https://www.freepik.com/free-vector/book-shelves-dtcorative-colorful-icon-poster_2871137.htm">Image designed by Macrovector</a>
                </div>
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


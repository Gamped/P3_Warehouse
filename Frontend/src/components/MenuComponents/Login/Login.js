import React from 'react';
import "./Login.css";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import axios from "axios"

// The box for sign-in to the system
class SignInBox extends React.Component{

    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.loginHandler = this.loginHandler.bind(this);
    }

    onChange = (e) => {
        const state = this.state.product;
        state[e.target.name] = e.target.value;
    }

    loginHandler = (event) => {
        event.preventDefault()

        axios.get("http://localhost:8080/" + this.state.userName + "/" + this.state.password)
            .then(res => {
                console.log(res)
                this.props.login({ userType:res.userType, loggedIn:true, name: res.nickName, userid:res.id})
            })
            .then(res => {
                if(this.props.userType.toLowerCase()==="admin"){
                    this.props.history.push("./Admin")
                } else {
                    this.props.history.push("./User")
                }
            })

        //We want it to succede a check here. Then get name, id and type on th user.
       /* if (this.state.username.toLowerCase() ==="admin"){ //Temp work until connected to backend
            this.props.login({ userType:"admin", loggedIn:"true", name: "Generic Name", userid:"UserID"})
            console.log(this.props.user.name);
            this.props.history.push("./Admin")
        } else if(this.state.username.toLowerCase() ==="user"){
            this.props.login("publisher", "Generic Name","UserID2")
            this.props.history.push("./User")
        } else{
            alert("Email should be either: User or Admin")
        }*/
    }

    render(){

        return(
            //Functionality for responding to user input
            <div>
                <div className="signBox">
                    <img src={require('../../../resources/4n_logo_mini.jpg')} className="logoPic" alt="The logo of 4N"/>
                    <div className="form">
                        <form>
                            <input type="Username" name="userName" placeholder="Username" onChange={this.onChange} required></input>
                            <input type="Password" name="password" placeholder="Password" onChange={this.onChange} required></input>
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

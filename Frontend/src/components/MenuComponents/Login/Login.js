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
                if(this.props.userType.toLowerCase()==="employee"){
                    this.props.history.push("./Admin")
                }else if(this.props.userType.toLowerCase === "client"||this.props.userType.toLowerCase === "publisher"){
                    this.props.history.push("./User")
                }
            })

        
    }

    render(){

        return(
            //Functionality for responding to user input
            <div>
                
                <div className="col-sm makeRelative mx-auto mt-5">
                    <div className="mt-5">    
                        <form className="form-signin">
                            <div className="text-center mb-4">
                                <img src={require('../../../resources/4n_logo_mini.jpg')} className="logoPic" alt="The logo of 4N"/>
                                <h1 className="h3 mb-3 font-weight-normal">4N Mailhouse</h1>
                            </div>
                            <div className="input-group mb-3">
                                <div className="input-group-prepend">
                                <label htmlFor="inputEmail" ><span className="input-group-text" id="inputGroup-sizing-default">Username</span></label>
                                </div>
                                <input type="text" className="form-control" id="inputEmail" placeholder="Username" onChange={this.emailTypedHandler} required autoFocus/>
                            </div>
                            <div className="input-group mb-3">
                                <div className="input-group-prepend">
                                <label htmlFor="inputPassword"><span className="input-group-text" id="inputGroup-sizing-default">Password</span></label>
                                </div>
                                <input type="Password" className="form-control" id="inputPassword" placeholder="Password" onChange={this.passwordTypedHandler} required/>
                            </div>
                            
                            <button onClick={this.loginHandler} className="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                    
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

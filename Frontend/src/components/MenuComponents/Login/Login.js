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
        this.state = {
            userName:"",
            password:"",
            userType:"client"
        }
    }

    onChange = (e) => {
        const state = this.state.product;
        state[e.target.name] = e.target.value;
    }

    toggleCheckbox= (e) =>{
        if(this.state.userType === "client"){
            this.setState({userType:"employee"})
        }else{
            this.setState({usetType:"client"})
        }
    }

    loginHandler = (event) => {
        event.preventDefault()

       axios.get("localhost:8080/users?username=" + this.state.username + "&password="+this.state.password)
            .then(res => {
                console.log(res)
                this.props.setNickName(res.nickName)
                this.props.setUserType(res.userType)
                this.props.setUserId(res.id)
                this.props.setlogIn("True")
            })
            .then(res => {
                if(res.userType==="EMPLOYEE"){
                    this.props.history.push("./Admin")
                }else if(res.userType === "CLIENT"||res.userType==="PUBLISHER"){
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
                                <input type="text" className="form-control" id="inputEmail" name="userName" placeholder="Username" onChange={this.onChange} required autoFocus/>
                            </div>
                            <div className="input-group mb-3">
                                <div className="input-group-prepend">
                                <label htmlFor="inputPassword"><span className="input-group-text" id="inputGroup-sizing-default">Password</span></label>
                                </div>
                                <input type="Password" className="form-control" id="inputPassword" name="password" placeholder="Password" onChange={this.onChange} required/>
                            </div>
                            <div className="input-group mb-3">
                                <div className="input-group-prepend center">
                                    <div className="input-group-text">
                                        <input type="checkbox" aria-label="employee textbox" id="EmployeeCheckbox" onChange={this.toggleCheckbox}/>
                                    </div>
                                    <label className="form-control" htmlFor="EmployeeCheckbox">Employee?</label>
                                </div>
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
        userType: state.loginReducer
    }
}

const mapDispatchToProps = (dispatch) =>{
    return {
        setUserType: (userType) => {dispatch({type: "SET_USERTYPE",payload: {userType}})},
        setNickName: (userName) => {dispatch({type: "SET_USERNAME",payload: {userName}})},
        setUserId: (userId) => {dispatch({type: "SET_USERID",payload: {userId}})},
        setlogIn: (logged) => {dispatch({type: "SET_LOGIN",payload: {logged}})}
    }
}

export default connect(mapStateToProps ,mapDispatchToProps)(withRouter(SignInBox))

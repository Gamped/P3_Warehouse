import React from 'react';
import "./Login.css";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import {get} from "./../../../handlers/requestHandlers"

// The box for sign-in to the system
class SignInBox extends React.Component {

    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.loginHandler = this.loginHandler.bind(this);
        this.state = {
            userName:"",
            password:"",
        }
    }

    //Mapping the value of the textbox to the state
    onChange = (e) => {
        this.setState({[e.target.name]: e.target.value});
    }

    //This handles our login. Takes an event and calls axios.
    //Then we map the results to redux through dispatches before pushing the user to main.
    loginHandler = (event) => {
        event.preventDefault()
        get("users/login/" + this.state.userName + "/" +this.state.password,(res)=>{
            console.log(res)
            console.log(this.state)
            this.props.setUserName(res.contactInformation.nickName);
            this.props.setUserType(res.userType);
            this.props.setUserId(res.hexId)
            this.props.setlogIn("True")
        })
    }

    render(){
        if(this.props.user.loggedIn==="True"){
            this.props.history.push("./Home")
        }

        return(
            //This is what we return and what the user sees.
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

                            <button onClick={this.loginHandler} className="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                    
                        </form>
                    </div>
                </div>
                
            </div>
        );
    }
}


const mapStateToProps = (state)=>{
    return {
        user: state.loginReducer
    }
}

const mapDispatchToProps = (dispatch) =>{
    return {
        setUserType: (userType) => {dispatch({type: "SET_USERTYPE",payload: {userType}})},
        setUserName: (userName) => {dispatch({type: "SET_USERNAME",payload: {userName}})},
        setUserId: (userId) => {dispatch({type: "SET_USERID",payload: {userId}})},
        setlogIn: (loggedIn) => {dispatch({type: "SET_LOGIN",payload: {loggedIn}})}
    }
}

export default connect(mapStateToProps ,mapDispatchToProps)(withRouter(SignInBox))

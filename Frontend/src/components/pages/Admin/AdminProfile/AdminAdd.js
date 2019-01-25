import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";
import {Link, Redirect} from "react-router-dom"
import {post} from './../../../../handlers/requestHandlers.js';
import {connect} from "react-redux";
import {signUpEmployee} from "./../../../../redux/actions/authActions";

class AdminAdd extends React.Component {

    constructor(props) {
        super(props);
        
        this.state = {
            email: "",
            nickName: "",
            password: "",
            userType: "employee"
        };
    }

    onChange = (e) => {this.setState({[e.target.name]: e.target.value});}

    onSubmit = (e) =>{
        e.preventDefault();

        this.props.signUp(this.state);
    }

    render() {

        if(!this.props.auth.uid){
            return <Redirect to="/"/>
        }

        const error = this.props.error;

        return(
            <div className="PageStyle">
                <div className="container col-md-6 offset-md-3">
                    <h1 className="text-center ">Add new employee:</h1>
                    <div className="container">
                        <div className="input-group mb-3 center">
                            {error ? <p className="text-danger">{error}</p>:null}
                        </div>
                        <form onSubmit={this.onSubmit}>
                            <input 
                                type="email" 
                                className="form-control mb-2" 
                                onChange={this.onChange}
                                name="email"
                                placeholder="Email" required/>
                            <input 
                                type="text" 
                                className="form-control mb-2" 
                                onChange={this.onChange}
                                name="nickName"
                                placeholder="Nickname" required/>
                            <input 
                                type="password" 
                                className="form-control mb-2" 
                                onChange={this.onChange}
                                name="password"
                                placeholder="New password" required/>

                                <button type="submit" className="btn-success btn-lg btn-block btn my-2">Add new employee</button>
                        </form>
                        <Link to="/Admin/Profile" role="button" className="btn-secondary btn-lg btn-block btn my-2">Back</Link>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) =>{
    return{
        auth: state.firebase.auth,
        error: state.loginReducer.error
    }
}

const mapDispatchToProps = (dispatch) =>{
    return{
        signUp: (payload) => dispatch(signUpEmployee(payload))
    }
}

export default connect(mapStateToProps,mapDispatchToProps)(AdminAdd)
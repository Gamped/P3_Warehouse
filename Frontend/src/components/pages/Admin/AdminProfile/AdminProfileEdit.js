import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";
import { Link, Redirect } from "react-router-dom";
import { connect } from "react-redux";
import { updateCurrentEmployeeEmail, updateCurrentUserName } from "./../../../../redux/actions/authActions";

class AdminProfileEdit extends React.Component {

    constructor(props) {
        super(props);
        
        this.state = {
            userId: this.props.userId,
            name:null,
            email:null,
        };
    }

    confirmed = (event) =>{
        event.preventDefault();
        const {name,email} = this.state;
        if(name !== null){
            if(email !== null){
                this.props.updateName({name:name})
                this.props.updateEmail({email:email})    
            }else(
                this.props.updateName({name:name})
            )
        }else if(email !== null){
            this.props.updateEmail({email:email})
        }else{
            alert("Nothing was changed")
        }
    }

    onChange = (e) => {this.setState({[e.target.name]: e.target.value});}
    
    render(){

        if((!this.props.auth.uid)||(this.props.profile.userType !=="employee")){
            return <Redirect to="/"/>
        }

        const {name,email} = this.props.profile;
        const error = this.props.error;

        return(
            <div className="PageStyle">
                <h1 className="text-center">Edit profile:</h1>
                <div className="row">
                    <div className ="col-md-4 offset-md-4">
                        {error ? <p className="text-danger text-center">{error}</p>:null}
                        <form>
                        <div className="row">
                            <div className="input-group mb-3">
                                <div className="input-group-prepend">
                                    <span className="input-group-text" htmlFor="" id="basic-addon1">Name</span>
                                </div>
                                <input 
                                    type="text" 
                                    name="name"
                                    className="form-control"
                                    defaultValue={name} 
                                    onChange={this.onChange}
                                    placeholder="Name"/>
                            </div>
                        </div>
                                
                        <div className="row">
                            <div className="input-group mb-3">
                                <div className="input-group-prepend">
                                    <span className="input-group-text" htmlFor="" id="basic-addon1">Email</span>
                                </div>
                                <input 
                                    type="email" 
                                    placeholder="Email" 
                                    name="email" 
                                    className="form-control" 
                                    aria-label="Email" 
                                    defaultValue={email}
                                    onChange={this.onChange} 
                                    aria-describedby="basic-addon1"/>
                            </div>
                        </div>
        

                            <button className="btn-warning btn-lg btn-block btn my-2" onClick={this.confirmed}>Save profile</button>
                        </form>
                        <Link to="/Admin/Profile" role="button" className="btn-secondary btn-lg btn-block btn my-2">Back</Link>
                    </div>
                </div>
            </div>
        );
    }
}   
    
const mapStateToProps = (state) => {

    return {
        auth: state.firebase.auth,
        profile: state.firebase.profile,
        error: state.loginReducer.error
    }
}

const mapDispatchToProps = (dispatch) =>{
    return {
        updateEmail: (payload) => dispatch(updateCurrentEmployeeEmail(payload)),
        updateName: (payload) => dispatch(updateCurrentUserName(payload))
    }
}

export default connect(mapStateToProps,mapDispatchToProps)(AdminProfileEdit)
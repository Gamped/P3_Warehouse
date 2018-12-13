import React from 'react';
import "../../Pages.css";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import { put } from "./../../../../handlers/requestHandlers";
import { userProfileFieldsAreValidated } from "./../../../../handlers/fieldsValidator";

class UserProfileEdit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.userId,
            userName: this.props.user.userName,
            nickName: this.props.user.nickName,
            email: this.props.user.email,
            phoneNumber: this.props.user.phoneNumber,
            address: this.props.user.address,
            city:this.props.user.city,
            zipCode:this.props.user.zipCode,
            country:this.props.user.country,
            changed:{passwordNew:"",passwordNewRepeat:""}
        };
    }

    onChangeHandler = (event) => {
        console.log(this.state)
        this.setState({
            changed:{[event.target.name]: event.target.value},
        })
    }

    
    confirmed = (event) =>{
        event.preventDefault();

        let fields = this.state;
        if (userProfileFieldsAreValidated(fields)) {
            
        

            const usertype= this.props.userType
            const body = this.makeBodyFromChangedState();

            if (usertype === "PUBLISHER") {
                put("publishers/"+this.props.userId, body, (response)=>{
                    this.props.history.push("/User/Profile");
                })
            } else if (usertype === "CLIENT") {
                put("clients/"+this.props.userId, body, (response)=>{
                    this.props.history.push("/User/Profile");
                })
            }
    }

}

    makeBodyFromChangedState() {
        let newState = {};
        const changedState = {...this.state.changed}
        Object.keys(changedState).forEach((key) => {
            if(changedState[key] !=="" && changedState[key] !== null) {
                newState[key] = changedState[key];
            }
        })

        newState={...this.state,...newState}
        const body = {
            userName:newState.userName,
            password:newState.password,
            userType:newState.userType,
            contactInformation:{
                nickName:newState.nickName,
                email:newState.email,
                phoneNumber:newState.phoneNumber,
                city: newState.city,
                address: newState.address,
                zipCode: newState.zipCode,
                country: newState.country
            }
        }
        return body;
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <h1 className="text-center">Edit profile:</h1>
                <div className="row">
                    <div className ="col-md-4 offset-md-4">
                        <form>
                            <div className="input-group mb-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text" id="basic-addon10">User Name</span>
                                </div>
                                <input
                                    name="userName" 
                                    type="text" 
                                    className="form-control" 
                                    onChange={this.onChangeHandler}
                                    defaultValue={this.props.user.userName}/>
                            </div>

                            <div className="input-group mb-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text" id="basic-addon9">Nickname</span>
                                </div>
                                <input 
                                    name="nickName"
                                    type="text" 
                                    className="form-control" 
                                    onChange={this.onChangeHandler}
                                    defaultValue={this.props.user.nickName}/>
                            </div>

                            <div className="input-group mb-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text" id="basic-addon8">Email</span>
                                </div>
                                <input
                                    name="email" 
                                    type="email" 
                                    className="form-control" 
                                    onChange={this.onChangeHandler}
                                    defaultValue={this.props.user.email}/>
                            </div>
                            
                            <div className="input-group mb-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text" id="basic-addon7">Phonenumber</span>
                                </div> 
                                <input
                                name="phoneNumber" 
                                type="tel" 
                                className="my-2 form-control" 
                                onChange={this.onChangeHandler}
                                defaultValue={this.props.user.phoneNumber}/>
                            </div>

                            <div className="input-group mb-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text" id="basic-addon6">Adress</span>
                                </div>
                            <input
                                name="address" 
                                type="text" 
                                className="form-control" 
                                onChange={this.onChangeHandler}
                                defaultValue={this.props.user.address}/>
                            </div>

                            <div className="input-group mb-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text" id="basic-addon5">City</span>
                                </div>
                                <input
                                    name="city" 
                                    type="text" 
                                    className="form-control" 
                                    onChange={this.onChangeHandler}
                                    defaultValue={this.props.user.city}/>
                            </div>
                            
                            <div className="input-group mb-2">
                                <div className="input-group-prepend">
                                    <span className="input-group-text" id="basic-addon4">Zip Code</span>
                                </div>
                                <input
                                name="zipCode" 
                                type="text" 
                                className="form-control" 
                                onChange={this.onChangeHandler}
                                defaultValue={this.props.user.zipCode}/>
                            <input
                                name="country" 
                                type="text" 
                                className="my-2 form-control" 
                                onChange={this.onChangeHandler}
                                defaultValue={this.props.user.country}/>
                            <input
                                name="passwordNew"
                                type="password" 
                                className="my-2 form-control" 
                                onChange={this.onChangeHandler}
                                placeholder="New password"/>
                            <input
                                name="passwordNewRepeat" 
                                type="password" 
                                className="my-2 form-control" 
                                onChange={this.onChangeHandler}
                                placeholder="New password repeat"/>
                        </form>
                        
                        <form className="newForm stockForm">
                            <button className="btn btn-block btn-warning my-2" onClick={this.confirmed}>Edit profile</button>
                        </form>
                        
                        <Link to="/User/Profile" className="btn-info btn btn-block btn my-2">Back</Link>
                        
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        user: state.profileReducer,
        userId: state.loginReducer.userId,
        userType: state.loginReducer.userType,
    }
}

export default connect(mapStateToProps)(UserProfileEdit)

import React from 'react';
import "../../Pages.css";
import { connect } from "react-redux";
import { Link } from "react-router-dom";

class UserProfileEdit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.userId,
            userName: "User Name",
            name: "Name",
            email: "Email",
            phoneNumber: "Phonenumber",
            address: "Address",
            passwordNewRepeat: "",
            passwordNew: "",
            cvr: "CVR",
            city:"City",
            zip:"Zip Code",
            country:"Country",
        };
    }

    onChangeHandler = (event) => {
        this.setState({
            [event.target.name]: event.target.value,
        });
    }

    confirmed = (event) =>{
        event.preventDefault();
        if (this.state.passwordNew===this.passwordNewRepeat
            || (this.state.passwordNew.length===0 && this.state.passwordNewRepeat.length===0)){
            //Todo: Insert axios der skriver nyt information til serveren. Bruger id er this.props.userID
            this.props.history.push("/User/Profile")
        }else{
            alert("Password has not been repeated correctly.")
        }
    }

    render(){
        console.log(this.props.user)
        return(
            <div className="PageStyle rounded">
                <h1 className="text-center">Edit profile:</h1>
                <div className="row">
                    <div className ="col-md-4 offset-md-4">
                        <form>
                            <input
                                name="userName" 
                                type="text" 
                                className="my-2 form-control" 
                                onChange={this.handleUName}
                                defaultValue={this.props.user.userName}/>
                            <input 
                                name="name"
                                type="text" 
                                className="my-2 form-control" 
                                onChange={this.handleName}
                                defaultValue={this.props.user.nickName}/>
                            <input
                                name="email" 
                                type="email" 
                                className="my-2 form-control" 
                                onChange={this.handleEmail}
                                defaultValue={this.props.user.email}/>
                            <input
                                name="phoneNumber" 
                                type="text" 
                                className="my-2 form-control" 
                                onChange={this.handlePhoneNumber}
                                defaultValue={this.props.user.phoneNumber}/>
                            <input
                                name="address" 
                                type="text" 
                                className="my-2 form-control" 
                                onChange={this.handleAddress}
                                defaultValue={this.props.user.address}/>
                            <input
                                name="city" 
                                type="text" 
                                className="my-2 form-control" 
                                onChange={this.handleAddress}
                                defaultValue={this.props.user.city}/>
                            <input
                                name="zip" 
                                type="text" 
                                className="my-2 form-control" 
                                onChange={this.handleAddress}
                                defaultValue={this.props.user.zipCode}/>
                            <input
                                name="country" 
                                type="text" 
                                className="my-2 form-control" 
                                onChange={this.handleAddress}
                                defaultValue={this.props.user.country}/>
                            <input 
                                name="cvr"
                                type="text" 
                                className="my-2 form-control" 
                                onChange={this.handleCVR}
                                defaultValue={this.state.cvr}/>
                            <input
                                name="passwordNew"
                                type="test" 
                                className="my-2 form-control" 
                                onChange={this.handleCurPass}
                                placeholder="New password"/>
                            <input
                                name="passwordNewRepeat" 
                                type="password" 
                                className="my-2 form-control" 
                                onChange={this.handleNewPass}
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
        user:state.profileReducer,
        userId:state.loginReducer.userId,
        userType:state.loginReducer.userType,
    }
}

export default connect(mapStateToProps)(UserProfileEdit)
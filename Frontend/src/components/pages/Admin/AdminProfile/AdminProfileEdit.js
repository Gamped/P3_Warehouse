import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";
import { Link } from "react-router-dom";
import { connect } from "react-redux";

class AdminProfileEdit extends React.Component {
   
constructor(props) {
    super(props);
    this.state = {
        userID: props.ID,
        userName: "",
        name: "",
        email: "",
        phoneNumber: "",
        passwordNewRepeat: "",
        passwordNew: "",
    };
}

onChangeHandler = (event) => {
    this.setState({
        [event.target.name]: event.target.value,
    });
}

confirmed = (event) =>{
    event.preventDefault();
    if (this.state.passwordNew==this.passwordNewRepeat
        || (this.state.passwordNew.length===0 && this.state.passwordNewRepeat.length===0)){
        //Todo: Insert axios der skriver nyt information til serveren. Bruger id er this.props.userID
        this.props.history.push("/Admin/Profile")
    }else{
        alert("Password has not been repeated correctly.")
    }
}

render(){
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
                            placeholder="User name"/>
                        <input 
                            name="name"
                            type="text" 
                            className="my-2 form-control" 
                            onChange={this.handleName}
                            placeholder="Name"/>
                        <input
                            name="email" 
                            type="email" 
                            className="my-2 form-control" 
                            onChange={this.handleEmail}
                            placeholder="Email"/>
                        <input
                            name="phoneNumber" 
                            type="number" 
                            className="my-2 form-control" 
                            onChange={this.handlePhoneNumber}
                            placeholder="Phone Number"/>
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
                    <button className="btn-success btn-lg btn-block btn my-2" onClick={this.confirmed}>Save profile</button>
                    </form>
                    <Link to="/Admin/Profile" className="btn-info btn-lg btn-block btn my-2">Back</Link>
                </div>
            </div>
        </div>
    );
}
}

const mapStateToProps = (state) => {
return {
    userID: state.loginReducer.userId
}
}

export default connect(mapStateToProps)(AdminProfileEdit)
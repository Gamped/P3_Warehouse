import React from 'react';
import {Link} from "react-router-dom";
import {connect} from 'react-redux';
import {get} from './../../../../handlers/requestHandlers.js'
import "../../Pages.css";
import "./AdminProfile.css";

class AdminProfile extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            userName: "",
            nickName: "",
            userType: props.userType,
            userId: props.userId,

            userName: "",
            nickname: ""
        };
    }
   
    componentDidMount(){
      
       get("employee/" + this.state.userId, (data) => {

            this.state.userName = data.userName;
            this.state.nickName = data.nickName;
       })       
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <h1 className="title customText_b_big">Profile information</h1>
                <div className="informationBox">
                    <h1 className="lead"><strong>User name: {this.state.userName}</strong></h1>
                    <h1 className="lead"><strong>Name: {this.state.nickName}</strong></h1>


                    <Link to="/Admin/Profile/AddEmployee" className="btn-block btn-success btn my-2">Add employee</Link>
                    
                    <Link to="/Admin/Profile/Edit" className="btn-block btn-warning btn my-2">Edit employee</Link>
                    
                    <Link to="/Admin/Profile/RemoveEmployee" className="btn-block btn-danger btn my-2">Remove employee</Link>
                    
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state)=>{
    return{
        userType: state.loginReducer.userType,
        userId: state.loginReducer.userId
    }
}

export default connect(mapStateToProps)(AdminProfile);
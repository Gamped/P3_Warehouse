import React from 'react';
import Axios from "axios";
import {Link} from "react-router-dom";
import {connect} from 'react-redux';

import "../../Pages.css";
import "./AdminProfile.css";

class AdminProfile extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            userType: props.userType,
            userId: props.userId,

            userName: "",
            nickname: ""
        };
    }
   
    componentDidMount(){
        //Todo: Sørg for at den henter det rigtige sted fra
        Axios.get("http://localhost:8080/api/employee/" + this.state.userId)
            .then((response) => {
                this.setState({
                    userName: response.data.userName,
                    nickname: response.data.nickname
                })
            })
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <h1 className="title customText_b_big">Profile information</h1>
                <div className="informationBox">
                    <h1 className="lead"><strong>User name: {this.state.userName}</strong></h1>
                    <h1 className="lead"><strong>Name: {this.state.nickname}</strong></h1>

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
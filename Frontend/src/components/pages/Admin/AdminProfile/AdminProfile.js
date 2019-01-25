import React from 'react';
import {Link, Redirect} from "react-router-dom";
import {connect} from 'react-redux';
import "../../Pages.css";
import "./AdminProfile.css";
import { getColumnsFromArray } from '../../../../handlers/columnsHandlers.js';
import {compose} from "redux";
import {firestoreConnect} from "react-redux-firebase";
import {deleteCurrentUser} from "./../../../../redux/actions/authActions";

class AdminProfile extends React.Component {

    constructor(props){
        super(props);
        
        this.state = {
            userName: "",
            nickName: "",
            userType: props.userType,
            userId: props.userId,
            employees: [],
            selected: [],
            selectedId: ""
        };
    }

    ///makeEmployeeData(data)
    makeEmployeeData(data) {
        if(!(data === undefined||data===null)){

            let employees = [];            
        
            data.forEach((employee) => {
                if(employee.userType === "employee" && employee.id !== this.props.auth.uid){
                    employees.push({
                        email: employee.email,
                        name: employee.name,
                        id: employee.id
                    })
                }
            })
            return employees;
        }
        return undefined
    }


    deleteEmployee = (e) => {
        e.preventDefault();

        if (window.confirm("Do you wish to delete this employee from the system?")) {
            this.props.delete();
        } 
    }

    render() {

        if((!this.props.auth.uid)||(this.props.profile.userType !== "employee")){
            return <Redirect to="/"/>
        }

        const employees = this.makeEmployeeData(this.props.data);
        const columns = getColumnsFromArray(["Name","Email"]);

        return(
            <div className="PageStyle"> 
                <div className="col-md-6 offset-md-3">
                    <div className="row">
                        <h1 className="">Profile information</h1>
                    </div>
                    <div className="row">
                        <div className="input-group mb-3">
                            <div className="input-group-prepend">
                                <span className="input-group-text" htmlFor="" id="basic-addon1">Name</span>
                            </div>
                            <span className="form-control" aria-label="Name" aria-describedby="basic-addon1">{this.props.profile.name}</span>
                        </div>
                    </div>
                    <div className="row">
                        <div className="input-group mb-3">
                            <div className="input-group-prepend">
                                <span className="input-group-text" htmlFor="" id="basic-addon1">Email</span>
                            </div>
                            <span className="form-control" aria-label="Name" aria-describedby="basic-addon1">{this.props.profile.email}</span>
                        </div>
                    </div>
                    <div className="row">
                        <Link to="/Admin/Profile/AddEmployee" role="button" className="btn-success btn my-2 mx-2">Add new employee</Link>
                        <Link to="/Admin/Profile/Edit/" role="button" className="btn-warning btn my-2 mx-2">Edit this profile</Link>
                        <button className="btn-danger btn my-2 mx-2" onClick={this.deleteEmployee}>Remove this employee</button>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state)=> {
    console.log(state)
    return{
        auth: state.firebase.auth,
        userType: state.loginReducer.userType,
        userId: state.loginReducer.userId,
        data: state.firestore.ordered.users,
        profile: state.firebase.profile
    }
}

const mapDispatchToProps = (dispatch) =>{
    return{
        delete: () =>{dispatch(deleteCurrentUser())}
    }
}

export default compose(
    connect(mapStateToProps,mapDispatchToProps),
    firestoreConnect([
        {collection: "users"}
    ])
)(AdminProfile);
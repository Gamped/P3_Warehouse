import React from 'react';
import ReactTable from 'react-table';
import {Link} from "react-router-dom";
import {connect} from 'react-redux';
import {get, del} from './../../../../handlers/requestHandlers.js'
import {makeEmployeeData} from './../../../../handlers/dataHandlers'
import "../../Pages.css";
import "./AdminProfile.css";
import { getColumnsFromArray } from '../../../../handlers/columnsHandlers.js';
import AdminStock from '../AdminStock/AdminStock.js';
let alert = require('./../../../../handlers/alertHandlers.js');

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
   
    componentDidMount(){

       this.getEmployees();
    }

    setLoggedInUserData() {
        console.log(this.props.userId);

        const loggedInUser = this.state.employees.filter(employee => employee.hexId == this.props.userId);
        console.log(loggedInUser);

        this.setState({userName: loggedInUser[0].userName, nickName: loggedInUser[0].nickname});
    }

    getEmployees() {
        
        get("employee/employees", (data) => {
            const employees = makeEmployeeData(data);
            this.setState({employees: employees});
            this.setLoggedInUserData();
        });  
    }

    deleteEmployee = (e) => {
        e.preventDefault();

        console.log(this.state.selectedId);
        if (window.confirm("Do you wish to delete this employee user?")) {
            
            del("employee/delete/" + this.state.selectedId, (status) => {
                console.log(status);
                window.location.reload();
            })
        } 
    }

    render() {

        const employees = this.state.employees;
        const columns = getColumnsFromArray(["User Name", "Nick name"]);

        return(
            <div className="PageStyle rounded"> 
                    <h1 className="title customText_b_big">Profile information</h1>
                    <div className="informationBox">
                        <ReactTable
                            data={employees} 
                            columns={columns} 
                            showPagination={false} 
                            className="-striped -highlight"
                            getTrProps={(state, rowInfo) => {
                                if (rowInfo && rowInfo.row) {
                                return {
                                    onClick: (e) => {
                                        
                                    this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId })
                                    console.log(this.state.selectedId)
                                    },
                                    style: {
                                    background: rowInfo.index === this.state.selected ? '#00afec' : 'white',
                                    color: rowInfo.index === this.state.selected ? 'white' : 'black'
                                    }
                                }
                                }else{
                                return {}
                                }
                            }}
                            style={{height: "50vh"}}
                        />
                   
                        <Link to="/Admin/Profile/AddEmployee" className=" btn-success btn my-2 mx-2">Add employee</Link>
                        <Link to={`/Admin/Profile/Edit/${this.state.selectedId}`} className="btn-warning btn my-2 mx-2">Edit employee</Link>
                        <div className="btn-danger btn my-2 mx-2" onClick={this.deleteEmployee}>Remove employee</div>
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
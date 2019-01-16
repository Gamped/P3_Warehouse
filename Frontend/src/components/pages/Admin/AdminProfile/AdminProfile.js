import React from 'react';
import ReactTable from 'react-table';
import {Link, Redirect} from "react-router-dom";
import {connect} from 'react-redux';
import {get, del} from './../../../../handlers/requestHandlers.js'
import {makeEmployeeData} from './../../../../handlers/dataHandlers'
import "../../Pages.css";
import "./AdminProfile.css";
import { getColumnsFromArray } from '../../../../handlers/columnsHandlers.js';

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
   
    componentDidMount() {
    
        this.getEmployees();
    }

    setLoggedInUserData() {
    
        const loggedInUser = this.state.employees.filter(employee => employee.hexId == this.props.userId);
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

        if (window.confirm("Do you wish to delete this employee from the system?")) {
            
            del("employee/delete/" + this.state.selectedId, (status) => {
                
                const updatedList = this.state.employees.filter(x=>x.hexId !== this.state.selectedId);
                this.setState({employees: updatedList})
            })
        } 
    }

    sendToEdit = () =>{

        if(this.state.selectedId === ""){
            window.alert("Please choose a profile to edit.")
        } else {
            this.props.history.push(`/Admin/Profile/Edit/${this.state.selectedId}`)
        }
    }

    render() {
        if(!this.props.auth.uid){
            return <Redirect to="/"/>
        }

        const employees = this.state.employees;
        const columns = getColumnsFromArray(["User Name", "Nick name"]);

        return(
            <div className="PageStyle customText_b"> 
                <h1 className="title customText_b_big">Profile information</h1>
                <div className="informationBox">
                    <h1 className="lead"><strong>Other employees: {this.state.userName}</strong></h1>
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
                   
                    <Link to="/Admin/Profile/AddEmployee" className="green_BTN btn my-2 mx-2">Add employee</Link>
                    <button onClick={this.sendToEdit} className="yellow_BTN btn my-2 mx-2">Edit employee</button>
                    <div className="red_BTN btn my-2 mx-2" onClick={this.deleteEmployee}>Remove employee</div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state)=> {

    return{
        auth: state.firebase.auth,
        userType: state.loginReducer.userType,
        userId: state.loginReducer.userId
    }
}

export default connect(mapStateToProps)(AdminProfile);
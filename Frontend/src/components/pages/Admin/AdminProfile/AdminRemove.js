import React from 'react';
import { Link } from "react-router-dom";
import axios from 'axios';
import {connect} from 'react-redux';
import ReactTable from 'react-table';

import "../../Pages.css";
import "./AdminProfile.css";
import {getColumnsFromArray} from "../../../../handlers/columnsHandlers";
import {makeEmployeeData} from "../../../../handlers/dataHandlers";

class AdminRemove extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: props.userId,
            password: "",

            employees: [],

            selected: null,
            selectedId: ""
        };
        this.initiateRemoval = this.initiateRemoval.bind(this)
    }

    componentDidMount(){
        axios.get("http://localhost:8080/api/employee/employees")
            .then((response) => {
                this.setState({
                    employees: makeEmployeeData(response.data)
                });
            });
    }

    setStateAsSelected = (rowInfo) => {
        this.setState({selected: rowInfo.index, selectedId: rowInfo.original.id });
    }
    
    initiateRemoval = () => {
        axios.delete("http://localhost:8080/api/employee/delete/" + this.state.selectedId, {password: this.state.password});
    }
    
    handleUName = (event) => {
        this.setState({
            userName: event.target.value,
        });
    }

    handlePass = (event) => {
        this.setState({
            password: event.target.value,
        });
    }

    render(){
        const columns = getColumnsFromArray([
            "Username", 
            "Name",
            "Id"
        ])
        const data = this.state.employees
        return(
        <div className="PageStyle rounded">
            <h1 className="text-center">Remove employee:</h1>
                <form>
                    <ReactTable 
                        columns={columns}
                        data={data}
                        showPagination={false}
                        className="-striped -highlight"
                        getTrProps={(state, rowInfo) => {
                            if (rowInfo && rowInfo.row) {
                                return {
                                    onClick: (e) => {
                                        this.setStateAsSelected(rowInfo);
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
                    
                    style={{height: "59vh"}}
                    />
                
                    <input 
                        type="password" 
                        className="my-2 form-control" 
                        onChange={this.handlePass}
                        placeholder="YOUR password"/>
                </form>
                
                <Link to="/Admin/Profile" className="btn-lg btn-danger btn-block my-2 btn" onClick={this.initiateRemoval}>REMOVE employee</Link>
                
                <Link to="/Admin/Profile" className="btn-info btn-lg btn-block btn my-2">Back</Link>
                
        </div>);
    }
}

const mapStateToProps = (state)=>{
    return{
        userType: state.loginReducer.userType,
        userId: state.loginReducer.userId
    }
}

export default connect(mapStateToProps)(AdminRemove);
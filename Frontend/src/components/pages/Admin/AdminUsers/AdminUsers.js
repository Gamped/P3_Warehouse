import React,{Component} from 'react';
import "./AdminUsers.css";
import axios from 'axios';
import ReactTable from 'react-table';
import {Link} from "react-router-dom";

export default class AdminUsers extends Component {
    constructor(props) {
        super(props);

        this.state = {
            customers: [],
            selectedCustomer: [],
            selected: null,
            selectedId: ""
        };
    }

    componentDidMount() {

       this.getClients();
       this.getPublishers();      
    }

    getClients() {
        axios.get('http://localhost:8080/api/employee/clients')
        .then((response) => {

            const clients = this.makeCustomerData(response.data);
            this.concatinateWithNewData(clients);
        })
    }

    getPublishers() {
        axios.get('http://localhost:8080/api/employee/publishers')
        .then((response) => {

            const publishers = this.makeCustomerData(response.data);
            this.concatinateWithNewData(publishers);
    });
    }

    makeCustomerData(data){
        var customers = [];
        data.forEach((customer) => {
            
            customers.push({
                userName: customer.userName,
                password: customer.password,
                hexId: customer.hexId,
                nickName: customer.contactInformation.nickName,
                email: customer.contactInformation.email,
                phoneNumber: customer.contactInformation.phoneNumber,
                address: customer.contactInformation.address,
                zipCode: customer.contactInformation.zipCode              
            })
        });
        return customers;
    }

 
    concatinateWithNewData(newData) {
    
        const customersCopy = this.state.customers;
        let concatinatedData = customersCopy.concat(newData);
        this.setState({ customers: concatinatedData });
    }
    
    getColumns = () => {
    return [{
        Header: "Customer",
        accessor: "nickName"
        }]    
    }

    onChange = (e) => {
        const state = this.state.selectedCustomer;
        state[e.target.name] = e.target.value;
        this.setState({selectedCustomer:state});
    }

    onSubmit = () => {
    

    }
 
    onDelete = () => {

    }

    render(){

        let selectedCustomer = this.state.selectedCustomer;
        let columns = this.getColumns();
        return(
            <div className="PageStyle rounded">
                <div className="userPageStyle rounded">
                    <div className="container row">

                        <div className="SelectionBar col sidebar border border-dark rounded bg-secondary userNavbar">
                            <div className="border border-light rounded bg-info">
                            <ReactTable 
                            data={this.state.customers}
                            columns={columns} 
                            showPagination={false} 
                            className="-striped -highlight"getTrProps={(state, rowInfo) => {
                                if (rowInfo && rowInfo.row) {
                                  return {
                                    onClick: (e) => {
                                        this.setState({selected: rowInfo.index, 
                                            selectedId: rowInfo.original.hexId,
                                            selectedCustomer: this.state.customers[rowInfo.index] });
                                        
                                    },
                                    style: {
                                      background: rowInfo.index === this.state.selected ? '#00afec' : 'white',
                                      color: rowInfo.index === this.state.selected ? 'white' : 'black'
                                    }
                                  }
                                }else{
                                  return {}
                                }
                               }
                            }
                             />
                             </div>
                            
                           
                        </div>
                        
                        <div className="ContentBar  col-sm text-center">
                           <div className="container col">
                                <div className="container col">
                                    <div className="row">
                                        
                                        <div className="input-group mt-3 mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="nickName" 
                                                className="input-group-text" 
                                                id="nickNameLabel">
                                                    Nickname
                                                </label>
                                            </div>
                                                 <input
                                                 id="nickName" 
                                                 className="form-control" 
                                                 type="text"
                                                 defaultValue={selectedCustomer.nickName}
                                                 name="nickName"                                        
                                                 />
                                        </div>
                                        <div className="input-group mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="userNameInput" 
                                                className="input-group-text" 
                                                id="userNameLabel">
                                                    Username
                                                </label>
                                            </div>
                                            <input
                                            id="userNameInput" 
                                            className="form-control" 
                                            type="text"
                                            defaultValue={selectedCustomer.userName}
                                            name="userName"                                        
                                            />
                                        </div>
                                        <div className="input-group mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="email" 
                                                className="input-group-text" 
                                                id="emailLabel">
                                                    Email
                                                </label>
                                            </div>
                                            <input
                                            id="email" 
                                            className="form-control" 
                                            type="text"
                                            defaultValue={selectedCustomer.email}
                                            name="email"                                        
                                            />
                                        </div>
                                        <div className="input-group mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="phone" 
                                                className="input-group-text" 
                                                id="phoneLabel">
                                                    Phone Number
                                                </label>
                                            </div>
                                            <input
                                                id="phone" 
                                                 className="form-control" 
                                                 type="number"
                                                 defaultValue={selectedCustomer.phoneNumber}
                                                 name="phoneNumber"                                        
                                                 />
                                        </div>
                                        <div className="input-group mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="address" 
                                                className="input-group-text" 
                                                id="addressLabel">
                                                    Address
                                                </label>
                                            </div>
                                            <input
                                                id="address" 
                                                className="form-control" 
                                                type="text"
                                                defaultValue={selectedCustomer.address}
                                                name="address"                                        
                                                 />
                                        </div>
                                        <div className="input-group mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="city" 
                                                className="input-group-text" 
                                                id="cityLabel">
                                                    City
                                                </label>
                                            </div>
                                            <input
                                                id="city" 
                                                 className="form-control" 
                                                 type="text"
                                                 defaultValue={selectedCustomer.city}
                                                 name="city"                                        
                                                 />
                                        </div>
                                        <div className="input-group mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="zip" 
                                                className="input-group-text" 
                                                id="zipLabel">
                                                    Zipcode
                                                </label>
                                            </div>
                                            <input
                                                id="zip" 
                                                 className="form-control" 
                                                 type="number"
                                                 defaultValue={selectedCustomer.city}
                                                 name="zipCode"                                        
                                                 />
                                        </div>
                                        
                                         <h4 className="text-center">Change password:</h4>
                                        
                                        <div className="input-group mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="newPassword" 
                                                className="input-group-text" 
                                                id="passwordLabel">
                                                    New Password
                                                </label>
                                            </div>
                                            <input 
                                                id="newPassword"
                                                 className="form-control" 
                                                 type="password"
                                                 name="newPassword"                                        
                                                 />
                                        </div>
                                        <div className="input-group mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="confirmedPassword" 
                                                className="input-group-text" 
                                                id="confirmedLabel">
                                                    Repeat
                                                </label>
                                            </div>
                                                 <input
                                                 id="confirmedPassword" 
                                                 className="form-control" 
                                                 type="password"
                                                 placeholder="Retype new password"
                                                 name="confirmedNewPassword"                                        
                                                 />
                                        </div>
                                    </div>

                                </div>
                                <div className="container row">
                                    <div className="col my-2">
                                        <button type="button" onClick={this.onDelete} className="btn btn-danger">Delete this user</button>
                                    </div>
                                    <div className="col my-2">
                                        <button type="button" onClick={this.onSubmit} className="btn btn-warning">Confirm edit</button>
                                    </div>
                                    <div className="col my-2">
                                        <Link to="/Admin/Users/Create" className="btn btn-success">Create user</Link>
                                    </div>
                                </div>  
                           </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

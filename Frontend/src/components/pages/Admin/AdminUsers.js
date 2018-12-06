import React,{Component} from 'react';
import "./AdminUsers.css";
import "../Pages.css";
import axios from 'axios';
import ReactTable from 'react-table';

import TextBox from "../../MenuComponents/TextBox/TextBox";

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

                                         <div className="col-xs-6 form-group">
                                            <label>Nick Name</label>
                                                 <input 
                                                 className="form-control" 
                                                 type="text"
                                                 defaultValue={selectedCustomer.nickName}
                                                 name="nickName"                                        
                                                 />
                                        </div>

                                         <div className="col-xs-6 form-group">
                                            <label>User Name</label>
                                                 <input 
                                                 className="form-control" 
                                                 type="text"
                                                 defaultValue={selectedCustomer.userName}
                                                 name="userName"                                        
                                                 />
                                        </div>

                                         <div className="col-xs-6 form-group">
                                            <label>Email</label>
                                                 <input 
                                                 className="form-control" 
                                                 type="text"
                                                 defaultValue={selectedCustomer.email}
                                                 name="email"                                        
                                                 />
                                        </div>

                                         <div className="col-xs-6 form-group">
                                            <label>Phone number</label>
                                                 <input 
                                                 className="form-control" 
                                                 type="text"
                                                 defaultValue={selectedCustomer.phoneNumber}
                                                 name="phoneNumber"                                        
                                                 />
                                        </div>

                                         <div className="col-xs-6 form-group">
                                            <label>Address</label>
                                                 <input 
                                                 className="form-control" 
                                                 type="text"
                                                 defaultValue={selectedCustomer.address}
                                                 name="address"                                        
                                                 />
                                        </div>

                                         <div className="col-xs-6 form-group">
                                            <label>Zipcode</label>
                                                 <input 
                                                 className="form-control" 
                                                 type="text"
                                                 defaultValue={selectedCustomer.zipCode}
                                                 name="zipCode"                                        
                                                 />
                                        </div>

                                        <br></br>
                                        
                                         <label>Change password:</label><br></br>
                                           

                                        <div className="col-xs-6 form-group">
                                            <label>New password</label>
                                                 <input 
                                                 className="form-control" 
                                                 type="password"
                                                 placeholder="Minimum 6 characters"
                                                 name="newPassword"                                        
                                                 />
                                        </div>

                                        <div className="col-xs-6 form-group">
                                            <label>Confirm password</label>
                                                 <input 
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
                                </div>  
                           </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

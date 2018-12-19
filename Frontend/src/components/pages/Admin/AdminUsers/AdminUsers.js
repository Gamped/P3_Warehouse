import React,{Component} from 'react';
import "./AdminUsers.css";
import ReactTable from 'react-table';
import {Link} from "react-router-dom";
import {get, del, put} from "../../../../handlers/requestHandlers.js";
import {makeCustomerData} from "../../../../handlers/dataHandlers";

export default class AdminUsers extends Component {
    
    constructor(props) {
        super(props);

        this.state = {
            customers: [],
            selectedCustomer: [],
            selected: null,
            selectedId: null,
            changed:{}
        };
    }

    componentDidMount() {

       this.getClients();
       this.getPublishers();      
    }

    getClients() {

        get('employee/clients', (data) => {
            const clients = makeCustomerData(data);
            this.concatinateWithNewData(clients);
        });
    }

    getPublishers() {

       get('employee/publishers', (data) => {
            const publishers = makeCustomerData(data);
            this.concatinateWithNewData(publishers);
        });
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
            }, {Header: "User Type", accessor: "userType"}]    
    }

    onChange = (e) => {

        this.setState({...this.state,changed:{...this.state.changed,[e.target.name]:e.target.value}});
        console.log(this.state)
    }

    onSubmit = () => {

        if(this.state.changed.password===this.state.changed.confirmedNewPassword){
            const usertype= this.state.selectedCustomer.userType.toLowerCase();

            let newState = {};
                const changedState = {...this.state.changed}
                Object.keys(changedState).forEach((key,index)=>{
                    if(changedState[key] !=="" && changedState[key] !==null){
                        newState[key] = changedState[key]
                    }
                })
                newState={...this.state.selectedCustomer,...newState}
                const body = {
                    userName:newState.userName,
                    password:newState.password,
                    userType:newState.userType,
                    contactInformation:{
                        nickName:newState.nickName,
                        email:newState.email,
                        phoneNumber:newState.phoneNumber,
                        city: newState.city,
                        address: newState.address,
                        zipCode: newState.zipCode
                        }
                    }
                    console.log(body)

            if(usertype==="publisher"){
                put("publishers/"+this.state.selectedCustomer.hexId,body,(respondse)=>{
                    let customers = this.state.customers.filter(customer =>{
                        return this.state.selectedId !== customer.hexId
                    })
                    let selectCustomer = this.state.customers.filter(customer =>{
                        return this.state.selectedId === customer.hexId
                    })
                    selectCustomer[0]=body;
                    const finalCustomers = [...customers,...selectCustomer];
                    this.setState({
                        customers:finalCustomers
                        
                    })
                    this.props.history.push("/Admin/Users/Push")
                })
            } else if(usertype==="client") {
                put("clients/"+this.state.selectedCustomer.hexId,body,(respondse)=>{
                    let customers = this.state.customers.filter(customer =>{
                        return this.state.selectedId !== customer.hexId
                    })
                    let selectCustomer = this.state.customers.filter(customer =>{
                        return this.state.selectedId === customer.hexId
                    })
                    selectCustomer[0]=body;
                    const finalCustomers = [...customers,...selectCustomer];
                    this.setState({
                        customers:finalCustomers
                    })
                    this.props.history.push("/Admin/Users/Push")
                })
            } else {alert("Nothing chosen")}
        } else {alert("Passwords not the same. If you do not wish to change passwords, leave both fields empty.")}
    }
 
    onDelete = () => {
        
        const selectedId = this.state.selectedId;

        if (!selectedId) {
            window.alert("Select a customer before deleting");
        } else {
            const usertype= this.state.selectedCustomer.userType.toLowerCase();
            
            if (usertype === "publisher"){
            
                del("employee/publishers/delete/" + selectedId,(res)=>{
                let customers = this.state.customers.filter(customer =>{
                    return selectedId !== customer.hexId
                })
                this.setState({
                    customers:customers
                })
            });
        } else if(usertype==="client") {
            del("employee/clients/delete/" + selectedId,(res)=>{
                let customers = this.state.customers.filter(customer =>{
                    return selectedId !== customer.hexId
                })
                this.setState({
                    customers:customers
                })
            });
        } else { 
            window.alert("Nothing chosen")
        }
        }
        
    }

    render(){

        let selectedCustomer = this.state.selectedCustomer;
        let columns = this.getColumns();

        return(
            <div className="PageStyle customText_b">
                <div className="frameBordering userPageStyle">
                    <div className="container row">
                        <div className="SelectionBar col sidebar">
                            <div className="border border-light  bg-info">
                                <ReactTable 
                                data={this.state.customers}
                                columns={columns} 
                                showPagination={false} 
                                className="noBlueTable -striped -highlight" 
                                getTrProps={(state, rowInfo) => {
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

                                    <form onSubmit={this.onSubmit}>   
                                        <div className="input-group mt-3 mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="nickName" 
                                                    className="input-group-text" 
                                                    id="nickNameLabel">
                                                    Nickname
                                                </label>
                                            </div>
                                                 <input
                                                    onChange={this.onChange}
                                                    id="nickName" 
                                                    className="form-control" 
                                                    type="text"
                                                    defaultValue={selectedCustomer.nickName}
                                                    name="nickName"                                        
                                                    required/>
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
                                                onChange={this.onChange}
                                                id="userNameInput" 
                                                className="form-control" 
                                                type="text"
                                                defaultValue={selectedCustomer.userName}
                                                name="userName"                                        
                                                required/>
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
                                                onChange={this.onChange}
                                                id="email" 
                                                className="form-control" 
                                                type="email"
                                                defaultValue={selectedCustomer.email}
                                                name="email"                                        
                                                required />
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
                                                onChange={this.onChange}
                                                id="phone" 
                                                className="form-control" 
                                                type="number"
                                                defaultValue={selectedCustomer.phoneNumber}
                                                name="phoneNumber"                                        
                                                required/>
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
                                                onChange={this.onChange}
                                                id="address" 
                                                className="form-control" 
                                                type="text"
                                                defaultValue={selectedCustomer.address}
                                                name="address"                                        
                                                required/>
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
                                                onChange={this.onChange}
                                                id="city" 
                                                className="form-control" 
                                                type="text"
                                                defaultValue={selectedCustomer.city}
                                                name="city"                                        
                                                required/>
                                        </div>
                                        <div className="input-group mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="zipCode" 
                                                    className="input-group-text" 
                                                    id="zipLabel">
                                                    Zipcode
                                                </label>
                                            </div>
                                            <input
                                                onChange={this.onChange}
                                                id="zipCode" 
                                                className="form-control" 
                                                type="number"
                                                defaultValue={selectedCustomer.zipCode}
                                                name="zipCode"                                        
                                                required/>
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
                                                onChange={this.onChange}
                                                id="newPassword"
                                                className="form-control" 
                                                type="password"
                                                name="password"                                        
                                                />
                                        </div>
                                        <div className="input-group mb-2">
                                            <div className="input-group-prepend">
                                                <label htmlFor="confirmedPassword" 
                                                    className="input-group-text" 
                                                    id="confirmedLabel">
                                                    Repeat Password
                                                </label>
                                            </div>
                                                 <input
                                                    onChange={this.onChange}
                                                    id="confirmedPassword" 
                                                    className="form-control" 
                                                    type="password"
                                                    placeholder="Retype new password"
                                                    name="confirmedNewPassword"                                        
                                                />
                                                
                                        </div>
                                        <div className="container row">
                                    <div className="col my-2">
                                        <Link to="/Admin/Users/Create" className="btn adminUserBtn green_BTN">Create new user</Link>
                                    </div>
                                    <div className="col my-2">

                                        <button type="submit" className="btn adminUserBtn yellow_BTN">Confirm edit</button>
                                    </div>
                                    <div className="col my-2">
                                        <button type="button" onClick={this.onDelete} className="btn adminUserBtn red_BTN">Delete this user</button>
                                    </div>
                                   
                                </div>
                                        </form>
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

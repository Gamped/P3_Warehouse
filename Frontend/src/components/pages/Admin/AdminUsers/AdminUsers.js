import React,{Component} from 'react';
import "./AdminUsers.css";
import ReactTable from 'react-table';
import {Link, Redirect} from "react-router-dom";
import {get, del, put} from "../../../../handlers/requestHandlers.js";
import {makeCustomerData} from "../../../../handlers/dataHandlers";
import {connect} from "react-redux";
import {firestoreConnect} from "react-redux-firebase";
import {compose} from "redux";

class AdminUsers extends Component {
    
    constructor(props) {
        super(props);

        this.state = {
            rawData:this.props.data,
            customers: [],
            selectedCustomer: [],
            selected: null,
            selectedId: null,
            changed:{}
        };
    }

    componentDidMount=()=>{
        this.setState({customers:this.makeData(this.props.data)})
    }

    makeData(data){

        console.log("Data",data)

        if(!(data === undefined||data===null)){

            let customers = [];
            data.forEach(customer=>{
                console.log("Customer",customer)
                if(customer.userType !== "employee"){
                    let userType = customer.userType.charAt(0).toUpperCase() + customer.userType.slice(1);
                    
                    //TODO indsæt at man får fat i customers for publishers.

                    customers.push({
                        userType: customer.userType,
                        password: customer.password,
                        id: customer.id,
                        name: customer.name,
                        email: customer.contactInformation.email,
                        phoneNumber: customer.contactInformation.phoneNumber,
                        address: customer.contactInformation.address,
                        zipCode: customer.contactInformation.zipCode,
                        city: customer.contactInformation.city              
                    })
                }
            })
            return(customers) 
        }else{
            return undefined;
        }
    }
    
    getColumns = () => {

        return [{
            Header: "Customer",
            accessor: "name"
            }, {Header: "User Type", accessor: "userType"}]    
    }

    onChange = (e) => {

        this.setState({...this.state,changed:{...this.state.changed,[e.target.name]:e.target.value}});
    }

    onSubmit = () => {

        //TODO: Lav update af email https://firebase.google.com/docs/reference/js/firebase.User#updateEmail

        /*

        */

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

        console.log("Firestore",this.props.data)
        
        const customers = this.makeData(this.props.data);

        if(!this.props.auth.uid){
            return <Redirect to="/"/>
        }

        let selectedCustomer = this.state.selectedCustomer;
        let columns = this.getColumns();

        return(
            <div className="PageStyle customText_b">
                <div className="frameBordering userPageStyle">
                    <div className="container row">
                        <div className="SelectionBar col sidebar">
                            <div className="border border-light  bg-info">
                                <ReactTable 
                                data={customers}
                                columns={columns} 
                                showPagination={false} 
                                className="noBlueTable -striped -highlight" 
                                getTrProps={(state, rowInfo) => {
                                    if (rowInfo && rowInfo.row) {
                                    return {
                                        onClick: (e) => {
                                            this.setState({selected: rowInfo.index, 
                                                selectedId: rowInfo.original.id,
                                                selectedCustomer: customers[rowInfo.index] });
                                            
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
                                                    defaultValue={selectedCustomer.name}
                                                    name="nickName"                                        
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
                                        
                                        <div className="col my-2">
                                            <button type="button" onClick={()=>{}} className="btn rounded btn-block btn-warning">Send password reset to email</button>
                                        </div>
                                        
                                        <div className="container row">
                                            <div className="col my-2">
                                                <Link to="/Admin/Users/Create" type="button" className="btn btn-block btn-success rounded">Create new user</Link>
                                            </div>
                                            <div className="col my-2">
                                                <button type="submit" className="btn btn-block btn-warning">Confirm edit</button>
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

const mapStateToProps = (state) =>{
    console.log(state)
    return{
        auth: state.firebase.auth,
        data: state.firestore.ordered.users
    }
}

export default compose(
    connect(mapStateToProps),
    firestoreConnect([
        {collection: "users"}
    ])
)(AdminUsers)
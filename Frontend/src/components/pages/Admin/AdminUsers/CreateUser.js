import React from "react";
import {Link} from "react-router-dom";
import {get, post} from "../../../../handlers/requestHandlers";
import Dropdown from "../../../MenuComponents/Dropdown/Dropdown";
import {makeCustomerData} from './../../../../handlers/dataHandlers.js';
import {customerProfileFieldsAreValidated} from './../../../../handlers/fieldsValidator';
import { publisherNotSetOnClientProfileCreationWarning } from "../../../../handlers/exceptions";

class CreateUser extends React.Component{
    constructor(props){
        super(props);
        this.state={
            userType:"CLIENT",
            userName:"",
            password:"",
            passwordRepeat:"",
            //ContactInformation
                email: "",
                phoneNumber: "",
                nickName: "",
                address: "",
                city: "",
                zip: "",
            
            publishers: [],
            selectedActorHexId: "DEFAULT",
            selectedActorUserType: "DEFAULT"
        }
    }

    componentDidMount(){
        
        this.getPublishers();
    }

    getPublishers() {
        
        get('employee/publishers', (data) => {
            let publishers = makeCustomerData(data);
            publishers.push({nickName:"Independent client",userType:"Create",hexId:"IC"})
            this.setState({ publishers: publishers});
       });
    }

    onChange = (e) => {
        this.setState({[e.target.name]:e.target.value});
        
    }


    showPublisherDropdown(flag){

        this.setState({
            publisherTableShows: flag
        })
    }

    toggleUserType = () =>{
        if(this.state.userType === "CLIENT"){
            this.setState({userType:"PUBLISHER"},()=>{console.log(this.state)})
        }else{
            this.setState({userType:"CLIENT"},()=>{console.log(this.state)})
        }
    }

    onSubmit=(e)=>{
        e.preventDefault();
       
        const fields = this.state;
        if (customerProfileFieldsAreValidated(fields)) {
        const body = {
                    userName:this.state.userName,
                    password:this.state.password,
                    userType:this.state.userType,
                    contactInformation:{
                        nickName:this.state.nickName,
                        email:this.state.email,
                        phoneNumber:this.state.phoneNumber,
                        address: this.state.address,
                        city: this.state.city,
                        zipCode:this.state.zip
                        }
                    }
                   
            if(this.state.userType==="CLIENT"){
                if(this.state.selectedActorHexId === "IC"){

                }else{
                    
                }
                post("employee/clients", body, (response)=>{
                    this.props.history.push("/Admin/Users/")
                    });
            } else {
                post("employee/publishers", body, (response)=>{
                    this.props.history.push("/Admin/Users/")
                    });
            }
        
    }
}


    setSelected = (e) =>{
        if (e.target.value.toLowerCase() !== 'choose customer') {
            
            this.setState({
                selectedActorHexId:e.target.value,
                selectedActorUserType:this.state.publishers.find(x=>x.hexId===e.target.value).userType.toUpperCase()
            },()=>{console.log(this.state)})
    
        } else {
            publisherNotSetOnClientProfileCreationWarning();
        }
    }

    render(){
        return(
            <div className="PageStyle customText_b">
                <h1 className="customText_b_big text-center">Create a new user</h1>
                <div className="col-md-4 offset-md-4">
                    <form onSubmit={this.onSubmit}>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="login">Login:</label>
                            </div>
                            <input type="text" className="form-control" id="login" name="userName" placeholder="Username for Login" onChange={this.onChange} required autoFocus/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="password">Password:</label>
                            </div>
                            <input type="text" className="form-control" id="password" name="password" placeholder="Minimum 6 characters" onChange={this.onChange} required autoFocus/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="passwordRepeat">Repeat Password:</label>
                            </div>
                            <input type="text" className="form-control" id="passwordRepeat" name="passwordRepeat" placeholder="Repeat typed password" onChange={this.onChange} required autoFocus/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="nickName">Name:</label>
                            </div>
                            <input type="text" className="form-control" id="nickName" name="nickName" placeholder="Name of the company" onChange={this.onChange} required/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="email">Email:</label>
                            </div>
                            <input type="email" className="form-control" id="email" name="email" placeholder="Fx. example@example.com" onChange={this.onChange} required/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="phone">Phone number:</label>
                            </div>
                            <input type="number" className="form-control" id="phone" name="phoneNumber" placeholder="12345678" onChange={this.onChange} required/>
                        </div>


                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="address">Address:</label>
                            </div>
                            <input type="text" className="form-control" id="address" name="address" placeholder="Fx. Industrivej 2" onChange={this.onChange} required/>
                        </div>

                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="city">City:</label>
                            </div>
                            <input type="text" className="form-control" id="city" name="city" placeholder="Fx. Aalborg" onChange={this.onChange} required/>
                        </div>

                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="zipCode">Zipcode:</label>
                            </div>
                            <input type="text" className="form-control" id="zipCode" name="zipCode" placeholder="Fx. 9000" onChange={this.onChange} required/>
                        </div>

                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="publisher?">Publisher?:</label>
                            </div>
                            <input type="checkbox" className="form-control"  onChange={this.toggleUserType}/>
                        </div>

                        <label className="input-group-text" htmlFor="dropdown">If Client, please choose publisher:</label>
                        <Dropdown className="form-control" actors = {this.state.publishers} action={this.setSelected} id="dropdown"/>

                             
                        <div className="row">
                            <div className="col my-3 mx-4">

                                <button className="btn btn-success btn-block" type="submit">Create User</button>

                            </div>
                            <div className="col my-3 mx-4">
                                <Link to="/Admin/Users/" className="btn adminUserBtn std_BTN btn-block">Go Back</Link>
                            </div>
                        </div>
                    </form>
                   
                </div>
            </div>
        )
    }
}
export default CreateUser
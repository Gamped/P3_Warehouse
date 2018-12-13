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
            publisherTableShows:false,
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
            const publishers = makeCustomerData(data);
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

    onSubmit=(e)=>{
        e.preventDefault();
       
        console.log(this.state.email)
        const fields = this.state;
        console.log("Her")
        if (customerProfileFieldsAreValidated(fields)) {
        console.log("I iffen")
        const body = {
                    userName:this.state.userName,
                    password:this.state.password,
                    userType:this.state.userType,
                    contactInformation:{
                        nickName:this.state.nickName,
                        email:this.state.email,
                        phoneNumber:this.state.phoneNumber
                        }
                    }
                   
            if(this.state.publisherTableShows===true){
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
            })
    
        } else {
            publisherNotSetOnClientProfileCreationWarning();
        }
            console.log(this.state.selectedActorHexId);
    }

    render(){
        return(
            <div className="PageStyle customText_b">
                <h1 className="customText_b_big text-center">Create a new user</h1>
                <div className="col-md-4 offset-md-4">
                    <form>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="login">Login:</label>
                            </div>
                            <input type="text" className="form-control" id="login" name="userName" placeholder="The username the company logs in with" onChange={this.onChange} required autoFocus/>
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
                                <label className="input-group-text" htmlFor="nickName">Address:</label>
                            </div>
                            <input type="text" className="form-control" id="address" name="address" placeholder="Fx. Industrivej 2 (Optional)" onChange={this.onChange}/>
                        </div>

                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="nickName">City:</label>
                            </div>
                            <input type="text" className="form-control" id="city" name="city" placeholder="Fx. Aalborg (Optional)" onChange={this.onChange}/>
                        </div>

                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="nickName">Zipcode:</label>
                            </div>
                            <input type="text" className="form-control" id="zipCode" name="zipCode" placeholder="Fx. 9000 (Optional)" onChange={this.onChange}/>
                        </div>


                             <button type="button" className="btn btn-success" data-toggle="button" aria-pressed="false" autocomplete="off" onClick ={()=> this.showPublisherDropdown(false)} >Make Publisher</button> 
                             <button type="button" className="btn btn-success" data-toggle="button" aria-pressed="false" autocomplete="off" onClick ={()=> this.showPublisherDropdown(true)}>Client</button>

                        {    
                            this.state.publisherTableShows?
                            <div>
                                <Dropdown actors = {this.state.publishers} action={this.setSelected}/>
                            </div>
                            :null
                        }
                        <div className="row">
                            <div className="col my-3 mx-4">

                                <button className="btn btn-success btn-block" onClick={this.onSubmit}>Create User</button>

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
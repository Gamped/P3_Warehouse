import React from "react";
import {Link} from "react-router-dom";
import {get, post} from "../../../../handlers/requestHandlers";
import Dropdown from "../../../MenuComponents/Dropdown/Dropdown";
import {makeCustomerData} from './../../../../handlers/dataHandlers.js';

class CreateUser extends React.Component{
    constructor(props){
        super(props);
        this.state={
            ShowMe:false,
            userType:"CLIENT",
            userName:"",
            password:"",
            repeatPass:"",
            contactInformation:{
                email: "",
                phoneNumber: "",
                nickName: ""
            },
            publishers: [],
            selectedActorHexId: "DEFAULT",
            selectedActorUserType: "DEFAULT"
        }
    }

    componentDidMount(){
        get('employee/publishers', (data) => {
            const publishers = makeCustomerData(data);
            this.setState({ publishers: publishers});
       });
    }

    onChange = (e) => {
        this.setState({[e.target.name]:e.target.value});
    }
    
    showclient(boolean){
        this.setState({
            ShowMe:boolean
        })
    }

    submit=(e)=>{
        e.preventDefault();
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
        if(this.state.password===this.state.repeatPass){
            if(this.state.ShowMe===true){
                post("employee/clients", body, (response)=>{
                    this.props.history.push("/Admin/Users/")
                    });
            }else{
                post("employee/publishers", body, (response)=>{
                    this.props.history.push("/Admin/Users/")
                    });
            }
        }else{
            alert("Password does not match")
        }
        
        console.log(body)
        
    }

    setSelected = (e) =>{
        this.setState({
            selectedActorHexId:e.target.value,
            selectedActorUserType:this.state.Actors.find(x=>x.hexId===e.target.value).userType.toUpperCase()
        })
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <div className="col-md-4 offset-md-4">
                    <h1 className="text-center">Create a user</h1>
                    <form>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="login">Login:</label>
                            </div>
                            <input type="text" className="form-control" id="login" name="userName" onChange={this.onChange} required autoFocus/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="password">Password:</label>
                            </div>
                            <input type="text" className="form-control" id="password" name="password" onChange={this.onChange} required autoFocus/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="repeatPass">Repeat Password:</label>
                            </div>
                            <input type="text" className="form-control" id="repeatPass" name="repeatPass" onChange={this.onChange} required autoFocus/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="nickName">Name:</label>
                            </div>
                            <input type="text" className="form-control" id="nickName" name="nickName" onChange={this.onChange} required/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="email">Email:</label>
                            </div>
                            <input type="email" className="form-control" id="email" name="email" onChange={this.onChange} required/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="phone">Phonenumber:</label>
                            </div>
                            <input type="number" className="form-control" id="phone" name="phoneNumber" onChange={this.onChange} required/>
                        </div>
                             <button type="button" class="btn btn-success" data-toggle="button" aria-pressed="false" autocomplete="off" onClick ={()=> this.showclient(false)} >Make Publisher</button> 
                             <button type="button" class="btn btn-success" data-toggle="button" aria-pressed="false" autocomplete="off" onClick ={()=> this.showclient(true)}>Client</button>
                        {    
                            this.state.ShowMe?
                            <div>
                                <Dropdown actors = {this.state.publishers} action={this.setSelected}/>
                            </div>
                            :null
                        }
                        <div className="row">
                            <div className="col my-3 mx-4">
                                <button className="btn btn-success btn-block" onClick={this.submit}>Create User</button>
                            </div>
                            <div className="col my-3 mx-4">
                                <Link to="/Admin/Users/" className="btn btn-danger btn-block">Go Back</Link>
                            </div>
                        </div>
                    </form>
                   
                </div>
            </div>
        )
    }
}
export default CreateUser
import React from "react";
import {Link} from "react-router-dom";
import {post} from "../../../../handlers/requestHandlers";

class CreateUser extends React.Component{
    constructor(props){
        super(props);
        this.state={
            userType:"CLIENT",
            login:"",
        }
    }

    toggleUserType=()=>{
        if(this.state.userType==="CLIENT"){
            this.setState({userType:"PUBLISHER"})
        }else{
            this.setState({userType:"CLIENT"})
        }
        console.log(this.state.userType)
    }

    onChange = (e) => {
        this.setState({[e.target.name]:e.target.value});
    }

    submit=(e)=>{
        e.preventDefault();
        const body = {...this.state};
        if(this.state.password===this.state.repeatPass){
            if(this.state.userType==="CLIENT"){
                post("employee/clients/", {body}, (response)=>{
                    this.props.history.push("/Admin/Users/")
                    });
            }else{
                post("employee/publishers/", {body}, (response)=>{
                    this.props.history.push("/Admin/Users/")
                    });
            }
        }else{
            alert("Password does not match")
        }
        
        console.log(body)
        
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
                            <input type="text" className="form-control" id="login" name="login" onChange={this.onChange} required autoFocus/>
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
                                <label className="input-group-text" htmlFor="name">Name:</label>
                            </div>
                            <input type="text" className="form-control" id="name" name="name" onChange={this.onChange} required/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="email">Email:</label>
                            </div>
                            <input type="email" className="form-control" id="email" name="email" onChange={this.onChange} required/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="contact">Contact person:</label>
                            </div>
                            <input type="text" className="form-control" id="contact" name="contact" onChange={this.onChange} required/>
                        </div>
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="phone">Phonenumber:</label>
                            </div>
                            <input type="number" className="form-control" id="phone" name="phone" onChange={this.onChange} required/>
                        </div>        
                        <div className="input-group my-3">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="publisher">Publisher:</label>
                            </div>
                            <input type="checkbox" className="form-control" id="publisher" name="publisher" onChange={this.toggleUserType}/>
                        </div>
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
import React from 'react';
import "../../Pages.css";
import "./AdminProfile.css";

export default class AdminRemove extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            userName: "",
            password: "",
        };
    }

    /*
    * SOME FUNCTION TO RETRIEVE & SEND INFO FROM DB
    */

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
        return(
        <div className="PageStyle rounded">
            <h1 className="text-center">Remove employee:</h1>
                <form>
                    <input 
                        type="text" 
                        className="my-2 form-control" 
                        onChange={this.handleUName}
                        placeholder="Username of Employee"/>
                    <input 
                        type="password" 
                        className="my-2 form-control" 
                        onChange={this.handlePass}
                        placeholder="YOUR password"/>
                </form>
                <form action="/Admin/Profile" className="newForm stockForm">
                    <button className="btn-lg btn-danger btn-block my-2 btn">REMOVE employee</button>
                </form>
                <form action="/Admin/Profile" className="newForm stockForm">
                    <button className="btn-info btn-lg btn-block btn my-2">Back</button>
                </form>
        </div>);
    }
}
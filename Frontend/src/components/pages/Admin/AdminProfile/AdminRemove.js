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
        <div className="PageStyle">
            <h1 className="title customText_b_big">Remove employee:</h1>
                <form>
                    <input 
                        type="text" 
                        className="newForm" 
                        onChange={this.handleUName}
                        placeholder="Username of Employee"/>
                    <input 
                        type="password" 
                        className="newForm" 
                        onChange={this.handlePass}
                        placeholder="YOUR password"/>
                </form>
                <form action="/Admin/Profile" className="newForm stockForm">
                    <button className="btn-lg btn-success btn-block my-2 btn">Back</button>
                </form>
                <form action="/Admin/Profile" className="newForm stockForm">
                    <button className="btn-lg btn-danger btn-block my-2 btn">REMOVE employee</button>
                </form>
        </div>);
    }
}
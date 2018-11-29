import React from 'react';
import "./Header.css";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";

// The header component
class Header extends React.Component {
 
    exitHandler = (event) =>{
        event.preventDefault();
        this.props.history.push("../../..")

    }

    render(){
        const title= "4N: " + this.props.title
        return(
            <div className="headerStyle">
                <h1 className="headerText">{title}</h1>
                <form className="float-right">
                <button onClick={this.exitHandler} className="float-right btn btn-sm btn-dark">Log Out</button>
                </form>
            </div>
        );
    }
}

const mapStateToProps = (state)=>{
    return{
        user: state.user
    }
}

const mapDispatchToProps = (dispatch) =>{
    return {
        login: (user) => {dispatch({type: "LOGOUT"})}
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Header));
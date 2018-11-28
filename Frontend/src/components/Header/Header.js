import React from 'react';
import "./Header.css";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";

// The header component
class Header extends React.Component {
 
    exitHandler = (e) =>{
        this.props.history.push("/")

    }

    render(){
        const title= "4N: " + this.props.title
        return(
            <div className="headerStyle">
                <h1 className="headerText">{title}</h1>
                <img  src={require("../../resources/exit.png")} className="icon img-thumbnail rounded float-right" alt="Exit Icon" onClick={this.exitHandler}/>
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

export default connect(mapStateToProps ,mapDispatchToProps)(withRouter(Header))
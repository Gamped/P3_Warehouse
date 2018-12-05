import React from 'react';
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import ButtonList from "../MenuComponents/ButtonList/ButtonList"

// The header component
class Header extends React.Component {

    exitHandler = (event) =>{
        event.preventDefault();
        this.props.history.replace("/")
    }

    render(){
        let buttons=[]
        const user = this.props.userType;
        if(user==="EMPLOYEE"){
            buttons= [
                {name: "Home",location: "/Admin", id:"1"},
                {name: "Orders",location:"/Admin/Orders", id:"2"},
                {name: "Users",location:"/Admin/Users",id:"3"},
                {name: "Stock",location:"/Admin/Stock",id:"4"},
                {name: "Profile",location:"/Admin/Profile",id:"5"}
            ]
        }else if(user==="CLIENT"){
            buttons=[
                {name: "Home",location: "/User", id:"1"},
                {name: "Order",location:"/User/Order", id:"2"},
                {name: "Stock",location:"/User/Stock",id:"3"},
                {name: "Profile",location:"/User/Profile",id:"4"},
            ]
        }else if(user==="PUBLISHER"){
            buttons=[
                {name: "Home",location: "/User", id:"1"},
                {name: "Order",location:"/User/Order", id:"2"},
                {name: "Stock",location:"/User/Stock",id:"3"},
                {name: "Profile",location:"/User/Profile",id:"4"},
                {name: "Clients",location:"/User/Clients",id:"5"}
            ]
        }else{
            this.props.history.push("/")
        }

        const title= "4N: " + this.props.title
        return(
            <div>

            <nav className="navbar bg-dark navbar-dark" style={{height: "100vh"}} >
            <div className="sidebar-sticky">
                <ButtonList buttons={buttons} link={true}/>
                <img 
                    src={require('../../resources/4n_logo_mini.jpg')} 
                        className="navbar-icon justify-content-end" 
                        alt="The logo of 4N" />
            </div>
        </nav>

                <nav className="flex-row navbar navbar-expand navbar-dark bg-dark fixed-top">
                    <div className="container">  
                        <h5 className="navbar-text justify-content-start">{title}</h5>
                        <form className="float-right justify-content-end">
                        <button onClick={this.exitHandler} className="float-right btn btn-sm btn-secondary">Log Out</button>
                        </form>
                    </div>
                </nav>
            </div>
        );
    }
}

const mapStateToProps = (state)=>{
    return{
        userType: state.loginReducer.userType
    }
}

const mapDispatchToProps = (dispatch) =>{
    return {
        login: (user) => {dispatch({type: "LOGOUT",})}
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Header));
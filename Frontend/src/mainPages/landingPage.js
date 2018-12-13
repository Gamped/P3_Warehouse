import React from 'react';
import LandingPage from "../components/MenuComponents/LandingPage/LandingPage";
import { connect } from "react-redux";

class Home extends React.Component{
    render(){
        console.log(this.props)
        
        let landingPageButtons = []
        
        if(this.props.userType==="CLIENT"){
            landingPageButtons = [
                {name:"Order",location:"./User/Order",id:"1"},
                {name:"Stock",location:"./User/Stock",id:"2"},
                {name:"Profile",location:"./User/Profile",id:"3"}
            ]
        } else if(this.props.userType === "PUBLISHER"){
            landingPageButtons = [
                {name:"Order",location:"./User/Order",id:"1"},
                {name:"Stock",location:"./User/Stock",id:"2"},
                {name:"Profile",location:"./User/Profile",id:"3"},
                {name:"Clients",location:"./User/Clients",id:"4"},
            ]
        } else if(this.props.userType === "EMPLOYEE"){
            landingPageButtons = [
                {name:"Orders",location:"./Admin/Orders",id:"1"},
                {name:"Users",location:"./Admin/Users",id:"2"},
                {name:"Stock",location:"./Admin/Stock",id:"3"},
                {name:"Profile",location:"./Admin/Profile",id:"4"}
        ]}
    
        return ( 
            <div className="PageStyle">
                <LandingPage buttons={landingPageButtons} name={this.props.name}/>
            </div>
        )
    }
}

const mapStateToProps = (state) =>{
    return {
        name: state.loginReducer.userName,
        userType: state.loginReducer.userType
    }
}
 
export default connect(mapStateToProps)(Home);
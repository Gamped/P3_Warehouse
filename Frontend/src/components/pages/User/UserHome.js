import React from 'react';
import LandingPage from "../../MenuComponents/LandingPage/LandingPage";
import "../Pages.css";
import { connect } from "react-redux";

class UserHome extends React.Component{

    render(){

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
        }

        return ( 
            <div className="PageStyle rounded">
                <LandingPage buttons={landingPageButtons} name={this.props.name}/>
            </div>
        )
    }
}

const mapStateToProps = (state) =>{
    return {
        name: state.loginReducer.nickName,
        userType: state.loginReducer.userType
    }
}
 
export default connect(mapStateToProps)(UserHome);
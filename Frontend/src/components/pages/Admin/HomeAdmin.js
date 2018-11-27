import React from 'react';
import LandingPage from "../../MenuComponents/LandingPage/LandingPage";
import "../Pages.css";
import { connect } from "react-redux";

class HomeAdmin extends React.Component{
    constructor(props){
        super(props);
        
    }
    
    render(){
        var landingPageButtons = [
            {name:"Orders",location:"./Admin/Orders",id:"1"},
            {name:"Users",location:"./Admin/Users",id:"2"},
            {name:"Stock",location:"./Admin/Stock",id:"3"},
            {name:"Profile",location:"./Admin/Profile",id:"4"}
        ]
    
        return ( 
            <div className="PageStyle">
                <LandingPage buttons={landingPageButtons} name={this.props.nickName}/>
            </div>
        )
    }
}

const mapStateToProps = (state) =>{
    return {
        nickName: state.name
    }
}
 
export default connect(mapStateToProps)(HomeAdmin);
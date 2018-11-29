import React from 'react';
import "./Header.css";

// The header component
<<<<<<< HEAD
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
=======
const Header = (props) => {
    const title= "4N: " + props.title
    return(
        <div className="headerStyle">
            <h1 className="headerText">{title}</h1>
        </div>
    );
>>>>>>> parent of 255b879... User is able to log on
}

export default Header;
import React from 'react';
import "../../Pages.css";
import "./UserStock.css"

export default class UserStock extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",
        };
    }

    //TODO: INDSÆT REACT TABLE!

    render(){
        return(
            <div className="PageStyle rounded">
                <navbar className="navbar navbar-secondary bg-secondary"><h2>Your Stock</h2></navbar>

             
            </div>
        );
    }
}

const mapStateToProps = (state) =>{
    return{
        userId: state.loginReducer.userId
    }
}
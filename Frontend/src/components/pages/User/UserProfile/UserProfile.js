import React from 'react';
import "../../Pages.css";
import { connect } from "react-redux";

class UserProfile extends React.Component{
    constructor(props){
        super(props);
        this.state= {
            userName: "No username set",
            name: "No name set",
            email: "No@email.set",
            phoneNumber: "No phone number set",
            address: "No adress set",
            cvr: "No CVR set",
            city: "No City set",
            zip: "No zip set",
            country: "No country set"
        }
    }
  

    //TODO: Brug axios til at få fat på en bruger. Bruger Id hedder this.props.userID
    render(){
        return(
            <div className="PageStyle rounded">
                <h1 className="text-center">Profile information</h1>
                <div className="row">
                    <div className="Container col-md-4 offset-md-4">
                        <h5 className="text-justify">Username: {this.state.userName}</h5>
                        <h5 className="text-justify">Name: {this.state.name}</h5>
                        <h5 className="text-justify">Email: {this.state.email}</h5>
                        <h5 className="text-justify">Phone number: {this.state.phoneNumber}</h5>
                        <h5 className="text-justify">Address: {this.state.address}</h5>
                        <h5 className="text-justify">City: {this.state.city}</h5>
                        <h5 className="text-justify">Zip: {this.state.zip}</h5>
                        <h5 className="text-justify">Country: {this.state.country}</h5>
                        <h5 className="text-justify">CVR: {this.state.cvr}</h5>

                        <form action="/User/Profile/Edit">
                            <button className="btn-lg btn-block btn-warning my-2" >Edit</button>
                        </form>
                        <form action="/User/Profile/OrderHistory">
                            <button className="btn-lg btn-block my-2" >Order histroy</button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}
 
const mapStateToProps = (state) => {
    return {
        userID: state.loginReducer.userId
    }
}

export default connect(mapStateToProps)(UserProfile)
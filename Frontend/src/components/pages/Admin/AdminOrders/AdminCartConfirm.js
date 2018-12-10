import React from 'react';
import "../../Pages.css";
import { connect } from "react-redux"
import {Link} from "react-router-dom";

 class AdminCartConfirm extends React.Component {
     //This is the constructur for our class, this is where we predifine this.state
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",
            products: props.productList,
        };
    }

    //On confirm the adress is sent to the server.
    componentDidMount() {

        console.log(this.props.order)
    }

    confirmed = (event) => {
        alert("Your order has been confirmed.")
        //TODO: Sent this.props.adress to server. 
    }
 

    render(){
        //Some imidiate logic before rendering. 
        //Store Redux state adress and the redux order in a varibale. 
        //Next we go through through the orders and assign them some code our html can understand
        let lines = this.props.order
        console.log(lines)
        lines = lines.map((line)=>{return(
                <tr key={line.productId}>
                    <th scope="row">{line.productId}</th>
                    <td>{line.productName}</td>
                    <td>{line.amount}</td>
                </tr>
            )})
        //This is what is actually rendered for the user
        return(
        <div className="PageStyle rounded">
            <nav className="navbar navbar-dark bg-secondary"> <h2 className="text-center text-light">Cart:</h2></nav>
            <div className="container">
                <div className="row">
                    <div className="col">
                        <div className="container my-3">
                            <table className="table table-dark">
                                <thead>
                                    <tr key="header">
                                        <th scope="col">Product ID</th>
                                        <th scope="col">Product name</th>
                                        <th scope="col">Amount</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {lines}                                 
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div className="col">
                        <h4 className="display-4">Please verify:</h4>
                        <br/>
                        <br/>
                        <label className="font-weight-bold">Company name: </label>
                        <label className="font-weight-normal">{this.props.adress.company.company}</label>
                        <br/>
                        <label className="font-weight-bold">Recipient: </label>
                        <label className="font-weight-normal">{this.props.adress.contactPerson.contactPerson}</label>
                        <br/>
                        <label className="font-weight-bold">Phone: </label>
                        <label className="font-weight-normal">{this.props.adress.phoneNumber.phoneNumber}</label>
                        <br/>
                        <label className="font-weight-bold">CVR: </label>
                        <label className="font-weight-normal">{this.props.adress.cvr.cvr}</label>
                        <br/>
                        <label className="font-weight-bold">Address: </label>
                        <label className="font-weight-normal">{this.props.adress.address.address}</label>
                        <br/>
                        <label className="font-weight-bold">Zip: </label>
                        <label className="font-weight-normal">{this.props.adress.zip.zip}</label>
                        <br/>
                        <label className="font-weight-bold">City</label>
                        <label className="font-wight-normal">{this.props.adress.city.city}</label>
                        <br/>
                        <label className="font-weight-bold">Country: </label>
                        <label className="font-weight-normal">{this.props.adress.country.country}</label>
                        
                        <Link to="/Admin/Orders" className="btn-success btn-block my-3 btn" role="button">Confirm order</Link>
                        <Link to="/Admin/Order/Cart" className="btn-info btn btn-block my-3" role="button">Back</Link>
                        
                    </div>
                    
                </div>
            </div>        
        </div>
        );
    }
}

//Here we map the redux state to this.props
const mapStateToProps = (state)=>{
    return{
        order: state.orderReducer.order,
        adress: state.adressReducer
    }
}

//Export the class as a higher order component.
export default connect(mapStateToProps)(AdminCartConfirm);

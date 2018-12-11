import React from 'react';
import "../../Pages.css";
import "./UserOrder.css";
import "./UserCart.css";
import { connect } from "react-redux"
import {Link} from "react-router-dom";

 class UserCartConfirm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",
            products: props.productList,
        };
    }

    confirmed = (event) => {
        alert("Your order has been confirmed.")
        //TODO: Sent this.props.adress to server. 
    }
 

    render(){
        const company = this.props.adress
        console.log(company)
        let lines = this.props.order
        lines = lines.map((line)=>{return(
                <tr key={line.productId}>
                    <th scope="row">{line.productId}</th>
                    <td>{line.productName}</td>
                    <td>{line.amount}</td>
                </tr>
            )})
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
                        <label className="font-weight-normal">{this.props.adress.company}</label>
                        <br/>
                        <label className="font-weight-bold">Recipient: </label>
                        <label className="font-weight-normal">{this.props.adress.contactPerson}</label>
                        <br/>
                        <label className="font-weight-bold">Phone: </label>
                        <label className="font-weight-normal">{this.props.adress.phoneNumber}</label>
                        <br/>
                        <label className="font-weight-bold">CVR: </label>
                        <label className="font-weight-normal">{this.props.adress.cvr}</label>
                        <br/>
                        <label className="font-weight-bold">Address: </label>
                        <label className="font-weight-normal">{this.props.adress.address}</label>
                        <br/>
                        <label className="font-weight-bold">Zip: </label>
                        <label className="font-weight-normal">{this.props.adress.zip}</label>
                        <br/>
                        <label className="font-weight-bold">City</label>
                        <label className="font-wight-normal">{this.props.adress.city}</label>
                        <br/>
                        <label className="font-weight-bold">Country: </label>
                        <label className="font-weight-normal">{this.props.adress.country}</label>
                        
                        <Link to="/User/Order" className="btn-success btn-block my-3 btn" role="button">Confirm order</Link>
                       
                        <Link to="/User/Order/Cart" className="btn-info btn btn-block my-3" role="button">Back</Link>
                        
                    </div>
                    
                </div>
            </div>        
        </div>
        );
    }
}

const mapStateToProps = (state)=>{
    return{
        order: state.orderReducer.order,
        adress: state.adressReducer
    }
}

export default connect(mapStateToProps)(UserCartConfirm);
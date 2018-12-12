import React from 'react';
import "../../Pages.css";
import "./UserOrder.css";
import "./UserCart.css";
import { connect } from "react-redux"
import {Link} from "react-router-dom";
import { put, post } from '../../../../handlers/requestHandlers';

 class UserCartConfirm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: this.props.userId,
            products: props.productList,
        };
    }

    confirmed = (event) => {
        window.confirm("Your order has been confirmed. If you wish to make any edit from now, please contact 4N");
        console.log("THIS")
        console.log(this.props.address)
        console.log(this.props.orderLines)
        console.log(this.props.userId)
        console.log(this.props.userType)
        post('/orders/'+this.props.userId+'/'+this.props.userType, {}, (response) => {


        });
    }
 

    render(){
        const company = this.props.address
        console.log(company)
        let lines = this.props.orderLines;
        console.log("OrderLines: " + lines)

        lines = lines.map((line, i)=>{return(
                <tr key={i}>
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
                        <label className="font-weight-normal">{this.props.address.company}</label>
                        <br/>
                        <label className="font-weight-bold">Recipient: </label>
                        <label className="font-weight-normal">{this.props.address.contactPerson}</label>
                        <br/>
                        <label className="font-weight-bold">Phone: </label>
                        <label className="font-weight-normal">{this.props.address.phoneNumber}</label>
                        <br/>
                        <label className="font-weight-bold">CVR: </label>
                        <label className="font-weight-normal">{this.props.address.cvr}</label>
                        <br/>
                        <label className="font-weight-bold">Address: </label>
                        <label className="font-weight-normal">{this.props.address.address}</label>
                        <br/>
                        <label className="font-weight-bold">Zip: </label>
                        <label className="font-weight-normal">{this.props.address.zip}</label>
                        <br/>
                        <label className="font-weight-bold">City</label>
                        <label className="font-wight-normal">{this.props.address.city}</label>
                        <br/>
                        <label className="font-weight-bold">Country: </label>
                        <label className="font-weight-normal">{this.props.address.country}</label>
                        
                        <div onClick={this.confirmed} className="btn-success btn-block my-3 btn" role="button">Confirm order</div>
                       
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
        orderLines: state.orderReducer.orderLines,
        address: state.addressReducer,
        userType: state.loginReducer.userType,
        userId: state.loginReducer.userId
    }
}

export default connect(mapStateToProps)(UserCartConfirm);
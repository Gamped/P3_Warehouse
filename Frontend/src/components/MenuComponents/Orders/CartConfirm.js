import React from 'react';
import "./Order.css";
import "./Cart.css";
import { connect } from "react-redux"
import {Link} from "react-router-dom";
import { post } from '../../../handlers/requestHandlers';

 class UserCartConfirm extends React.Component {
    constructor(props) {
        super(props);

        let address = this.setPropsToState();
        this.state = {
            userID: this.props.userId,
            products: props.productList,
            address: {}
        };
    }

    componentDidMount() {

        this.setPropsToState();
    }

    setPropsToState() {
        let address = {};
        let props = this.props.address;
        console.log("PROPS " + props);
        
        if (props) {
            props.company ? address.company = props.company : address.company = "Not specified";
            props.zip ? address.zip = props.zip : address.zip = "Not specified";
            props.phoneNumber ? address.phoneNumber = props.phoneNumber : address.phoneNumber = "Not specified";
            props.country ? address.country = props.country : address.country = "Not specified";
            props.contactPerson ? address.contactPerson = props.contactPerson : address.contactPerson = "Not specified";
            props.address ? address.address = props.address : address.address = "Not specified";
            props.city ? address.city = props.city : address.city = "Not specified";
        }

        return address;

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

    onChange = (event) => {
        
    }
 

    render(){
        const address = this.state.address;
        
        console.log(address);

        let lines = this.props.orderLines;
        console.log("OrderLines: " + lines)
       
        if (lines) {
            
        lines = lines.map((line, i)=>{return(
                <tr key={i}>
                    <th scope="row">{line.productId}</th>
                    <td>{line.productName}</td>
                    <td>{line.amount}</td>
                </tr>
            )});
        }

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
                        <label className="font-weight-normal">{this.state.address.company}</label>
                        <br/>
                        <label className="font-weight-bold">Recipient: </label>
                        <label className="font-weight-normal">{this.state.address.contactPerson}</label>
                        <br/>
                        <label className="font-weight-bold">Phone: </label>
                        <label className="font-weight-normal">{this.state.address.phoneNumber}</label>
                        <br/>
                       
                        <br/>
                        <label className="font-weight-bold">Address: </label>
                        <label className="font-weight-normal">{this.state.address.address}</label>
                        <br/>
                        <label className="font-weight-bold">Zip: </label>
                        <label className="font-weight-normal">{this.state.address.zip}</label>
                        <br/>
                        <label className="font-weight-bold">City</label>
                        <label className="font-wight-normal">{this.state.city}</label>
                        <br/>
                        <label className="font-weight-bold">Country: </label>
                        <label className="font-weight-normal">{this.state.address.country}</label>
                        
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
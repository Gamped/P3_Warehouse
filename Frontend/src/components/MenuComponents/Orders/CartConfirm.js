import React from 'react';
import "./Order.css";
import "./Cart.css";
import { connect } from "react-redux";
import {makeOrderBodyFromData} from "../../../handlers/dataHandlers";
import { post } from '../../../handlers/requestHandlers';

 class UserCartConfirm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            userID: this.props.userId,
            products: props.productList,
            address: {}
        };
    }

    confirmed = (event) => {
        const {userId,userType,orderLines} = this.props;
        const data = {...this.props.address}
        const body = makeOrderBodyFromData(data,orderLines)
        console.log("Data: ", data)
        console.log("Body: ", body)
        /*
        const body = {

        }

        if(userType === "EMPLOYEE"){

        }else{
            post('orders/'+userId+'/'+userType, {body}, (response) => {
                if(response === null){
                    this.props.history.push("/User/Order/Failed")
                }else{
                    this.props.history.push("/User/Order/Success")
                }
            });
        }*/
    }
 
    goBack = () =>{
        if(this.props.userType==="EMPLOYEE"){
            this.props.history.push("/Admin/Order/Cart")
        }else{
            this.props.history.push("/User/Order/Cart")
        }
    }

    render(){
        const address = this.props.address;
        console.log(address)
        let lines = this.props.orderLines;
       
        if (lines !== undefined) {
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
                        <label className="font-weight-normal">{address.company}</label>
                        <br/>
                        <label className="font-weight-bold">Recipient: </label>
                        <label className="font-weight-normal">{address.contactPerson}</label>
                        <br/>
                        <label className="font-weight-bold">Phone: </label>
                        <label className="font-weight-normal">{address.phoneNumber}</label>
                        <br/>
                       
                        <br/>
                        <label className="font-weight-bold">Address: </label>
                        <label className="font-weight-normal">{address.address}</label>
                        <br/>
                        <label className="font-weight-bold">Zip: </label>
                        <label className="font-weight-normal">{address.zip}</label>
                        <br/>
                        <label className="font-weight-bold">City</label>
                        <label className="font-wight-normal">{address.city}</label>
                        <br/>
                        <label className="font-weight-bold">Country: </label>
                        <label className="font-weight-normal">{address.country}</label>
                        
                        <div onClick={this.confirmed} className="btn-success btn-block my-3 btn" role="button">Confirm order</div>
                       
                        <button className="btn-info btn btn-block my-3" onClick={this.goBack} role="button">Back</button>
                        
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
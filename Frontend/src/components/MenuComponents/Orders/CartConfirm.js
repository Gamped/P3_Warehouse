import React from 'react';
import "./Order.css";
import "./Cart.css";
import { connect } from "react-redux";
import {makeOrderBodyFromData} from "../../../handlers/bodyHandlers";
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
        console.log("Data: ", data,"Orderlines: ", orderLines)
        console.log("Body: ", body)

        if(userType === "EMPLOYEE"){
            let {userId,userType} = this.props.employeeUser;
            userType = userType.toLowerCase()
            post('orders/'+userId+'/'+userType, {body}, (response) => {
                if(response === null){
                    this.props.history.push("/Admin/Order/Failed")
                }else{
                    this.props.history.push("/Admin/Order/Success")
                }
            });
        }else{
            userType = userType.toLowerCase()
            post('orders/'+userId+'/'+userType, {body}, (response) => {
                if(response === null){
                    this.props.history.push("/User/Order/Failed")
                }else{
                    this.props.history.push("/User/Order/Success")
                }
            });
        }
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
        <div className="PageStyle customText_b">
            <h2 className="customText_b_big">Cart:</h2>
            <div className="container">
                <div className="row">
                    <div className="col">
                        <div className="container my-3">
                            <table className="table orderCartTable">
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
                        <h4 className="customText_b_big">Please verify:</h4>
                        <br/>
                        <label className="customText_b_bold">Company name: </label>
                        <label className="customText_b">{address.company}</label>
                        <br/>
                        <label className="customText_b_bold">Recipient: </label>
                        <label className="customText_b">{address.contactPerson}</label>
                        <br/>
                        <label className="customText_b_bold">Phone: </label>
                        <label className="customText_b">{address.phoneNumber}</label>
                        <br/>
                       
                        <br/>
                        <label className="customText_b_bold">Address: </label>
                        <label className="customText_b">{address.address}</label>
                        <br/>
                        <label className="customText_b_bold">Zip: </label>
                        <label className="customText_b">{address.zip}</label>
                        <br/>
                        <label className="customText_b_bold">City</label>
                        <label className="customText_b">{address.city}</label>
                        <br/>
                        <label className="customText_b_bold">Country: </label>
                        <label className="customText_b">{address.country}</label>
                        
                        <div onClick={this.confirmed} className="green_BTN btn-block my-3 btn" role="button">Confirm order</div>
                       
                        <button className="std_BTN btn btn-block my-3" onClick={this.goBack} role="button">Back</button>
                        
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
        employeeUser:state.orderReducer.customer,
        address: state.addressReducer,
        userType: state.loginReducer.userType,
        userId: state.loginReducer.userId
    }
}

export default connect(mapStateToProps)(UserCartConfirm);
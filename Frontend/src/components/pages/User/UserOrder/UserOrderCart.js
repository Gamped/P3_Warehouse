import React from 'react';
import "../../Pages.css";
import "./UserOrder.css";
import "./UserCart.css";
import {shrinkToHtmlNames} from './../../../../global.js';

class UserOrderCart extends React.Component {
    constructor() {
        super();
        
        let addressForm = ["Company", "Contact Person", "Phone Number", "Address", "Zip and City"];
        let htmlNames = shrinkToHtmlNames(addressForm);

        this.state = {
            orderDetails: {},
            addressForm: addressForm,
            htmlNames: htmlNames,
            quarry: "",
            previousAddresses: [],
            orderLines:[]
        };
    }
 
    onChange = (e) => {
        const state = this.state.orderDetails;
        state[e.target.name] = e.target.value;
        this.setState({orderDetails:state});
    }

    handlePreviousAddressSelect = (event) => {
        this.setState({
            previousAddresses: event.target.value,
        });
    }

    handleQuarry = (event) => {
        this.setState({
            quarry: event.target.value,
        });
    }

    render(){
        return(
            <div className="PageStyle rounded">
                    <nav className="navbar navbar-dark bg-secondary"> <h2 className="text-center text-light">Cart:</h2></nav>
                <div className="container">
                    <div className="row">
                        <div className="col">
                            <div className="container my-3">
                                <table className="table table-dark">
                                    <thead>
                                        <th scope="col">Product ID</th>
                                        <th scope="col">Product name</th>
                                        <th scope="col">Amount</th>
                                    </thead>
                                    <tbody>
                                        
                                            {this.state.orderLines.map((line)=>{
                                                return(
                                                <tr>
                                                    <th scope="row">{line.id}</th>
                                                    <td>{line.name}</td>
                                                    <td>{line.amount}</td>
                                                </tr>
                                                ) 
                                            })}
                                            
                                        
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div className="col container">
                            <form action="/User/Order/Cart/Confirm" className="container">
                                <button className=" btn-success btn btn-block my-3">Send order</button>
                            </form>
                            <form action="/User/Order/" className="container my-3">
                                <button className=" btn-danger btn btn-block">Cancel order</button>
                            </form>
                                <h4 className="text-center my-2">Where to send the order:</h4>

                            <form className="">
                                {this.state.addressForm.map((placeholder, i) => {
                                    return(
                                <input 
                                type="text" 
                                className="input-group mb-3" 
                                name= {this.state.htmlNames[i]}
                                onChange={this.onChange}
                                    placeholder={placeholder}
                                    key={placeholder}/> 
                                    )
                                })}
            
                            </form>
                    
                        </div>
                    </div>        
                </div>
            </div>
        );
    }
}

export default UserOrderCart;
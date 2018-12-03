import React from 'react';
import "../../Pages.css";
import "./UserOrder.css";
import "./UserCart.css";
import {shrinkToHtmlNames} from './../../../../global.js';
import axios from 'axios';

export default class UserOrder extends React.Component {
    constructor() {
        super();
        
        let addressForm = ["Company", "Contact Person", "Phone Number", "Address", "Zip and City"];
        let htmlNames = shrinkToHtmlNames(addressForm);

        this.state = {
            orderDetails: {},
            addressForm: addressForm,
            htmlNames: htmlNames,
            quarry: "",
            previousAddresses: []
        };
    }

    componentWillMount() {
       
        //TODO: Make route for address saving previous addresses
        //axios.get('http://localhost:8080/user/'+this.props.userId+'/previousAddresses')
        // .then((response) => { 
        //    this.setState({previousAddresses: response.data})
      //  })
    }
 
    onChange = (e) => {
        const state = this.state.orderDetails;
        state[e.target.name] = e.target.value;
        this.setState({orderDetails:state});
        console.log(JSON.stringify(this.state.orderDetails))
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
                         <div className="topBox topBoxStyle">
                    <h2 className="topText text-center text-white"> Cart:</h2>
                </div>

                <div className="cartBox contentBoxStyle">
                    <select 
                        className="cartDropdown" 
                        value={this.state.previousAddresses.addressLine}
                        onChange={this.handlePreviousAddressSelect}>

                        <option value="">[Use previous addresses]</option>
                        {this.state.previousAddresses.map(address =>
                            <option value={address}>{address}</option>
                        )}
                    </select> 

                    <div className="productListBox bottomBoxStyle">
                       
                        <table className="cartTable">
                            <tbody>
                                <tr>
                                    <th>Product name</th>
                                    <th>Amount</th>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div className="infoBox">
                        <div className="cartButtonBox">
                            <form action="/User/Order/" className="cartButtonForm cartButton">
                                <button className=" stockButton_f btn">Cancel</button>
                            </form>
                            <form action="/User/Order/Cart/Confirm" className="cartButtonForm cartButton">
                                <button className=" stockButton_f btn">Send order</button>
                            </form>
                            <h1 className="cartTxt customText_b">Where to send:</h1>
                        </div>

                        <form className="cartFormPlacer">
                            {this.state.addressForm.map((placeholder, i) => {
                                return(
                               <input 
                               type="text" 
                               className="cartForm" 
                               name= {this.state.htmlNames[i]}
                               onChange={this.onChange}
                                   placeholder={placeholder}/> 
                                )
                            })}
        
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}
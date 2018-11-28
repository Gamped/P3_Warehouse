import React from 'react';
import "../../Pages.css";
import "./UserOrder.css";
import "./UserCart.css";

export default class UserOrder extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",
            products: props.productList,
            dropdownValue: "",
            dropdownOptions: [],
        };
    }


    /*
    * SOME FUNCTION TO RETRIEVE & SEND INFO FROM DB
    */

    handleDropdown = (event) => {
        this.setState({
            dropdownValue: event.target.value,
        });
    }

    handleQuarry = (event) => {
        this.setState({
            quarry: event.target.value,
        });
    }

    render(){
        return(
            <div className="PageStyle">
                <div className="topBox topBoxStyle">
                    <h1 className="topText customText_w"> Cart:</h1>
                </div>

                <div className="cartBox contentBoxStyle">
                    <select 
                        className="cartDropdown" 
                        value={this.state.dropdownValue}
                        onChange={this.handleDropdown}>

                        <option value="">[Use previous addresses]</option>
                        {this.state.dropdownOptions.map(address =>
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
                            <input 
                                type="text" 
                                className="cartForm" 
                                onChange={this.handleUName}
                                    placeholder="User name"/>
                            <input 
                                type="text" 
                                className="cartForm" 
                                onChange={this.handleName}
                                placeholder="Name"/>
                            <input 
                                type="email" 
                                className="cartForm" 
                                onChange={this.handleEmail}
                                placeholder="Email"/>
                            <input 
                                type="tel" 
                                className="cartForm" 
                                onChange={this.handlePhoneNumber}
                                placeholder="Phone Number"/>
                            <input 
                                type="text" 
                                className="cartForm" 
                                onChange={this.handleAddress}
                                placeholder="Address"/>
                            <input 
                                type="text" 
                                className="cartForm" 
                                onChange={this.handleAddress}
                                placeholder="Zip and City"/>
                            <input 
                                type="text" 
                                className="cartForm" 
                                onChange={this.handleCVR}
                                placeholder="CVR"/>
                            <input 
                                type="password" 
                                className="cartForm" 
                                onChange={this.handleCurPass}
                                placeholder="Current password"/>
                            <input 
                                type="password" 
                                className="cartForm" 
                                onChange={this.handleNewPass}
                                placeholder="New password"/>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}
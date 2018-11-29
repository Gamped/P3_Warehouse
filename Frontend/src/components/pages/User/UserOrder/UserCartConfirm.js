import React from 'react';
import "../../Pages.css";
import "./UserOrder.css";
import "./UserCart.css";

export default class UserCartConfirm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",
            products: props.productList,
        };
    }


    /*
    * SOME FUNCTION TO RETRIEVE & SEND INFO FROM DB
    */

    handleQuarry = (event) => {
        this.setState({
            quarry: event.target.value,
        });
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <div className="topBox topBoxStyle">
                    <h1 className="topText customText_w"> Cart: CONFIRM</h1>
                </div>

                <div className="cartBox contentBoxStyle">
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
                            <form action="/User/Order/Cart" className="cartButtonForm cartButton">
                                <button className=" stockButton_f btn">Back</button>
                            </form>
                            <form action="/User" className="cartButtonForm cartButton">
                                <button className=" stockButton_f btn">CONFIRM order</button>
                            </form>
                            <h1 className="cartTxt customText_b">Please verify:</h1>
                            <br/>
                            <br/>
                            <h1 className="confirmText customText_b">Company name: </h1>
                            <h1 className="confirmText customText_b">Recipient:</h1>
                            <h1 className="confirmText customText_b">Phone: </h1>
                            <h1 className="confirmText customText_b">CVR: </h1>
                            <h1 className="confirmText customText_b">Adress: </h1>
                            <h1 className="confirmText customText_b">Zip and city: </h1>
                            <h1 className="confirmText customText_b">Country: </h1>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
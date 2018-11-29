import React from 'react';
import "../../Pages.css";
import "./UserOrder.css"

export default class UserOrder extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",
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
                    <h1 className="topText customText_w"> Order:</h1>
                </div>

                <div className="deciderBox leftBoxStyle">
                    <input 
                        type="text" 
                        className="serachBar" 
                        onChange={this.handleQuarry}
                        placeholder="Search for product(s)"/>
                    <form action="/User/Order/Cart" className="orderForm">
                        <button className="exportButton stockButton_f btn">Go to cart</button>
                    </form>
                </div>

                <div className="listBox contentBoxStyle">

                </div>
            </div>)
        ;
    }
}
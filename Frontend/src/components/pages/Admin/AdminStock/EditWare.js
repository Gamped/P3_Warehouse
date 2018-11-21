import React from 'react';
import "../../Pages.css";
import "./AdminStock.css";

export default class EditWare extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            productID: props.ID,
            productName: "",
            quantity: 1,
            owner: "",
        };
    }

    /*
    * SOME FUNCTION TO RETRIEVE INFO FROM DB
    */

    handlePName = (event) => {
        this.setState({
            productName: event.target.value,
       });
    }

    handleQuantity = (event) => {
        this.setState({
            quantity: event.target.value,
        });
    }

    handleOwner = (event) => {
        this.setState({
            owner: event.target.value,
        });
    }

    render(){
        return(
        <div className="PageStyle">
            <customText_b_big className="title">Edit product:</customText_b_big>
            <customText_b className="subTitle">"Product name"</customText_b>
            <form>
                <input 
                    type="text" 
                    className="newForm" 
                    onChange={this.handlePName}
                    placeholder="Product name"/>
                <input 
                    type="text" 
                    className="newForm" 
                    onChange={this.handleQuantity}
                    placeholder="Quantity"/>
                <input 
                    type="text" 
                    className="newForm" 
                    onChange={this.handleOwner}
                    placeholder="Owner"/>
            </form>
            <form action="/Admin/Stock" className="newForm stockForm">
                <button className="newButton stockButton_f btn">Back</button>
            </form>
            <form action="/Admin/Stock" className="newForm stockForm">
                <button className="newButton stockButton_f btn">Edit product</button>
            </form>
            <customText_b className="note">Please double check to make sure you have changed to the correct info</customText_b>
        </div>);
    }
}
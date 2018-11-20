import React from 'react';
import "../../Pages.css";
import "./AdminStock.css";

export default class NewWare extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            productName: "",
            quantity: 1,
            fufillText: "Add product",
            owner: "",
        };
    }

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
            <customText_b_big className="title">Add new product</customText_b_big>
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
                    <button className="newButton stockButton_f btn">Create product</button>
                </form>
        </div>);
    }
}
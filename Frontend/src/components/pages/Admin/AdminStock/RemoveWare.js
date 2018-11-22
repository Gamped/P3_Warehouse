import React from 'react';
import "../../Pages.css";
import "./AdminStock.css";

export default class RemoveWare extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            productID: props.ID,
            productName: "",
            quantity: 1,
            owner: "",
            trueName: "",
            note: "",
        };
    }

    /*
    * SOME FUNCTION TO VERIFY IF PRODUCT NAME IS CORRECT & THEN SEND DELETE REQUEST
    */

   handlePName = (event) => {
        this.setState({
            productName: event.target.value,
        });
        this.removeNoteUpdate();
    }



    render(){
        return(
        <div className="PageStyle">
            <h1 className="title_r customText_b_big">REMOVE product:</h1>
            <h1 className="subTitle customText_b">"Product name"</h1>
            <form>
                <input 
                    type="text" 
                    className="newForm" 
                    onChange={this.handlePName}
                    placeholder="Please enter product name to confirm"/>
            </form>
            <form action="/Admin/Stock" className="newForm stockForm">
                <button className="newButton_g stockButton_f btn">Keep product</button>
            </form>
            <form action="/Admin/Stock" className="newForm stockForm">
                <button 
                    className="newButton_r stockButton_f btn"
                    onClick={this.handleRemove}>Remove product</button>
            </form>
            <h1 className="note customText_b">Please enter name of ware, to make sure that you have the correct product to be deleted from the system</h1>
        </div>);
    }
}
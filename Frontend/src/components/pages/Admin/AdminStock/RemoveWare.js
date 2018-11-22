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
            <customText_b_big className="title_r">REMOVE product:</customText_b_big>
            <customText_b className="subTitle">"Product name"</customText_b>
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
            <customText_b className="note">Please enter name of ware, to make sure that you have the correct product to be deleted from the system</customText_b>
        </div>);
    }
}
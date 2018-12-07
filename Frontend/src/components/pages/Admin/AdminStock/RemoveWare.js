import React from 'react';
import "../../Pages.css";
import "./AdminStock.css";
import { Link } from "react-router-dom";

const RemoveWare =(props)=> {

    const handleRemove = () => {
        
    }
    /*
    * SOME FUNCTION TO VERIFY IF PRODUCT NAME IS CORRECT & THEN SEND DELETE REQUEST
    */

   const handlePName = (event) => {
        this.setState({
            productName: event.target.value,
        });
        this.removeNoteUpdate();
    }


    return(
        <div className="PageStyle rounded">
            <h1 className="title_r customText_b_big">REMOVE product:</h1>
            <h1 className="subTitle customText_b">"Product name"</h1>
            <form>
                <input 
                    type="text" 
                    className="my-2 form-control" 
                    onChange={handlePName}
                    placeholder="Please enter product name to confirm"/>
            </form>
            
            <Link to="/Admin/Stock" className="btn-success btn-block my-2 btn">Keep product</Link>
            
            <form action="/Admin/Stock" className="newForm stockForm">
                <button 
                    className="btn-danger btn-block my-2 btn"
                    onClick={handleRemove}>Remove product</button>
            </form>
            <h1 className="note customText_b">Please enter name of ware, to make sure that you have the correct product to be deleted from the system</h1>
        </div>);
    }

export default RemoveWare
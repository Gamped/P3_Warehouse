import React from 'react';
import "../../Pages.css";
import "./AdminStock.css"

const AdminStock = (props) => {
    

    return(
        <div className="PageStyle">

            <div className="topBoxStyle topBox">
                <customText_w className="stockTxt">Stock</customText_w>
            </div>

            <div className="leftBoxStyle pickBox">
                <customText_b className="leftTxt">Filter by:</customText_b>
            </div>

            <div className="contentBoxStyle listBox">

            </div>

            <div className="bottomBoxStyle bottomBox">
                <form action="/Admin/Stock/New" className="stockForm">
                    <button  className="stockButton_f btn" >New</button>
                </form>
                <form action="/Admin/Stock/Edit" className="stockForm">
                    <button  className="stockButton_f btn" >Edit</button>
                </form>
                <form action="/Admin/Stock/Remove" className="stockForm">
                    <button  className="stockButton_f btn" >Remove</button>
                </form>
                <button className="stockButton btn">Export</button>
            </div>

        </div>

    );
}
 
export default AdminStock;
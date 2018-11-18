import React from 'react';
import Table from "../../../MenuComponents/Table"
import "../../Pages.css";

const AdminOrders = (props) => {
    const columns = [
        {Header: "Product Name", accessor: "name"},
        
    ]

    return(
        <div className="PageStyle">
            <customText_b>You are on Admin order page</customText_b>
        </div>

    );
}
 
export default AdminOrders;


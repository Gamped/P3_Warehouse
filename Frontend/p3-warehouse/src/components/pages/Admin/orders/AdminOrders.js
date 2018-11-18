import React from 'react';
import Table from "../../../MenuComponents/Table"
import "../../Pages.css";

//TODO: Implement data fetching from backend/database
const AdminOrders = (props) => {
    const columns = [
        {Header: "Package Name", accessor: "name"},
        {Header: "Package ID", accessor: "id"},
        {Header: "Unit Amount", accessor: "amount"},
        {Header: "Unit Type", accessor: "type"},
        {Header: "Packaged?", accessor: "packaged"}
    ]

    return(
        <div className="PageStyle">
            <customText_b>You are on Admin order page</customText_b>
            <Table columns={columns}/>
        </div>

    );
}

export default AdminOrders;


import React from 'react';
import Table from "../../MenuComponents/Table/Table"
import "../Pages.css";
import "./AdminOrders.css";

//TODO: Implement data fetching from backend/database
const AdminOrders = (props) => {
    const columns = [
        {Header: "Package Name", accessor: "name"},
        {Header: "Package ID", accessor: "id"},
        {Header: "Unit Amount", accessor: "amount"},
        {Header: "Unit Type", accessor: "type"},
        {Header: "Packaged?", accessor: "packaged"}
    ]

    const tableHeight = window.innerHeight * 0.8;

    return(
        <div className="PageStyle">
            <div className="AdminTable">
                <Table columns={columns} height={tableHeight}/>
            </div>
        </div>
    );
}

export default AdminOrders;

import React from 'react'
import ReactTable from "react-table"
import 'react-table/react-table.css'
import "./orders.css"
import Table from '../../MenuComponents/Table';

class AdminTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = { 
            data: [], 
            loading: false
        }
    }

    render() { 
        const {data} = this.state.data;
        var rTable = new Table()
        rTable.state.data = this.state.data
        rTable.state.columns = [
            {Header: "Package Name", accessor: "name"},
            {Header: "Package ID", accessor: "id"},
            {Header: "Unit Amount", accessor: "amount"},
            {Header: "Unit Type", accessor: "type"},
            {Header: "Packaged?", accessor: "packaged"}
        ]
        return ( 
            <div className = "AdminTable">
                <rTable />
                <button>Mark All Packed</button>
                <button>Print Pack List</button>
                <button>Edit Order</button>
                <button>Order Pickup</button>
            </div>
        )
    }
}

 
export default AdminTable;
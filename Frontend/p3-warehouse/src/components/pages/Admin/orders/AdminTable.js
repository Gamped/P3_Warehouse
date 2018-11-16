import React from 'react'
import ReactTable from "react-table"
import 'react-table/react-table.css'
import "../../orders.css"

class AdminTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = { 
            data: [
                {name: "ass", id: 200, amount: 300, type: "books", packaged: true}
            ], 
            loading: false
        }
    }

    render() { 
        const {data} = this.state.data;
        return ( 
            <div className = "AdminTable">
                <ReactTable
                    data = {data}
                    columns = {[
                        {Header: "Package Name", accessor: "name"},
                        {Header: "Package ID", accessor: "id"},
                        {Header: "Unit Amount", accessor: "amount"},
                        {Header: "Unit Type", accessor: "type"},
                        {Header: "Packaged?", accessor: "packaged"}
                    ]}
                />
                <button>Mark All Packed</button>
                <button>Print Pack List</button>
                <button>Edit Order</button>
                <button>Order Pickup</button>
            </div>
        )
    }
}

 
export default AdminTable;
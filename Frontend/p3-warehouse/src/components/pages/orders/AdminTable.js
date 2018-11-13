import React from 'react'
import ReactTable from "react-table"
import 'react-table/react-table.css'
import "./orders.css"

class AdminTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = { data: [
            {name: "Harry Potter", id: 300, amount: 235, type: "book", packed: false},
        ] }
    }
    render() { 
        const {data} = this.state;
        return ( 
            <div className = "AdminTable">
                <ReactTable 
                    data = {data}
                    columns = {[
                        {
                            Header: "Package Name",
                            accessor: "name"
                        },
                        {
                            Header: "Package ID",
                            accessor: "id"
                        },
                        {
                            Header: "Unit Amount",
                            accessor: "amount"
                        },
                        {
                            Header: "Unit Type",
                            accessor: "type"
                        },
                        {
                            Header: "Packaged?",
                            accessor: "packaged"
                        }
                    ]}
                />
                <button>Add Entry</button>
                <button>Remove Entry</button>
                <button>Edit Entry</button>
            </div>
        )
    }
}
 
export default AdminTable;
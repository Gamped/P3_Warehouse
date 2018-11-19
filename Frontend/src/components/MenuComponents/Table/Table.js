import React from 'react'
import ReactTable from 'react-table'
import 'react-table/react-table.css'
import "./Table.css"

const rTable = (props) => {
    const {data, columns} = props;
    return(
        <div className = "rTable">
            <ReactTable 
                data = {data}
                columns = {columns}
            />
        </div>
    )
}

export default rTable;
import React from 'react'
import ReactTable from 'react-table'
import 'react-table/react-table.css'
import "./Table.css"

const rTable = (props) => {
    const {data, columns, height, width} = props;
    return(
        <div >
            <ReactTable 
                data = {data}
                columns = {columns}
                className = "rTable"
                style={{
                    height: height,
                    width: width,
                }}
            />
        </div>
    )
}

export default rTable;
import React from 'react';
import ReactTable from 'react-table'

const rTable = (data, columns) => {
    return(
        <div className = "rTable">
            <ReactTable 
                data = {data}
                columns = {columns}
            />
        </div>
    )
}
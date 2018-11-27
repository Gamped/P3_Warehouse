import React from 'react'
import BootstrapTable from 'react-bootstrap-table-next';
import "./Table.css";
const rTable = (props) => {
    const {data, columns, name } = props;
    const selectRow = {
        mode: 'radio',
        clickToSelect: true,
        onSelect: (row, isSelect, rowIndex,e)=>{
            console.log(row, isSelect, rowIndex, e)
            return null
        }
      };


    return(
            <BootstrapTable selectRow={selectRow}
                            keyField='id' 
                            data={ data } 
                            columns={ columns } />
    )
}

export default rTable;
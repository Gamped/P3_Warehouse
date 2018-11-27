import React from 'react'
import BootstrapTable from 'react-bootstrap-table-next';

const rTable = (props) => {
    const {data, columns, name } = props;
    const selectRow = {
        mode: 'radio',
        clickToSelect: true
      };
    return(
        <div >
            <BootstrapTable selectRow={selectRow} 
                            keyField='id' 
                            data={ data } 
                            columns={ columns } />
        </div>
    )
}

export default rTable;
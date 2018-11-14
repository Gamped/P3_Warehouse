import React from 'react'
import ReactTable from 'react-table'

class Table extends React.Component{
    constructor(props){
        super(props)
        this.state = {
            data: [],
            loading: false,
            columns: []
        }
    }

    render(){
        const {data} = this.state.data
        const {columns} = this.state.columns
        return(
            <div>
                <ReactTable 
                    data = {data}
                    columns = {columns}
                />
            </div>
        )
    }
}

export default Table
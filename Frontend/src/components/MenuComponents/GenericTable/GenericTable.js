import React,{Component} from 'react';
import ReactTable from 'react-table';
import axios from 'axios';

export default class GenericTable extends Component{
    constructor(props){
        super(props);
        this.state = {
            data: props.data,
            columns: props.columns,

            selected: null,
            selectedId: "",
        }
    }

    render(){
        const tableData = this.state.data;
        const tableColumns = this.state.columns;
        return(
            <ReactTable 
                data={tableData}
                columns={tableColumns}
                showPagination={false} 
                className="-striped -highlight"
                getTrProps={(state, rowInfo) => {
                    if (rowInfo && rowInfo.row) {
                        return {
                        onClick: (e) => {
                            
                            this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId })
                            console.log(rowInfo.original)
                        },
                        style: {
                            background: rowInfo.index === this.state.selected ? '#00afec' : 'white',
                            color: rowInfo.index === this.state.selected ? 'white' : 'black'
                        }
                        }
                    }else{
                        return {}
                    }
                }}
            />
        )
    }
}
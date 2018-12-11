import React,{Component} from 'react';
import axios from 'axios';
import ReactTable from 'react-table';
import { Link } from "react-router-dom";
import "../../Pages.css";
import "./EditOrder.css"
import {getColumnsFromArray} from './../../../../handlers/columnsHandlers.js';
import {get} from './../../../../handlers/requestHandlers.js';
import {makeOrderLinesData} from './../../../../handlers/dataHandlers.js';

export default class EditOrder extends Component{
    constructor(props){
        super(props);
        this.state = {
            orderLines: []
        }
    }

    componentDidMount() {
        
        this.getOrderLines();
    }

    getOrderLines() {
        get("employee/order/"+this.props.match.params.id, (data)=> {
            const orderLines = makeOrderLinesData(data);
            console.log(orderLines);
            this.setState({orderLines: orderLines})
        });
    }

    sendToPage = (address) => {
        this.props.history.push(address);
    }

    renderEditable = cellInfo => {
        return (
          <div
            style={{ backgroundColor: "#fafafa" }}
            contentEditable
            suppressContentEditableWarning
            onBlur={e => {
                var typedValue = e.target.innerHTML;
                
                this.state.orderLines
                .filter(orderLine => orderLine.hexId === cellInfo.original.hexId)
                .map(orderLine => 
                    orderLine.amount = typedValue);
                    
                    cellInfo.original[e.target.name] = typedValue;
    
            }}
            dangerouslySetInnerHTML={{
              __html: this.state.orderLines[cellInfo.index][cellInfo.column.id]
            }}
          />
        );
      };

    render(){
        
        let columns = getColumnsFromArray(["Product Name", "Amount", "Quantity"]);
        columns[1].Cell = this.renderEditable;
        let orderLines = this.state.orderLines;
        console.log(orderLines)
        
        return(
            <div className="PageStyle rounded">
                <div className="Contents">
                    <ReactTable 
                        data={orderLines}
                        columns={columns}
                        showPagination={false}
                        className="OrderLines -striped -highlight"
                        getTrProps={(state, rowInfo) => {
                            if (rowInfo && rowInfo.row) {
                                return {
                                onClick: (e) => {
                                    
                                    this.setState({selectedOrderLine: rowInfo.index})
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
                    <div className="Buttons container row">
                        <button className="btn btn-warning btn-lg mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit/OrderAddress")}>Edit Address</button>
                        <button className="btn btn-warning btn-lg mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit/OrderContent")}>Edit Contents</button>
                    </div>
                </div>
            </div>
        )
    }
}
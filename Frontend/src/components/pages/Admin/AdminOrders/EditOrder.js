import React,{Component} from 'react';
import axios from 'axios';
import ReactTable from 'react-table';
import { Link } from "react-router-dom";
import "../../Pages.css";
import "./EditOrder.css"
import {getColumnsFromArray} from './../../../../handlers/columnsHandlers.js';
import {get} from './../../../../handlers/requestHandlers.js';
import {makeOrderLinesData, makeCustomerProductsData} from './../../../../handlers/dataHandlers.js';

export default class EditOrder extends Component{
    constructor(props){
        super(props);
        this.state = {
            orderLines: [],
            stock: []
        }
    }

    componentDidMount() {
        
        this.getOrderLines();
    }

    getOrderLines() {
        get("employee/order/"+this.props.match.params.id, (data)=> {
            const orderLines = makeOrderLinesData(data);
            let userType = data.owner.userType.toLowerCase();

            this.setState({
                orderLines: orderLines, 
                userType: userType,
                userHexId: data.owner.userHexId
            });
        
            //this makes sure the table only shows products owned by the person who placed the order
            this.ownerIsCustomer() ? this.getCustomerStock(userType, data.owner.userHexId) : this.getStock();
        });
    }

    ownerIsCustomer() {
        if (userType.toLowerCase() == 'employee') {
            return false;
        } else {
            return true;
        }
    }

    getCustomerStock(userType, userHexId) {
        
        get("employee/"+userType.toLowerCase()+"/"+userHexId, (data) => {
            const products = makeCustomerProductsData(data);
            this.setState({products: products});
        });
    }

    getStock() {
        
        get("employee/products", (data) => {
            const products = makeProductsRowsFromResponseData(data);
            this.setState({products: products});
        })
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
        
        let orderColumns = getColumnsFromArray(["Product Name", "Amount", "Quantity"]);
        columns[1].Cell = this.renderEditable;
        let orderLines = this.state.orderLines;
        console.log(orderLines)


        const stockColumns = getColumnsFromArray(["Product Name", "Amount", "Quantity"]);
        stockColumns[1].Cell = this.renderEditable;
        let stock = this.state.stock;

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

                     </div>
                        <div className="editButtons col col-md-auto">
                             <div class="h-25"></div>   
                             <div class="btn-group-vertical">
                                <div className="row my-5">
                                    <button className=" addButton btn btn-success  mx-1 "> Add Product </button>
                                    <button className=" removeButton btn btn-danger mx-1 my-5">Remove Product</button>
                                </div>  
                             </div>
                        </div>
                        
                        <div className=" col col-md-auto">
                            <nav class="navbar navbar-light bg-light">
                                <h className="navnbar" >Publiser stock</h>
                            </nav>
                            <ReactTable 
                                data={this.state.productData}
                                columns={productColumns}
                                className="Products"
                                showPagination={false} 
                                className="productTable -striped -highlight"
                                getTrProps={(state, rowInfo) => {
                                    if (rowInfo && rowInfo.row) {
                                        return {
                                            onClick: (e) => {
                                                this.setStateAsSelected(rowInfo);
                                                this.showOrderLines(rowInfo);
                                            },
                                            style: {
                                                background: rowInfo.index === this.state.productSelected ? '#00afec' : 'white',
                                                color: rowInfo.index === this.state.productSelected ? 'white' : 'black'
                                            }
                                        }
                                    }else{
                                        return {}
                                    }
                                }}
                            />

                    <div className="Buttons container row">
                        <button className="btn btn-warning btn-lg mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit/OrderAddress/"+this.state.selectedOrderLine)}>Edit Address</button>
                        <button className="btn btn-warning btn-lg mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit/OrderContent")}>Edit Contents</button>
                        <div className="finishingButtons container row my-3">
                        <button className="col  btn btn-success mx-2">Save Content</button>
                        <button className="col  btn btn-danger mx-2">Discard Content</button>
                        <button className="col btn btn-info mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit")}>Back</button>                       
                    </div>
                    
                    </div>
                </div>
            </div>
        )
    }

    
    

}
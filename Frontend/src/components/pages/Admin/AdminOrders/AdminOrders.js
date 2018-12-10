import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import ReactTable from "react-table";
import axios from "axios";

import "../../Pages.css";
import "./AdminOrders.css";
import {allProductsNotPackedWarning} from "./../../../../handlers/exceptions.js";
import {makePublisherAndClientOrdersData} from "./../../../../handlers/dataHandlers.js"
import {get, del} from "./../../../../handlers/requestHandlers.js"
import {packListPDF, orderNotePDF} from "./../../../../handlers/pdfHandlers.js"
import {getColumnsFromArray} from "./../../../../handlers/columnsHandlers.js"


export default class AdminOrders extends Component {

    //A constructor that contains our state.
    constructor(props) {
        super(props);
        this.state = { 
            orders: [], 
            orderLines: [], 
            selectedOrder: [],
            selected: null, 
            selectedIndex: -1,
            selectedId: "",
            packed: {},
            allPacked: 0
        }

        this.setStateAsSelected = this.setStateAsSelected.bind(this);
        this.showOrderLines = this.showOrderLines.bind(this);
        this.toggleRow = this.toggleRow.bind(this);
        this.finishOrder = this.finishOrder.bind(this);
    }

    componentDidMount() {

       this.getPublishers();
    }

    getPublishers() {
        get("publishers", (data)=> {
            const orders = makePublisherAndClientOrdersData(data);
            
            this.setState({ 
                data: data,
                orders: orders
            });
        })
        
            
        
    }

    setStateAsSelected = (rowInfo) => {
        
        this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId });
    }

    showOrderLines = (rowInfo) => {

        const selectedOrder = this.state.orders[rowInfo.index].orderLines;
        this.setState({orderLines: selectedOrder});
    }

    toggleRow(productName) {

		const packedItem = Object.assign({}, this.state.packed);
		packedItem[productName] = !this.state.packed[productName];
        this.setState({
			packed: packedItem,
			selectAll: 2
		});
	}

	toggleSelectAll() {
		let packedItem = {};

		if (this.state.selectAll === 0) {
			this.state.data.forEach(x => {
				packedItem[x.productName] = true;
			});
        }

		this.setState({
			packed: packedItem,
			allPacked: this.state.allPacked === 0 ? 1 : 0
        });
        console.log(JSON.stringify(this.state.packed) + " " + this.state.allPacked)
    }


    getCheckBoxColumn() {
       const checkBoxColumn = {
        id: "checkbox",
        accessor: "",
        Cell: ({ original }) => {
            return (
                <input
                    type="checkbox"
                    className="checkbox"
                    checked={this.state.packed[original.productName] === true}
                    onChange={() => this.toggleRow(original.productName)}
                />
            );
        },
        Header: x => {
            return (
                <input
                    type="checkbox"
                    className="checkbox"
                    checked={this.state.selectAll === 1}
                    ref={input => {
                        if (input) {
                            input.indeterminate = this.state.selectAll === 2;
                        }
                    }}
                    onChange={() => this.toggleSelectAll()}
                />
            );
        }
    }
        return checkBoxColumn;

    }

    sendToPage = (address) => {
        this.props.history.push(address);
    }

    finishOrder() {
        let allPacked = this.state.allPacked;
        if (allPacked == 1) {
            del("employee/orders/delete/" + this.state.selectedId, () => {});
        } else {
            allProductsNotPackedWarning();
        }
           }

    render() {
      
        const orders = this.state.orders;

        let noSelectedOrderItem = [{
            productName: "No selected order",
            date: 0,
            amount: 0,
            packed: 0
        }];
  
        const orderColumns = getColumnsFromArray(["Owner", "Date", "Order Id"]);

        let orderLineColumns = getColumnsFromArray(["Product Name", "Amount"]);
            orderLineColumns.push(this.getCheckBoxColumn());

        return (
            <div className="PageStyle rounded">
                <div className="container row">
                    <div className="SideBar col sidebar border border-dark rounded bg-secondary">
                        <div className="OrderList">
                            <ReactTable 
                            data={orders}
                            columns={orderColumns} 
                            showPagination={false} 
                            className="-striped -highlight"
                            getTrProps={(state, rowInfo) => {
                                if (rowInfo && rowInfo.row) {
                                  return {
                                    onClick: (e) => {
                                       this.setStateAsSelected(rowInfo);
                                       this.showOrderLines(rowInfo);
                                    },
                                    style: {
                                      background: rowInfo.index === this.state.selected ? '#00afec' : 'white',
                                      color: rowInfo.index === this.state.selected ? 'white' : 'black'
                                    }
                                  }
                                }else{
                                  return {}
                                }
                               }
                            }
                             />
                        </div>
                        <div className=" md-2">
                                <button type= "button" className="btn btn-success mx-2" onClick={()=>this.sendToPage("/Admin/Orders/New")}>Create order</button>                           
                                <Link className="btn btn-warning mx-2" to={`/Admin/Orders/Edit/${this.state.selectedId}`}>Edit order</Link>
                                <button type= "button" className="btn btn-danger mx-2"  onClick={()=>this.sendToPage("/Admin/Orders/Delete")}>Delete order</button>
                        </div>
                    </div>
                        <div className="Table">
                                <ReactTable data={this.state.orderLines ? this.state.orderLines : noSelectedOrderItem} columns={orderLineColumns} showPagination={false} 
                                className="-striped -highlight"/>
                                 <div className="  px-1">

                                </div>
                       </div>  
                 </div>    
            </div>
        )
    }
}

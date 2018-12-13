import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import ReactTable from "react-table";
import axios from "axios";

import "../../Pages.css";
import "./AdminOrders.css";
import {allProductsNotPackedWarning} from "./../../../../handlers/exceptions.js";
import {makePublisherAndClientOrdersData, makeClientOrdersData} from "./../../../../handlers/dataHandlers.js"
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
        this.getIndependentClients();
        this.getPublishers();
    }

    getPublishers() {
        get("employee/publishers", (data)=> {
            console.log(data);
            const orders = makePublisherAndClientOrdersData(data);
            console.log("ORDERS " + orders)
            this.setState({ 
                data: data,
                orders: this.state.orders.concat(orders)
            });
        })
    }

    getIndependentClients(){
        get("clients/independent", (data)=> {
            const orders = makeClientOrdersData(data);
            this.setState({
                data: data,
                orders: this.state.orders.concat(orders)
            })
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
            del("orders/delete/:id" + this.state.selectedId, () => {});
        } else {
            allProductsNotPackedWarning();
        }
    }
    deleteOrder(){
        console.log(this.state.selectedId)
        del("orders/delete/" + this.state.selectedId, (response) => {
            console.log(response);
        });
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
                        <div className=" md-2 my-2">
                                <button type= "button" className="btn btn-success mx-2" onClick={()=>this.sendToPage("/Admin/Orders/New")}>Create order</button>                           
                                <Link className="btn btn-warning mx-2" to={`/Admin/Orders/Edit/${this.state.selectedId}`}>Edit order </Link>
                                <button type= "button" className="btn btn-danger mx-2"  onClick={()=>this.deleteOrder()}>Delete order</button>
                        </div>
                    </div>
                    <div className=" col-7">
                        <div className="Table">
                                <ReactTable data={this.state.orderLines ? this.state.orderLines : noSelectedOrderItem} columns={orderLineColumns} showPagination={false} 
                                className="-striped -highlight"/>
                                 <div className="  px-1">
                                </div>
                       </div> 
                       <div className="btn-group">
                             <button type= "button mx-2" className="btn btn-info mx-2" >Export Order</button> 
                             <button type= "button " className="btn btn-dark mx-5"onClick={()=>this.finishOrder()}>Finish Order</button> 
                       </div>
                    </div>    
                 </div>    
            </div>
        )
    }
}

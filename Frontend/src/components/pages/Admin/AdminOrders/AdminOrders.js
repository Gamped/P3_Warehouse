import React, {Component} from 'react';
import {connect} from "react-redux";
import ReactTable from "react-table";

import "../../Pages.css";
import "./AdminOrders.css";
import {allProductsNotPackedWarning} from "./../../../../handlers/exceptions.js";
import {makeAllPublishersAndClientsOrdersData, makeClientsOrdersData} from "./../../../../handlers/dataHandlers.js"
import {get, del} from "./../../../../handlers/requestHandlers.js"
import {packListPDF, orderNotePDF} from "./../../../../handlers/pdfHandlers.js"
import {getColumnsFromArray} from "./../../../../handlers/columnsHandlers.js"

class AdminOrders extends Component {

    //A constructor that contains our state.
    constructor(props) {
        super(props);
        this.state = {
            pureOrders:[], 
            orders: [], 
            orderLines: [],
            selected: null, 
            selectedIndex: -1,
            selectedId: "",
            selectedItem: null,
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
        this.getOrders();
    }

    getOrders = () =>{
        get("employee/orders", (data)=>{
            this.setState({pureOrders:data},()=>{
                console.log(this.state)
            })
        })
    }

    getPublishers() {
        get("employee/publishers", (data)=> {
            console.log(data);
            const orders = makeAllPublishersAndClientsOrdersData(data);
            console.log("ORDERS " + orders)
            
            this.setState({ 
                data: data,
                orders: this.state.orders.concat(orders)
            });
        })
    }

    getIndependentClients(){
        get("clients/independent", (data)=> {

            const orders = makeClientsOrdersData(data);
            this.setState({
                data: data,
                orders: this.state.orders.concat(orders)
            })
        })
    }

    setStateAsSelected = (rowInfo) => {

        this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId, selectedItem: rowInfo.original });
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
    deleteOrder = (e) =>{
        
        console.log(this.state.selectedId)
        del("orders/delete/" + this.state.selectedId, (response) => {
            let newOrders = this.state.orders.filter(item=>item.hexId!==this.state.selectedId)
            this.setState({selectedId:"",orders:newOrders})
        });
    }

    goToEdit = (event) =>{
        event.preventDefault();
        if(this.state.selectedItem !== null){
            this.props.setSelectedOrder(this.state.pureOrders.find(x=>x.hexId===this.state.selectedId))
            this.props.history.push("/Admin/Orders/Edit/")
        }else{
            window.alert("Please select an order to edit.")
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

        let orderLineColumns = getColumnsFromArray(["Product Id", "Product Name", "Amount"]);
        
            orderLineColumns.push(this.getCheckBoxColumn());

        return (
            <div className="PageStyle AdminOrderFontMinimize customText_b">
                <div className="frameBordering">
                    <div className="AdminOrderLeft">
                        <div className="leftReactTableAdminOrder OrderList ">
                            <ReactTable 
                            data={orders}
                            columns={orderColumns} 
                            showPagination={false} 
                            className=" -striped -highlight darkenReactTable"
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
                            style={{height: "100%"}}
                             />
                        </div>
                        <div className=" md-2 my-2">
                                <button type= "button" className="AdinOrderButtonSizer btn green_BTN mx-2" onClick={()=>this.sendToPage("/Admin/Orders/New")}>Create order</button>                           
                                <button className="AdinOrderButtonSizer btn std_BTN mx-2" onClick={this.goToEdit}>Edit order </button>
                                <button type= "button" className="AdinOrderButtonSizer btn red_BTN mx-2"  onClick={()=>this.deleteOrder()}>Delete order</button>
                        </div>
                    </div>

                    
                    <div className="AdminOrderRight">
                        <div className="Table rightReactTableAdminOrder">
                                <ReactTable data={this.state.orderLines ? this.state.orderLines : noSelectedOrderItem}
                                 columns={orderLineColumns} 
                                 showPagination={false} 
                                className="-striped -highlight"
                                style={{height: "100%"}}
                                />
                                 <div className="  px-1">
                                </div>
                       </div> 
                             <button type= "button" className="AdinOrderButtonSizer btn std_BTN mx-2  " >Export Order</button> 
                             <button type= "button" className="AdinOrderButtonSizer btn blue_BTN mx-2 "onClick={()=>this.finishOrder()}>Finish Order</button> 
                    </div>    
                </div>    
            </div>
        )
    }
}

const mapDispatchToProps = (dispatch) => {
    return{
        setSelectedOrder: (selectedOrder) => {dispatch({type: "SET_SELECTEDORDER",payload: {selectedOrder}})},
    }
}

export default connect(null,mapDispatchToProps)(AdminOrders)
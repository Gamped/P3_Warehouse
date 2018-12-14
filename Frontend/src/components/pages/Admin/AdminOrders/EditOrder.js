import React,{Component} from 'react';
import ReactTable from 'react-table';
import "../../Pages.css";
import {connect} from "react-redux";
import {getColumnsFromArray} from './../../../../handlers/columnsHandlers.js';
import {get, put} from './../../../../handlers/requestHandlers.js';
import {makeOrderLinesData, makeCustomerProductsData} from './../../../../handlers/dataHandlers.js';
import {amountIsNotANumberWarning, amountExceedingQuantityWarning} from './../../../../handlers/exceptions.js';
import "./EditOrder.css";


class EditOrder extends Component{
    constructor(props){
        super(props);
        this.state = {
            orderLines: [],
            stock: [],
            userType: "",
            userHexId: "",
            selectedOrderLine: -1,
            selectedOrderLine: {product:{quantity:"Nothing Chosen"}}
        }
    }

    addOrderLine = (e) => {
        e.preventDefault();
        let orderLines = this.state.orderLines;
        let newOrderLine = this.state.stock[this.state.selectedProduct];
        this.setState({orderLines : [...orderLines, newOrderLine]});
    }

    removeOrderLine = (e) => {
        e.preventDefault();
        let id = this.state.orderLines[this.state.selectedOrderLine].productId;
    
        let orderLines = this.state.orderLines.filter(orderLine => {
           return  orderLine.productId !== id
            });

       this.setState({orderLines : orderLines})
    }

    rowIsRemoved() {
        return this.state.orderLines[this.state.selectedOrderLine] ? false : true;
      }

    sendToPage = (address) => {
        this.props.history.push(address);
    }

    renderEditable = (cellInfo) => {
        //renderEditable complains if a row with a Cell property is being removed
        if (!this.rowIsRemoved()) {
        
        return (
          <div
            style={{ backgroundColor: "#fafafa" }}
            contentEditable
            type="number"
            
            onBlur={e => {
              
                var typedAmount = e.target.innerHTML ? e.target.innerHTML : "0";
                if (!typedAmount.match(/^\d+$/)) { amountIsNotANumberWarning() }
                this.state.orderLines
                .filter(orderLine => orderLine.hexId === cellInfo.original.hexId)
                .map(orderLine => {
                    if (typedAmount <= orderLine.quantity) { 
                        typedAmount = orderLine.amount ;
                    } else { 
                        amountExceedingQuantityWarning();
                        typedAmount = "0";
                    }
                         
                })
                    typedAmount = cellInfo.original.amount ;
                    typedAmount =   e.target.innerHTML;
                   
            }}
            dangerouslySetInnerHTML={{
              __html: this.state.orderLines[cellInfo.index][cellInfo.column.id]
            }}
          required/>
        );
      }
    }

     
    updateOrder = (e) => {
        e.preventDefault();
        put("orders/update/"+this.props.match.params.id, this.state.orderLines, (response) => {
            console.log("response: ",response);
        })
        

    }

    render() {
        console.log("State:",this.state)
        
        let orderLines = [];
        orderLines = this.props.orderLines
        if(orderLines !== undefined){
            orderLines.forEach((orderLine) => {
                orderLine.product.amount = orderLine.quantity;
            })
        }
        
        console.log("orderlines:",orderLines)

/*        let orderLineColumns = getColumnsFromArray(["Product Name", "Product Id", "Amount", "Quantity"]);
        orderLineColumns[2].Cell = this.renderEditable;*/
        
        /*
        
        */

        return (
             <div className="PageStyle customText_b">
                <div className="frameBordering">
                    <div className="EditOrderLeft">
                    <ReactTable 
                        data={orderLines}
                        columns={orderLineColumns}
                        showPagination={false}
                        className="editOrderColor -striped -highlight"
                        getTrProps={(state, rowInfo) => {
                            if (rowInfo && rowInfo.row) {
                                return {
                                onClick: (e) => {    
                                    this.setState({selectedOrderLine: rowInfo.original, selectedOrderLineNumber:rowInfo.index}, ()=>{console.log("Selected Orderline:",this.state.selectedOrderLine)})
                                },
                                style: {
                                    background: rowInfo.index === this.state.selectedOrderLine ? '#00afec' : 'white',
                                    
                                }
                            }
                            }else{
                                return {}
                            }
                        }}
                        style={{height: "70vh"}}
                    />                
                        <button className="AdinOrderButtonSizer green_BTN btn mx-1 " onClick={this.addOrderLine}> Add Product </button>
                        <button className="AdinOrderButtonSizer red_BTN btn mx-1 my-5" onClick={this.removeOrderLine}>Remove Product</button>
                    </div>


                    <div className="EditOrderRight">
                        <form>

                        <div className="input-group my-2">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="name" id="Item">Name</label> 
                            </div>
                            <input id="name" className="form-control" type="text" name="name" onChange={()=>{}} defaultValue={this.state.selectedOrderLine.product.productName} placeholder="Name"/>
                        </div>

                        <div className="input-group my-2">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="id" id="Item2">product Id</label> 
                            </div>
                            <input id="id" className="form-control" type="text" name="id" onChange={()=>{}} defaultValue={this.state.selectedOrderLine.product.productId} placeholder="Product id"/>
                        </div>

                        <div className="input-group my-2">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="amount" id="Item3">Amount</label> 
                            </div>
                            <input id="amount" className="form-control" type="number" name="amount" onChange={()=>{}} defaultValue={this.state.selectedOrderLine.quantity} placeholder="Amount ordered"/>
                        </div>

                        <div className="input-group my-2">
                            <div className="input-group-prepend">
                                <label className="input-group-text" htmlFor="name" id="Item">Quantity in Stock: </label> 
                            </div>
                            <label id="name" className="input-group-text" type="text"> {this.state.selectedOrderLine.product.quantity}</label>
                        </div>

                        </form>
                            <button className="btn AdinOrderButtonSizer std_BTN btn-lg mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit/OrderAddress/"+ this.props.match.params.id)}>Edit Address</button>        
                            <button className="col btn AdinOrderButtonSizer blue_BTN mx-2" onClick={this.updateOrder}>Save Content</button>
                            <button className="col btn AdinOrderButtonSizer dark_BTN mx-2" onClick={()=>this.sendToPage("/Admin/Orders")}>Back</button>                       
                    </div>

                </div>       
            </div>                        
        )
    }
}

const mapStateToProps = (state) =>{
    return{
        order: state.orderReducer.selectedOrder,
        orderLines: state.orderReducer.selectedOrder.orderLines
    }
}

export default connect(mapStateToProps)(EditOrder)
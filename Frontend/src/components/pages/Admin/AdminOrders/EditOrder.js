import React,{Component} from 'react';
import ReactTable from 'react-table';
import "../../Pages.css";
import {getColumnsFromArray} from './../../../../handlers/columnsHandlers.js';
import {get, put} from './../../../../handlers/requestHandlers.js';
import {makeOrderLinesData, makeCustomerProductsData} from './../../../../handlers/dataHandlers.js';
import {amountIsNotANumberWarning, amountExceedingQuantityWarning} from './../../../../handlers/exceptions.js';
import "./EditOrder.css";


export default class EditOrder extends Component{
    constructor(props){
        super(props);
        this.state = {
            orderLines: [],
            stock: [],
            userType: "",
            userHexId: "",
            selectedOrderLine: -1,
            selectedProduct: -1
        }
    }

    componentDidMount() {
        
        this.getOrderLines();
    }

    getOrderLines() {
        get("employee/order/"+this.props.match.params.id, (data)=> {
            console.log("data")
            const orderLines = makeOrderLinesData(data);
            
            this.setState({
                orderLines: orderLines, 
                owner: {userType: data.owner.userType,
                        userHexId: data.owner.userHexId,
                        nickName: data.owner.nickName }
            });

            this.getCustomerStock(data.owner.userType, data.owner.userHexId);
        });
    }



    getCustomerStock(userType, userHexId) {

        get("employee/"+userType.toLowerCase()+"/"+userHexId, (data) => {
            const stock = makeCustomerProductsData(data);
            this.setState({stock: stock});
        });
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
            onClick={(e) => {e.target.innerHTML = ""}}
            onBlur={e => {
              
                var typedAmount = e.target.innerHTML ? e.target.innerHTML : "0";
                if (!typedAmount.match(/^\d+$/)) { amountIsNotANumberWarning() }
                this.state.orderLines
                .filter(orderLine => orderLine.hexId === cellInfo.original.hexId)
                .map(orderLine => {
                    if (typedAmount <= orderLine.quantity) { 
                        orderLine.amount = typedAmount;
                    } else { 
                        amountExceedingQuantityWarning();
                        typedAmount = "0";
                    }
                         
                })
                   cellInfo.original.amount = typedAmount;
                   e.target.innerHTML = typedAmount;
                   
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
            console.log(response);
        })
        

    }

    render() {
        
        let orderLineColumns = getColumnsFromArray(["Product Name", "Product Id", "Amount", "Quantity"]);
        orderLineColumns[2].Cell = this.renderEditable;
        let orderLines = this.state.orderLines;

        const stockColumns = getColumnsFromArray(["Product Name", "Quantity"]);
        let stock = this.state.stock;

        return (
            <div className="PageStyle rounded">
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
                                        this.setState({selectedOrderLine: rowInfo.index})
                                        console.log(this.state.selectedOrderLine)
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
                        <ReactTable 
                                data={stock}
                                columns={stockColumns}
                                className="Products"
                                showPagination={false} 
                                className=" -striped -highlight"
                                getTrProps={(state, rowInfo) => {
                                    if (rowInfo && rowInfo.row) {
                                        return {
                                            onClick: (e) => {
                                                this.setState({selectedProduct: rowInfo.index})
                                            },
                                            style: {
                                                background: rowInfo.index === this.state.selectedProduct ? '#00afec' : 'white',
                                                color: rowInfo.index === this.state.selectedProduct ? 'white' : 'black'
                                            }
                                        }
                                    }else{
                                        return {}
                                    }      
                                }}
                                style={{height: "70vh"}}
                            />
                            <button className="btn AdinOrderButtonSizer std_BTN btn-lg mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit/OrderAddress/"+ this.props.match.params.id)}>Edit Address</button>        
                            <button className="col btn AdinOrderButtonSizer blue_BTN mx-2" onClick={this.updateOrder}>Save Content</button>
                            <button className="col btn AdinOrderButtonSizer dark_BTN mx-2" onClick={()=>this.sendToPage("/Admin/Orders")}>Back</button>                       
                    </div>

                </div>       
            </div>                        
        )
    }
}
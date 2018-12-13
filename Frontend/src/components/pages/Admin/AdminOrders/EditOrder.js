import React,{Component} from 'react';
import ReactTable from 'react-table';
import "../../Pages.css";
import {getColumnsFromArray} from './../../../../handlers/columnsHandlers.js';
import {get, put} from './../../../../handlers/requestHandlers.js';
import {makeOrderLinesData, makeCustomerProductsData} from './../../../../handlers/dataHandlers.js';

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
                    <div className="Contents row">
                        <div className="col col-4">
                            <ReactTable 
                                data={orderLines}
                                columns={orderLineColumns}
                                showPagination={false}
                                className="OrderLines -striped -highlight"
                                getTrProps={(state, rowInfo) => {
                                    if (rowInfo && rowInfo.row) {
                                        return {
                                        onClick: (e) => {    
                                            this.setState({selectedOrderLine: rowInfo.index})
                                            console.log(this.state.selectedOrderLine)
                                        },
                                        style: {
                                            background: rowInfo.index === this.state.selectedOrderLine ? '#00afec' : 'white',
                                            color: rowInfo.index === this.state.selectedOrderLine ? 'white' : 'black'
                                        }
                                    }
                                    }else{
                                        return {}
                                    }
                                }}
                                style={{height: "75vh"}}
                            />
                            </div>
                        
                            <div className="col col-2">
                                <div className="h-25"></div>   
                                    <div className="btn-group-vertical">
                                        <div className="row my-5">
                                            <button className=" addButton btn btn-success  mx-1 " onClick={this.addOrderLine}> Add Product </button>
                                            <button className=" removeButton btn btn-danger mx-1 my-5" onClick={this.removeOrderLine}>Remove Product</button>
                                        </div>  
                                    </div>
                                </div>
                            
                                <div className=" col col-5">
                                    <nav className="navbar navbar-light bg-light">
                                        <h className="navnbar" >Publisher stock</h>
                                    </nav>
                                    <ReactTable 
                                        data={stock}
                                        columns={stockColumns}
                                        className="Products"
                                        showPagination={false} 
                                        className="productTable -striped -highlight"
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
                                        style={{height: "75vh"}}
                                    />
                            <div className="Buttons container row my-2">
                                <button className="btn btn-warning btn-lg mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit/OrderAddress/"+ this.props.match.params.id)}>Edit Address</button>        
                                <button className="col  btn btn-success mx-2" onClick={this.updateOrder}>Save Content</button>
                                <button className="col btn btn-info mx-2" onClick={()=>this.sendToPage("/Admin/Orders")}>Back</button>                       
                            </div>
                    </div>
                    </div>
                </div>
            </div>
        )
    }

    
    

}
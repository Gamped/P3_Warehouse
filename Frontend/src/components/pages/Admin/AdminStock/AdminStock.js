import React, { Component } from 'react';
import {connect} from "react-redux";
import "../../Pages.css";
import "./AdminStock.css";
import ReactTable from 'react-table';
import {makeProductsData} from './../../../../handlers/dataHandlers.js'
import {getColumnsFromArray} from './../../../../handlers/columnsHandlers.js';
import {get, del} from './../../../../handlers/requestHandlers.js';
import {entireStockPDF} from './../../../../handlers/pdfHandlers.js';

 class AdminStock extends Component {
    constructor(props) {
        super(props);
        this.state = { products: [], selected: null, selectedId: "" };
       
    }

    componentDidMount() {this.getProducts();}

    getProducts() {
        get('employee/products', (data) => {
            const products = makeProductsData(data);
            this.setState({ products: products });
        });  
    }

    sendToPage = (address) => {this.props.history.push(address);}

    changeToEditPage = () =>{
        if(this.state.selectedId !== ""){
            const item = this.state.products.find(x=>x.hexId===this.state.selectedId)
            this.props.setProductId(item.productId);
            this.props.setProductName(item.productName);
            this.props.setProductQuantity(item.quantity);
            this.props.history.push(`/Admin/Stock/Edit/${this.state.selectedId}`)

        } else{ 
            alert("Please choose an item to edit first.")
        }
    }

    deleteProduct = () => {
        let selectedId = this.state.selectedId;
        if(selectedId !== ""){ 
            if(window.confirm("You are deleting an item")){
                del('employee/products/delete/'+this.state.selectedId, (res) => {
                    let products = this.state.products.filter(product =>{
                        return this.state.selectedId !== product.hexId
                    })
                    this.setState({
                        products:products
                    })      
                })    
            }
        }
    }
    
    newStock = () => {this.props.history.push("/Admin/Stock/New")}

    render() {
        let selectedId = this.state.selectedId
        const columns = getColumnsFromArray(["Product Id", "Product Name", "Owner", "Quantity"]);

        return(
            <div className="PageStyle customText_b">
                <div className="frameBordering MainContainer container row">
                    <div className="Table container col">
                        <h1 className="customText_b_big">Stock</h1>

                        <ReactTable
                            data={this.state.products} 
                            columns={columns} 
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
                                } else {
                                    return {}
                                }
                            }}
                            style={{height: "60vh"}}
                        />
                        
                        <div className="CRUD container row">
                            <div className="">
                                <button  className="green_BTN stockBTNSizer btn my-2" onClick={this.newStock}>New</button>
                            </div>

                            <div action="/Admin/Stock/Remove" className="">
                                <button  className="red_BTN stockBTNSizer btn my-2" onClick={this.deleteProduct}>Remove</button>
                            </div>

                            <div action="/Admin/Stock/Edit" className="">
                                <button  className="blue_BTN stockBTNSizer btn my-2" onClick={this.changeToEditPage} >Edit</button>
                            </div>

                            <button onClick={()=>entireStockPDF(this.state)} className="std_BTN stockBTNSizer btn my-2">Export</button>
                        </div>
                    </div>
                </div>
            </div>   
        );
    }
}

const mapDispatchToProps = (dispatch) =>{
    return{
        setProductId: (id) => {dispatch({type: "SET_PRODUCT_ID",payload: {id}})},
        setProductQuantity: (quantity) => {dispatch({type: "SET_PRODCUT_QUANTITY",payload: {quantity}})},
        setProductName: (name) => {dispatch({type: "SET_PRODUCT_NAME",payload: {name}})},
    }
}

export default connect(null,mapDispatchToProps)(AdminStock)

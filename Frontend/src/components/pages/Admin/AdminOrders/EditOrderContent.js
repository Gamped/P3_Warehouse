import React,{Component} from 'react';
import axios from 'axios';
import ReactTable from 'react-table';
import { Link } from "react-router-dom";
import "../../Pages.css";
import "./EditOrderContent.css"

export default class EditOrderContent extends Component{
    constructor(props){
        super(props);
        const orderId = props;
        this.state = {


            orderLineSelected: null,
            selectedOrderLineId: null,
            productSelected: null,
            selectedProductId: null,

            orderLineData: [],
            productData: []
        }
    }

    componentDidMount(){
    }
    sendToPage = (address) => {
        this.props.history.push(address);
    }

    render(){
        const orderLineColumns = [
            {Header: "Products", accessor:"product"},
            {Header: "Quantity", accessor:"quantity"}
        ]

        const productColumns = [
            {Header: "Products", accessor:"product"},
            {Header: "Selected Quantity", accessor: "selectedQuantity"},
            {Header: "Available Quantity", accessor:"availableQuantity"}
        ]

        return(
            <div className="PageStyle rounded">
                <div className="container col">     
                    <div className="mainContent container row">
                    <div className=" col col-md-auto ">
                        <nav class="navbar navbar-light bg-light"> 
                            <a className="navnbar" >Customer order</a>
                        </nav>   
                        <ReactTable 
                            data={this.state.orderLineData}
                            columns={orderLineColumns}
                            className="OrderLines"
                            showPagination={false} 
                            className="orderLineTable -striped -highlight"
                            getTrProps={(state, rowInfo) => {
                                if (rowInfo && rowInfo.row) {
                                    return {
                                        onClick: (e) => {
                                            this.setStateAsSelected(rowInfo);
                                        this.showOrderLines(rowInfo);
                                    },
                                    style: {
                                        background: rowInfo.index === this.state.orderLineSelected ? '#00afec' : 'white',
                                        color: rowInfo.index === this.state.orderLineSelected ? 'white' : 'black'
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
                        </div> 
                    </div>
                    <div className="finishingButtons container row my-3">
                        <button className="col  btn btn-success mx-2">Save Content</button>
                        <button className="col  btn btn-danger mx-2">Discard Content</button>
                        <button className="col btn btn-info mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit")}>Back</button>                       
                    </div>
                </div>
            </div>
        )
    }
}
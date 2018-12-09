import React,{Component} from 'react';
import axios from 'axios';
import ReactTable from 'react-table';

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
                        <ReactTable 
                            data={this.state.orderLineData}
                            columns={orderLineColumns}
                            showPagination={false} 
                            className="OrderLines orderLineTable -striped -highlight"
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
                        <div className="editButtons container col">
                            <button className="addButton btn btn-success mx-2">Add Product</button>
                            <button className="removeButton btn btn=success mx-2">Remove Product</button>
                        </div>
                        <ReactTable 
                            data={this.state.productData}
                            columns={productColumns}
                            showPagination={false} 
                            className="Products productTable -striped -highlight"
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
                    <div className="finishingButtons container row">
                            <button className="btn btn-success mx-2">Save Content</button>
                            <button className="btn btn-danger mx-2">Discard Content</button>
                    </div>
                </div>
            </div>
        )
    }
}
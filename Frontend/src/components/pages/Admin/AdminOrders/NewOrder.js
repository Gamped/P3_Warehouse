import React,{Component} from 'react';
import axios from 'axios';
import ReactTable from 'react-table';

import "../../Pages.css";
import "./NewOrder.css"

export default class NewOrder extends Component{
    constructor(props){
        super(props);
        this.state = {
            
        };
    }

    render(){
        const customerColumns = [{Header: "Owner", accessor: "owner"}]
        const productColumns = [
            {Header: "Products", accessor:"product"},
            {Header: "Quantity", accessor:"quantity"}
        ]
        return(
            <div className="PageStyle rounded">
                <div className="container col">
                    <h1 className=" text-center">Add new order</h1>
                    <input 
                        type=" text"
                        placeholder={"Order Title"}
                    />
                    <input 
                        type=" text"
                        placeHolder={"Enter the recipient's address"}
                    />
                    <div className="container row">
                        <ReactTable 
                            columns={customerColumns}
                            showPagination={false}
                            className="CustomerTable -striped -highlight"
                        />
                        <div className="container col">
                            <ReactTable 
                                columns={productColumns}
                                showPagination={false}
                                className="SecondaryTable AvailableTable -striped -highlight"
                            />
                            <ReactTable 
                                columns={productColumns}
                                showPagination={false}
                                className="SecondaryTable SelectedTable -striped -highlight"
                            />
                        </div>
                        <div className="container col">
                            <button className="btn btn-succes mx-2">Select Product</button>
                            <button className="btn btn-warning mx-2">Deselect Product</button>
                            <button className="btn btn-danger mx-2">Deselect All Products</button>
                            <button className="btn btn-succes mx-2">Save Order</button>
                            <button className="btn btn-danger mx-2">Discard Order</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
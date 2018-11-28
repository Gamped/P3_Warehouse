import React, { Component } from 'react';
import "../../Pages.css";
import "./AdminStock.css"
import axios from 'axios';
import ReactDOM from 'react-dom'
import { Link } from 'react-router-dom';
import ReactTable from 'react-table';

export default class AdminStock extends Component {
    constructor(props) {
        super(props);
        this.state = { products: [] };
        this.makeRow = this.makeRow.bind(this);
    }

    componentDidMount() {
        axios.get('http://localhost:8080/api/products')
            .then((response) => {
                const products = this.makeRow(response);
                this.setState({ products: products });
              })
    }

    makeRow(response){
      var products = [];
      response.data.forEach((product) => {
        products.push({
          productId: product.productId,
          productName: product.productName,
          quantity: product.quantity
        })
        })
      return products;
    }

    render() {
      const data = this.state.products;
      const columns = [
          {Header: "Product ID", accessor: "productId"},
          {Header: "Product Name", accessor: "productName"},
          {Header: "Quantity", accessor: "quantity"},
          {Header: "Owner", accessor: "owner"}
      ]

        return(
            <div className="PageStyle">

                <div className="topBoxStyle topBox">
                    <h1 className="stockTxt customText_w">Stock</h1>
                </div>

                <div className="leftBoxStyle pickBox">
                    <h1 className="leftTxt customText_b">Filter by:</h1>
                </div>


                <div className="TableContainer">
                    <ReactTable data={data} tableWidth={window.innerWidth} className="AdminStockTable -striped -highlight" columns={columns} defaultPageSize={15}/>
                </div> 

                <div className="bottomBoxStyle bottomBox">
                        <form action="/Admin/Stock/New" className="stockForm">
                            <button  className="stockButton_f btn" >New</button>
                        </form>
                        <form action="/Admin/Stock/Edit" className="stockForm">
                            <button  className="stockButton_f btn" >Edit</button>
                        </form>
                        <form action="/Admin/Stock/Remove" className="stockForm">
                            <button  className="stockButton_f btn" >Remove</button>
                        </form>
                        <button className="stockButton btn">Export</button>
                </div>
            </div>
        );
    }
}

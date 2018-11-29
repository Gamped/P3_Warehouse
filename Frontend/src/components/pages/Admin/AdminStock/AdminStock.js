import React, { Component } from 'react';
import "../../Pages.css";
import "./AdminStock.css"
import axios from 'axios';
import ReactDOM from 'react-dom'
import { Link } from 'react-router-dom';
import ReactTable from 'react-table';
import Buttonlist from '../../../MenuComponents/ButtonList/ButtonList';

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
                <div className="container row">
                    <div className="CustomerList col border-dark rounded bg-secondary">
                        <h1 className="Header">Filter by:</h1>
                    </div>
                    <div className="Table container col">
                        <h1 className="Header">Stock</h1>
                        <ReactTable data={data} columns={columns} showPagination={false} className="-striped -highlight"/>
                        <div className="CRUD container row">
                            <form action="/Admin/Stock/New" className="stockForm">
                                <button  className="CRUDbutton stockButton_f btn" >New</button>
                            </form>
                            <form action="/Admin/Stock/Edit" className="stockForm">
                                <button  className="CRUDbutton stockButton_f btn" >Edit</button>
                            </form>
                            <form action="/Admin/Stock/Remove" className="stockForm">
                                <button  className="CRUDbutton stockButton_f btn" >Remove</button>
                            </form>
                            <button className="CRUDbutton stockButton_f btn">Export</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

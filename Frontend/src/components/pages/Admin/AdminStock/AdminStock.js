import React, { Component } from 'react';
import "../../Pages.css";
import "./AdminStock.css"
import axios from 'axios';
import ReactTable from 'react-table';

export default class AdminStock extends Component {
    constructor(props) {
        super(props);
        this.state = { products: [], selected: null, selectedId: "" };
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
                quantity: product.quantity,
                owner: product.owner.nickName,
                hexId: product.hexId
            })
        });
        return products;
    }

    sendToPage = (address) => {
        this.props.history.push(address);
    }

    removeItem = () => {
        const selectedId = this.state.selectedId;
        if(selectedId !== ""){
            if(window.confirm("You are deleting an item")){
                //Todo: Få den til at remove et product by id. axios.remove()
            }
        }
        
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
            <div className="PageStyle rounded">
                <div className="MainContainer container row">
                    <div className="CustomerList col border-dark rounded bg-secondary">
                        <h1 className="Header">Filter by:</h1>
                    </div>
                    <div className="Table container col">
                        <h1 className="Header">Stock</h1>

                    <ReactTable 
                        data={data} 
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
                            }else{
                              return {}
                            }
                        }}
                          />

                        <div className="CRUD container row">
                            <div className="">
                                <button  className="btn btn-block" onClick={()=>this.sendToPage("/Admin/Stock/New")}>New</button>
                            </div>
                            <div action="/Admin/Stock/Edit" className="">
                                <button  className="btn btn-block" onClick={()=>this.sendToPage("/Admin/Stock/Edit")}>Edit</button>
                            </div>
                            <div action="/Admin/Stock/Remove" className="">
                                <button  className="btn btn-block" onClick={this.removeItem}>Remove</button>
                            </div>
                            <div>
                                <button className="btn btn-block">Export</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>   
        );
    }
}

import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import "../../Pages.css";
import "./AdminStock.css"
import axios from 'axios';
import ReactTable from 'react-table';
import {makeProductsRowsFromResponseData} from './../../../../handlers/dataHandlers.js'
import {getColumnsFromArray} from './../../../../handlers/columnsHandlers.js';
import {get, del} from './../../../../handlers/requestHandlers.js';

export default class AdminStock extends Component {

    constructor(props) {
        super(props);
        this.state = { products: [], selected: null, selectedId: "" };
       
    }

    componentDidMount() {
       
        this.getProducts();
    }

    getProducts() {
        get('employee/products', (data) => {
            const products = makeProductsRowsFromResponseData(data);
            this.setState({ products: products });
        });  
    }

    sendToPage = (address) => {
        this.props.history.push(address);
    }

    deleteProduct = () => {
        let selectedId = this.state.selectedId;
        if(selectedId !== ""){
            
            if(window.confirm("You are deleting an item")){
               
               del('employee/products/'+this.state.selectedId, (res) => {
                window.location.reload()
               })
               
            }    
        }
        
    }

    export = () => {
        const pdfConverter = require('jspdf');
        const doc = new pdfConverter();
        const elements= {...this.state.products}
        
        doc.setFontSize(22);
        doc.text(20,50,"Entire stock:");
        doc.setFontSize(10);
        let pdfXPlace = 25;
        let pdfYPlace = 65;
        let counter = 0;
        for (const key in elements){
            
            doc.text("Name: "+elements[key].productName,pdfXPlace,pdfYPlace);
            doc.text("Quantity: " + elements[key].quantity,pdfXPlace+120,pdfYPlace);
            doc.line(20,pdfYPlace+5,175,pdfYPlace+5);
            pdfYPlace += 17;
            counter += 1;
            if(counter%25===0){
                doc.addPage()
            }
        }

        doc.save("EntireStock.pdf")

    }

    render() {
        
        let selectedId = this.state.selectedId
        const columns = getColumnsFromArray(["Product Id", "Product Name", "Quantity"]);

        return(
            <div className="PageStyle rounded">
                <div className="MainContainer container row">
                    <div className="CustomerList col border-dark rounded bg-secondary">
                        <h1 className="Header">Filter by:</h1>
                    </div>
                    <div className="Table container col">
                        <h1 className="Header">Stock</h1>

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
                            }else{
                              return {}
                            }
                        }}
                          />
                        
                        <div className="CRUD container row">
                            <div className="">
                                <button  className="btn-success btn-lg btn-block my-2" onClick={()=>this.sendToPage("/Admin/Stock/New")}>New</button>
                            </div>
                            <div action="/Admin/Stock/Edit" className="">
                                <Link  className="btn-lg btn-block btn-warning my-2" to={`/Admin/Stock/Edit/${selectedId}`}>Edit</Link>
                                
                            </div>
                            <div action="/Admin/Stock/Remove" className="">
                                <button  className="btn-lg btn-danger btn-block my-2" onClick={this.deleteProduct}
                                >Remove</button>
                            </div>
                            <div>
                                <button onClick={this.export} className="btn-lg btn-block btn-block my-2">Export</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>   
        );
    }
}

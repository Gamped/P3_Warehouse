import React, { Component } from 'react';
import "../../Pages.css";
import "./AdminStock.css"
import axios from 'axios';
import { Link } from 'react-router-dom';

export default class AdminStock extends Component {
    constructor(props) {
        super(props);
        this.state = {products: []};
    }

    componentDidMount() {
        console.log("Did mount");
        axios.get('http://localhost:8080/api/products')
            .then(response => {
                this.setState({ products: response.data });
                console.log(response);
            }
        )
    }

    render() {
        return(
            <div className="PageStyle">

                <div className="topBoxStyle topBox">
                    <h1 className="stockTxt customText_w">Stock</h1>
                </div>

                <div className="leftBoxStyle pickBox">
                    <h1 className="leftTxt customText_b">Filter by:</h1>
                </div>

                <div className="stockListBox 'contentBoxStyle'">
                    <table className="stockTable">
                        <tbody>
                            <tr>
                                <th>Product name</th>
                                <th>Quantity</th>
                                <th>Owner</th>
                                <th>Pick</th>
                                <th>Product Number</th>
                            </tr>
                            {this.state.products.map(product =>
                                <tr>
                                    <td><Link to={`/Admin/Stock/Edit/${product.hexId}`}>{product.productName}</Link></td>
                                    <td>{product.quantity}</td>
                                    <td>{product.owner}</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
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

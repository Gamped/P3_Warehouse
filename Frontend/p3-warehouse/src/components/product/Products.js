import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router-dom';
import axios from 'axios';

class Products extends Component {

    constructor(props) {
        super(props);

        this.state = { products: [] };

    }

    componentDidMount() {
        console.log("Did mount");
        axios.get('/api/products')
            .then(response => {
                this.setState({ products: response.data });
                console.log(this.state.products);
            })
    }

    render() {
        return (
            <div className="container">
                <div className="panel panel-default">
                    <div className="panel-heading">
                        <h3 className="panel-title">
                            Product list
                        </h3>
                    </div>
                    <div className="panel-body">
                        <h4><Link to="/products/create"><span className="glyphicon glyphicon-plus-sign"
                                                     aria-hidden="true"></span> Add Product</Link></h4>
                        <table className="table table-stripe">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Quantity</th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.state.products.map(p =>
                                <tr>
                                    <td><Link to={`/products/show/${p.id}`}>{p.name}</Link></td>
                                    <td>{p.quantity}</td>
                                </tr>
                            )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        )
    }
}

export default Products;
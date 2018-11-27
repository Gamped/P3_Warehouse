import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import {Link} from 'react-router-dom';

class CreateProduct extends Component {
    constructor(){
        super();
        this.state = {
            name: '',
            quantity: ''
        };
    }

    onChange = (e) => {
        const state = this.state;
        state[e.target.name] = e.target.value;
        this.setState(state);
    }

    onSubmit = (e) => {
        e.preventDefault();
        const {name, quantity} = this.state;

        axios.post('/api/products', {name, quantity}).then((result)=> {
            this.props.history.push("/");
        });
    }


    render(){
        const {name, quantity} = this.state;

        return (
            <div className="container">
                <div className="panel panel-default">
                    <div className="panel-heading">
                        <h3 className="panel-title">
                            Add Product
                        </h3>
                    </div>
                    <div className="panel-body">
                        <h4><Link to="/"><span className="glyphicon glyphicon-th-list"
                                               aria-hidden="true"></span> Product List</Link></h4>
                        <form onSubmit={this.onSubmit}>
                            <div className="form-group">
                                <label htmlFor="title">Name:</label>
                                <input type="text" className="form-control" name="product-name" value={name}
                                       onChange={this.onChange} placeholder="Product name"/>
                            </div>
                            
                            <div className="form-group">
                                <label htmlFor="title">Quantity:</label>
                                <input type="text" className="form-control" name="quantity" value={quantity}
                                       onChange={this.onChange} placeholder="Quantity"/>
                            </div>

                            <button type="submit" className="btn btn-default">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default CreateProduct;

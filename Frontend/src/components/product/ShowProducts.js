import React, { Component } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

class ShowProducts extends Component {

    constructor(props) {
        super(props);
        this.state = { product: {} };

    }

    componentDidMount(){
        axios.get('/api/products/' + this.props.match.params.id)
            .then(response => {
                this.setState({ product: response.data });
                console.log(this.state.product);

            });
    }

    delete(id){
        console.log(id);
        axios.delete('/api/products/'+id)
            .then((result) => {
                this.props.history.push("/")
            });
    }

    render() {

        return (
            <div className="container">
                <div className="panel panel-default">
                    <div className="panel-heading">
                        <h3 className="panel-title">
                            Product
                        </h3>
                    </div>
                    <div className="panel-body">
                        <h4><Link to="/"><span className="glyphicon glyphicon-th-list"
                                               aria-hidden="true"></span> List of products</Link></h4>
                        <dl>
                            <dt>Name:</dt>
                            <dd>{this.state.product.name}</dd>
                            <dt>Quantity:</dt>
                            <dd>{this.state.product.quantity}</dd>

                        </dl>
                        <Link to={`/edit/${this.state.product.id}`} class="btn btn-success">Edit</Link>&nbsp;
                        <button onClick={this.delete.bind(this, this.state.product.id)}
                                className="btn btn-danger">Delete
                        </button>
                    </div>
                </div>
            </div>


        )


    }


}

export default ShowProducts;
import React, {Component} from 'react';
import axios from 'axios';
import {Link} from 'react-router-dom';

class EditProduct extends Component {

    constructor(props) {
        super(props);

        this.state = { product: {} };

    }

  componentDidMount() {
        axios.get('/api/products/'+this.product.match.params.id)
        .then(response => {
                this.setState({ product: response.data });
                console.log("Response.data = " + response.data)
                console.log(this.state.product);
        })
  }

  onChange = (e) => {
        const state = this.state.product;
        state[e.target.name] = e.target.value;
        this.setState({product:state});

  }

  onSubmit = (e) => {
        e.preventDefault();

        const { name, quantity } = this.state.product;

        axios.put('/api/products/'+this.props.match.params.hexId, {name, quantity})
            .then((result) => {

                this.props.history.push("/api/products/show/"+this.props.match.params.hexId)
            });

  }

  render() {
      return (
          <div class="container">
              <div class="panel panel-default">
                  <div class="panel-heading">
                      <h3 class="panel-title">
                          Edit Product
                      </h3>
                  </div>
                  <div class="panel-body">
                      <h4><Link to={`/show/${this.state.product.id}`}><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> Product List</Link></h4>
                      <form onSubmit={this.onSubmit}>
                          <div class="form-group">
                              <label for="name">Name:</label>
                              <input type="text" class="form-control" name="name" value={this.state.product.name} onChange={this.onChange} placeholder="Name" />
                          </div>
                          <div class="form-group">
                              <label for="quantity">Quantity:</label>
                              <input type="text" class="form-control" name="quantity" value={this.state.product.quantity} onChange={this.onChange} placeholder="Quantity" />
                          </div>
                          <button type="submit" class="btn btn-default">Update</button>
                      </form>
                  </div>
              </div>
          </div>
      )
  }

}

export default EditProduct;

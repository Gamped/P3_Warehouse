import React, {Component} from 'react';
import axios from 'axios';
import {Link} from 'react-router-dom';

import "../../Pages.css";
import "./AdminStock.css";

export default class EditWare extends Component {

      constructor(props) {
          super(props);

          this.state = { product: {} };
      }

    componentDidMount() {
    const id = this.props.match.params.id;
    console.log(id);
          axios.get('http://localhost:8080/api/products/' + id).
             then(response => {
                 this.setState({ product: response.data });

                  console.log(this.state.product);

          })
    }

    onChange = (e) => {
          const state = this.state.product;
          state[e.target.name] = e.target.value;
          this.setState({product : state});

    }

    onSubmit = (e) => {

          e.preventDefault();
          const { name, quantity, owner } = this.state.product;
          axios.put('http://localhost:8080/api/products/edit/'+this.props.match.params.hexId, {name, quantity, owner})
              .then((result) => {
                  this.props.history.push("/Admin/Stock/"+this.props.match.params.hexId)
              });

    }

    render(){
        return(
        <div className="PageStyle">
            <customText_b_big className="title">Edit product:</customText_b_big>
            <customText_b className="subTitle">"Product name"</customText_b>
            <form>
                <input
                    type="text"
                    className="newForm"
                    defaultValue={this.state.product.name}
                    onChange={this.onChange}
                    placeholder="Product name"/>
                <input
                    type="text"
                    className="newForm"
                    defaultValue={this.state.product.quantity}
                    onChange={this.onChange}
                    placeholder="Quantity"/>
                <input
                    type="text"
                    className="newForm"
                    defaultValue={this.state.product.owner}
                    onChange={this.onChange}
                    placeholder="Owner"/>
            </form>
            <form action="/Admin/Stock" className="newForm stockForm">
                <button className="newButton stockButton_f btn">Back</button>
            </form>
            <form action="/Admin/Stock" className="newForm stockForm">
                <button className="newButton stockButton_f btn" type="submit">Edit product</button>
            </form>
            <customText_b className="note">Please double check to make sure you have changed to the correct info</customText_b>
        </div>);
    }
}

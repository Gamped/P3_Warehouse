import React, {Component} from 'react';
import axios from 'axios';

import "../../Pages.css";
import "./AdminStock.css";

export default class EditWare extends Component {
    constructor(props) {
        super(props);
        this.state = { product: {}, hexId: this.props.match.params.id };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        axios.get('http://localhost:8080/api/products/' + this.state.hexId)
            .then(response => {
                 this.setState({ product: response.data });
                 console.log(this.state.product);
            }
        )
    }

    onChange = (e) => {
        const state = this.state.product;
        state[e.target.name] = e.target.value;
        this.setState({product:state});
    }

    onSubmit = (e) => {
        e.preventDefault();
        const { productName, productId, quantity } = this.state.product;
    
        axios.put('http://localhost:8080/api/products/edit/'+this.state.hexId, {productName, productId, quantity})
            .then((result) => {

                 this.props.history.push("/Admin/Stock/");
            });
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <h1 className="title customText_b_big">Edit product:</h1>
                <h1 className="subTitle customText_b">"Product name"</h1>
                <div>
                    <input
                        type="text"
                        name="productName"
                        className="newForm"
                        defaultValue={this.state.product.productName}
                        onChange={this.onChange}
                        placeholder="Product Name"/>
                    <input
                        type="text"
                        className="newForm"
                        name="productId"
                        defaultValue={this.state.product.productId}
                        onChange={this.onChange}
                        placeholder="Product ID"/>
                    <input
                        type="text"
                        className="newForm"
                        name="quantity"
                        defaultValue={this.state.product.quantity}
                        onChange={this.onChange}
                        placeholder="Quantity"/>
                 </div>
                 <div className="newForm stockForm">
                    <button className="newButton stockButton_f btn">Back</button>
                 </div>
                 <div className="newForm stockForm">
                    <button className="newButton stockButton_f btn" onClick={this.onSubmit} >Edit product</button>
                 </div>
                 <h1 className="note customText_b_big">Please double check to make sure you have changed to the correct info</h1>
            </div>
        );
    }
}

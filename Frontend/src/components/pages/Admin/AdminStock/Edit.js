import React, {Component} from 'react';
import "../../Pages.css";
import "./AdminStock.css";
import {setProductProps} from './../../../../handlers/dataHandlers.js'
import {get, put} from './../../../../handlers/requestHandlers.js';
import {Link} from "react-router-dom";
import {connect} from "react-redux";

class Edit extends Component {
    constructor(props) {
        super(props);
        this.state = { product: this.props.product, hexId: this.props.match.params.id };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        
    }


    onChange = (e) => {
        const state = this.state.product;
        state[e.target.name] = e.target.value;
        this.setState({product:state});
    }

    onSubmit = (e) => {
        e.preventDefault();
        const { productName, productId, quantity } = this.state.product;
    
        put('employee/product/edit/'+this.state.hexId, {productName, productId, quantity}, () => {
            this.props.history.push("/Admin/Stock/");
        })
    }

    render(){
        return(
            
            <div className="PageStyle rounded">
                <nav class="navbar navbar-light bg-light">
                    <h1 className="customText_b_big">Edit product:</h1>
                    <h1 className="subTitle customText_b">{this.state.product.name} </h1>´
                </nav>
                 <div className=" row">
                    <div className="col-md-4 offset-md-5 my-5">
                        <div className="input-group-prepend my-2">
                        <input
                            type="text"
                            name="productName"
                            className="newForm"
                            defaultValue={this.state.product.name}
                            onChange={this.onChange}
                            placeholder="Product Name"/>
                        </div>
                        <div className="input-group-prepend my-2">
                        <input
                            type="text"
                            className="newForm"
                            name="productId"
                            defaultValue={this.state.product.id}
                            onChange={this.onChange}
                            placeholder="Product ID"/>
                        </div>
                        <div className="input-group-prepend my-2">
                        <input
                            type="text"
                            className="newForm"
                            name="quantity"
                            defaultValue={this.state.product.quantity}
                            onChange={this.onChange}
                            placeholder="Quantity"/>
                            </div>
                    </div>
                        <div class="w-100"></div>
                        <div className="col-md-6 offset-md-5">
                                <button className="btn btn-warning" onClick={this.onSubmit} >Edit product</button>´
                                <Link to="/Admin/Stock" className="btn btn-info">Back</Link>
                        </div>
                    </div>
            </div>
        );
    }
}

const mapStateToProps = (state) =>{
    return{
        product:state.productReducer
    }
}

export default connect(mapStateToProps)(Edit);
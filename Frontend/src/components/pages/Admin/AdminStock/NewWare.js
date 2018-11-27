import React,{Component} from 'react';
import "../../Pages.css";
import "./AdminStock.css";
import axios from 'axios';


export default class NewWare extends Component {
    constructor(props) {
        super(props);
        this.state = {
            productName: "",
            productId: "",
            quantity: 0
        };

        this.onChangeProductName = this.onChangeProductName.bind(this);
        this.onChangeProductId = this.onChangeProductId.bind(this);
        this.onChangeQuantity = this.onChangeQuantity.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit = (e) => {
        e.preventDefault();
        const {productName, productId, quantity} = this.state;

        console.log({productName, productId, quantity});

        setTimeout(function () {
            axios.post('http://localhost:8080/api/products/new', {productName, productId, quantity}).then((result)=> {
                this.props.history.push("/");
            }).catch((err) => {
            console.log(err.response);
            });
        }, 1000);
    }

    onChangeProductName = (e) => {
        this.setState({ productName: e.target.value});
    }

    onChangeProductId = (e) => {
        this.setState({ productId: e.target.value});
    }

    onChangeQuantity = (e) => {
        this.setState({ quantity: e.target.value});
    }


    render() {
      const {productName, productId, quantity} = this.state;

        return (
            <div className="PageStyle">
                <h1 className="title customText_b_big">Add new product</h1>
                <form>
                    <input
                        type="text"
                        className="newForm"
                        defaultValue={productName}
                        onChange={this.onChangeProductName}
                        placeholder="Product Name"/>
                    <input
                        type="text"
                        className="newForm"
                        defaultValue={productId}
                        onChange={this.onChangeProductId}
                        placeholder="Product Id"/>
                    <input
                        type="text"
                        className="newForm"
                        defaultValue={quantity}
                        onChange={this.onChangeQuantity}
                        placeholder="Quantity"/>
                </form>
                <form action="/Admin/Stock" className="newForm stockForm">
                    <button className="newButton stockButton_f btn">Back</button>
                </form>
                <form className="newForm stockForm">
                    <button className="newButton stockButton_f btn" onClick={this.onSubmit}>Create product</button>
                </form>
            </div>
        )     
    }
}

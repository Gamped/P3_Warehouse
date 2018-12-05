import React,{Component} from 'react';
import axios from 'axios';
import "../../Pages.css";
import "./AdminStock.css";


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
        const {productName, productId, quantity,owner} = this.state;

        console.log({productName, productId, quantity});

        setTimeout(function () {
            axios.post('http://localhost:8080/api/products/new', {productName, productId, quantity, owner}).then((result)=> {
                this.props.history.goBack();
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
    onChangeOwner = (e) => {
        this.setState({  owner : e.target.value});
    }


    render() {
      const {productName, productId, quantity, Owner} = this.state;

        return (
            <div className="PageStyle rounded">
                <h1 className=" text-center">Add new product</h1>
                <form>
                    <input
                        type="text "
                        className="my-2 form-control  "
                        defaultValue={productName}
                        onChange={this.onChangeProductName}
                        placeholder="Product Name"/>
                    <input
                        type="text"
                        className="my-2 form-control "
                        defaultValue={productId}
                        onChange={this.onChangeProductId}
                        placeholder="Product Id"/>
                    <input
                        type="text"
                        className="my-2 form-control"
                        //defaultValue={quantity}
                        defaultValue={null}
                        onChange={this.onChangeQuantity}
                        placeholder="Quantity"/>
                    <input
                        type="text"
                        className="my-2 form-control"
                        defaultValue={null}
                        onChange={this.onChangeOwner}
                        placeholder="Owner"/>
                </form>
                <form className="">
                    <button className="btn-success btn-lg btn-block btn my-2" onClick={this.onSubmit}>Create product</button>
                </form>
                <form action="/Admin/Stock" className="">
                    <button className="btn-info btn-lg btn-block btn my-2">Back</button>
                </form>
               
            </div>
        )     
    }
}

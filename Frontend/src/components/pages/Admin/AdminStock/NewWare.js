import React,{Component} from 'react';
import axios from 'axios';
import "../../Pages.css";
import "./AdminStock.css";


export default class NewWare extends Component {

    constructor(props) {
        
        super(props);
        this.state = {
            product: {},
            owners: []
        };

        this.onChangeProductName = this.onChangeProductName.bind(this);
        this.onChangeProductId = this.onChangeProductId.bind(this);
        this.onChangeQuantity = this.onChangeQuantity.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
       
        this.getOwnerList = this.getOwnerList.bind(this);
        this.setOwnerList = this.setOwnerList.bind(this);
        
    }

    componentDidMount() {
      
        this.getOwnerList();
    }

    
    getOwnerList = (e) => {

  //      axios.get('http://localhost:8080/api/employee/customerList')
  //      .then(response => { 
  //          console.log(response.data);
  //          this.setOwnerList(response.data);
  //      })
    }

    setOwnerList = (data) => {
        let owners = [];
        data.forEach((owner) => {
            owners.push({
                ownerName: owner.nickName,
                hexId: owner.hexId
            })
        })
        this.setState({owners: owners});
    }



    onSubmit = (e) => {
        e.preventDefault();
        const {productName, productId, quantity} = this.state;

        console.log({productName, productId, quantity});

        
            axios.post('http://localhost:8080/api/employee/products', {productName, productId, quantity}).then((result)=> {
            
                this.props.history.goBack();
            }).catch((err) => {
            
                console.log(err.response);
            });
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
      const currentProduct = this.state.product;

        return (
            <div className="PageStyle rounded">
                <h1 className=" text-center">Add new product</h1>
                <form>
                    <input
                        type="text "
                        className="my-2 form-control  "
                        defaultValue={currentProduct.productName}
                        onChange={this.onChangeProductName}
                        placeholder="Product Name"/>
                    <input
                        type="text"
                        className="my-2 form-control "
                        defaultValue={currentProduct.productId}
                        onChange={this.onChangeProductId}
                        placeholder="Product Id"/>
                    <input
                        type="text"
                        className="my-2 form-control"
                        defaultValue={currentProduct.quantity}
                        onChange={this.onChangeQuantity}
                        placeholder="Quantity"/>
                    <input
                        type="text"
                        className="my-2 form-control"
                        defaultValue={null}
                        placeholder="Owner"/>
                </form>
                <form className="" action="/Admin/Stock">
                    <button className="btn-success btn-lg btn-block btn my-2" onClick={this.onSubmit}>Create product</button>
                </form>
                <form action="/Admin/Stock" className="">
                    <button className="btn-info btn-lg btn-block btn my-2">Back</button>
                </form>
               
            </div>
        )     
    }
}

import React,{Component} from 'react';
import axios from 'axios';
import "../../Pages.css";
import "./AdminStock.css";
import { Link } from "react-router-dom";


export default class NewWare extends Component {

    constructor(props) {
        
        super(props);
        this.state = {
            product: {},
            owners: [],
            selectedOwnerHexId: "DEFAULT",
            selectedOwnerUserType: "DEFAULT"
        };

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.getCustomers = this.getCustomers.bind(this);
        this.makeOwnerData = this.makeOwnerData.bind(this);
    }

    componentDidMount() {
      
        this.getCustomers();
    }

    
    getCustomers() {

       axios.get('http://localhost:8080/api/employee/publishers')
        .then(response => { 
            this.makeOwnerData(response.data);
        })
    }

    makeOwnerData(data) {
        this.setState({rawOwnerData: data});
        let owners = [];

        data.forEach((publisher) => {
            owners.push({
                ownerName: publisher.contactInformation.nickName,
                hexId: publisher.hexId,
                userType: publisher.userType
            })

            if (publisher.numberOfClients !== 0) {

                publisher.clientStream.forEach((client) => {
                    owners.push({
                       ownerName: client.contactInformation.nickName,
                       hexId: client.hexId,
                       userType: client.userType
                   })
                  })      
            }
        })

        this.setState({owners: owners});
    }


    onSubmit = (e) => {
        e.preventDefault();
        const {productName, productId, quantity} = this.state.product;

            axios.post('http://localhost:8080/api/employee/products/'
                + "assignTo=" + this.state.selectedOwnerHexId + "/withUserType=" + 
                this.state.selectedOwnerUserType,
                {productName, productId, quantity}).then((result)=> {

                    return(
                        <div class="alert alert-success">
                        <strong>Product added!</strong>
                        </div>)
            }).catch((err) => {
            
                console.log(err.response);
            });
    }

    onChange = (e) => {
        const state = this.state.product;
        state[e.target.name] = e.target.value;
        this.setState({product:state});
    }
    
    
      

    getOwnerListItems(owner, i) {
         return (
         <option 
         key={i}
         onSelect={()=> {
            this.setState({selectedOwnerHexId: owner.hexId})
         }} value={owner.hexId}>
         {owner.userType} - {owner.nickName}
         </option>
         )        
    }
    

    render() {
      
        const currentProduct = this.state.product;

        return (
            <div className="PageStyle rounded">
                <h1 className=" text-center">Add new product</h1>
                <form>
                    <input
                        type="text "
                        name="productName"
                        className="my-2 form-control  "
                        defaultValue={currentProduct.productName}
                        onChange={this.onChangeProductName}
                        placeholder="Product Name"/>
                    <input
                        type="text"
                        name="productId"
                        className="my-2 form-control "
                        defaultValue={currentProduct.productId}
                        onChange={this.onChangeProductId}
                        placeholder="Product Id"/>
                    <input
                        type="text"
                        name="quantity"
                        className="my-2 form-control"
                        defaultValue={currentProduct.quantity}
                        onChange={this.onChangeQuantity}
                        placeholder="Quantity"/>
                    
                    <select className="custom-select my-2" >
                    <option selected>Choose owner</option>
                    {this.state.owners.map(this.getOwnerListItems)}
                    </select>   
                </form>


                <div className="" action="/Admin/Stock">
                    <button className="btn-success btn-lg btn-block btn my-2" onClick={this.onSubmit}>Create product</button>
                </div>
                
                <Link to="/Admin/Stock" className="btn-info btn-lg btn-block btn my-2">Back</Link>
               
            </div>
        )     
    }
}

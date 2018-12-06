import React,{Component} from 'react';
import axios from 'axios';
import ReactTable from 'react-table';

import "../../Pages.css";
import "./NewOrder.css"

export default class NewOrder extends Component{
    constructor(props){
        super(props);
        this.state = {
            address: {},
            owners: [],
            products: [],
            orderLines: [],
            selectedProduct: null,
            selectedOrderLine: null,
            selectedOwner: null,
            selectedOwnerId: "",
            selectedProductId: ""        
        };
   

    }



    componentDidMount() {
        this.getProducts();
        this.getOwners();

    }

    getProducts = () => {

        axios.get('http://localhost:8080/api/employee/products')
            .then((response) => {
                const products = this.makeProductRows(response.data);
                this.setState({ products: products });
            })
    }

    getOwners = () => {

        axios.get('http://localhost:8080/api/employee/clients')
        .then((response) => {
                const clients = this.makeOwnerRows(response.data);
                this.setState({ clients: clients });
            })
       
         axios.get('http://localhost:8080/api/employee/publishers')
            .then((response) => {
                const publishers = this.makeOwnerRows(response.data)
                this.setState({publishers: publishers});
            })  
       
    }

    makeOwnerRows = (data) => {
        let ownerCopy = this.state.owners;
        
        data.forEach((owner) => {
            let nickName = "Nickname";
            
            ownerCopy.push({
                nickName: nickName,
                hexId: owner.hexId
            })

        });

        this.setState({owners: ownerCopy});
    }


    makeProductRows = (data) => {
        var products = [];
        data.forEach((product) => {
            products.push({
                productId: product.productId,
                productName: product.productName,
                quantity: product.quantity,
                hexId: product.hexId
            })
        });
        return products;
    }


    onChange = (e) => {
        const state = this.state.product;
        state[e.target.name] = e.target.value;
        this.setState({product:state});
    }

    onSubmit = () => {


    }

    render(){
        const customerColumns = [{Header: "Owner", accessor: "owner"}]
        const productColumns = [
            {Header: "Products", accessor:"product"},
            {Header: "Quantity", accessor:"quantity"}
        ]
        return(
            <div className="PageStyle rounded">
                <div className="container col">
                    <h1 className=" text-center">Add new order</h1>
                    <input 
                        type=" text"
                        placeholder={"Order Title"}
                    />

                    
                    <div className="container row">
                        <ReactTable 
                            data={this.state.owners}
                            columns={customerColumns}
                            showPagination={false}
                            className="CustomerTable -striped -highlight"
                            getTrProps={(state, rowInfo) => {
                                if (rowInfo && rowInfo.row) {
                                  return {
                                    onClick: (e) => {
                                        
                                      this.setState({selectedOwner: rowInfo.index, selectedOwnerId: rowInfo.original.hexId })
                                      console.log(rowInfo.original)
                                    },
                                    style: {
                                      background: rowInfo.index === this.state.selected ? '#00afec' : 'white',
                                      color: rowInfo.index === this.state.selected ? 'white' : 'black'
                                    }
                                  }
                                }else{
                                  return {}
                                }
                            }}
                        />
                        <div className="container col">
                            <ReactTable 
                                data={this.state.products}
                                columns={productColumns}
                                showPagination={false}
                                className="SecondaryTable AvailableTable -striped -highlight"
                                getTrProps={(state, rowInfo) => {
                                    if (rowInfo && rowInfo.row) {
                                      return {
                                        onClick: (e) => {
                                            
                                          this.setState({selectedProduct: rowInfo.index, selectedProductId: rowInfo.original.hexId })
                                          console.log(rowInfo.original)
                                        },
                                        style: {
                                          background: rowInfo.index === this.state.selected ? '#00afec' : 'white',
                                          color: rowInfo.index === this.state.selected ? 'white' : 'black'
                                        }
                                      }
                                    }else{
                                      return {}
                                    }
                                }}
                            />
                            <ReactTable 
                                data={this.state.orderLines}
                                columns={productColumns}
                                showPagination={false}
                                className="SecondaryTable SelectedTable -striped -highlight"
                                getTrProps={(state, rowInfo) => {
                                    if (rowInfo && rowInfo.row) {
                                      return {
                                        onClick: (e) => {
                                            
                                          this.setState({selectedOrderLine: rowInfo.index })
                                          console.log(rowInfo.original)
                                        },
                                        style: {
                                          background: rowInfo.index === this.state.selected ? '#00afec' : 'white',
                                          color: rowInfo.index === this.state.selected ? 'white' : 'black'
                                        }
                                      }
                                    }else{
                                      return {}
                                    }
                                }}
                            />
                        </div>
                        <div className="container col">
                            <button className="btn btn-succes mx-2">Select Product</button>
                            <button className="btn btn-warning mx-2">Deselect Product</button>
                            <button className="btn btn-danger mx-2">Deselect All Products</button>
                            <button className="btn btn-succes mx-2">Save Order</button>
                            <button className="btn btn-danger mx-2">Discard Order</button>
                        </div>

                        <div className="CRUDButtons">
                            <input type="text" name="company" className="newForm" onChange={this.onChange} placeholder="Company"/> 
                            <input type="text" name="contactPerson" className="newForm" onChange={this.onChange} placeholder="Contact Person"/>
                            <input type="text" name="address" className="newForm" onChange={this.onChange} placeholder="Address"/>
                            <input type="text" name="zipCode" className="newForm" onChange={this.onChange} placeholder="Zipcode"/>
                            <input type="text" name="city" className="newForm" onChange={this.onChange} placeholder="City"/>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
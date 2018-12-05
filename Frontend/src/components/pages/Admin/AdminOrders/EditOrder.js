import React,{Component} from 'react';
import axios from 'axios';
import ReactTable from 'react-table';

import "../../Pages.css";

export default class EditOrder extends Component{
    constructor(props){
        super(props);
        this.state = {
            //There's prolly a more beautiful way of setting these using arrays.
            //Please refactor them if you know about it.
            companyInfo: props,
            contactPersonInfo: props,
            addressInfo: props,
            zipCodeInfo: props,
            cityInfo: props,
            
            orderLineData: [],
            productData: [],
        }
    }

    render(){
        const orderLineColumns=[ /*You might wanna do something fancy where you can edit the quantity directly in the table*/
            {Header: "Products", accessor:"product"},
            {Header: "Quantity", accessor:"quantity"}
        ];

        const productColumns=[
            {Header: "Products", accessor:"product"},
            {Header: "Specified Quantity", accessor:"specifiedQuantity"},   /*  This one should definitely have an input field
                                                                                to enable a specific quantity of a product being added.
                                                                                Remember to make sure the quantity doesn't go over what
                                                                                is available.*/
            {Header: "Available Quantity", accessor:"availableQuantity"}
        ];
        
        return(
            <div className="PageStyle rounded">
                <div className="container col">
                    <div className="container row">
                        <div className="TextFields container col" /*Funny order information editing*/> 
                            <input type="text" name="company" className="newForm" onChange={this.onChange} placeholder="Company" defaultValue={this.state.companyInfo}/> 
                            <input type="text" name="contactPerson" className="newForm" onChange={this.onChange} placeholder="Contact Person" defaultValue={this.state.contactPersonInfo}/>
                            <input type="text" name="address" className="newForm" onChange={this.onChange} placeholder="Address" defaultValue={this.state.addressInfo}/>
                            <input type="text" name="zipCode" className="newForm" onChange={this.onChange} placeholder="Zipcode" defaultValue={this.state.zipCodeInfo}/>
                            <input type="text" name="city" className="newForm" onChange={this.onChange} placeholder="City" defaultValue={this.state.cityInfo}/>
                        </div>
                        <div className="OrderLines container col">
                            <button className="RemoveOrderLine btn btn-warning mx-2">Remove Selected Orderline</button>
                            <ReactTable /*List of orderlines already present in the order*/
                                columns={orderLineColumns}
                                data={this.state.orderLineData}
                                showPagination={false}
                                className="SelectedTable -striped -highlight"
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
                        <div className="AllProducts container col">
                        <button className="AddOrderLine btn btn-success mx-2">Add Selected Product</button>
                                <ReactTable /*List of products the owner of the order has access to*/
                                    columns={productColumns}
                                    data={this.state.productData}
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
                    </div>
                    <div className="container row">
                        <button className="btn btn-success mx-2">Save Changes</button>
                        <button className="btn btn-danger mx-2">Discard Changes</button>
                    </div>
                </div>
            </div>
        )
    }
}
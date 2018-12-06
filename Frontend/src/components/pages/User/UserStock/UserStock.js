import React from 'react';
import ReactTable from 'react-table';
import axios from 'axios';

import GenericTable from '../../../MenuComponents/GenericTable/GenericTable';

import "../../Pages.css";
import "./UserStock.css"

export default class UserStock extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",

            products: []
        };
    }

    componentDidMount(){
        axios.get('http://localhost:8080/api/clients/' + this.state.userID + '/products')
            .then((response) => {
                const products = this.makeRow(response);
                this.setState({ products: products });
            })
    }

    makeRow(response){
        var products = [];
        response.data.forEach((product) => {
            products.push({
                productId: product.productId,
                productName: product.productName,
                quantity: product.quantity,
                hexId: product.hexId
            })
        });
        return products;
    }

    /*
    * SOME FUNCTION TO RETRIEVE & SEND INFO FROM DB
    */

    handleQuarry = (event) => {
        this.setState({
            quarry: event.target.value,
        });
    }

    render(){
        const columns=[
            {Header: "Product", accessor: "productName"},
            {Header: "Quantity", accessor: "quantity"}
        ]
        return(
            <div className="PageStyle rounded">
                <div className="topBox topBoxStyle">
                    <h2 className="topText text-center text-white"> Stock:</h2>
                </div>

                <div className="stockDeciderBox bottomBoxStyle">
                    <input 
                        type="text" 
                        className="serachBar" 
                        onChange={this.handleQuarry}
                        placeholder="Search for product(s)"/>
                    <button className="stockExportButton stockButton_f btn">Export current list</button>
                </div>

                <div className="listBox contentBoxStyle">
                    <GenericTable 
                        columns={columns}
                        data={this.state.products}
                    />
                </div>
            </div>
        );
    }
}
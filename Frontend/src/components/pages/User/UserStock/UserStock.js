import React from 'react';
import ReactTable from 'react-table';
import axios from 'axios';

import "../../Pages.css";
import "./UserStock.css"

export default class UserStock extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",

            products: [],

            selected: null,
            selectedId: ""
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
                <navbar className="navbar navbar-secondary bg-secondary"><h2>Your Stock</h2></navbar>

                <div className="listBox contentBoxStyle">
                    <ReactTable
                        columns={columns}
                        data={this.state.products}
                        showPagination={false} 
                        className="-striped -highlight"
                        getTrProps={(state, rowInfo) => {
                            if (rowInfo && rowInfo.row) {
                                return {
                                onClick: (e) => {
                                    
                                    this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId })
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
        );
    }
}

const mapStateToProps = (state) =>{
    return{
        userId: state.loginReducer.userId
    }
}
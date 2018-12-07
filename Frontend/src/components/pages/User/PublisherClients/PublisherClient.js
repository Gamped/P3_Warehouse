import React from 'react';
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import ReactTable from 'react-table';
import axios from 'axios';

import "../../Pages.css";
import "./PublisherClient.css";

class PublisherClient extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: props.ID,
            clients: [],

            selected: null,
            selectedId: ""
        };
    }

    componentDidMount(){
        axios.get("localhost:8080/api/publishers/" + this.state.userId + "/clients/products")
            .then((response) => {
                const products = this.makeRow(response);
                this.setState({ products: products });
            })
    }

    makeRow(response){
        var products = [];
        response.data.forEach((product) => {
            products.push({
                ownerName: product.owner.name,
                productId: product.productId,
                productName: product.productName,
                quantity: product.quantity,
                hexId: product.hexId
            })
        });
        return products;
    }


    //Todo: get a pdf back when sending the userID
  render() {
      const columns=[
          {Header: "Owner", accessor: "ownerName"},
          {Header: "Product Name", accessor: "productName"},
          {Header: "Quantity", accessor: "productQuantity"}
      ]
        return(
            <div className="PageStyle rounded">
                <nav className="navbar navbar-secondary bg-secondary"><h3> All clients' stock</h3></nav>
                    <div className="mainContent container col">
                        <ReactTable
                            columns={columns}
                            data={this.state.products}
                            showPagination={false} 
                            className="table -striped -highlight"
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

                        <div className="buttons container row">
                            <div className="col">    
                                <Link to="/User/Clients/Request" className="btn btn-block btn-warning">Request client change</Link>
                            </div>
                            <div className="col">
                                <button className="btn-info btn btn-block">Export their stock</button>
                            </div>
                        </div>
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
  
export default connect(mapStateToProps)(PublisherClient)
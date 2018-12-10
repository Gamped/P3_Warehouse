
import React from 'react';
import "../../Pages.css";
import "./NewOrder.css";
import axios from 'axios';
import ReactTable from 'react-table';
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import {itemPreviouslyAddedWarning, 
        amountExceedingQuantityWarning, 
        amountIsZeroWarning} from "./../../../../handlers/exceptions.js";
import {makeProductsRowsFromResponseData} from "./../../../../handlers/dataHandlers.js";
import {get} from './../../../../handlers/requestHandlers.js'
//TODO: Render warning in previouslyAddedWarning
//TODO: Fix textfield in row errors

class NewOrder extends React.Component {
    
    constructor(props) {
        super(props);

        this.state = {
            userID: props.ID,
            quarry: "",
            products: [],
            selected: null,
            selectedId: "",
            orderLines: []
        };

        this.makeRow = this.makeRow.bind(this);
        this.addSelectedToOrderLine = this.addSelectedToOrderLine.bind(this);
        this.undoOrderLine = this.undoOrderLine.bind(this);
        this.renderEditable = this.renderEditable.bind(this);
    }


    componentWillMount() {
       
        this.getProducts();
    }

    getProducts() {

        get('employee/products', (data) => {
            const products = makeProductsRowsFromResponseData(data);
            this.setState({products: products})
        })
    }


   

    handleQuarry = (event) => {
        this.setState({
            quarry: event.target.value,
        });
    }


    renderEditable = cellInfo => {
        return (
          <div
            style={{ backgroundColor: "#fafafa" }}
            contentEditable
            suppressContentEditableWarning
            onBlur={e => {
                
                var typedAmount = e.target.innerHTML;
                
                this.state.products
                .filter(product => 
                    product.hexId === cellInfo.original.hexId)
                .map(product => 
                    product.amount === typedAmount)

                    cellInfo.original.amount = typedAmount;

            }}
            dangerouslySetInnerHTML={{
              __html: this.state.products[cellInfo.index][cellInfo.column.id]
            }}
          />
        );
      };


    undoOrderLine = () => {
        let orderLinesCopy = this.state.orderLines;
        orderLinesCopy.splice(-1,1);
        this.setState({orderLines: orderLinesCopy});
        
      }

    orderLineNotPreviouslyAdded = () => {
        
        let exists = false;
        let orderLines = this.state.orderLines;
        if (orderLines.length !== 0) {
            for (let i = 0; i < orderLines.length; i++) {
                if (orderLines[i].hexId === this.state.products[this.state.selected].hexId) {
                    exists = true;
                }
            }
        }
        if (exists) {
            itemPreviouslyAddedWarning();
            } else {
                this.amountNotExceedingQuantity();
            }

        }
    
    
    
   

    amountNotExceedingQuantity() {
        console.log("amountNotExceeding")
        let selectedProduct = this.state.products[this.state.selected];
        if (selectedProduct.amount > selectedProduct.quantity) {
            amountExceedingQuantityWarning();
        } else {
            this.amountIsNotZero();
        }
    }

    

    amountIsNotZero() {
        console.log("isNotZero")
        let selectedProduct = this.state.products[this.state.selected];
        if (selectedProduct.amount === 0 || selectedProduct.amount === "0") {
            amountIsZeroWarning();
        } else {
            this.addSelectedToOrderLine();
                }
        }

   

    addSelectedToOrderLine = () => {
        this.state.orderLines.push(this.state.products[this.state.selected])
        console.log(this.state.orderLines)
        }

    changeToCart = (event) => {
        this.props.addItemToCart(this.state.orderLines)
        this.props.history.push("/Admin/Order/Cart")
    }

    render(){
        const data = this.state.products;
        const columns = [
            {Header: "Product Id", accessor: "productId"},
            {Header: "Product Name", accessor: "productName"},
            {Header: "Amount", accessor: "amount", Cell: this.renderEditable},
            {Header: "Quantity", accessor: "quantity"},
            {Header: "Owner", accessor: "owner"}];

        return(
            <div className="PageStyle rounded">
            <nav className="navbar navbar-light bg-light"> 
                <h2 className=" text-center "> Order:</h2>
            </nav>   
                <nav className="navbar navbar-light bg-light">                   
                        <form className = "form-inline">
                            <input  className="from-control mr-sm-2 " 
                                    type="search" 
                                    placeholder="Search for product(s)" aria-label="Search"/>
                                    <button className="btn btn-outline-success my-2 my-sm-0" onClick={this.changeToCart}>Search</button>
                        </form>
                        <div>
                            <Link to="/Admin/Order/Cart" className="btn btn-block btn-primary">Go to cart</Link>
                        </div>      
                </nav>         
                
                <div className="table">
                    <div className="SideBar col rounded bg-secondary">
                         <div class="col-my-auto">
                                 <div className="OrderList">
                                    <ReactTable  
                                    data={data} 
                                    columns={columns} 
                                    showPagination={false} 
                                    className="-striped -highlight"
                                    getTrProps={(state, rowInfo) => {
                                        if (rowInfo && rowInfo.row) {
                                          return {
                                            onClick: () => {
                                                
                                                this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId })
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
                                    // This will force the table body to overflow and scroll, 
                                    // since there is not enough room
                                    defaultPageSize={25}
                                    style={{
                                        height: "400px"                                      
                                     }}
                                    />
                                 </div>
                         </div>  
                    </div>  
                    <nav className="navbarToButtoms navbar-light bg-light"> 
                         <div className="container row">
                             <div className="col my-2">
                                 <button type="button" className="btn-success btn-lg btn-block btn my-2" onClick={this.orderLineNotPreviouslyAdded}>Add to order</button>
                             </div>
                             <div className="col my-2">
                                 <button type="button" className="btn-lg btn-block btn-warning my-2" onClick={this.undoOrderLine}>Undo</button>
                            </div>
                        </div>
                    </nav>      
                </div>
            </div> 
         );
    }
}

const mapStateToProps = (state)=>{
    return{
        userType: state.orderReducer
    }
}

const mapDispatchToProps = (dispatch) =>{
    return {
        addItemToCart: (item) => {dispatch({type: "ADD_ITEMTOORDER",payload: {item}})}
    }
}

export default connect(mapStateToProps ,mapDispatchToProps)(NewOrder)



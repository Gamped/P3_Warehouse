
import React from 'react';
import "../../Pages.css";
import "./UserOrder.css";
import axios from 'axios';
import ReactTable from 'react-table';
import UserOrderCart from './UserOrderCart.js';

//TODO: Render warning in previouslyAddedWarning
//TODO: Put items in cart notification symbol on cart button
//TODO: Make downsliding text saying "Added to cart" and "Removed from cart"
//TODO: Fix textfield in row errors
//TODO: Properly pass orderLines in state as props to UserOrderCart child

export default class UserOrder extends React.Component {
    
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
        this.makeRow = this.makeRow.bind(this);
        this.renderEditable = this.renderEditable.bind(this);
      //  this.setStateAsSelected = this.setStateAsSelected.bind(this);
    }


    componentWillMount() {
        axios.get('http://localhost:8080/api/products/')
        .then((response) => {
            const data = this.makeRow(response);
            this.setState({products: data})
        })
    }


   makeRow = (response) => {
    var products = [];
    response.data.forEach((product) => {
      products.push({
        productId: product.productId,
        productName: product.productName,
        quantity: product.quantity,
        amount: 0,
        hexId: product.hexId
      })
      })
    return products;
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
                    product.hexId == cellInfo.original.hexId)
                .map(product => 
                    product.amount = typedAmount)

                    cellInfo.original.amount = typedAmount;

            }}
            dangerouslySetInnerHTML={{
              __html: this.state.products[cellInfo.index][cellInfo.column.id]
            }}
          />
        );
      };

    addSelectedToOrderLine = () => {
    

      //  this.setState({orderLines: [...this.state.orderLines, this.state.product[this.state.selected]]}); 
      this.state.orderLines.push(this.state.products[this.state.selected]);  
      console.log(this.state.orderLines)
            
      }

    undoOrderLine = () => {

        this.state.orderLines.splice(-1, 1);
      }

    checkIfPreviouslyAdded = (orderLine) => {
          
        if(this.state.orderLines.filter(line => orderLine.hexId === line.hexId)) {
            this.previouslyAddedWarning(); 
        }
    }
    
    previouslyAddedWarning = () => {
        //TODO: Render popup warning
        console.log("Item already added!")
    }


    changeToCart = (event) => {
        this.props.history.push({
            pathname: "/User/Order/Cart",
            search: "?the=query",
            state: this.state
          })
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
            <UserOrderCart orderLines={this.state.orderLines}/>
            <nav class="navbar navbar-light bg-light"> 
                <h2 className=" text-center "> Order:</h2>
            </nav>   
                <nav class="navbar navbar-light bg-light">                   
                        <form class = "form-inline">
                            <input  class="from-control mr-sm-2 " 
                                    type="search" 
                                    placeholder="Search for product(s)" aria-label="Search"/>
                                    <button class="btn btn-outline-success my-2 my-sm-0" onClick={this.changeToCart}>Search</button>
                        </form>
                        <div>
                            <form action="/User/Order/Cart" className="orderForm">
                                 <button className="exportButton stockButton_f btn">Go to cart</button>
                            </form>
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
                                    />
                                 </div>
                         </div>  
                    </div>  
                    <nav class="navbarToButtoms navbar-light bg-light"> 
                         <div className="container row">
                             <div className="col my-2">
                                 <button type="button" className="btn-success btn-lg btn-block btn my-2" onClick={this.addSelectedToOrderLine}>Add to order</button>
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


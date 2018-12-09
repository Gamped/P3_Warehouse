
import React from 'react';
import "../../Pages.css";
import "./NewOrder.css";
import axios from 'axios';
import ReactTable from 'react-table';
import { connect } from "react-redux";
import { Link } from "react-router-dom";

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
                    product.hexId === cellInfo.original.hexId)
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
      this.setState({orderLines: [...this.state.orderLines, this.state.selected]}); 
      console.log(this.state.orderLines)
      }

    undoOrderLine = () => {
        this.setState({orderLines: this.state.orderLines.splice(-1, 1)})
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



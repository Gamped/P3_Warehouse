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
        this.setStateAsSelected = this.setStateAsSelected.bind(this);
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
       
        this.state.orderLines.push(this.state.products[this.state.selected]); 
      }

    undoOrderLine = () => {

        this.state.orderLines.splice(-1, 1);
        console.log(this.state.orderLines)
      }

    checkIfPreviouslyAdded = (orderLine) => {
          
        if(this.state.orderLines.filter(line => orderLine.hexId == line.hexId)) {
            this.previouslyAddedWarning();
      }
    }
    
    previouslyAddedWarning = () => {
        //TODO: Render popup warning
    }

    setStateAsSelected = (rowInfo) => {

        this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId })
      }

    render(){

        const data = this.state.products;
        const tableHeight = window.innerHeight*0.7;

        const columns = [
            {Header: "Product Id", accessor: "productId"},
            {Header: "Product Name", accessor: "productName"},
            {Header: "Amount", accessor: "amount", Cell: this.renderEditable},
            {Header: "Quantity", accessor: "quantity"},
            {Header: "Owner", accessor: "owner"}];

        return(
            <div className="PageStyle rounded">

            <UserOrderCart orderLines={this.state.orderLines}/>

                <div className="topBox topBoxStyle">
                    <h2 className="topText text-center text-white"> Order:</h2>
                </div>

                <div className="deciderBox leftBoxStyle">
                    <input 
                        type="text" 
                        className="serachBar" 
                        onChange={this.handleQuarry}
                        placeholder="Search for product(s)"/>
                    <form action="/User/Order/Cart" className="orderForm">
                        <button className="exportButton stockButton_f btn">Go to cart</button>
                    </form>
                </div>
                
                <div className="listBox contentBoxStyle">
                <ReactTable 
                        data={data} 
                        columns={columns} 
                        showPagination={false} 
                        className="-striped -highlight"
                        getTrProps={(state, rowInfo) => {
                            if (rowInfo && rowInfo.row) {
                              return {
                                onClick: (e) => {

                                    this.setStateAsSelected(rowInfo);
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
                <div className="container row">
             <div className="col my-2">
                 <button type="button" className="btn btn-success" onClick={this.addSelectedToOrderLine}>Add to order</button>
             </div>
             <div className="col my-2">
                 <button type="button" className="btn btn-warning" onClick={this.undoOrderLine}>Undo</button>
             </div>
             </div>
             </div>
          
            
            );
    }

}


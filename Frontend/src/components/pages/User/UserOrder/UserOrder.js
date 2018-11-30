import React from 'react';
import "../../Pages.css";
import "./UserOrder.css"
import {getColumnsFromArray} from './../../../../global.js'
import axios from 'axios';
import ReactTable from 'react-table';
import ToolTip from 'react-portal-tooltip';


export default class UserOrder extends React.Component {
    
    constructor(props) {
        super(props);
        this.state = {
            userID: props.ID,
            quarry: "",
            products: [],
            selected: null,
            selectedId: "",
            isTooltipActive: false,
            orderLines: []
        };
        
        this.props = this.state.orderLines;

        this.makeRow = this.makeRow.bind(this);
        this.showTooltip = this.showTooltip.bind(this);
        this.hideTooltip = this.hideTooltip.bind(this);
    }

    showTooltip() {
        this.setState({isTooltipActive: true})
    }
    hideTooltip() {
        this.setState({isTooltipActive: false})
    }


    componentWillMount() {
        axios.get('http://localhost:8080/api/products/')
        .then((response) => {
            const data = this.makeRow(response);
            this.setState({products: data})
        })
    }
    /*
    * SOME FUNCTION TO RETRIEVE & SEND INFO FROM DB
    */


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
                this.state.products.filter(product => 
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
        this.state.orderLines.push( this.state.products[this.state.selected] );
      }
      

    render(){
        const data = this.state.products;
        const tableHeight = window.innerHeight*0.7;
        const columns = [
            {Header: "Product Id", accessor: "productId"},
            {Header: "Product Name", accessor: "productName"},
            {Header: "Amount", accessor: "amount", Cell: this.renderEditable},
            {Header: "Quantity", accessor: "quantity"},
            {Header: "Owner", accessor: "owner"}]
        const orderLinesColumns = getColumnsFromArray(["Product Id", "Product Name", "Amount", "Quantity", "Owner"]);
        const popup = (this.state.isTooltipActive ? <ToolTip active={this.state.isTooltipActive} position="top" arrow="center" parent="#text">
        <div>
         <p>This is the content of the tooltip</p>
        </div>
        </ToolTip> : null);

        return(
            <div className="PageStyle rounded">
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
                    data={this.state.orderLines}
                    columns={orderLinesColumns}
                    showPagination={false} 
                    className="-striped -highlight" />
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
                                    
                                  this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId })
                                  console.log(rowInfo.original)
                                  this.state.isTooltipActive = true;
                                  this.state.orderLines.push(rowInfo.original)
                                  console.log(this.state.orderLines)
                                  
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
             </div>
            </div>
           
            );
    }
}
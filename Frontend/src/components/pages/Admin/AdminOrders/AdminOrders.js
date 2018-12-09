import React, {Component} from 'react';
import ReactTable from "react-table";
import axios from "axios";
import "../../Pages.css";
import "./AdminOrders.css";


export default class AdminOrders extends Component {

    //A constructor that contains our state.
    constructor(props) {
        super(props);
        this.state = { 
            orders: [], 
            orderLines: [], 
            selectedOrder: [],
            selected: null, 
            selectedIndex: -1,
            packed: {},
            allPacked: 0
        }
        //binds different functions to our constructor
        this.getData = this.getData.bind(this);
        this.setStateAsSelected = this.setStateAsSelected.bind(this);
        this.showOrderLines = this.showOrderLines.bind(this);
        this.toggleRow = this.toggleRow.bind(this);
    }

    //This happens when the component has mounded.
    componentDidMount() {
        //Makes a get request for orders, then binds those orders to the state.
        axios.get("http://localhost:8080/api/orders")
        .then((response) => {
            const ordersData = this.getData(response.data);
        
            this.setState({ 
                data: response.data,
                orders: ordersData
            });
        });
    }

    //Gets data for each order. Then gives that order an owner and id.
    getData(data) {
        var orders = [];
        data.forEach((order) => {

            orders.push({
                owner: order.owner.userName,
                orderId: order.orderId
            });
        });
        
        return orders;
    }

    //This sets a state as the selected state
    setStateAsSelected = (rowInfo) => {

        this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId });
    }

    //Shows all the oderlines
    showOrderLines = (rowInfo) => {
        const selectedOrder = this.state.data[rowInfo.index].orderLines;
        let orderLines = [];
        
        selectedOrder.map(orderLine => {
            return {...orderLine,
                productName: orderLine.product.productName,
                date: orderLine.product.date,
                amount: orderLine.quantity
            }
        });

        
        this.setState({orderLines: orderLines});
    }

    //Marks a row as selected.
    toggleRow(productName) {

		const packedItem = Object.assign({}, this.state.packed);
		packedItem[productName] = !this.state.packed[productName];
        this.setState({
			packed: packedItem,
			selectAll: 2
		});
	}

    //Mark all rows as selected
	toggleSelectAll() {
		let packedItem = {};

		if (this.state.selectAll === 0) {
			this.state.data.forEach(x => {
				packedItem[x.productName] = true;
			});
        }

		this.setState({
			packed: packedItem,
			allPacked: this.state.allPacked === 0 ? 1 : 0
        });
        
        if (this.state.allPacked === 1) {
            //TODO: ACTIVATE FINISH ORDER BUTTON
        }
	}

    //Get
    getCheckBoxColumn() {
       const checkBoxColumn = {
        id: "checkbox",
        accessor: "",
        Cell: ({ original }) => {
            return (
                <input
                    type="checkbox"
                    className="checkbox"
                    checked={this.state.packed[original.productName] === true}
                    onChange={() => this.toggleRow(original.productName)}
                />
            );
        },
        Header: x => {
            return (
                <input
                    type="checkbox"
                    className="checkbox"
                    checked={this.state.selectAll === 1}
                    ref={input => {
                        if (input) {
                            input.indeterminate = this.state.selectAll === 2;
                        }
                    }}
                    onChange={() => this.toggleSelectAll()}
                />
            );
        }
    }
        return checkBoxColumn;

    }

    sendToPage = (address) => {
        this.props.history.push(address);
    }

    export = () =>{
        const pdfConverter = require('jspdf');
        const doc = new pdfConverter();
        alert("HIV HVAD DER STÅR I ORDER UD. SNAK VENLIGST MED TOBIAS")
        
        /* TODO: Find ud af hvad der skal skrives ind i pdfen.

        doc.setFontSize(22);
        doc.text(20,50,"Entire stock:");
        doc.setFontSize(10);
        let pdfXPlace = 25;
        let pdfYPlace = 65;
        let counter = 0;
        for (const key in elements){
            
            doc.text("Name: "+elements[key].productName,pdfXPlace,pdfYPlace);
            doc.text("Quantity: " + elements[key].quantity,pdfXPlace+120,pdfYPlace);
            doc.line(20,pdfYPlace+5,175,pdfYPlace+5);
            pdfYPlace += 17;
            counter += 1;
            if(counter%25===0){
                doc.addPage()
            }
        }

        doc.save("EntireStock.pdf")
*/
    }

    finishOrder = () =>{
        //TODO: See above.
        alert("Missing func. Her skal den printe en følge seddel og slette ordren.")
    }

    render() {
      
        const orders = this.state.orders;

        let noSelectedOrderItem = [{
            productName: "No selected order",
            date: 0,
            amount: 0,
            packed: 0
        }];
  
        const orderColumns = [
            {Header: "Owner", accessor: "owner"},
            {Header: "ID", accessor: "orderId"}
        ];

        const orderLineColumns = [
            {Header: "Product Name", accessor: "productName"},
            {Header: "Date", accessor: "date"},
            {Header: "Amount", accessor: "amount"},
            this.getCheckBoxColumn()];

        return (
            <div className="PageStyle rounded">
                <div className="container row">
                    <div className="SideBar col sidebar border border-dark rounded bg-secondary">
                        <div className="OrderList">
                            <ReactTable 
                            data={orders}
                            columns={orderColumns} 
                            showPagination={false} 
                            className="-striped -highlight"getTrProps={(state, rowInfo) => {
                                if (rowInfo && rowInfo.row) {
                                  return {
                                    onClick: (e) => {
                                       this.setStateAsSelected(rowInfo);
                                       this.showOrderLines(rowInfo);
                                    },
                                    style: {
                                      background: rowInfo.index === this.state.selected ? '#00afec' : 'white',
                                      color: rowInfo.index === this.state.selected ? 'white' : 'black'
                                    }
                                  }
                                }else{
                                  return {}
                                }
                               }
                            }
                             />
                        </div>
                        <div className=" md-2">
                                <button type= "button" className="btn btn-success mx-2" onClick={()=>this.sendToPage("/Admin/Orders/New")}>Create order</button>
                                <button type= "button" className="btn btn-warning mx-2" onClick={()=>this.sendToPage("/Admin/Orders/Edit")}>Edit order</button>                            
                                <button type= "button" className="btn btn-danger mx-2"  onClick={()=>this.sendToPage("/Admin/Orders/Delete")}>Delete order</button>
                        </div>
                    </div>
                        <div className="Table">
                                <ReactTable data={this.state.orderLines ? this.state.orderLines : noSelectedOrderItem} columns={orderLineColumns}showPagination={false} 
                                className="-striped -highlight"/>
                                 <div className="  px-1">
                                    <button type= "button" className="btn btn-info mx-3" onClick={this.export}>Export order To PDF</button>  
                                    <button type= "button" className="btn btn-dark mx-3" onClick={this.finishOrder}>Finish order </button> 
                                </div>
                       </div>  
                 </div>    
            </div>
        )
    }
}
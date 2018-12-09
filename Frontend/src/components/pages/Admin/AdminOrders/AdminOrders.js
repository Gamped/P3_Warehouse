import React, {Component} from 'react';
import {Link} from 'react-router-dom';
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
            selectedOrderHexId: "",
            packed: {},
            allPacked: 0
        }

        this.makePublisherAndClientOrderData = this.makePublisherAndClientOrderData.bind(this);
        this.setStateAsSelected = this.setStateAsSelected.bind(this);
        this.showOrderLines = this.showOrderLines.bind(this);
        this.toggleRow = this.toggleRow.bind(this);
    }

    //This happens when the component has mounded.
    componentDidMount() {

        axios.get("http://localhost:8080/api/publishers")
        .then((response) => {
            const ordersData = this.makePublisherAndClientOrderData(response.data);
            
            this.setState({ 
                data: response.data,
                orders: ordersData
            });
        });
    }

    makePublisherAndClientOrderData(data) {
        var orders = [];
        let orderObject = {};
        let owner = "";

        data.forEach((publisher) => {
            if (this.ordersExist(publisher)) {
         
                owner = publisher.contactInformation.nickName;

                publisher.orderStream.forEach((order) => {
                    orders.push(this.addOrder(order, owner));
                });
                
                orders.push(orderObject);   
            }

            if (this.clientsExist(publisher)) {
                publisher.clientStream.forEach((client) => {
                    
                    if (this.ordersExist(client)) {
                        owner = client.contactInformation.nickName;

                        client.orderStream.forEach((order) => {
                            orders.push(this.addOrder(order, owner));
                            
                        });
                    }
                });
            }
        });

        return orders;
    }

    ordersExist = (customer) => {
        return customer.orderStream != null && customer.orderStream != undefined;
    }

    clientsExist = (publisher) => {
        return publisher.numberOfClient != 0;
    }

    addOrder(order, owner) {
        let orderObject = {};
        orderObject.owner = owner;
        orderObject.orderId = order.orderId
        orderObject.data = order.date
        orderObject.orderLines = order.orderLines.map((orderLine) => {
            return {
                productName: orderLine.product.productName,
                amount: orderLine.quantity,
                productId: orderLine.product.productId
                }
        })
        return orderObject;
    }



    setStateAsSelected = (rowInfo) => {
        
        this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId });
        console.log(this.state.selectedId)
    }

    //Shows all the oderlines
    showOrderLines = (rowInfo) => {

        const selectedOrder = this.state.orders[rowInfo.index].orderLines;
        this.setState({orderLines: selectedOrder});
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

        /* TODO: Find ud af hvad der skal skrives ind i pdfen.*/
        let pdfXPlace = 20;
        let pdfYPlace = 50;
        

        doc.setFontSize(22);
        doc.text(pdfXPlace,pdfYPlace,"Packlist:");
        pdfYPlace +=5;
        doc.line(pdfXPlace,pdfYPlace,175,pdfYPlace);
        doc.setFontSize(15);
        pdfYPlace +=8;
        doc.text(pdfXPlace,pdfYPlace, "Order nummer: "+ "[Insert order number here]" )
        pdfYPlace +=8;
        doc.text(pdfXPlace,pdfYPlace, "Customer: "+ "[Insert Customer here]" )
        pdfYPlace +=8;
        doc.text(pdfXPlace,pdfYPlace,"Order date: "+"[Insert Date here]")
        doc.setFontSize(10);
        pdfYPlace +=10

        doc.setFontStyle("bold")
        doc.text(pdfXPlace,pdfYPlace,"Item ID")
        doc.text(pdfXPlace+50,pdfYPlace,"Item Name")
        doc.text(pdfXPlace+100,pdfYPlace,"Quantity")
        doc.text(pdfXPlace+140,pdfYPlace,"Packed?")
        pdfYPlace +=2
        doc.line(pdfXPlace,pdfYPlace,175,pdfYPlace);

        /*let counter = 0;
        for (const key in elements){
            
            doc.text("Name: "+elements[key].productName,pdfXPlace,pdfYPlace);
            doc.text("Quantity: " + elements[key].quantity,pdfXPlace+120,pdfYPlace);
            doc.line(20,pdfYPlace+5,175,pdfYPlace+5);
            pdfYPlace += 17;
            counter += 1;
            if(counter%25===0){
                doc.addPage()
            }
        }*/

        doc.save("PackList.pdf")

    }

    finishOrder = () =>{

        //TODO: DELETE ORDER TOO
        const pdfConverter = require('jspdf');
        const doc = new pdfConverter();

        /* TODO: Find ud af hvad der skal skrives ind i pdfen.*/
        let pdfXPlace = 20;
        let pdfYPlace = 50;
        
        doc.line(pdfXPlace,pdfYPlace,175,pdfYPlace);

        pdfYPlace += 7
        doc.setFontSize(12)
        doc.text(pdfXPlace,pdfYPlace,"Leveres til:")
        doc.setFontSize(16)
        doc.text(pdfXPlace+90, pdfYPlace,"Følgeseddel: "+"[INSERT NUMBER]")
        
        pdfYPlace += 5
        doc.setFontSize(12)
        doc.text(pdfXPlace,pdfYPlace,"[Company name]")
        doc.text(pdfXPlace+90, pdfYPlace,"Ordernr.: "+"[INSERT NUMBER]")

        pdfYPlace += 5
        doc.text(pdfXPlace,pdfYPlace,"[Person]")
        doc.text(pdfXPlace+90, pdfYPlace,"Dato.: "+"[INSERT Date]")

        pdfYPlace += 5
        doc.text(pdfXPlace,pdfYPlace,"[Address]")
        doc.text(pdfXPlace+90, pdfYPlace,"Kundenr.: "+"[INSERT NUMBER]")

        pdfYPlace += 5
        doc.text(pdfXPlace,pdfYPlace,"[zip and city name]")
        doc.text(pdfXPlace+90, pdfYPlace,"Sag: "+"[INSERT CASE]")

        pdfYPlace += 5
        doc.text(pdfXPlace,pdfYPlace,"[Country]")
        doc.text(pdfXPlace+90, pdfYPlace,"Reference: "+"[INSERT SPECIFIC PERSON]")

        pdfYPlace += 10

        doc.text(pdfXPlace,pdfYPlace,"Hermed følger:")
        pdfYPlace +=5

        doc.text(pdfXPlace,pdfYPlace,"Titel: " + "[INSERT TITEL]")
        doc.setFontSize(18)
        doc.text(pdfXPlace+90,pdfYPlace,"SLUTLEVERING")
        doc.setFontSize(12)
        pdfYPlace +=5

        doc.text(pdfXPlace,pdfYPlace,"Rekv.nr.: " +"[Insert number]")
        pdfYPlace +=7

        doc.setFontStyle("bold")
        doc.text(pdfXPlace,pdfYPlace,"Item ID")
        doc.text(pdfXPlace+50,pdfYPlace,"Item Name")
        doc.text(pdfXPlace+140,pdfYPlace,"Quantity")
        pdfYPlace +=2

        doc.line(pdfXPlace,pdfYPlace,175,pdfYPlace);

        /*let counter = 0;
        for (const key in elements){
            
            doc.text("Name: "+elements[key].productName,pdfXPlace,pdfYPlace);
            doc.text("Quantity: " + elements[key].quantity,pdfXPlace+120,pdfYPlace);
            doc.line(20,pdfYPlace+5,175,pdfYPlace+5);
            pdfYPlace += 17;
            counter += 1;
            if(counter%25===0){
                doc.addPage()
            }
        }*/

        doc.save("Følgeseddel.pdf")
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
            {Header: "Date", accessor: "date"},
            {Header: "ID", accessor: "orderId"}
        ];

        const orderLineColumns = [
            {Header: "Product Name", accessor: "productName"},
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
                                <Link className="btn btn-warning mx-2" to={`/Admin/Orders/Edit/${this.state.selectedId}`}>Edit order</Link>
                                <button type= "button" className="btn btn-danger mx-2"  onClick={()=>this.sendToPage("/Admin/Orders/Delete")}>Delete order</button>
                        </div>
                    </div>
                        <div className="Table">
                                <ReactTable data={this.state.orderLines ? this.state.orderLines : noSelectedOrderItem} columns={orderLineColumns} showPagination={false} 
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

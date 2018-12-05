import React, {Component} from 'react';
import "../Pages.css";
import "./AdminOrders.css";
import axios from "axios";
import ReactTable from "react-table";


export default class AdminOrders extends Component {


    constructor() {
        super();
        this.state = { 
            orders: [], 
            orderLines: [], 
            selectedOrder: [],
            selected: null, 
            selectedIndex: -1,
            packed: {},
            allPacked: 0
        }

        this.getData = this.getData.bind(this);
        this.setStateAsSelected = this.setStateAsSelected.bind(this);
        this.showOrderLines = this.showOrderLines.bind(this);
        this.toggleRow = this.toggleRow.bind(this);
    }

    componentDidMount() {

        axios.get("http://localhost:8080/api/orders")
        .then((response) => {
            const ordersData = this.getData(response.data);
        
            this.setState({ 
                data: response.data,
                orders: ordersData
            });
        });
    }

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


    setStateAsSelected = (rowInfo) => {

        this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId });
    }

    showOrderLines = (rowInfo) => {
        const selectedOrder = this.state.data[rowInfo.index].orderLines;
        let orderLines = [];
        
        selectedOrder.map(orderLine => {
            orderLines.push({
                productName: orderLine.product.productName,
                date: orderLine.product.date,
                amount: orderLine.quantity
              
            });
        });

        
        this.setState({orderLines: orderLines});
    }


    toggleRow(productName) {

		const packedItem = Object.assign({}, this.state.packed);
		packedItem[productName] = !this.state.packed[productName];
        this.setState({
			packed: packedItem,
			selectAll: 2
		});
	}

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
        
        if (this.state.allPacked == 1) {
            //TODO: ACTIVATE FINISH ORDER BUTTON
        }
	}

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
                                <button type= "button" className="btn btn-success mx-2" >Create order </button>
                                <button type= "button" className="btn btn-warning mx-2" >Edit order   </button>                            
                                <button type= "button" className="btn btn-danger mx-2"  >Del order    </button>
                        </div>
                    </div>
                        <div className="Table">
                                <ReactTable data={this.state.orderLines ? this.state.orderLines : noSelectedOrderItem} columns={orderLineColumns}showPagination={false} 
                                className="-striped -highlight"/>
                                 <div className="  px-1">
                                    <button type= "button" className="btn btn-info mx-0">Export order To </button>  
                                    <button type= "button" className="btn btn-dark mx-5">Fineshed order </button> 
                                </div>
                       </div>  
                 </div>    
            </div>
        )
    }
}
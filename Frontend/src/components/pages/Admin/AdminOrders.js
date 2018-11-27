import React, {Component} from 'react';
import Table from "../../MenuComponents/Table/Table"
import "../Pages.css";
import "./AdminOrders.css";
import axios from "axios";
import ReactTable from "react-table";

export default class AdminOrders extends Component {

  constructor() {
    super();
    this.state = { data: [], orders: [] }
    this.makeRow = this.makeRow.bind(this);
  }

  componentDidMount() {

    axios.get("http://localhost:8080/api/orders")
    .then((response) => {
        const orders = this.makeRow(response);
        this.setState({ orders: orders })
      })
  }

  makeRow(response) {

    var orders = [];

    response.data.forEach((order) => {

      orders.push({
        orderId: order.orderId,
        owner: order.owner.nickName,
        date: order.date,
        itemsToPack: order.orderLines.length
      });
    });
    console.log(response.data[0]);
    console.log(orders);
    return orders;
  }


  render() {
    const data = this.state.orders;
    console.log(data);
    console.log(JSON.stringify(data));

    const columns = [
        {Header: "Order Id", accessor: "orderId"},
        {Header: "Owner", accessor: "owner"},
        {Header: "Date", accessor: "date"},
        {Header: "Items To Pack", accessor: "itemsToPack"},
        {Header: "Packed?", accessor: "packed"}
    ]

      const tableHeight = window.innerHeight * 0.8;
      return (
        <div className="PageStyle">

        <ReactTable data={data} className="-striped -highlight" columns={columns} height={tableHeight} defaultPageSize={15}/>
    </div>
    )
  }

}

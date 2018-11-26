import React, {Component} from 'react';
import Table from "../../MenuComponents/Table/Table"
import "../Pages.css";
import "./AdminOrders.css";
import axios from "axios";
import ReactTable from "react-table";

export default class AdminOrders extends Component {

  constructor() {
    super();
    this.state = { data: [], productExample: [] }
    this.makeRow = this.makeRow.bind(this);
  }

  componentDidMount() {

    axios.get("http://localhost:8080/api/orders")
    .then((response) => {
        const example = this.makeRow(response);
        this.setState({ productExample: example })
      })
  }

  makeRow(response) {

    var exampleData = [];

    response.data.forEach((order) => {

      exampleData.push([{
        name: order.orderLines[0].product.productName,
        id: order.orderLines[0].product.productId,
        amount: order.orderLines[0].product.quantity,
        owner: order.orderLines[0].product.owner.userName
      }]);
    });

    return exampleData;
  }


  render() {
    const data = this.state.productExample[0];
    console.log(JSON.stringify(data));

    const columns = [
        {Header: "Package Name", accessor: "name"},
        {Header: "Package ID", accessor: "id"},
        {Header: "Unit Amount", accessor: "amount"},
        {Header: "Owner", accessor: "owner"},
        {Header: "Packaged?", accessor: "packaged"}
    ]
      const tableHeight = window.innerHeight * 0.8;
      return( <div className="PageStyle">
        <ReactTable data={data} className="-striped -highlight" columns={columns} height={tableHeight} defaultPageSize={15}/>
    </div>
)
  }









}

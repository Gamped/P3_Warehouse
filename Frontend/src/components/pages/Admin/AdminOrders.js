import React, {Component} from 'react';
import "../Pages.css";
import "./AdminOrders.css";
import axios from "axios";
import ReactTable from "react-table";
import ButtonList from "../../MenuComponents/ButtonList/ButtonList";


export default class AdminOrders extends Component {

  constructor() {
    super();
    this.state = { data: [], orders: [],
    tabs: [
        {name:"In Progress Orders",id:0},
        {name:"Finished Orders",id:1},
        {name:"Pending Orders",id:2}
    ],
    orderEntries: [
        {name: "Ground Beef", state: "Finished", id:"2"},
        {name: "Iron Halo", state: "Finished", id:"3"},
        {name: "Plasma Blaster", state: "Finished", id:"4"},
        {name: "Fist of the dragon", state: "Finished", id:"5"},
        {name: "Russian Molotov Cocktail", state: "Finished", id:"6"},
        {name: "Oatmeal", state: "In Progress", id:"7"},
        {name: "Cactus and lube", state: "In Progress", id:"8"},
        {name: "Firebowl", state: "In Progress", id:"9"},
        {name: "The golden Bra Of Ming", state: "Pending", id:"10"},
        {name: "Silver girls", state: "Pending", id:"11"},
        {name: "Fidget spinners", state: "Pending", id:"12"},
    ],
    listShown: 0,}
    this.makeRow = this.makeRow.bind(this);
    this.makeButtonData = this.makeButtonData.bind(this);
  }

  componentDidMount() {

    axios.get("http://localhost:8080/api/orders")
    .then((response) => {
        const orders = this.makeRow(response);
        this.setState({ orders: orders })
      })
  }

  makeButtonData(response) {
    var buttons = [];
    response.data.forEach((order) => {
      buttons.push({
        owner: order.owner.name,
        orderId: order.orderId,
        orderName: order.name,
        state: order.status
      })
    })

    return buttons;
  }

  makeRow(response) {

    var orders = [];

    response.data.forEach((order) => {

      orders.push({
        name: order.orderline.productName,
        date: order.date,
        quantity: order.orderline.quantity,
        packed: order.orderline.packed
      });
    });
    console.log(response.data[0]);
    console.log(orders);
    return orders;
  }

  changeList=(input)=>{
    this.setState({listShown: input});
  }

  buttonListShown=()=>{
    const x=this.state.listShown;
    switch(x){
        case 0:
            return(<ButtonList buttons={this.state.inProgressOrders} color="dark" link={false} action={this.changeUser} />)
        case 1:
            return(<ButtonList buttons={this.state.finishedOrders} color="dark" link={false} action={this.changeUser} />)
        case 2:
            return(<ButtonList buttons={this.state.pendingOrders} color="dark" link={false} action={this.changeUser} />)
        default:
            return(<h2 className="color-red">ERROR</h2>)
    }
}

    render() {
        const orderFields = this.state.orderEntries;
        const data = this.state.orders;
        console.log(data);
        console.log(JSON.stringify(data));

        const columns = [
            {Header: "Product Name", accessor: "productName"},
            {Header: "Date", accessor: "date"},
            {Header: "Quantity", accessor: "quantity"},
            {Header: "Packed?", accessor: "packed"}
        ]

        const column = [
            {Header: "Owner", accessor: "owner"},
            {Header: "ID", accessor: "orderId"},
            {Header: "Order", accessor: "orderName"},
            {Header: "Status", accessor: "state"}
        ]

        return (
            <div className="PageStyle rounded">
                <div className="container row">
                    <div className="SideBar col sidebar border border-dark rounded bg-secondary">
                        <div className="OrderList">
                            <ReactTable data={orderFields} columns={column} showPagination={false} className="-striped -highlight"/>
                        </div>
                    </div>
                    <div className="Table">
                        <ReactTable data={data} columns={columns} showPagination={false} className="-striped -highlight"/>
                    </div>
                </div>
            </div>
        )
    }
}

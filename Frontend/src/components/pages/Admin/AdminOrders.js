import React, {Component} from 'react';
import "../Pages.css";
import "./AdminOrders.css";
import axios from "axios";
import ReactTable from "react-table";
import TextBox from "../../MenuComponents/TextBox/TextBox";
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
    finishedOrders: [
        {name: "DragonDildos", id:"1"},
        {name: "Ground Beef", id:"2"},
        {name: "Iron Halo", id:"3"},
        {name: "Plasma Blaster", id:"4"},
        {name: "Fist of the dragon", id:"5"},
        {name: "Russian Molotov Cocktail", id:"6"},
    ],
    inProgressOrders: [
        {name: "Oatmeal", id:"7"},
        {name: "Cactus and lube", id:"8"},
        {name: "Firebowl", id:"9"},
    ],
    pendingOrders: [
        {name: "The golden Bra Of Ming", id:"10"},
        {name: "Silver girls", id:"11"},
        {name: "Fidget spinners", id:"12"},
    ],
    listShown: 0,}
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
            <div className="container row">
                <div className="col sidebar fullbar border border-dark rounded bg-secondary">
                    <div className="border border-light rounded bg-info">
                        <ButtonList buttons={this.state.tabs} color="secondary" link={false} action={this.changeList}/>
                    </div>
                    {this.buttonListShown()}
                </div>

                <ReactTable data={data} className="-striped -highlight" columns={columns} height={tableHeight} defaultPageSize={15}/>
            </div>
        </div>
    )
  }

}

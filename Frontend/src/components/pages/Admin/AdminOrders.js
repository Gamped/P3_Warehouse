import React, {Component} from 'react';
import "../Pages.css";
import "./AdminOrders.css";
import axios from "axios";
import ReactTable from "react-table";
import 'react-table/react-table.css'
import TextBox from "../../MenuComponents/TextBox/TextBox";
import ButtonList from "../../MenuComponents/ButtonList/ButtonList";


export default class AdminOrders extends Component {

  constructor() {
    super();
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
        owner: order.owner.nickName,
        id: order.orderId
      })
    })

    return buttons;
  }

  makeData(response) {

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

  getColumns() {
    return [
        {Header: "Order Id", accessor: "orderId"},
        {Header: "Owner", accessor: "owner"},
        {Header: "Date", accessor: "date"},
        {Header: "Items To Pack", accessor: "itemsToPack"},
        {Header: "Packed?", accessor: "packed"}
    ]
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

        return (
            <div className="PageStyle rounded">
                <div className="container row">
                    <div className="col sidebar border border-dark rounded bg-secondary">

                        <div className="border border-light rounded bg-info">
                            <ButtonList buttons={this.state.tabs} color="secondary" link={false} action={this.changeList}/>
                        </div>
                        {this.buttonListShown()}
                    </div>
                    <div className="col sidebar border border-dark rounded bg-secondary">
                        
                    </div>
                    <div className="Table">
                        <ReactTable data={data} columns={columns} showPagination={false} className="-striped -highlight"/>
                    </div>
                </div>
            </div>
        )
    }
    // update the state
    this.setState({ selection });
  }


  isSelected(key) {
    /*
      Instead of passing our external selection state we provide an 'isSelected'
      callback and detect the selection state ourselves. This allows any implementation
      for selection (either an array, object keys, or even a Javascript Set object).
    */
    return this.state.selection.includes(key);
  }

  logSelection() {
    console.log("selection:", this.state.selection);
  }

  render() {
    const { toggleSelection, isSelected, logSelection } = this;
    const { data, columns } = this.state;
    console.log(data);

    const checkboxProps = {
  isSelected,
  toggleSelection,
  selectType: "checkbox",
  getTrProps: (s, r) => {
    // someone asked for an example of a background color change
    // here it is...
    const selected = this.isSelected(r.original._id);
    return {
      style: {
        backgroundColor: selected ? "lightgreen" : "inherit"
        // color: selected ? 'white' : 'inherit',
      }
    };
  }
};

      const tableHeight = window.innerHeight * 0.8;

      return (
        <div className="PageStyle">
            <div className="container row">
                </div>

                <div>
                 <button onClick={logSelection}>Log Selection</button>
                <CheckboxTable ref={r => (this.checkboxTable = r)}
                data={data}
                tableHeight={tableHeight}
                className="-striped -highlight"
                columns={columns}
                defaultPageSize={15}/>
                </div>

}

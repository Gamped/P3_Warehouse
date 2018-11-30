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

  componentWillReceiveProps(nextProps) {     
    var annotated=this.annotateOrders (nextProps.orders);
     this.setState ({pipelines: annotated});      
   } 

  componentDidMount() {
    axios.get("http://localhost:8080/api/orders")
    .then((response) => {
        const data = this.makeData(response);
        this.setState({ orders: data })
      })
  }

  annotatePipeline (orders) {
    var annotated=new Array ();
    
    for (let i = 0; i < orders.length; i++) {
      var converter = orders[i];
      converter ["selected"]=false;
      annotated.push (converter);
    }
    
    return (annotated);
  }


  selectOrder (e,state,column,rowInfo,instance) {      
    if (this.state.selectedIndex != -1) {
      var previousSelectedOrder=this.state.orders[this.state.selectedIndex];
      previousSelectedOrder.selected=false;
    }
    
    let orders = this.state.orders[rowInfo.index];
    orders.selected = true;
    
    if (orders != null) {
      if (this.props.onClickOrder) {
        this.props.onClickOrder(orders.orderId);  
      } else {
        console.log ("No event handler available to process pipeline select event");  
      } 
    } else {
      console.log ("Error retrieving pipeline from pipeline list");  
    }
    
    this.setState ({selectedIndex: rowInfo.index});
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




  render() {

      let orders = <ReactTable data={this.state.orders} 
                               columns={this.ordersTable} 
                               className='-striped -highlight'
                               defaultPageSize={10}
                               getTdProps={(state, rowInfo, column, instance) => {
                                 return {
                                   onClick: e => {
                                      this.selectOrder (e,state,column,rowInfo,instance)
                                       }
                                 }
                               }
     }/>
       
     return (  
      <div>
        {orders}
      </div> );
    
  }
}
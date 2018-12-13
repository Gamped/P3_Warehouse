import React from 'react';
import "./Order.css";
import ReactTable from 'react-table';
import { connect } from "react-redux";
import {makeProductsData, makeCustomerProductsData} from '../../../handlers/dataHandlers.js';
import {itemPreviouslyAddedWarning} from '../../../handlers/exceptions.js';
import { getColumnsFromArray } from '../../../handlers/columnsHandlers.js';
import { get } from '../../../handlers/requestHandlers';
import Dropdown from "../../MenuComponents/Dropdown/Dropdown";
import {makeCustomerData} from "../../../handlers/dataHandlers";

//TODO: Render warning in previouslyAddedWarning
//TODO: Put items in cart notification symbol on cart button
//TODO: Make downsliding text saying "Added to cart" and "Removed from cart"
//TODO: Fix textfield in row errors
//TODO: Properly pass orderLines in state as props to UserOrderCart child

class UserOrder extends React.Component {
    
    constructor(props) {
        super(props);

        this.state = {
            userID: props.ID,
            quarry: "",
            products: [],
            selected: null,
            selectedId: "",
            orderLines: [],
            customers:[],
            userSelectedId:"",
            userSelectedType:"",
        };

        this.addSelectedToOrderLine = this.addSelectedToOrderLine.bind(this);
        this.undoOrderLine = this.undoOrderLine.bind(this);
        this.renderEditable = this.renderEditable.bind(this);
       
    }

    componentDidMount(){
        this.getClients();
        this.getPublishers();
        this.getStock();           
    }

     getClients() {
         get('employee/clients', (data) => {
             const clients = makeCustomerData(data);
             this.concatinateWithNewData(clients);
         });
     }
 
     getPublishers() {
        get('employee/publishers', (data) => {
             const publishers = makeCustomerData(data);
             this.concatinateWithNewData(publishers);
         });
     }
 
     concatinateWithNewData(newData) {
     
         const customersCopy = this.state.customers;
         let concatinatedData = customersCopy.concat(newData);
         this.setState({ customers: concatinatedData });
     }

    getStock() {
        
        const userType = this.props.userType.toLowerCase();
        const id = this.props.userId;

        if(userType==="employee"){
            get('employee/products', (data) => {
                const products = makeProductsData(data);
                this.setState({products: products})
            })
        }else{
            get(userType + 's/' + id + '/products', (data) => {
                let products = [];

                userType === 'publisher' ? products = makeCustomerProductsData(data) : products = makeProductsData(data);

                this.setState({ products: products });
            });
        }
    }

    handleQuarry = (event) => {
        this.setState({
            quarry: event.target.value,
        });
    }

    addSelectedToOrderLine = () => {
      this.setState({orderLines: [...this.state.orderLines, this.state.products[this.state.selected]]}); 
      console.log(this.state.orderLines)
      }

    undoOrderLine = () => {
        this.setState({orderLines: this.state.orderLines.splice(-1, 1)})
      }

    checkIfPreviouslyAdded = (orderLine) => {
          
        if(this.state.orderLines.filter(line => orderLine.hexId === line.hexId)) {
            itemPreviouslyAddedWarning(); 
        }
    }

    changeToCart = (event) => {
        this.props.addItemToCart(this.state.orderLines)
        const userType = this.props.userType
        if(userType === "EMPLOYEE"){
            this.props.history.push("/Admin/Order/Cart")
        }else{
            this.props.history.push("/User/Order/Cart")
        }
        
    }

    renderEditable = cellInfo => {
        return (
          <div
            style={{ backgroundColor: "#fafafa" }}
            contentEditable
            onClick={(e) => {e.target.innerHTML = ""}}
            suppressContentEditableWarning
            onBlur={e => {
                
                var typedAmount = e.target.innerHTML;
                console.log(typedAmount)
                this.state.products
                .filter(product => 
                    product.hexId === cellInfo.original.hexId)
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


    createNavBar = () =>{
        let navbar = null;
        if(this.props.userType==="EMPLOYEE"){
            navbar = (
                <nav class="navbar navbar-light bg-light">                   
                    <form class = "form-inline">
                                <button class="btn btn-outline-success my-2 my-sm-0" onClick={this.changeToCart}>Go to cart</button>
                    </form>       
                        <Dropdown actors={this.state.customers} action={this.setSelectedUser}/>
                </nav> 
                )
        }else{
            navbar = (
                <nav class="navbar navbar-light bg-light">                   
                    <form class = "form-inline">
                                <button class="btn btn-outline-success my-2 my-sm-0" onClick={this.changeToCart}>Go to cart</button>
                    </form>       
                </nav> 
                )
        }

        return navbar;
    }

    setSelectedUser = (e) =>{
        this.setState({userSelectedId:e.target.value},()=>{
            this.setState({userSelectedType:this.state.customers.find(x=>x.hexId===this.state.userSelectedId).userType})
        })
    }

    render(){
        const data = this.state.products;
        const columns = getColumnsFromArray([
            "Product Id", 
            "Product Name", 
            "Amount", 
            "Quantity", 
            "Owner Name"]);
        columns[2].Cell = this.renderEditable;

        return(
            <div className="PageStyle rounded">
            <nav class="navbar navbar-light bg-light"> 
                <h2 className=" text-center "> Order:</h2>
            </nav>   
                {this.createNavBar()}        
                
                <div className="table">
                    <div className="SideBar col rounded bg-secondary">
                         <div class="col-my-auto">
                                 <div className="OrderList">
                                    <ReactTable  
                                    data={data} 
                                    columns={columns} 
                                    showPagination={false} 
                                    className="-striped -highlight"
                                    getTrProps={(state, rowInfo) => {
                                        if (rowInfo && rowInfo.row) {
                                          return {
                                            onClick: () => {
                                                
                                                this.setState({selected: rowInfo.index, selectedId: rowInfo.original.hexId })
                                            },
                                            style: {
                                              background: rowInfo.index === this.state.selected ? '#00afec' : 'white'
                                            }
                                          }
                                        }else{
                                          return {}
                                        }
                                    }}
                                    defaultPageSize={25}
                                    style={{
                                        height: "400px"                                      
                                     }}
                                    />
                                 </div>
                         </div>  
                    </div>  
                    <nav class="navbarToButtoms navbar-light bg-light"> 
                         <div className="container row">
                             <div className="col my-2">
                                 <button type="button" className="btn-success btn-lg btn-block btn my-2" onClick={this.addSelectedToOrderLine}>Add to order</button>
                             </div>
                             <div className="col my-2">
                                 <button type="button" className="btn-lg btn-block btn-warning my-2" onClick={this.undoOrderLine}>Undo</button>
                            </div>
                        </div>
                    </nav>      
                </div>
            </div>
            );
    }

}

const mapStateToProps = (state)=>{
    return{
        userType: state.loginReducer.userType, 
        userId: state.loginReducer.userId
    }
}

const mapDispatchToProps = (dispatch) =>{
    return {
        addItemToCart: (orderLines) => {dispatch({type: "ADD_ITEMTOORDER",payload: {orderLines}})}
    }
}

export default connect(mapStateToProps ,mapDispatchToProps)(UserOrder)



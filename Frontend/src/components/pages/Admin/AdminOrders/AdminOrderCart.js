import React from 'react';
import "../../Pages.css";
import { connect } from "react-redux";
import {Link} from "react-router-dom";

class AdminOrderCart extends React.Component {
    constructor() {
        super();
        
    
        this.state = {
           address:"",
           company:"",
           cvr:"",
           contact:"",
           phone:null,
           zip:null,
           city:"",
           country:""

        };
    }
 
    onChange = (e) => {
        this.setState({[e.target.name]:e.target.value})
    }

    confirmed = (event) =>{
        event.preventDefault();
        console.log(this.state)
        this.props.setAdress(this.state.address)
        this.props.setCompany(this.state.company)
        this.props.setContactPerson(this.state.contact)
        this.props.setPhoneNumber(this.state.phone)
        this.props.setZip(this.state.zip)
        this.props.setCity(this.state.city)
        this.props.setCVR(this.state.cvr)
        this.props.setCountry(this.state.country)

        this.props.history.push("/Admin/Order/Cart/Confirm")
    }


    render(){
        let lines = this.props.order
        lines = lines.map((line)=>{return(
                <tr key={line.productId}>
                    <th scope="row">{line.productId}</th>
                    <td>{line.productName}</td>
                    <td>{line.amount}</td>
                </tr>
            )})
        
        
        return(
            <div className="PageStyle rounded">
                    <nav className="navbar navbar-dark bg-secondary"> <h2 className="text-center text-light">Cart:</h2></nav>
                <div className="container">
                    <div className="row">
                        <div className="col">
                            <div className="container my-3">
                                <table className="table table-dark">
                                    <thead>
                                        <tr key="header">
                                            <th scope="col">Product ID</th>
                                            <th scope="col">Product name</th>
                                            <th scope="col">Amount</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {lines}                                       
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div className="col container">
                            <h4 className="text-center my-2">Where to send the order:</h4>

                            <form className="">
                                <input type="text" className="input-group mb-3" name="company" onChange={this.onChange} placeholder="Company" key="company"/>
                                <input type="text" className="input-group mb-3" name="cvr" onChange={this.onChange} placeholder="CVR" key="cvr"/>
                                <input type="text" className="input-group mb-3" name="contact" onChange={this.onChange} placeholder="Contact Person" key="contact" required/>
                                <input type="number" className="input-group mb-3" name="phone" onChange={this.onChange} placeholder="PhoneNumber" key="phone" required/>
                                <input type="text" className="input-group mb-3" name="address" onChange={this.onChange} placeholder="Address" key="address" required/>
                                <input type="number" className="input-group mb-3" name="zip" onChange={this.onChange} placeholder="Zip" key="zip" required/>
                                <input type="text" className="input-group mb-3" name="city" onChange={this.onChange} placeholder="City" key="city" required/>
                                <input type="text" className="input-group mb-3" name="country" onChange={this.onChange} placeholder="Country" key="country" required/>
                                
                                <button className=" btn-success btn btn-block my-3" onClick={this.confirmed} type="submit">Send order</button>
                                <Link to="/Admin/Orders/" className=" btn-danger btn btn-block" >Cancel order</Link>        
                                   
                            </form>
                        </div>
                    </div>        
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state)=>{
    return{
        order: state.orderReducer.order
    }
}

const mapDispatchToProps = (dispatch) =>{
    return{
        
        setCompany: (company) => {dispatch({type: "SET_COMPANY",payload: {company}})},
        setContactPerson: (contactPerson) => {dispatch({type: "SET_CONTACTPERSON",payload: {contactPerson}})},
        setPhoneNumber: (phoneNumber) => {dispatch({type: "SET_PHONENUMBER",payload: {phoneNumber}})},
        setAdress: (address) => {dispatch({type: "SET_ADDRESS",payload: {address}})},
        setZip: (zip) => {dispatch({type: "SET_ZIP",payload: {zip}})},
        setCity: (city) => {dispatch({type: "SET_CITY",payload: {city}})},
        setCVR: (cvr) => {dispatch({type:"SET_CVR", payload:{cvr}})},
        setCountry: (country) => {dispatch({type:"SET_COUNTRY", payload:{country}})}

    }
}

export default connect(mapStateToProps, mapDispatchToProps)(AdminOrderCart);
import React,{Component} from 'react';
import axios from 'axios';
import { Link } from "react-router-dom";
import "../../Pages.css"
import "./EditOrderAddress.css"

export default class EditOrderAddress extends Component{
    constructor(props){
        super(props);
        const {company, contactPerson, address, zipCode, city} = props;
        this.state = {
            
        }
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <div className="container col md-auto">
                    <form>
                        <input type="text" name="company" defaultValue={this.company} className="newForm" onChange={this.onChange} placeholder="Company"/> 
                        <input type="text" name="contactPerson" defaultValue={this.contactPerson} className="newForm" onChange={this.onChange} placeholder="Contact Person"/>
                        <input type="text" name="address" defaultValue={this.address} className="newForm" onChange={this.onChange} placeholder="Address"/>
                        <input type="text" name="zipCode" defaultValue={this.zipCode} className="newForm" onChange={this.onChange} placeholder="Zipcode"/>
                        <input type="text" name="city" defaultValue={this.city} className="newForm" onChange={this.onChange} placeholder="City"/>
                    </form>
                    <div class="w-100"></div>
                    <div ></div>
                    <div className="container row">
                        <button className="col btn btn-succes mx-2">Save Changes</button>
                        <button className="col btn btn-warning mx-2">Discard Changes</button>
                        <Link to="/Admin/Order" className="col btn btn-info mx-2 " role=" button" >Back</Link>
                    </div>
                </div>
            </div>
        )
    }
}
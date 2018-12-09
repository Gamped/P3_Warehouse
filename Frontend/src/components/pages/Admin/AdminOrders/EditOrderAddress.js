import React,{Component} from 'react';
import axios from 'axios';
import { Link } from "react-router-dom";
import "../../Pages.css"
import "./EditOrderAddress.css"

export default class EditOrderAddress extends Component{
    constructor(props){
        super(props);
        const {company, cvr, contactPerson, phoneNumber, address, zipCode, city, country} = props;
        this.state = {
            
        }
    }

    render(){
        return(
            <div className="PageStyle rounded">
                    <div class="btn-group-vertical my-5 mx-5">
                        <input type="text" name="company" defaultValue={this.company} className="newForm" onChange={this.onChange} placeholder="Company"/> 
                        <input type="text" name="cvr" defaultValue={this.cvr} className="newForm" onChange={this.onChange} placeholder="CVR"/> 
                        <input type="text" name="contactPerson" defaultValue={this.contactPerson} className="newForm" onChange={this.onChange} placeholder="Contact Person"/>
                        <input type="text" name="phonenumber" defaultValue={this.phoneNumber} className="newForm" onChange={this.onChange} placeholder="Phonenumber"/>
                        <input type="text" name="address" defaultValue={this.address} className="newForm" onChange={this.onChange} placeholder="Address"/>
                        <input type="text" name="zipCode" defaultValue={this.zipCode} className="newForm" onChange={this.onChange} placeholder="Zipcode"/>
                        <input type="text" name="city" defaultValue={this.city} className="newForm" onChange={this.onChange} placeholder="City"/>
                        <input type="text" name="country" defaultValue={this.country} className="newForm" onChange={this.onChange} placeholder="Country"/>

                    <div className="btn-group my-2">
                        <button className="col btn btn-succes mx-2">Save Changes</button>
                        <button className="col btn btn-warning mx-2">Discard Changes</button>
                        <Link to="/Admin/Order" className="col btn btn-info mx-2 " role=" button" >Back</Link>
                    </div>
                    </div>
                </div>
        )
    }
}
import React from "react";
import { SSL_OP_ALLOW_UNSAFE_LEGACY_RENEGOTIATION } from "constants";

const Dropdown = (props) =>{
    const {action, owners} = props;
    const ownerList = owners.map((owner)=>{
        return(
            <option 
            key={owner.hexId}
            value={owner.hexId}
            name={owner.userType}>
            {owner.userType} - {owner.nickName}
            </option>
         )
    })
    return(
        <select className="custom-select my-2" onChange={action}>
            <option>Choose owner</option>
            {ownerList}
        </select>  
    )

}

export default Dropdown
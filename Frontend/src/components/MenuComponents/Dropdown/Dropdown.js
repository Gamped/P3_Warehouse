import React from "react";

const Dropdown = (props) =>{
    const {action, actors} = props;
    const actorList = actors.map((actor)=>{
        return(
            <option 
            key={actor.hexId}
            value={actor.hexId}
            name={actor.userType}>
            {actor.userType} - {actor.nickName}
            </option>
         )
    })
    return(
        <select className="custom-select form-control" id="dropdown" onChange={action}>
            <option>Choose Customer</option>
            {actorList}
        </select>  
    )
}

export default Dropdown
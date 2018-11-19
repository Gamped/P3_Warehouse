import React from 'react'

const OrderItem = (props) => {
    const {title, owner} = props;

    return(
        owner + " - " + title
    )
}

export default OrderItem
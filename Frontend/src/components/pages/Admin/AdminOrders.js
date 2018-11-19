import React from 'react';
import Table from "../../MenuComponents/Table/Table"
import "../Pages.css";
import ItemLists from "../../MenuComponents/ItemList/ItemList";
import OrderItem from "../../MenuComponents/ItemList/OrderItem";

//TODO: Implement data fetching from backend/database
const AdminOrders = (props) => {
    const columns = [
        {Header: "Package Name", accessor: "name"},
        {Header: "Package ID", accessor: "id"},
        {Header: "Unit Amount", accessor: "amount"},
        {Header: "Unit Type", accessor: "type"},
        {Header: "Packaged?", accessor: "packaged"}
    ]

    const ReadyOrders = []

    const inProgressOrders = [
        {title: "Harry Potter", owner: "Lindgren", data: [
            {name: "Harry Potter Vol. 4", id: 177037, amount: 2053, type: "book", packed: true}
        ]},
        {title: "Food Recipe Magazines", owner: "JustEat", data: [
            {name: "Amazing Food Recipes Vol. 1", id: 10034, amount: 2, type: "Magazine", packed: true},
            {name: "Amazing Food Recipes Vol. 3", id: 10052, amount: 5, type: "Magazine", packed: true},
            {name: "Amazing Food Recipes Vol. 4", id: 10098, amount: 17, type: "Magazine", packed: false}
        ]}
    ]

    const pendingOrders = []

    return(
        <div className="PageStyle">
            <customText_b>You are on Admin order page</customText_b>
            <Table columns={columns}/>
        </div>
    );
}

export default AdminOrders;


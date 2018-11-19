import React from 'react'
import ScrollArea from 'react-scrollbar'

const ItemList = (props) => {
    const items = [{props}];
    
    return (
        items.map(item => {
            return(
                <ScrollArea>
                    <li>
                        {item}
                    </li>
                </ScrollArea>
            );
        })
    )
}

export default ItemList;
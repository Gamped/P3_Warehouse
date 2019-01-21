
export const createOrder = (payload) =>{
    return (dispatch,getstate,{getFirebase,getFirestore}) =>{
        firestore = getFirestore();

        firestore.collection("products").document().set({ 
            amount:payload.amount, 
            products:{},
            owner: null,
            created: new Date.now(),
            state: "Created"
        }).then(
            dispatch({type:"ORDER_CREATED_SUCCESS"})
        ).catch((error)=>{
            dispatch({type: "ORDER_CREATED_ERROR",error})
        });
    }
}

export const updateOrderStateToPacked = (payload) =>{
    return (dispatch,getstate,{getFirebase,getFirestore}) =>{
        firestore = getFirestore();

        firestore.collection("products").document(payload.id).update({
            state:"Packed" 
        }).then(
            dispatch({type:"PRODUCT_PACKED_SUCCESS"})
        ).catch((error)=>{
            dispatch({type: "PRODUCT_PACKED_ERROR",error})
        });
    }
}

export const createNewProduct = (payload) => {
    return(dispatch, getState, {getFirebase, getFirestore}) =>{
        const firestore = getFirestore();

        firestore.collection("products").add({ 
            amount:payload.amount, 
            name:payload.name,
            owner: payload.owner,
            created: new Date.now()
        }).then(()=>{
            dispatch({type:"CREATE_PRODUCT", payload})
        }).catch((error)=>{
            dispatch({type:"CREATE_PRODUCT_ERROR",error})
        })
    }
};

export const updateProduct = (payload) =>{
    return(dispatch, getState, {getFirebase,getFirestore}) =>{
        const firestore = getFirestore();
        firestore.collection("products").document(payload.id).update({
            amount:payload.amount,
            name: payload.name,
            owner: payload.owner,
        }).then(
            dispatch({type:"PRODUCT_UPDATED"})
        ).catch((error)=>{
            dispatch({type:"PRODUCT_UPDATED_ERROR"},error)
        });
    }
}

export const updateProductAmount = (payload) =>{
    return(dispatch, getState, {getFirebase,getFirestore}) =>{
        const firestore = getFirestore();
        firestore.collection("products").document(payload.id).update({
            amount:payload.amount
        }).then(
            dispatch({type:"PRODUCT_UPDATED"})
        ).catch((error)=>{
            dispatch({type:"PRODUCT_UPDATED_ERROR"},error)
        });
    }
}

export const deleteProduct = (payload) =>{
    return(dispatch, getState, {getFirebase,getFirestore})=>{
        const firestore = getFirestore();
        firestore.collection("products").document(payload.id).delete().then(
            dispatch({type:"PRODUCT_DELETED_SUCCESS"})
        ).catch((error)=>{
            dispatch({type:"PRODUCT_DELETED_ERROR",error});
        });
    }
}
export const signIn = (credentials) =>{
    return (dispatch, getState, {getFirebase})=>{
        const firebase = getFirebase();

        firebase.auth().signInWithEmailAndPassword(
            credentials.email,
            credentials.password
        ).then( ()=> {
            dispatch({type: "LOGIN_SUCCESS"})
        }).catch((error)=>{
            dispatch({type: "LOGIN_ERROR",error})
        })
    }
}

export const signOut = () =>{
    return (dispatch,getState,{getFirebase}) =>{
        const firebase = getFirebase();
        firebase.auth().signOut().then(()=>{
            dispatch({type: "SIGNOUT_SUCCESS"})
        }).catch((error)=>{
            dispatch({type: "SIGNOUT_ERROR",error})
        })
    }
}

export const signUp = (payload) =>{
    return(dispatch,getState,{getFirebase,getFirestore}) =>{
        const firebase = getFirebase();
        const firestore = getFirestore();

        firebase.auth().createUserWithEmailAndPassword(
            payload.email,
            payload.password
        ).then((res)=>{
            return firestore.collection("users").doc(res.user.uid).set({
                name:payload.nickName,
                userType:payload.userType,
                contactInformation:{
                    email:payload.email,
                    phoneNumber:payload.phoneNumber,
                    address: payload.address,
                    city: payload.city,
                    zipCode:payload.zipCode
                }
            })
        }).then(()=>{
            dispatch({type:"SIGNUP_SUCCESS"})
        }).catch(error =>{
            dispatch({type:"SIGNUP_ERROR",error})
        })
    }
}

export const signUpEmployee = (payload) =>{
    return(dispatch,getState,{getFirebase,getFirestore}) =>{
        const firebase = getFirebase();
        const firestore = getFirestore();

        firebase.auth().createUserWithEmailAndPassword(
            payload.email,
            payload.password
        ).then((res)=>{
            return firestore.collection("users").doc(res.user.uid).set({
                name:payload.nickName,
                userType:"employee",
                email:payload.email
            })
        }).then(()=>{
            dispatch({type:"SIGNUP_SUCCESS"})
        }).catch(error =>{
            dispatch({type:"SIGNUP_ERROR",error})
        })
    }
}

export const updateUserProfile = (payload) =>{
    return(dispatch, getstate, {getFirebase,getFirestore}) =>{
        const firestore = getFirestore();

        firestore.collection("users").doc(payload.id).set({
            name:payload.name,
            contactInformation:{
                email:payload.email,
                
            }
        })
    }
}

export const deleteUser = (payload) =>{
    return(dispatch, getState, {getFirebase,getFirestore}) =>{
        const firestore = getFirestore();
        const firebase = getFirebase();
        const user = firebase.auth()
        //Todo: Fix så at du kan logge ind på brugeren.
        user.delete().then(
            firestore.collection("users").doc(payload.id).delete()
        ).then(
            dispatch({type:"DELETION_SUCCESS"})
        ).catch((error)=>{
            dispatch({type:"DELETION_ERROR",error})
        })
    }
}


export const sendPasswordReset = (payload) =>{
    return(dispatch, getstate,{getFirebase}) =>{
        var user = firebase.auth().currentUser;
        user.sendEmailVerification().then(function() {
        // Email sent.
        }).catch(function(error) {
        // An error happened.
});
    }
}
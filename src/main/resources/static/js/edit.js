'use strict'

let stompClient
let username
let labelUsername
let inputUsername
let labelRole
let labelStatus
let buttonSave

const editName = (btn) => {
    labelUsername = document.getElementById("username-label")
    inputUsername = document.getElementById("username-input")
    if (btn.innerHTML !== "Save username") {
        btn.innerHTML = "Save username";
        labelUsername.style.display = "none"
        inputUsername.style.display ="block"
        inputUsername.value = labelUsername.innerText
    } else {
        btn.innerHTML = "Edit username";
        inputUsername.style.display ="none"
        labelUsername.style.display = "block"
        if(inputUsername.value !== "" && inputUsername.value !== labelUsername.innerText){
            username = inputUsername.value.trim()
            editUsername()
        }
    }
};

const connect = () => {
    const socket = new SockJS('/ws')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, getSubscribe)
}
const getSubscribe = () =>{
    stompClient.subscribe('/edit/user', onResponseReceived)
}

const editUsername = () =>{
    if(stompClient){
        stompClient.send("/app/user.editUsername",{}, JSON.stringify({username}))
    }
}

const onResponseReceived = (payload) =>{
    const response = JSON.parse(payload.body);
    let responseEvent = {
        'TRUE': function () {
            labelUsername.innerHTML = username
        },
        'ERROR_USER': function () {
            window.alert("This name is already exists")
        },
        'TRUE_ADMIN': function (){
          window.alert("There aren't any problems")
        },
        'ERROR_ADMIN': function () {
            window.alert("Error with changing users")
        }
    }
    responseEvent[response.typeResponse]()
}

const changeRole = (idLabelRole) =>{
    idLabelRole = 'label-role'+idLabelRole
    labelRole = document.getElementById(idLabelRole)
    if(labelRole.innerText !== 'ADMIN'){
        labelRole.innerHTML = 'ADMIN'
    }else{
        labelRole.innerHTML = 'USER'
    }
}

const changeStatus = (idLabelStatus) =>{
    idLabelStatus = 'label-status' + idLabelStatus
    labelStatus = document.getElementById(idLabelStatus)
    if(labelStatus.innerText !== 'BANNED'){
        labelStatus.innerHTML = 'BANNED'
    }else{
        labelStatus.innerHTML = 'ACTIVE'
    }
}

const saveChange = (idSaveButton) =>{
    let idLabelStatus = 'label-status' + idSaveButton
    let idLabelRole = 'label-role'+idSaveButton
    let idLabelUsername = 'label-username'+idSaveButton
    labelRole = document.getElementById(idLabelRole)
    labelStatus = document.getElementById(idLabelStatus)
    username = document.getElementById(idLabelUsername)
    if(stompClient){
        stompClient.send("/app/user.changeUsers",{}, JSON.stringify({username: username.innerText, role: labelRole.innerText, status: labelStatus.innerText}))
    }
}

document.addEventListener("DOMContentLoaded", connect);

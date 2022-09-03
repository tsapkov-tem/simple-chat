'use strict'

let stompClient
let username
let separate
let isLoadMessage

const connect = (event) => {
    const socket = new SockJS('/ws')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, online)
    separate = 0
    event.preventDefault()
}

const online = () => {
    stompClient.subscribe('/topic/public', onMessageReceived)
    loadMessages()
}

const sendMessage = (event) => {
    const messageInput = document.querySelector('#message')
    const messageContent = messageInput.value.trim()

    if (messageContent && stompClient) {
        const chatMessage = {
            content: messageInput.value,
            type: 'CONTENT',
        }
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage))
        messageInput.value = ''
    }
    isLoadMessage = false
    event.preventDefault();
}

const loadMessages = (event) =>{
    stompClient.send("/app/chat.load",{}, JSON.stringify({'separate': separate, 'numberOfMessage': 0}))
    for (let i = 1; i <= 10; i++) {
        stompClient.send("/app/chat.load", {}, JSON.stringify({'separate': separate, 'numberOfMessage': i}))
    }
    separate++
    isLoadMessage = true
    event.preventDefault();
}


const onMessageReceived = (payload) => {
    const message = JSON.parse(payload.body);

    const chatCard = document.createElement('div')
    chatCard.className = 'card-body'

    const flexBox = document.createElement('div')
    flexBox.className = 'd-flex justify-content-end mb-4'
    chatCard.appendChild(flexBox)

    const messageElement = document.createElement('div')
    messageElement.className = 'msg_container_send'

    flexBox.appendChild(messageElement)

    let messageTypeEvent = {
        'CONNECT': function () {
            messageElement.classList.add('event-message')
            message.content = message.sender + ' join to the chat!'
        },
        'DISCONNECT': function (){
            messageElement.classList.add('event-message')
            message.content = message.sender + ' left the chat!'
        },
        'CONTENT': function (){
            messageElement.classList.add('chat-message')
            const avatarContainer = document.createElement('div')
            avatarContainer.className = 'img_cont_msg'
            const avatarElement = document.createElement('div')
            avatarElement.className = 'circle user_img_msg'

            const avatarText = document.createTextNode(message.sender.slice(0,2))
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message.sender)
            avatarContainer.appendChild(avatarElement)
            messageElement.style['background-color'] = getAvatarColor(message.sender)

            flexBox.appendChild(avatarContainer)
        }
    }

    messageTypeEvent[message.type]()

    messageElement.innerHTML = message.content

    const chat = document.querySelector('#chat')
    if(isLoadMessage) {
        chat.prepend(flexBox)
    }else {
        chat.append(flexBox)
    }
}

const hashCode = (str) => {
    let hash = 0
    for (let i = 0; i < str.length; i++) {
       hash = str.charCodeAt(i) + ((hash << 5) - hash)
    }
    return hash
}


const getAvatarColor = (messageSender) => {
    const colours = ['rgba(0,0,255,0.5)', 'rgba(0,252,0,0.5)', 'rgba(255,0,0,0.5)', 'rgba(236,0,170,0.5)']
    const index = Math.abs(hashCode(messageSender) % colours.length)
    return colours[index]
}

document.addEventListener("DOMContentLoaded", connect);
const messageLoad = document.querySelector('#message-load')
messageLoad.addEventListener('submit', loadMessages,true);
const messageControls = document.querySelector('#message-controls')
messageControls.addEventListener('submit', sendMessage, true)
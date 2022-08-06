'use strict'

let stompClient
let username

const connect = (event) => {
    const socket = new SockJS('/ws')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, online)
    event.preventDefault()
}

const online = () => {
    stompClient.subscribe('/topic/public', onMessageReceived)
}

const sendMessage = (event) => {
    const messageInput = document.querySelector('#message')
    const messageContent = messageInput.value.trim()

    if (messageContent && stompClient) {
        const chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CONTENT',
            time: moment().calendar()
        }
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage))
        messageInput.value = ''
    }
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
            const avatarText = document.createTextNode(message.sender[0])
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message.sender)
            avatarContainer.appendChild(avatarElement)
            messageElement.style['background-color'] = getAvatarColor(message.sender)

            flexBox.appendChild(avatarContainer)

            const time = document.createElement('span')
            time.className = 'msg_time_send'
            time.innerHTML = message.time
            messageElement.appendChild(time)
        }
    }

    messageTypeEvent[message.type]()

    messageElement.innerHTML = message.content

    const chat = document.querySelector('#chat')
    chat.appendChild(flexBox)
    chat.scrollTop = chat.scrollHeight
}

const hashCode = (str) => {
    let hash = 0
    for (let i = 0; i < str.length; i++) {
       hash = str.charCodeAt(i) + ((hash << 5) - hash)
    }
    return hash
}


const getAvatarColor = (messageSender) => {
    const colours = ['#2196F3', '#32c787', '#1BC6B4', '#A1B4C4']
    const index = Math.abs(hashCode(messageSender) % colours.length)
    return colours[index]
}

document.addEventListener("DOMContentLoaded", connect);
const messageControls = document.querySelector('#message-controls')
messageControls.addEventListener('submit', sendMessage, true)
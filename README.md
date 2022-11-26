# Simple-chat
### A simple chat with a basic function, profiles and the division of roles into administrators and users. The message exchange history is saved in the database.


### Stack of technologies
#### The main programming language is Java

+ Spring boot
  + Spring web
  + Spring websocket
  + Spring security
  + Spring boot data Mongodb
+ Lombok
+ Thymeleaf
+ JavaScript
+ JSock
+ Html
+ CSS
+ MongoDB

___

The connection between the Java-Server and the JavaScript-client is implemented using JSock.

### JavaScript
``` JavaScript
const connect = (event) => {
    const socket = new SockJS('/ws')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, online)
    separate = 0
    event.preventDefault()
}
```
### Java
``` Java
public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint ("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS ();
    }
```

When receiving a message from the client, the server saves the message to the Mongodb database.
___

Spring security allow you to define the available functionality for users. Ordinary users are allowed to use the chat, change their profile. Administrators can also block other users, change user profiles, and assign other users the administrator role.

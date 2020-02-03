# wotnectivity-ws
Implementation of WoTnectivity Requester to manage WebSocket requests

## Installation Method

If you want to compile your own source code, you will need to add the generated .jar to the local mvn repository.

```console
foo@bar:~$ mvn install:install-file "-Dfile=wotnectivity-0.0.1-ALPHA-SNAPSHOT.jar" "-DgroupId=es.ual.acg" "-DartifactId=wotnectivity-ws" "-Dversion=0.0.1-ALPHA-SNAPSHOT" "-Dpackaging=jar"
```

After installing it to your local maven repository you will only need to add it to the dependencies of the project where you want to use it.

```xml
<dependency>
    <groupId>es.ual.acg</groupId>
    <artifactId>wotnectivity-ws</artifactId>
    <version>0.0.1-ALPHA-SNAPSHOT</version>
</dependency>
```

## Use Example

In the next fragment of code you can see a use case of `WsReq`.

```java
String address = "wss://echo.websocket.org";
String payload = "test";
WsReq tester = new WsReq();
// WsReq tester2 = new WsReq(new WsListener());
var configuration = new HashMap<String, Object>();
var requestType = "sendMessage";
configuration.put("requestType", requestType);

HttpReq tester = new HttpReq();

try{
    var response = this.tester.sendRequest(address, configuration, payload);
}catch(Exception e){
    System.out.println(e.getMessage());
}
```

The configuration parameter of the request needs to have at least one parameter `requestType` where the type of request will be declared. The request type can be of two different types:

* `sendRequest`: Sends a request to the declared channel.
* `close`: Close the conection with the channel.

The example request is done with the default listener that is implemented in `WsListener.java`. This listener just shows in the console the messages that other users send to the channel. This implementation is very straight forward, if you want to give your application other behaviour when listening messages in the queue you have to implement a clase like this one.

```java
package es.ual.acg.utils;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class WsListener implements WebSocket.Listener {


    
    /** 
     * @param webSocket
     * @param data
     * @param last
     * @return CompletionStage<?>
     */
    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        System.out.println(data);
        webSocket.request(1);
        return CompletableFuture.completedFuture("onBinary() completed.").thenAccept(System.out::println);
    }

    
    /** 
     * @param webSocket
     * @param statusCode
     * @param reason
     * @return CompletionStage<?>
     */
    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket Listener has been closed with statusCode(" + statusCode + ").");
        System.out.println("Cause: " + reason);
        webSocket.sendClose(statusCode, reason);
        return new CompletableFuture<Void>();
    }

    
    /** 
     * @param webSocket
     * @param error
     */
    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("A " + error.getCause() + " exception was thrown.");
        System.out.println("Message: " + error.getLocalizedMessage());
        webSocket.abort();
    }

    
    /** 
     * @param webSocket
     */
    @Override
    public void onOpen(WebSocket webSocket) {
         // This WebSocket will invoke onText, onBinary, onPing, onPong or onClose
        // methods on the associated listener (i.e. receive methods) up to n more times
        webSocket.request(1);
        System.out.println("WebSocket Listener has been opened for requests.");
    }

    
    /** 
     * @param webSocket
     * @param message
     * @return CompletionStage<?>
     */
    @Override
    public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Ping: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return CompletableFuture.completedFuture("Ping completed.").thenAccept(System.out::println);
    }

    
    /** 
     * @param webSocket
     * @param message
     * @return CompletionStage<?>
     */
    @Override
    public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Pong: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return CompletableFuture.completedFuture("Pong completed.").thenAccept(System.out::println);
    }

    
    /** 
     * @param webSocket
     * @param data
     * @param last
     * @return CompletionStage<?>
     */
    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println(data);
        webSocket.request(1);
        return CompletableFuture.completedFuture("onText() completed.").thenAccept(System.out::println);
    }
    
}
```
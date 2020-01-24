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
package es.ual.acg;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import es.ual.acg.utils.WsListener;

public class WsReq implements IRequester {

    private HttpClient client;
    private WebSocket wsConnection;
    private WebSocket.Listener listener;

    
    /** 
     * @param listener
     * @return 
     */
    public WsReq(WebSocket.Listener listener) {
        this.client = HttpClient.newHttpClient();
        this.listener = listener;

    }

    
    /** 
     * @return 
     */
    public WsReq() {
        this.client = HttpClient.newHttpClient();
        this.listener = new WsListener();

    }

    
    /** 
     * @param address
     * @param payload
     * @return CompletableFuture<WebSocket>
     */
    private CompletableFuture<WebSocket> sendText(String address, String payload) {

        this.wsConnection = this.client.newWebSocketBuilder().buildAsync(URI.create(address), this.listener).join();
        ;
        return this.wsConnection.sendText(payload, true);
    }

    
    /** 
     * @return CompletableFuture<WebSocket>
     */
    private CompletableFuture<WebSocket> sendClose() {

        return this.wsConnection.sendClose(WebSocket.NORMAL_CLOSURE, "Finished communication");
    }

    
    /** 
     * @param address
     * @param configuration
     * @param payload
     * @return CompletableFuture<?>
     */
    @Override
    public CompletableFuture<?> sendRequest(String address, HashMap<String, Object> configuration, Object payload) {
        String requestType = configuration.get("requestType").toString();
        configuration.remove("requestType");
        CompletableFuture<?> output;
        if(requestType.equals("close")){
            output = this.sendClose();
        }else if (requestType.equals("sendMessage")){
            output = this.sendText(address, payload.toString());
        }
        else{
            output = null;
        }
        return output;
    }

}
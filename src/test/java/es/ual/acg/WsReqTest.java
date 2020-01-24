package es.ual.acg;

import org.junit.Test;

import es.ual.acg.utils.WsListener;

import static org.junit.Assert.*;

import java.util.HashMap;




public class WsReqTest{

    String address = "wss://echo.websocket.org";
    String payload = "test";
    WsReq tester = new WsReq(new WsListener());
    WsReq tester2 = new WsReq();

    @Test
    public void TestWsReqSendText() {
        
        
        try{
            var configuration = new HashMap<String, Object>();
            var requestType = "sendMessage";
            configuration.put("requestType", requestType);
            var response = this.tester.sendRequest(address, configuration, payload);

            System.out.println(response.toString());
            assertNotEquals(response,"");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }

    @Test
    public void TestWsReqSendClose() {
        
        
        try{
            var configuration = new HashMap<String, Object>();
            var requestType = "close";
            configuration.put("requestType", requestType);
            var response = this.tester.sendRequest(address, configuration, payload);

            System.out.println(response.get().toString());
            assertNotEquals(response.get().toString(),"");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }

    @Test
    public void TestWsReqSendTextDefaultListener() {
        
        
        try{
            var configuration = new HashMap<String, Object>();
            var requestType = "sendMessage";
            configuration.put("requestType", requestType);
            var response = this.tester2.sendRequest(address, configuration, payload);

            System.out.println(response.toString());
            assertNotEquals(response,"");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }

}



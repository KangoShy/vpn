package com.dachui.vpn.controller;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;


@ServerEndpoint("/cmd/prod")
public class WebSocket {

    private static Map<String, Session> sessions = new HashMap<>();


    @OnOpen
    public void open(@PathParam("user") String user, Session session) {
        sessions.put(user, session);
        System.err.println("open了------");

    }

    @OnMessage
    public void message(@PathParam("user") String user, Session session) {
        System.err.println("message了========");
    }

}
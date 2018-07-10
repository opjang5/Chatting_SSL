package com.testserversocket.main;

import java.util.Vector;
public class ChatManager {
    private ChatManager(){}
    private static final ChatManager CM=new ChatManager();
    public static ChatManager getChatManager(){
        return CM;
    }

    Vector<ChatSocket> vector = new Vector<ChatSocket>();
    public void add(ChatSocket cs){
        vector.add(cs);
    }

    public void publish(ChatSocket cs, String msg){
        for (int i = 0; i < vector.size(); i++) {
            ChatSocket csTemp = vector.get(i);
            if (!cs.equals(csTemp)) {
                csTemp.out(msg+"\n");
            }
        }
    }

}
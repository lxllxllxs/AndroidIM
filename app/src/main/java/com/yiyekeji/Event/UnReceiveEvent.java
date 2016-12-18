package com.yiyekeji.Event;

/**
 * 该类将被废弃
 * 用作通知信息列表里
 * Created by Administrator on 2016/11/1.
 */
public class UnReceiveEvent {
  /*  HashMap<String, ArrayList<IInformation>> hashMap;
    public static HashMap<String, ArrayList<IInformation>> chatMap = new HashMap<>();
    private static List<String> msgIdList = new ArrayList<>();

    *//**对信息进行归类
     * end作为结束符
     * @param chatMessage
     *//*
    public static void setChatMessageMessage(ChatMessage chatMessage) {
        msgIdList.add(chatMessage.getMsgId());
        if (!chatMap.containsKey(chatMessage.getSenderId())){
            chatMap.put(chatMessage.getSenderId(),new ArrayList<IInformation>());
        }
        chatMap.get(chatMessage.getSenderId()).add(chatMessage);
    }

    public static void addUnReceiMessageToSession() {
        Iterator iterator=chatMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry  item= (Map.Entry) iterator.next();
            String userId = (String) item.getKey();
            List<ChatMessage> list = (List<ChatMessage>) item.getValue();
            int msgListSize = list.size();
            ChatMessage msg=list.get(msgListSize-1);//最新一条消息
            DbUtil.upDataUnReceiSession(msg.getMsgId(),userId,msgListSize+"");
        }
    }


    public HashMap<String, ArrayList<IInformation>> getChatMap() {
        hashMap = new HashMap<>();
        assert chatMap != null;
        hashMap.putAll(UnReceiveEvent.chatMap);
        return hashMap;
    }
    public List<String> getMsgIdList() {
        return msgIdList;
    }*/
}

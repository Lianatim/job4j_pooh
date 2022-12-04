package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "404";
        if ("POST".equals(req.httpRequestType()) && topics.containsKey(req.getSourceName())) {
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> clientQueue = topics.get(req.getSourceName());
            for (String key : clientQueue.keySet()) {
                clientQueue.get(key).add(req.getParam());
            }
            status = "200";
        } else {
            if (topics.containsKey(req.getSourceName())) {
                ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> clientQueue = topics.get(req.getSourceName());
                if (clientQueue.containsKey(req.getParam())) {
                    text = clientQueue.getOrDefault(req.getParam(), new ConcurrentLinkedQueue<>()).poll();
                    status = "200";
                }
            } else {
                topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
                ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> clientQueue = topics.get(req.getSourceName());
                clientQueue.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            }
        }
        return new Resp(text, status);
    }

}
package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        return content.contains("topic") ? parseTopic(content) : parseQueue(content);
    }

    public static Req parseTopic(String content) {
        String httpRequestType = content.contains("GET") ? "GET" : "POST";
        String poohMode = "topic";
        String sourceName;
        String param;
        if ("POST".equals(httpRequestType)) {
            String[] contentPart = content.split(" -d");
            param = content.substring(content.indexOf("d") + 3, content.lastIndexOf("\""));
            if (contentPart[0].contains("/")) {
                sourceName = content.substring(content.lastIndexOf("/") + 1, content.lastIndexOf(" -d"));
            } else {
                sourceName = content.substring(content.lastIndexOf("/") + 1);
            }
        } else {
            sourceName = content.substring(content.lastIndexOf("/") + 1);
            param = content.substring(content.lastIndexOf("/") + 1);
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public static Req parseQueue(String content) {
        String httpRequestType = content.contains("GET") ? "GET" : "POST";
        String poohMode = "queue";
        String sourceName;
        String param = " ";
        if ("POST".equals(httpRequestType)) {
            poohMode = "topic";
            param = content.substring(content.indexOf("d") + 3, content.lastIndexOf("\""));
            sourceName = content.substring(content.lastIndexOf("/") + 1, content.lastIndexOf(" -d"));
        } else {
            sourceName = content.substring(content.lastIndexOf("/") + 1);
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
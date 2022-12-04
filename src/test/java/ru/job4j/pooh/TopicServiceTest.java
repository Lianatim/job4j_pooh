package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicServiceTest {

    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("");
    }

    @Test
    public void whenTopicManyParam() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForPublisher2 = "temperature=20";
        String paramForPublisher3 = "temperature=30";
        String paramForSubscriber1 = "client407";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher2)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher3)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result3 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        assertThat(result1.text()).isEqualTo(paramForPublisher);
        assertThat(result2.text()).isEqualTo(paramForPublisher2);
        assertThat(result3.text()).isEqualTo(paramForPublisher3);
    }
}
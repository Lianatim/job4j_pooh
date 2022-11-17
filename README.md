# Проект "Pooh JMS"

## О прокте:

В этом проекте написан аналог асинхронной очереди. Приложение запускает Socket и ждет клиентов.
Клиенты могут быть двух типов: отправители (publisher), получатели (subscriber).
В качестве клиента использован [cURL](https://curl.se/download.html). В качестве протокола - HTTP.
В сервере описана система обмена сообщениями.

Pooh работает в двух режимах:

- Queue - все потребители получают данные из одной и той же очереди.
- Topic - для каждого потребителя будет своя уникальная очередь с данными.
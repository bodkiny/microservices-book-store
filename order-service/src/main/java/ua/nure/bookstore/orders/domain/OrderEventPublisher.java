package ua.nure.bookstore.orders.domain;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ua.nure.bookstore.orders.ApplicationProperties;
import ua.nure.bookstore.orders.domain.models.OrderCancelledEvent;
import ua.nure.bookstore.orders.domain.models.OrderCreatedEvent;
import ua.nure.bookstore.orders.domain.models.OrderDeliveredEvent;
import ua.nure.bookstore.orders.domain.models.OrderErrorEvent;

@Component
class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    OrderEventPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void publish(OrderCreatedEvent event) {
        send(properties.newOrdersQueue(), event);
    }

    public void publish(OrderDeliveredEvent event) {
        send(properties.deliveredOrdersQueue(), event);
    }

    public void publish(OrderCancelledEvent event) {
        send(properties.cancelledOrdersQueue(), event);
    }

    public void publish(OrderErrorEvent event) {
        send(properties.errorOrdersQueue(), event);
    }

    private void send(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), routingKey, payload);
    }
}

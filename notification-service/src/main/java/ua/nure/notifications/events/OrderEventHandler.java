package ua.nure.notifications.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ua.nure.notifications.domain.NotificationService;
import ua.nure.notifications.domain.OrderEventEntity;
import ua.nure.notifications.domain.OrderEventRepository;
import ua.nure.notifications.domain.models.OrderCancelledEvent;
import ua.nure.notifications.domain.models.OrderCreatedEvent;
import ua.nure.notifications.domain.models.OrderDeliveredEvent;
import ua.nure.notifications.domain.models.OrderErrorEvent;

@Component
class OrderEventHandler {
    public static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    private final NotificationService notificationService;
    private final OrderEventRepository orderEventRepository;

    public OrderEventHandler(NotificationService notificationService, OrderEventRepository orderEventRepository) {
        this.notificationService = notificationService;
        this.orderEventRepository = orderEventRepository;
    }

    @RabbitListener(queues = "${notifications.new-orders-queue}")
    void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Order Created Event: {}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received duplicate OrderCreatedEvent with eventId: {}", event.eventId());
            return;
        }
        notificationService.sendOrderCreatedNotification(event);
        OrderEventEntity eventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(eventEntity);
    }

    @RabbitListener(queues = "${notifications.delivered-orders-queue}")
    void handleOrderDeliveredEvent(OrderDeliveredEvent event) {
        log.info("Order Delivered Event: {}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received duplicate OrderDeliveredEvent with eventId: {}", event.eventId());
            return;
        }
        notificationService.sendOrderDeliveredNotification(event);
        OrderEventEntity eventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(eventEntity);
    }

    @RabbitListener(queues = "${notifications.cancelled-orders-queue}")
    void handleOrderCancelledEvent(OrderCancelledEvent event) {
        log.info("Order Cancelled Event: {}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received duplicate OrderCancelledEvent with eventId: {}", event.eventId());
            return;
        }
        notificationService.sendOrderCancelledNotification(event);
        OrderEventEntity eventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(eventEntity);
    }

    @RabbitListener(queues = "${notifications.error-orders-queue}")
    void handleOrderErrorEvent(OrderErrorEvent event) {
        log.info("Order Error Event: {}", event);
        if (orderEventRepository.existsByEventId(event.eventId())) {
            log.warn("Received duplicate OrderErrorEvent with eventId: {}", event.eventId());
            return;
        }
        notificationService.sendOrderErrorEventNotification(event);
        OrderEventEntity eventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(eventEntity);
    }
}

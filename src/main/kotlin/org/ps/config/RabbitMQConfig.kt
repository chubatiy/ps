package org.ps.config


import org.ps.consts.*
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {

    @Bean
    fun paymentExchange() = TopicExchange(PAYMENT_EXCHANGE)

    @Bean
    fun paymentCreatedQueue() = Queue(PAYMENT_CREATED_QUEUE)

    @Bean
    fun paymentCompletedQueue() = Queue(PAYMENT_COMPLETED_QUEUE)

    @Bean
    fun paymentFailedQueue() = Queue(PAYMENT_FAILED_QUEUE)

    @Bean
    fun paymentCreatedBinding(paymentExchange: TopicExchange, paymentCreatedQueue: Queue): Binding =
        BindingBuilder.bind(paymentCreatedQueue).to(paymentExchange).with(paymentCreatedQueue.name)

    @Bean
    fun paymentCompletedBinding(paymentExchange: TopicExchange, paymentCompletedQueue: Queue): Binding =
        BindingBuilder.bind(paymentCompletedQueue).to(paymentExchange).with(paymentCompletedQueue.name)

    @Bean
    fun paymentFailedBinding(paymentExchange: TopicExchange, paymentFailedQueue: Queue): Binding =
        BindingBuilder.bind(paymentFailedQueue).to(paymentExchange).with(paymentFailedQueue.name)


    @Bean
    fun messageConverter() = Jackson2JsonMessageConverter()

    @Bean
    fun rabbitTemplate(
        connectionFactory: ConnectionFactory,
        messageConverter: Jackson2JsonMessageConverter
    ) = RabbitTemplate(connectionFactory).apply {
        this.messageConverter = messageConverter
    }
}
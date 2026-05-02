package ru.shpg.analyticservice.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import ru.shpg.analyticservice.config.property.KafkaProperties

@Configuration
class KafkaConfig(
    private val kafkaProperties: KafkaProperties
) {

    @Bean
    fun transactionsTopic(): NewTopic {
        return TopicBuilder
            .name(kafkaProperties.transactionsTopic)
            .partitions(kafkaProperties.partitionSize)
            .replicas(kafkaProperties.replicaSize)
            .build()
    }

    @Bean
    fun alertsTopic(): NewTopic {
        return TopicBuilder
            .name(kafkaProperties.alertsTopic)
            .partitions(kafkaProperties.partitionSize)
            .replicas(kafkaProperties.replicaSize)
            .build()
    }

}
package com.betvictor.task2.kafka;

import com.betvictor.task2.entity.Message;
import com.betvictor.task2.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    private MessageRepository messageRepository;

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    private static final String topicName = "words.processed";
    private static Logger logger = LoggerFactory.getLogger(KafkaListener.class);

    @org.springframework.kafka.annotation.KafkaListener(
            topicPartitions = @TopicPartition(topic = topicName,
                    partitionOffsets = {
                            @PartitionOffset(partition = "0", initialOffset = "0"),
                            @PartitionOffset(partition = "1", initialOffset = "0"),
                            @PartitionOffset(partition = "2", initialOffset = "0"),
                            @PartitionOffset(partition = "3", initialOffset = "0")}),
            containerFactory = "partitionsKafkaListenerContainerFactory")
    public void listenToPartition(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
                logger.info("Received Message: " + message + "from partition: " + partition);
            Message bvMessage = new Message();
            bvMessage.setMessage(message);
            try {
                messageRepository.save(bvMessage);
            }catch(Exception e){
                logger.error("Error while trying to save message to DB: ", e);
            }
    }

}

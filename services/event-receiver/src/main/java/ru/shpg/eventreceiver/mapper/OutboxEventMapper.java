package ru.shpg.eventreceiver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.shpg.eventreceiver.entity.OutboxEvent;
import ru.shpg.eventreceiver.model.EventRequest;

@Mapper(componentModel = "spring", uses = JsonUtils.class)
public interface OutboxEventMapper {

    @Mapping(target = "id", source = "request.eventId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "eventType", source = "request.type")
    @Mapping(target = "payload", source = "request.payload", qualifiedByName = "toJson")
    @Mapping(target = "eventTimestamp", source = "request.timestamp")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    OutboxEvent toEntity(EventRequest request, String userId);

}

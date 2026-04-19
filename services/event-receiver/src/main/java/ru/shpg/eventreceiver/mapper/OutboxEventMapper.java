package ru.shpg.eventreceiver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.shpg.eventreceiver.entity.OutboxEvent;
import ru.shpg.eventreceiver.model.EventRequest;

@Mapper(componentModel = "spring", uses = JsonUtils.class)
public interface OutboxEventMapper {

    @Mapping(target = "eventId", source = "request.eventId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "type", source = "request.type")
    @Mapping(target = "payload", source = "request", qualifiedByName = "toJson")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    OutboxEvent toEntity(EventRequest request, String userId);

}

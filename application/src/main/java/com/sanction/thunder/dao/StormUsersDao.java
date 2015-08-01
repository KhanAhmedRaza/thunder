package com.sanction.thunder.dao;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Expected;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.sanction.thunder.models.StormUser;

import java.time.Instant;
import java.util.UUID;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class StormUsersDao {
  /** The DynamoDB table object to interact with. */
  private final Table table;

  /** A mapper for converting to and from JSON. */
  private final ObjectMapper mapper;

  @Inject
  public StormUsersDao(DynamoDB dynamo, ObjectMapper mapper) {
    this.table = dynamo.getTable("thunder-users-test");
    this.mapper = mapper;
  }

  /**
   * Insert a new StormUser into the data store.
   *
   * @param object The object to insert.
   * @return The data record created.
   */
  public boolean insert(StormUser object) {
    checkNotNull(object);

    long now = Instant.now().toEpochMilli();
    Item item = new Item()
        .withPrimaryKey("username", object.getUsername())
        .withString("version", UUID.randomUUID().toString())
        .withLong("creation_time", now)
        .withLong("update_time", now)
        .withString("document", toJson(mapper, object));

    try {
      table.putItem(item, new Expected("username").notExist());
    } catch (ConditionalCheckFailedException e) {
      return false;
    }

    return true;
  }

  private static <T> String toJson(ObjectMapper mapper, T object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw Throwables.propagate(e);
    }
  }


}

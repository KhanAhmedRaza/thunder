package com.sanctionco.thunder.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ResponseTypeTest {

  @Test
  void jsonTypeShouldMapToString() {
    assertAll("Assert equal JSON enum and string value",
        () -> assertEquals(ResponseType.JSON, ResponseType.fromString("json")),
        () -> assertEquals("json", ResponseType.JSON.toString()));
  }

  @Test
  void htmlTypeShouldMapToString() {
    assertAll("Assert equal HTML enum and string value",
        () -> assertEquals(ResponseType.HTML, ResponseType.fromString("html")),
        () -> assertEquals("html", ResponseType.HTML.toString()));
  }

  @Test
  void unknownStringsShouldMapToNull() {
    assertNull(ResponseType.fromString("unknown"));
  }
}

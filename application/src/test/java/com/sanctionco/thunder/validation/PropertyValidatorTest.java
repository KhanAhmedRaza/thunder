package com.sanctionco.thunder.validation;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PropertyValidatorTest {
  private static final List<PropertyValidationRule> VALIDATION_RULES
      = Collections.singletonList(
          new PropertyValidationRule("firstProperty", "string"));

  @Test
  void testSkipValidation() {
    PropertyValidator validator = new PropertyValidator(null);
    Map<String, Object> properties = Collections.emptyMap();

    assertTrue(validator.isValidPropertiesMap(properties));
  }

  @Test
  void testInvalidSize() {
    PropertyValidator validator = new PropertyValidator(VALIDATION_RULES);
    Map<String, Object> properties = Collections.emptyMap();

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  @Test
  void testMismatchName() {
    PropertyValidator validator = new PropertyValidator(VALIDATION_RULES);
    Map<String, Object> properties = Collections.singletonMap("myProperty", "value");

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  /* String type */
  @Test
  void testMismatchTypeString() {
    PropertyValidator validator = new PropertyValidator(VALIDATION_RULES);
    Map<String, Object> properties = Collections.singletonMap("firstProperty", 1);

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  @Test
  void testSuccessfulStringValidation() {
    PropertyValidator validator = new PropertyValidator(VALIDATION_RULES);
    Map<String, Object> properties = Collections.singletonMap("firstProperty", "value");

    assertTrue(validator.isValidPropertiesMap(properties));
  }

  /* Integer type */
  @Test
  void testMismatchTypeInteger() {
    List<PropertyValidationRule> validationRules = Arrays.asList(
        new PropertyValidationRule("firstProperty", "string"),
        new PropertyValidationRule("secondProperty", "integer"));

    PropertyValidator validator = new PropertyValidator(validationRules);
    Map<String, Object> properties = ImmutableMap.of(
        "firstProperty", "value",
        "secondProperty", "1");

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  @Test
  void testSuccessfulIntegerValidation() {
    PropertyValidator validator = new PropertyValidator(VALIDATION_RULES);
    Map<String, Object> properties = ImmutableMap.of(
        "firstProperty", "value",
        "secondProperty", 1);

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  /* Boolean type */
  @Test
  void testMismatchTypeBoolean() {
    List<PropertyValidationRule> validationRules = Arrays.asList(
        new PropertyValidationRule("firstProperty", "string"),
        new PropertyValidationRule("secondProperty", "integer"),
        new PropertyValidationRule("thirdProperty", "boolean"));

    PropertyValidator validator = new PropertyValidator(validationRules);
    Map<String, Object> properties = ImmutableMap.of(
        "firstProperty", "value",
        "secondProperty", 1,
        "thirdProperty", "false");

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  @Test
  void testSuccessfulBooleanValidation() {
    PropertyValidator validator = new PropertyValidator(VALIDATION_RULES);
    Map<String, Object> properties = ImmutableMap.of(
        "firstProperty", "value",
        "secondProperty", 1,
        "thirdProperty", false);

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  /* Boolean type */
  @Test
  void testMismatchTypeDouble() {
    List<PropertyValidationRule> validationRules = Arrays.asList(
        new PropertyValidationRule("firstProperty", "string"),
        new PropertyValidationRule("secondProperty", "integer"),
        new PropertyValidationRule("thirdProperty", "boolean"),
        new PropertyValidationRule("fourthProperty", "double"));

    PropertyValidator validator = new PropertyValidator(validationRules);
    Map<String, Object> properties = ImmutableMap.of(
        "firstProperty", "value",
        "secondProperty", 1,
        "thirdProperty", false,
        "fourthProperty", 1);

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  @Test
  void testSuccessfulDoubleValidation() {
    PropertyValidator validator = new PropertyValidator(VALIDATION_RULES);
    Map<String, Object> properties = ImmutableMap.of(
        "firstProperty", "value",
        "secondProperty", 1,
        "thirdProperty", false,
        "fourthProperty", 1.0);

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  /* List type */
  @Test
  void testMismatchTypeList() {
    List<PropertyValidationRule> validationRules = Arrays.asList(
        new PropertyValidationRule("firstProperty", "string"),
        new PropertyValidationRule("secondProperty", "integer"),
        new PropertyValidationRule("thirdProperty", "boolean"),
        new PropertyValidationRule("fourthProperty", "double"),
        new PropertyValidationRule("fifthProperty", "list"));

    PropertyValidator validator = new PropertyValidator(validationRules);
    Map<String, Object> properties = ImmutableMap.of(
        "firstProperty", "value",
        "secondProperty", 1,
        "thirdProperty", false,
        "fourthProperty", 1.0,
        "fifthProperty", Collections.emptyMap());

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  @Test
  void testSuccessfulListValidation() {
    PropertyValidator validator = new PropertyValidator(VALIDATION_RULES);
    Map<String, Object> properties = ImmutableMap.of(
        "firstProperty", "value",
        "secondProperty", 1,
        "thirdProperty", false,
        "fourthProperty", 1.0,
        "fifthProperty", Collections.emptyList());

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  /* Map type */
  @Test
  void testMismatchTypeMap() {
    List<PropertyValidationRule> validationRules = Arrays.asList(
        new PropertyValidationRule("firstProperty", "string"),
        new PropertyValidationRule("secondProperty", "integer"),
        new PropertyValidationRule("thirdProperty", "boolean"),
        new PropertyValidationRule("fourthProperty", "double"),
        new PropertyValidationRule("fifthProperty", "list"),
        new PropertyValidationRule("sixthProperty", "map"));

    PropertyValidator validator = new PropertyValidator(validationRules);
    Map<String, Object> properties = ImmutableMap.of(
        "firstProperty", "value",
        "secondProperty", 1,
        "thirdProperty", false,
        "fourthProperty", 1.0,
        "fifthProperty", Collections.emptyList());

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  @Test
  void testSuccessfulMapValidation() {
    PropertyValidator validator = new PropertyValidator(VALIDATION_RULES);
    Map<String, Object> properties = ImmutableMap.of(
        "firstProperty", "value",
        "secondProperty", 1,
        "thirdProperty", false,
        "fourthProperty", 1.0,
        "fifthProperty", Collections.emptyMap());

    assertFalse(validator.isValidPropertiesMap(properties));
  }

  @Test
  void testGetType() {
    assertAll("Assert equal return value from getType PropertyValidator method.",
        () -> assertEquals(String.class, PropertyValidator.getType("string")),
        () -> assertEquals(Integer.class, PropertyValidator.getType("integer")),
        () -> assertEquals(Boolean.class, PropertyValidator.getType("boolean")),
        () -> assertEquals(Double.class, PropertyValidator.getType("double")),
        () -> assertEquals(List.class, PropertyValidator.getType("list")),
        () -> assertEquals(Map.class, PropertyValidator.getType("map")),
        () -> assertEquals(Object.class, PropertyValidator.getType("unknown")));
  }
}

package com.website.surotec_academy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SemesterEnum {

    FIRST("FIRST"),
    SECOND("SECOND");

    private final String value;

    SemesterEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SemesterEnum fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Semester value cannot be null or empty");
        }

        String normalized = value.trim().toUpperCase();

        for (SemesterEnum semester : SemesterEnum.values()) {
            if (semester.value.equalsIgnoreCase(normalized) || semester.name().equalsIgnoreCase(normalized)) {
                return semester;
            }
        }

        throw new IllegalArgumentException("Invalid semester value: " + value + ". Valid values: FIRST, SECOND");
    }

    @Override
    public String toString() {
        return value;
    }
}

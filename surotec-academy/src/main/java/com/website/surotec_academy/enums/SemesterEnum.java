package com.website.surotec_academy.enums;

public enum SemesterEnum {
    FIRST("1"),
    SECOND("2");

    private final String value;

    SemesterEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Convierte un input a SemesterEnum.
     * Acepta:
     *  - los valores definidos ("1", "2")
     *  - los nombres del enum ("FIRST", "SECOND") sin importar mayúsculas/minúsculas
     *
     * Lanza IllegalArgumentException si no reconoce el valor.
     */
    public static SemesterEnum fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Semester value is null");
        }
        String v = value.trim();
        for (SemesterEnum semester : SemesterEnum.values()) {
            if (semester.value.equals(v) || semester.name().equalsIgnoreCase(v)) {
                return semester;
            }
        }
        throw new IllegalArgumentException("Invalid semester value: " + value);
    }

    @Override
    public String toString() {
        return name();
    }
}

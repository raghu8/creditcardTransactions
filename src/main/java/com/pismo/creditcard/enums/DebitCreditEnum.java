package com.pismo.creditcard.enums;

public enum DebitCreditEnum {
    DR("Debit"),
    CR("Credit");

    private final String description;

    DebitCreditEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static DebitCreditEnum fromString(String value) {
        for (DebitCreditEnum type : DebitCreditEnum.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid DebitCreditEnum value: " + value);
    }
}
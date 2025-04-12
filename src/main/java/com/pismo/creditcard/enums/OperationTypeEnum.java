package com.pismo.creditcard.enums;

public enum OperationTypeEnum {
    NORMAL_PURCHASE(1, "Normal Purchase"),
    PURCHASE_WITH_INSTALLMENTS(2, "Purchase with installments"),
    WITHDRAWAL(3, "Withdrawal"),
    PAYMENT(4, "Payment");

    private final int id;
    private final String description;

    OperationTypeEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static OperationTypeEnum fromId(int id) {
        for (OperationTypeEnum type : OperationTypeEnum.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid OperationType ID: " + id);
    }
}

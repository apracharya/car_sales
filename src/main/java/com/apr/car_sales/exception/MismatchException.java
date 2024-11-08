package com.apr.car_sales.exception;

public class MismatchException extends RuntimeException {
    String resourceName;
    String fieldName;
    long fieldValue;

    String stringValue;

    public MismatchException(String resourceName, String fieldName) {
        super(String.format("%s mismatch", resourceName)); // user mismatch
        this.resourceName = resourceName;
    }
}

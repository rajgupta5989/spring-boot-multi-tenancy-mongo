package com.sample.multitenant.mongo.exception;

/**
 * User: rajeshgupta
 * Date: 08/03/21
 */
public class TenantNotFoundException extends RuntimeException {

    public TenantNotFoundException() {
        super();
    }

    public TenantNotFoundException(String message) {
        super(message);
    }
}

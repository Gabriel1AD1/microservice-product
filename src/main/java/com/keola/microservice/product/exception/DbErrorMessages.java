package com.keola.microservice.product.exception;

import java.util.Map;
import java.util.HashMap;

public class DbErrorMessages {
    public static final Map<String, String> ERROR_MESSAGES = new HashMap<>();

    static {
        ERROR_MESSAGES.put("tbl_customer_email_key", "The email address already exists in the system.");
    }

    public static String getErrorMessage(String errorKey) {
        return ERROR_MESSAGES.getOrDefault(errorKey, "An unexpected error occurred.");
    }
}

package com.example.modeling_tools.exception;

import java.util.List;

/**
 * Exception thrown by the system when a user tries to suggest a Modeling Tool edit with the exact
 * same values as the Modeling Tool that is expected to be updated.
 */
public class DuplicateException extends ErrorListException {
    public DuplicateException(String messageSummary, List<String> errors) {
        super("Cause", messageSummary, errors);
    }
}

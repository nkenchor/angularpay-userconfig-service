package io.angularpay.userconfig.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_MESSAGE_ERROR("The message format read from the given topic is invalid"),
    VALIDATION_ERROR("The request has validation errors"),
    ILLEGAL_SELF_DELETE_ERROR("Self account deletion is NOT permitted"),
    ILLEGAL_SELF_STATUS_CHANGE_ERROR("Self account status change is NOT permitted"),
    DUPLICATE_ERROR("A resource with the same identifier already exist. Perhaps you want to update it?"),
    USER_DISABLED_ERROR("You cannot performed this action on a user resource that is disabled"),
    REQUEST_NOT_FOUND("The requested resource was NOT found"),
    DUPLICATE_REQUEST_ERROR("A resource having the same identifier already exist"),
    PEER_LOOKUP_QUEUE_ERROR("Unable to get a reference from peer-lookup-queue. Please ensure the queue exists and is populated"),
    GENERIC_ERROR("Generic error occurred. See stacktrace for details"),
    AUTHORIZATION_ERROR("You do NOT have adequate permission to access this resource"),
    NO_PRINCIPAL("Principal identifier NOT provided", 500);

    private final String defaultMessage;
    private final int defaultHttpStatus;

    ErrorCode(String defaultMessage) {
        this(defaultMessage, 400);
    }

    ErrorCode(String defaultMessage, int defaultHttpStatus) {
        this.defaultMessage = defaultMessage;
        this.defaultHttpStatus = defaultHttpStatus;
    }
}

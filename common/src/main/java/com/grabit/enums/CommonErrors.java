package com.grabit.enums;

import lombok.Getter;

public enum CommonErrors {
    unknown_error("Something Went Wrong"),
    PHONE_NUMBER_ALREADY_EXISTS("User Exists with Provided Phone Number"),
    EMAIL_ALREADY_EXISTS("User Exists with Provided Email"),
    RECORD_NOT_FOUND("Requested Record does not Exist"),
    INVALID_ADDRESS_ID("Address Id Provided is Not Valid"),
    NO_DATA_FOUND("No Records Found in the Database"),
    MEMBER_NOT_FOUND("No Member found with the provided member id"),
    ACCESS_DENIED("You do not have the permission to access this resource"),
    AUTHENTICATION_FAILED("Error decoding signature"),
    AUTHENTICATION_EXPIRED("Signature has expired"),
    AUTHENTICATION_REQUIRED("User is not authenticated"),
    Forbidden("You do not have the permission to access this resource"),
    REQUEST_BODY_MISSING("Request Body is required for this operation"),
    INVALID_MEMBER_ID("Member Id Provided is Not Valid");

    @Getter
    private String message;

    CommonErrors(String details) {
        this.message = details;
    }
}

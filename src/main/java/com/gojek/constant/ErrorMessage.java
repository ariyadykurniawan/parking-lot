package com.gojek.constant;

public enum ErrorMessage {
    PARKING_ALREADY_EXIST("Sorry, parking lot already created"),
    PARKING_LOT_IS_NOT_EXIST("Sorry, parking lot is not created"),
    PARKING_LOT_IS_FULL("Sorry, parking lot is full"),
    PARKING_LOT_IS_EMPTY("Sorry, parking lot is empty"),
    NOT_FOUND("Not found");
    private String message;

    private ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

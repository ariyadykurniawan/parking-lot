package com.gojek.exception;

import com.gojek.service.ParkingService;

public class ParkingException extends Exception{
    public ParkingException(String message){
        super(message);
    }
}

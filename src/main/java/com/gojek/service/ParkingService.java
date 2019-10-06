package com.gojek.service;

import com.gojek.exception.ParkingException;
import com.gojek.model.Car;

public interface ParkingService {
    public void createPakringLot(int capacity) throws ParkingException;
    public void parkCar(Car car) throws ParkingException;
    public void leavePark(int slotNumber) throws ParkingException;
    public void getStatus() throws ParkingException;
    public void getRegistrationNumberByColor(String color) throws ParkingException;
    public void getSLotNumberByColor(String color) throws ParkingException;
    public void getSlotNumberByRegisterNumber(String registrationNumber) throws ParkingException;
}

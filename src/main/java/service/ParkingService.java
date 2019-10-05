package service;

import model.Car;

public interface ParkingService {
    public void createPakringLot(int capacity);
    public void parkCar(Car car);
    public void leavePark(int slotNumber);
    public void getStatus();
    public void getRegistrationNumberByColor(String color);
    public void getSLotNumberByColor(String color);
    public void getSlotNumberByRegisterNumber(String registrationNumber);
}

package com.gojek.service;

import com.gojek.constant.ErrorMessage;
import com.gojek.exception.ParkingException;
import com.gojek.model.Car;

import java.util.*;

public class ParkingServiceImpl implements ParkingService{

    private int noOfParkingSlots = 0;

    private List<Integer> availableSlotList = new ArrayList<>();

    private Map<Integer, Car> slotCarMap;

    private Map<String, Integer> regNoCarSlotMap;

    private Map<String, List<String>> colorCarMap;

    @Override
    public void createPakringLot(int capacity) throws ParkingException{
        this.noOfParkingSlots = capacity;
        if(availableSlotList.size() > 0) {
            throw new ParkingException(ErrorMessage.PARKING_ALREADY_EXIST.getMessage());
        } else {
            availableSlotList = new ArrayList<Integer>();

            for (int i = 1; i <= noOfParkingSlots; i++) {
                availableSlotList.add(i);
            }

            slotCarMap = new HashMap<Integer, Car>();
            regNoCarSlotMap = new HashMap<String, Integer>();
            colorCarMap = new HashMap<String, List<String>>();
            System.out.println("Created parking lot with " + noOfParkingSlots + " slots");
        }
    }

    private void checkingParkingLot() throws ParkingException{
        if (noOfParkingSlots == 0) {
            throw new ParkingException(ErrorMessage.PARKING_LOT_IS_NOT_EXIST.getMessage());
        }
    }

    @Override
    public void parkCar(Car car) throws ParkingException {
        checkingParkingLot();
        if (slotCarMap.size() == noOfParkingSlots) {
            throw new ParkingException(ErrorMessage.PARKING_LOT_IS_FULL.getMessage());
        } else {
            Collections.sort(availableSlotList);
            int slot = availableSlotList.get(0);
            slotCarMap.put(slot, car);
            regNoCarSlotMap.put(car.getRegistrationNumber(), slot);
            if (colorCarMap.containsKey(car.getColor())) {
                List<String> regNoList = colorCarMap.get(car.getColor());
                colorCarMap.remove(car.getColor());
                regNoList.add(car.getRegistrationNumber());
                colorCarMap.put(car.getColor(), regNoList);
            } else {
                LinkedList<String> regNoList = new LinkedList<String>();
                regNoList.add(car.getRegistrationNumber());
                colorCarMap.put(car.getColor(), regNoList);
            }
            System.out.println("Allocated slot number: " + slot);
            availableSlotList.remove(0);
        }
    }

    @Override
    public void leavePark(int slotNumber) throws ParkingException{
        checkingParkingLot();
        if (slotCarMap.size() > 0) {
            Car carToLeave = slotCarMap.get(slotNumber);
            if (carToLeave != null) {
                slotCarMap.remove(slotNumber);
                regNoCarSlotMap.remove(carToLeave.getRegistrationNumber());
                List<String> regNoList = colorCarMap.get(carToLeave.getColor());
                if (regNoList.contains(carToLeave.getRegistrationNumber())) {
                    regNoList.remove(carToLeave.getRegistrationNumber());
                }
                availableSlotList.add(slotNumber);
                System.out.println("Slot number " + slotNumber + " is free");
            } else {
                throw new ParkingException("Slot number " + slotNumber + " is already empty");
            }
        } else {
            throw new ParkingException(ErrorMessage.PARKING_LOT_IS_EMPTY.getMessage());
        }

    }

    @Override
    public void getStatus() throws ParkingException{
        checkingParkingLot();
        if (slotCarMap.size() > 0) {
            System.out.println("Slot No.\tRegistration No.\tColor");
            Car car;
            for (int i = 1; i <= noOfParkingSlots; i++) {
                if (slotCarMap.containsKey(i)) {
                    car = slotCarMap.get(i);
                    System.out.println(i + "\t" + car.getRegistrationNumber() + "\t" + car.getColor());
                }
            }
        } else {
            throw new ParkingException(ErrorMessage.PARKING_LOT_IS_EMPTY.getMessage());
        }

    }

    @Override
    public void getRegistrationNumberByColor(String color) throws ParkingException{
        checkingParkingLot();
        if (colorCarMap.containsKey(color)) {
            List<String> regNoList = colorCarMap.get(color);
            for (int i = 0; i < regNoList.size(); i++) {
                if (!(i == regNoList.size() - 1)) {
                    System.out.print(regNoList.get(i) + ",");
                } else {
                    System.out.print(regNoList.get(i));
                }
            }
            System.out.println();
        } else {
            throw new ParkingException(ErrorMessage.NOT_FOUND.getMessage());
        }
    }

    @Override
    public void getSLotNumberByColor(String color) throws ParkingException{
        checkingParkingLot();
        if (colorCarMap.containsKey(color)) {
            List<String> regNoList = colorCarMap.get(color);
            List<Integer> slotList = new ArrayList<Integer>();
            for (int i = 0; i < regNoList.size(); i++) {
                slotList.add(Integer.valueOf(regNoCarSlotMap.get(regNoList.get(i))));
            }
            Collections.sort(slotList);
            for (int j = 0; j < slotList.size(); j++) {
                if (!(j == slotList.size() - 1)) {
                    System.out.print(slotList.get(j) + ", ");
                } else {
                    System.out.print(slotList.get(j));
                }
            }
            System.out.println();
        } else {
            throw new ParkingException(ErrorMessage.NOT_FOUND.getMessage());
        }
    }

    @Override
    public void getSlotNumberByRegisterNumber(String registrationNumber) throws ParkingException{
        checkingParkingLot();
        if (regNoCarSlotMap.containsKey(registrationNumber)) {
            System.out.println(regNoCarSlotMap.get(registrationNumber));
        } else {
            throw new ParkingException(ErrorMessage.NOT_FOUND.getMessage());
        }
    }
}

package service;

import constant.ErrorMessage;
import model.Car;

import java.util.*;

public class ParkingServiceImpl implements ParkingService{

    private int noOfParkingSlots = 0;

    private List<Integer> availableSlotList = new ArrayList<>();

    private Map<Integer, Car> slotCarMap;

    private Map<String, Integer> regNoCarSlotMap;

    private Map<String, List<String>> colorCarMap;
    @Override
    public void createPakringLot(int capacity){
        this.noOfParkingSlots = capacity;
        try {
            if(availableSlotList.size() > 0) {
                System.out.println(ErrorMessage.PARKING_ALREADY_EXIST.getMessage());
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void parkCar(Car car){
        try{
            if (noOfParkingSlots == 0) {
                System.out.println(ErrorMessage.PARKING_LOT_IS_NOT_EXIST.getMessage());
            }else if (slotCarMap.size() == noOfParkingSlots) {
                System.out.println(ErrorMessage.PARKING_LOT_IS_FULL.getMessage());
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void leavePark(int slotNumber){
        try{
            if (noOfParkingSlots == 0) {
                System.out.println(ErrorMessage.PARKING_LOT_IS_NOT_EXIST.getMessage());
            }else if (slotCarMap.size() > 0) {
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
                    System.out.println("Slot number " + slotNumber + " is already empty");
                }
            } else {
                System.out.println("Parking lot is empty\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getStatus(){
        try{
            if (noOfParkingSlots == 0) {
                System.out.println(ErrorMessage.PARKING_LOT_IS_NOT_EXIST.getMessage());
            }else if (slotCarMap.size() > 0) {
                System.out.println("Slot No.\tRegistration No.\tColor\n");
                Car car;
                for (int i = 1; i <= noOfParkingSlots; i++) {
                    if (slotCarMap.containsKey(i)) {
                        car = slotCarMap.get(i);
                        System.out.println(i + "\t" + car.getRegistrationNumber() + "\t" + car.getColor());
                    }
                }
                System.out.println();
            } else {
                System.out.println(ErrorMessage.PARKING_LOT_IS_EMPTY);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getRegistrationNumberByColor(String color){
        try{
            if (noOfParkingSlots == 0) {
                System.out.println(ErrorMessage.PARKING_LOT_IS_NOT_EXIST.getMessage());
            }else if (colorCarMap.containsKey(color)) {
                List<String> regNoList = colorCarMap.get(color);
                System.out.println();
                for (int i = 0; i < regNoList.size(); i++) {
                    if (!(i == regNoList.size() - 1)) {
                        System.out.print(regNoList.get(i) + ",");
                    } else {
                        System.out.print(regNoList.get(i));
                    }
                }
                System.out.println();
            } else {
                System.out.println(ErrorMessage.NOT_FOUND.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getSLotNumberByColor(String color) {
        try{
            if (noOfParkingSlots == 0) {
                System.out.println(ErrorMessage.PARKING_LOT_IS_NOT_EXIST.getMessage());
            } else if (colorCarMap.containsKey(color)) {
                List<String> regNoList = colorCarMap.get(color);
                List<Integer> slotList = new ArrayList<Integer>();
                System.out.println();
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
                System.out.println(ErrorMessage.NOT_FOUND.getMessage());
            }
        }catch (Exception e){
            new Exception(e);
        }

    }

    @Override
    public void getSlotNumberByRegisterNumber(String registrationNumber) {
        if (noOfParkingSlots == 0) {
            System.out.println(ErrorMessage.PARKING_LOT_IS_NOT_EXIST.getMessage());
        } else if (regNoCarSlotMap.containsKey(registrationNumber)) {
            System.out.println(regNoCarSlotMap.get(registrationNumber));
        } else {
            System.out.println(ErrorMessage.NOT_FOUND.getMessage());
        }
    }
}

package com.gojek.processor;

import com.gojek.constant.Constant;
import com.gojek.model.Car;
import com.gojek.service.ParkingService;
import com.gojek.service.ParkingServiceImpl;

public abstract class AbstractProcessor {

    private ParkingService parkingService = new ParkingServiceImpl();

    public abstract void process() throws Exception;

    public void validateAndProcess(String inputString){
        String[] inputStrArr = inputString.split(" ");
        if(inputStrArr[0].equals("")) {
            System.out.println("Thanks for using the program");
            return;
        }
        Constant command = Constant.findByName(inputStrArr[0]);

        if(command == null) {
            System.out.println("Invalid command");
            return;
        }
        switch(command) {
            case CREATE:
                try{
                    if(inputStrArr.length != 2) {
                        System.out.println(inputStrArr.length);
                        throw new Exception("Invalid no of arguments for command : " + command);
                    }
                    int noOfPrakingSlots = Integer.parseInt(inputStrArr[1]);
                    parkingService.createPakringLot(noOfPrakingSlots);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case PARK:
                try{
                    if(inputStrArr.length != 3) {
                        throw new Exception("Invalid no of arguments for command : " + command);
                    }
                    String regNo = inputStrArr[1];
                    String color = inputStrArr[2];
                    parkingService.parkCar(new Car(regNo, color));
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case LEAVE:
                try{
                    if(inputStrArr.length != 2) {
                        throw new Exception("Invalid no of arguments for command : " + command);
                    }
                    int slotNo = Integer.parseInt(inputStrArr[1]);
                    parkingService.leavePark(slotNo);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case STATUS:
                try{
                    if(inputStrArr.length != 1) {
                        throw new Exception("Invalid no of arguments for command : " + command);
                    }
                    parkingService.getStatus();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case FETCH_CAR_FROM_COLOR:
                try{
                    if(inputStrArr.length != 2) {
                        throw new Exception("Invalid no of arguments for command : " + command);
                    }
                    parkingService.getRegistrationNumberByColor(inputStrArr[1]);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case FETCH_SLOT_FROM_COLOR:
                try{
                    if(inputStrArr.length != 2) {
                        throw new Exception("Invalid no of arguments for command : " + command);
                    }
                    parkingService.getSLotNumberByColor(inputStrArr[1]);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case FETCH_SLOT_FROM_REG_NO:
                try{
                    if(inputStrArr.length != 2) {
                        throw new Exception("Invalid no of arguments for command : " + command);
                    }
                    parkingService.getSlotNumberByRegisterNumber(inputStrArr[1]);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
        }
    }

    public void printUsage()
    {
        StringBuffer buffer = new StringBuffer();
        buffer = buffer.append(
                "--------------Please Enter one of the below commands. {variable} to be replaced -----------------------")
                .append("\n");
        buffer = buffer.append("A) For creating parking lot of size n               ---> create_parking_lot {capacity}")
                .append("\n");
        buffer = buffer
                .append("B) To park a car                                    ---> park {car_number} {car_clour}")
                .append("\n");
        buffer = buffer.append("C) Remove(Unpark) car from parking                  ---> leave {slot_number}")
                .append("\n");
        buffer = buffer.append("D) Print status of parking slot                     ---> status").append("\n");
        buffer = buffer.append(
                "E) Get cars registration no for the given car color ---> registration_numbers_for_cars_with_color {car_color}")
                .append("\n");
        buffer = buffer.append(
                "F) Get slot numbers for the given car color         ---> slot_numbers_for_cars_with_color {car_color}")
                .append("\n");
        buffer = buffer.append(
                "G) Get slot number for the given car number         ---> slot_number_for_registration_number {car_number}")
                .append("\n");
        System.out.println(buffer.toString());
    }
}

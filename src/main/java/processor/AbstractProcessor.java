package processor;

import constant.Constant;
import model.Car;
import service.ParkingService;
import service.ParkingServiceImpl;

public abstract class AbstractProcessor {

    private ParkingService parkingService = new ParkingServiceImpl();

    public abstract void process() throws Exception;

    public void validateAndProcess(String inputString) throws Exception {
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

                }
                break;
            case PARK:
                if(inputStrArr.length != 3) {
                    throw new Exception("Invalid no of arguments for command : " + command);
                }
                String regNo = inputStrArr[1];
                String color = inputStrArr[2];
                parkingService.parkCar(new Car(regNo, color));
                break;
            case LEAVE:
                if(inputStrArr.length != 2) {
                    throw new Exception("Invalid no of arguments for command : " + command);
                }
                int slotNo = Integer.parseInt(inputStrArr[1]);
                parkingService.leavePark(slotNo);
                break;
            case STATUS:
                if(inputStrArr.length != 1) {
                    throw new Exception("Invalid no of arguments for command : " + command);
                }
                parkingService.getStatus();
                break;
            case FETCH_CAR_FROM_COLOR:
                if(inputStrArr.length != 2) {
                    throw new Exception("Invalid no of arguments for command : " + command);
                }
                parkingService.getRegistrationNumberByColor(inputStrArr[1]);  //color
                break;
            case FETCH_SLOT_FROM_COLOR:
                if(inputStrArr.length != 2) {
                    throw new Exception("Invalid no of arguments for command : " + command);
                }
                parkingService.getSLotNumberByColor(inputStrArr[1]);  //color
                break;
            case FETCH_SLOT_FROM_REG_NO:
                if(inputStrArr.length != 2) {
                    throw new Exception("Invalid no of arguments for command : " + command);
                }
                parkingService.getSlotNumberByRegisterNumber(inputStrArr[1]);  //regNo
                break;
        }
    }
}

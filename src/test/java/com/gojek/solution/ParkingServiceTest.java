package com.gojek.solution;

import com.gojek.constant.Constant;
import com.gojek.constant.ErrorMessage;
import com.gojek.exception.ParkingException;
import com.gojek.model.Car;
import com.gojek.service.ParkingService;
import com.gojek.service.ParkingServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.*;

public class ParkingServiceTest {

    private final ByteArrayOutputStream outContent	= new ByteArrayOutputStream();
    List<Car> carList = new ArrayList<>();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void init() throws ParkingException{
        System.setOut(new PrintStream(outContent));
        carList.add(new Car("KA-01-HH-1234", "White"));
        carList.add(new Car("KB-01-HH-2345", "Black"));
        carList.add(new Car("KC-01-HH-3456", "Yellow"));
        carList.add(new Car("KD-01-HH-4567", "White"));
        carList.add(new Car("KE-01-HH-5678", "Blue"));
        carList.add(new Car("KF-01-HH-5678", "Red"));
    }

    @After
    public void cleanUp() {
        System.setOut(null);
    }

    @Test
    public void testCreateParkingLot() throws ParkingException {
        ParkingService parkingService = new ParkingServiceImpl();
        int capacity = 6;
        parkingService.createPakringLot(capacity);
        assertEquals("Created parking lot with " + capacity + " slots\n", outContent.toString());
        exceptionRule.expect(ParkingException.class);
        exceptionRule.expectMessage(ErrorMessage.PARKING_ALREADY_EXIST.getMessage());
        parkingService.createPakringLot(9);
    }

    @Test
    public void testParkingLotNotExist() throws ParkingException{
        ParkingService parkingService = new ParkingServiceImpl();
        exceptionRule.expect(ParkingException.class);
        exceptionRule.expectMessage(ErrorMessage.PARKING_LOT_IS_NOT_EXIST.getMessage());
        parkingService.parkCar(new Car("KA-01-HH-1234", "White"));
    }

    @Test
    public void testParkingLotIsEmpty() throws ParkingException{
        ParkingService parkingService = new ParkingServiceImpl();
        parkingService.createPakringLot(2);

        exceptionRule.expect(ParkingException.class);
        exceptionRule.expectMessage(ErrorMessage.PARKING_LOT_IS_EMPTY.getMessage());
        parkingService.leavePark(1);
    }

    @Test
    public void testParkCar() throws ParkingException{
        ParkingService parkingService = new ParkingServiceImpl();
        parkingService.createPakringLot(3);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Created parking lot with 3 slots\n");
        for (int i = 1; i <= 4; i++){
            exceptionRule.expect(ParkingException.class);
            exceptionRule.expectMessage(ErrorMessage.PARKING_LOT_IS_FULL.getMessage());
            parkingService.parkCar(carList.get(i));
            assertEquals(stringBuilder.append("Allocated slot number: "+i+"\n").toString(), outContent.toString());
        }
    }

    @Test
    public void testLeavePark() throws ParkingException{
        ParkingService parkingService = new ParkingServiceImpl();
        parkingService.createPakringLot(2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Created parking lot with 2 slots\n");

        for (int i = 1; i <= 2; i++){
            parkingService.parkCar(carList.get(i));
            stringBuilder.append("Allocated slot number: "+i+"\n");
        }
        parkingService.leavePark(2);
        assertEquals(stringBuilder.append("Slot number 2 is free\n").toString(), outContent.toString());
    }

    @Test
    public void testLeaveParkAlreadyFree() throws ParkingException{
        ParkingService parkingService = new ParkingServiceImpl();
        parkingService.createPakringLot(2);
        for (int i = 1; i <= 2; i++){
            parkingService.parkCar(carList.get(i));
        }
        parkingService.leavePark(2);

        exceptionRule.expect(ParkingException.class);
        exceptionRule.expectMessage("Slot number 2 is already empty");
        parkingService.leavePark(2);
    }

    @Test
    public void testGetStatus() throws ParkingException{
        ParkingService parkingService = new ParkingServiceImpl();
        StringBuilder stringBuilder = new StringBuilder();

        parkingService.createPakringLot(2);
        stringBuilder.append("Created parking lot with 2 slots\n");
        for (int i = 1; i <= 2; i++){
            parkingService.parkCar(carList.get(i));
            stringBuilder.append("Allocated slot number: "+i+"\n");
        }

        parkingService.getStatus();
        stringBuilder.append("Slot No.\tRegistration No.\tColor\n");
        for (int i = 1; i <= 2; i++) {
            stringBuilder.append(i + "\t" + carList.get(i).getRegistrationNumber() + "\t" + carList.get(i).getColor()+"\n");
        }
        assertEquals(stringBuilder.toString(), outContent.toString());
    }

    @Test
    public void testGetRegistrationNumberByColor() throws ParkingException{
        ParkingService parkingService = new ParkingServiceImpl();
        StringBuilder stringBuilder = new StringBuilder();

        parkingService.createPakringLot(5);
        stringBuilder.append("Created parking lot with 5 slots\n");
        Map<String, String> carMap = new HashMap<String, String>();
        for (int i = 1; i <= 5; i++){
            parkingService.parkCar(carList.get(i));
            stringBuilder.append("Allocated slot number: "+i+"\n");
            carMap.put(carList.get(i).getColor(), carList.get(i).getRegistrationNumber());
        }

        parkingService.getRegistrationNumberByColor("White");

        if(carMap.containsKey(new String("White"))){
            List<String> carColorList = Collections.singletonList(carMap.get(new String("White")));
            for (int i = 0; i < carColorList.size(); i++) {
                if (!(i == carColorList.size() - 1)) {
                    stringBuilder.append(carColorList.get(i) + ",");
                } else {
                    stringBuilder.append(carColorList.get(i));
                }
            }
        }
        stringBuilder.append("\n");

        assertEquals(stringBuilder.toString(), outContent.toString());
    }

    @Test
    public void testGetRegistrationNumberByColorNotfound() throws ParkingException{
        ParkingService parkingServiceInit = new ParkingServiceImpl();

        parkingServiceInit.createPakringLot(5);
        for (int i = 1; i <= 5; i++) {
            parkingServiceInit.parkCar(carList.get(i));
        }

        exceptionRule.expect(ParkingException.class);
        exceptionRule.expectMessage(ErrorMessage.NOT_FOUND.getMessage());
        parkingServiceInit.getRegistrationNumberByColor("Green");
    }

    @Test
    public void testGetSLotNumberByColor() throws ParkingException{
        ParkingService parkingService = new ParkingServiceImpl();
        StringBuilder stringBuilder = new StringBuilder();

        parkingService.createPakringLot(5);
        stringBuilder.append("Created parking lot with 5 slots\n");
        Map<String, Integer> carMap = new HashMap<String, Integer>();
        for (int i = 1; i <= 5; i++){
            parkingService.parkCar(carList.get(i));
            stringBuilder.append("Allocated slot number: "+i+"\n");
            carMap.put(carList.get(i).getColor(), i);
        }

        parkingService.getSLotNumberByColor("White");

        if(carMap.containsKey(new String("White"))){
            List<Integer> carColorList = Collections.singletonList(carMap.get(new String("White")));
            for (int i = 0; i < carColorList.size(); i++) {
                if (!(i == carColorList.size() - 1)) {
                    stringBuilder.append(carColorList.get(i) + ",");
                } else {
                    stringBuilder.append(carColorList.get(i));
                }
            }
        }
        stringBuilder.append("\n");

        assertEquals(stringBuilder.toString(), outContent.toString());
    }

    @Test
    public void testGetSLotNumberByColorNotFound() throws ParkingException{
        ParkingService parkingServiceInit = new ParkingServiceImpl();

        parkingServiceInit.createPakringLot(5);
        for (int i = 1; i <= 5; i++) {
            parkingServiceInit.parkCar(carList.get(i));
        }

        exceptionRule.expect(ParkingException.class);
        exceptionRule.expectMessage(ErrorMessage.NOT_FOUND.getMessage());
        parkingServiceInit.getSLotNumberByColor(new String("Green"));
    }

    @Test
    public void testGetSlotNumberByRegisterNumber() throws ParkingException {
        ParkingService parkingService = new ParkingServiceImpl();
        StringBuilder stringBuilder = new StringBuilder();

        parkingService.createPakringLot(5);
        stringBuilder.append("Created parking lot with 5 slots\n");
        Map<String, Integer> carMap = new HashMap<String, Integer>();
        for (int i = 1; i <= 5; i++){
            parkingService.parkCar(carList.get(i));
            stringBuilder.append("Allocated slot number: "+i+"\n");
            carMap.put(carList.get(i).getRegistrationNumber(), i);
        }
        parkingService.getSlotNumberByRegisterNumber("KC-01-HH-3456");
        if(carMap.containsKey(new String("KC-01-HH-3456"))){
            stringBuilder.append(carMap.get("KC-01-HH-3456"));
            stringBuilder.append("\n");
            assertEquals(stringBuilder.toString(), outContent.toString());
        }
    }

    @Test
    public void testGetSlotNumberByRegisterNumberNotFound() throws ParkingException {
        ParkingService parkingServiceInit = new ParkingServiceImpl();

        parkingServiceInit.createPakringLot(5);
        for (int i = 1; i <= 5; i++) {
            parkingServiceInit.parkCar(carList.get(i));
        }

        exceptionRule.expect(ParkingException.class);
        exceptionRule.expectMessage(ErrorMessage.NOT_FOUND.getMessage());
        parkingServiceInit.getSlotNumberByRegisterNumber(new String("KC-01-HH-3457"));
    }

}

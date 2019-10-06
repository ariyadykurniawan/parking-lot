package com.gojek.constant;

public enum Constant {
    CREATE("create_parking_lot"),
    PARK("park"),
    LEAVE("leave"),
    STATUS("status"),
    FETCH_CAR_FROM_COLOR("registration_numbers_for_cars_with_colour"),
    FETCH_SLOT_FROM_COLOR("slot_numbers_for_cars_with_colour"),
    FETCH_SLOT_FROM_REG_NO("slot_number_for_registration_number");

    private final String name;

    private Constant(String s) {
        name = s;
    }

    public static Constant findByName(String abbr){
        for(Constant c : values()){
            if( c.toString().equals(abbr)){
                return c;
            }
        }
        return null;
    }

    public String toString() {
        return name;
    }
}

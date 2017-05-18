package com.synacy.shoppingcenter

public enum Location {
    FIRST_FLOOR("First Floor"), SECOND_FLOOR("Second Floor"), THIRD_FLOOR("Third Floor"), FOURTH_FLOOR("Fourth Floor")

    private final String floor

    Location(String location) {
        this.floor = location
    }

    public static Location valueOfLocation(String locationString) {
        values().find {
            it.name().equalsIgnoreCase(locationString) || it.floor.equalsIgnoreCase(locationString)
        }
    }

}

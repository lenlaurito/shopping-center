package com.synacy.shoppingcenter

public enum Location {
    FIRST_FLOOR,
    SECOND_FLOOR,
    THIRD_FLOOR,
    FOURTH_FLOOR,

    public static Location valueOfLocation(String locationString) {
        values().find { it.name().equalsIgnoreCase(locationString) }
    }
}

package com.synacy.shoppingcenter.exceptions

class InvalidLocationException extends RuntimeException {

    public InvalidLocationException() {
        super("Invalid Location. Possible locations are (First Floor, Second Floor, Third Floor and Fourth Floor)")
    }
}

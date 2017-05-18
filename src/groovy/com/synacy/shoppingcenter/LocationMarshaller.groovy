package com.synacy.shoppingcenter

import grails.converters.JSON

class LocationMarshaller {

    void register() {
        JSON.registerObjectMarshaller(Location) { Location location ->

            return location.toString()
        }
    }
}
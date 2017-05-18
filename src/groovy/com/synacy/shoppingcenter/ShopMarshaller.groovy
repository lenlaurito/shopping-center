package com.synacy.shoppingcenter

import grails.converters.JSON

class ShopMarshaller {

    void register() {
        JSON.registerObjectMarshaller(Shop) {Shop shop ->

            return [
                    id       : shop.id,
                    name     : shop.name,
                    description : shop.description,
                    location : shop.location,
                    tagId     : shop.tags.id
            ]
        }
    }

}
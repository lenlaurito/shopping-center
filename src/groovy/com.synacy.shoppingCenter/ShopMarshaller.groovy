package com.synacy.shoppingCenter

import grails.converters.JSON
/**
 * Created by steven on 5/17/17.
 */
public class ShopMarshaller {

    void register() {
        JSON.registerObjectMarshaller(Shop) { Shop shop ->

            return [
                    id              : shop.id,
                    name            : shop.name,
                    description     : shop.description,
                    location        : shop.location,
                    tags            : shop.tags
            ]
        }
    }
}
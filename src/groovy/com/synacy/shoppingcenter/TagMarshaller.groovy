package com.synacy.shoppingcenter

import grails.converters.JSON

class TagMarshaller {

    void register() {
        JSON.registerObjectMarshaller(Tag) { Tag tag ->

            return [
                    id          : tag.id,
                    name        : tag.name
            ]
        }
    }
}

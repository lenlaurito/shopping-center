package com.synacy.shoppingCenter.marshaller

import com.synacy.shoppingCenter.Tag
import grails.converters.JSON
/**
 * Created by steven on 5/17/17.
 */
public class TagMarshaller {

    void register() {
        JSON.registerObjectMarshaller(Tag) { Tag tag ->

            return [
                    id              : tag.id,
                    name            : tag.name,
                    shopId          : tag.shops.id
            ]
        }
    }
}
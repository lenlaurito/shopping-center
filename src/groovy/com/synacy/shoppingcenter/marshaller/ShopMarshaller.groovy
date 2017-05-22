package com.synacy.shoppingcenter.marshaller

import grails.converters.JSON
import com.synacy.shoppingcenter.Shop

class ShopMarshaller {

     void register() {

          JSON.registerObjectMarshaller(Shop) {

               return [
                         id: it.id,
                         name: it.name,
                         location: it.location.key,
                         tags: it.tags.name
               ]
          }
     }
}

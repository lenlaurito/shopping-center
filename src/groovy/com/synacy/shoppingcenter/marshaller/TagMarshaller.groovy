package com.synacy.shoppingcenter.marshaller

import grails.converters.JSON
import com.synacy.shoppingcenter.Tag

class TagMarshaller {

     void register() {

          JSON.registerObjectMarshaller(Tag) {

               return [
                         id: it.id,
                         name: it.name,
                         shops: it.shops.name
               ]
          }
     }
}

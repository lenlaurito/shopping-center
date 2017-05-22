package com.synacy.shoppingcenter

import com.synacy.shoppingcenter.exception.NoContentException
import com.synacy.shoppingcenter.trait.ErrorHandler
import grails.validation.ValidationException
import grails.converters.JSON
import org.springframework.http.HttpStatus

class ShopController implements ErrorHandler{

    static responseFormats = ['json']

    ShopService shopService
    TagService tagService

    def fetchShops() {

          Integer max = params.max ? Integer.parseInt(params.max) : null
          Integer offset = params.offset ? Integer.parseInt(params.offset) : null
          String tagName = params.tag ? String.valueOf(params.tag) : null

          List<Shop> shops = shopService.fetchAllShops(max, offset, tagName)

          Integer shopCount = shopService.fetchTotalNumberOfShops()
          Map<String, Object> paginatedShopDetails = [totalRecords: shopCount, records: shops]

          if(!shops) {
            throw new NoContentException("No shops exist.")
          }else {
            respond(shops)
          }
     }

    def fetchShop(Long shopId) {

          Shop shop = shopService.fetchShopById(shopId)

          if(!shop) {
             throw new NoContentException("This shop does not exist.")
          }else {
             respond(shop)
          }
     }

    def createShop() {

          String name = request.JSON.name ?: null
          Location location = request.JSON.location ?: null
          String description = request.JSON.description ?: null
          def tagNames = request.JSON.tags ?: null

          List<Tag> tags = []

          if(tagNames) {
              tags = tagService.fetchTagsByTagName(tagNames)
          }

          try{
              Shop shop = shopService.createShop(name, location, description, tags)
              respond(shop, [status: HttpStatus.CREATED])
          }catch(ValidationException e) {
              response.status = 400
              render([error: e.errors] as JSON)
          }

    }

    def updateShop(Long shopId) {

          String name = request.JSON.name ?: null
          Location location = request.JSON.location ?: null
          String description = request.JSON.description ?: null
          def tagNames = request.JSON.tags ?: null

          Shop shop = shopService.fetchShopById(shopId)

          if(!shop) {
              throw new NoContentException("This shop does not exist.")
          }

          List<Tag> tags = []

          if(tagNames) {
              tags = tagService.fetchTagsByTagName(tagNames)
          }

          try{
              shop = shopService.updateShop(shop, name, location, description, tags)
              respond(shop)
          }catch(ValidationException e) {
              response.status = 400
              render([error: e.errors] as JSON)
          }
    }

    def deleteShop(Long shopId) {

          Shop shop = shopService.fetchShopById(shopId)
          shopService.deleteShop(shop)

          render(status: HttpStatus.NO_CONTENT)
    }
}

package com.synacy.shoppingcenter

import com.synacy.shoppingcenter.exception.NoContentException
import com.synacy.shoppingcenter.trait.ErrorHandler
import org.springframework.http.HttpStatus

class ShopController implements ErrorHandler{

    static responseFormats = ['json']

    ShopService shopService
    TagService tagService

    def fetchShops() {

          Integer max = params.max ? Integer.parseInt(params.max) : null
          Integer offset = params.offset ? Integer.parseInt(params.offset) : null
          Integer tagId = params.tagId ? Long.valueOf(params.tagId) : null

          if(tagId) {
              List<Shop> shops = shopService.fetchAllShopsByTagId(tagId)
          } else {
              List<Shop> shops = shopService.fetchAllShops(max, offset)
          }

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
          def tagIds = request.JSON.tagIds ?: null

          List<Tag> tags = []

          if(tagIds) {
               tags = tagService.fetchTagsFromIds(tagIds)
          }

          Shop shop = shopService.createShop(name, location, description, tags)

          respond(shop, [status: HttpStatus.CREATED])
    }

    def updateShop(Long shopId) {

          String name = request.JSON.name ?: null
          Location location = request.JSON.location ?: null
          String description = request.JSON.description ?: null
          def tagIds = request.JSON.tagIds ?: null

          List<Tag> tags = []

          if(tagIds) {
               tags = tagService.fetchTagsFromIds(tagIds)
          }

          Shop shop = shopService.fetchShopById(shopId)

          if(!shop) {
              throw new NoContentException("This shop does not exist.")
          }else {
              shop = shopService.updateShop(shop, name, location, description, tags)
              respond(shop)
          }
    }

    def deleteShop(Long shopId) {

          Shop shop = shopService.fetchShopById(shopId)
          shopService.deleteShop(shop)

          render(status: HttpStatus.NO_CONTENT)
    }
}

package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus
import com.synacy.shoppingcenter.exceptions.*
import com.synacy.shoppingcenter.Location

import javax.naming.LimitExceededException

class ShopController {

    static responseFormats = ['json']

    ShopService shopService
    TagService tagService

    def fetchAllShops() {
        Integer max = params.max ? Integer.parseInt(params.max) : null
        Integer offset = params.offset ? Integer.parseInt(params.offset) : null
        Long tagId = params.tagId ? Long.parseLong(params.tagId) : null

        List<Shop> shops = shopService.fetchAllShops(offset, max, tagId)
        Integer shopCount = shopService.fetchTotalNumberOfShops()

        if(shopCount == 0) {
            throw new ResourceNotFoundException("No existing shop found")
        }

        Map<String, Object> paginatedShopDetails = [totalRecords: shopCount, records: shops]

        respond(paginatedShopDetails)
    }

    def fetchShop(Long shopId) {
        Shop shop = shopService.fetchShopById(shopId)

        if(shop == null) {
            throw new ResourceNotFoundException("Shop not found")
        }

        respond(shop)
    }

    def createShop() {
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        String locationString = request.JSON.location ?: null
        List tagIds = request.JSON.tags as List ?: null

        Location location = Location.valueOfLocation(locationString)
        if(location == null) {
            throw new InvalidLocationException()
        }

        if(tagIds.size() > 5) {
            throw new LimitExceededException("Max limit for tags is only 5.")
        }

        List<Tag> tags = tagService.checkTags(tagIds)

        Shop shop = shopService.createNewShop(name, description, location, tags)
        respond(shop, [status: HttpStatus.CREATED])
    }

    def updateShop(Long shopId) {
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        String locationString = request.JSON.location ?: null
        List tagIds = request.JSON.tags as List ?: null

        Location location = Location.valueOfLocation(locationString)
        if(location == null) {
            throw new InvalidLocationException()
        }

        Shop shop = shopService.fetchShopById(shopId)

        if(shop == null) {
            throw new ResourceNotFoundException("Shop not found")
        }

        if(tagIds == null) {
            tagIds = []
        }

        if(tagIds.size() > 5) {
            throw new LimitExceededException("Max limit for tags is only 5.")
        }

        List<Tag> tags = tagService.checkTags(tagIds)

        shop = shopService.updateShop(shop, name, description, location, tags)

        respond(shop)
    }

    def removeShop(Long shopId) {
        Shop shop = shopService.fetchShopById(shopId)

        if(!shop) {
            throw new ResourceNotFoundException("Shop not found")
        }

        shopService.deleteShop(shop)

        render(status: HttpStatus.NO_CONTENT)
    }

    def handleResourceNotFoundException(ResourceNotFoundException e) {
        response.status = HttpStatus.NOT_FOUND.value()
        respond([error: e.getMessage()])
    }

    def handleInvalidLocationException(InvalidLocationException e) {
        response.status = HttpStatus.NOT_FOUND.value()
        respond([error: e.getMessage()])
    }

    def handleLimitExceedException(LimitExceededException e) {
        response.status = HttpStatus.BAD_REQUEST.value()
        respond([error: e.getMessage()])
    }


}

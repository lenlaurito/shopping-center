package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus
import com.synacy.shoppingcenter.exceptions.*
import com.synacy.shoppingcenter.Location
import com.synacy.shoppingcenter.ExceptionHandlingTrait

import javax.naming.LimitExceededException

class ShopController implements ExceptionHandlingTrait {

    static responseFormats = ['json']

    ShopService shopService
    TagService tagService

    def fetchAllShops() {
        Integer max = params.max ? Integer.parseInt(params.max) : null
        Integer offset = params.offset ? Integer.parseInt(params.offset) : null
        Long tagId = params.tagId ? Long.parseLong(params.tagId) : null

        List<Shop> shops = shopService.fetchAllShops(offset, max, tagId)
        Integer shopCount = shopService.fetchTotalNumberOfShops()

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
        List tagIds = request.JSON.tagId as List ?: []

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
            return render(status: HttpStatus.NO_CONTENT)
        }

        shopService.deleteShop(shop)

        render(status: HttpStatus.NO_CONTENT)
    }
}

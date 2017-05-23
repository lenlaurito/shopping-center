package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus


class ShopController implements ErrorHandlingTrait {

    static responseFormats = ['json']

    ShopService shopService

    def fetchAllShops() {

        Integer offset = params.offset ? Integer.parseInt(params.offset) : null
        Integer max = params.max ? Integer.parseInt(params.max) : null
        Long tagId = params.tagId ? Integer.parseInt(params.tagId)?.longValue() : null

        List<Shop> shops = shopService.fetchAllShops(offset, max, tagId)

        Integer shopCount = shopService.fetchTotalNumberOfShops()
        Map<String, Object> paginatedShoptDetails = [totalRecords: shopCount, records: shops]

        respond(paginatedShoptDetails)
    }

    def createShop() {
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        def tags = request.JSON.tags ?: null
        Location location = request.JSON.location? Location.valueOfLocation(request.JSON.location) : null

        Shop createdShop = shopService.createShop(name, description, tags, location)

        respond(createdShop, [status: HttpStatus.CREATED])

    }

    def viewShop(Long shopId) {
        Shop shop = shopService.fetchShopById(shopId)

        respond(shop)
    }

    def updateShop(Long shopId) {
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        def tags = request.JSON.tags ?: null
        Location location = request.JSON.location? Location.valueOfLocation(request.JSON.location) : null

        Shop updatedShop = shopService.updateShop(shopId, name, description, tags, location)

        respond(updatedShop)
    }

    def deleteShop(Long shopId) {

        shopService.deleteShopById(shopId)

        render(status: HttpStatus.NO_CONTENT)
    }
}

package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus


class ShopController implements ErrorHandlingTrait {

    static responseFormats = ['json']

    ShopService shopService

    def fetchAllShops() {
        Integer offset = params.offset ? Integer.parseInt(params.offset) : ShopService.DEFAULT_PAGINATION_OFFSET
        Integer max = params.max ? Integer.parseInt(params.max) : ShopService.DEFAULT_PAGINATION_MAX
        Long tagId = params.tagId ? Integer.parseInt(params.tagId)?.longValue() : null

        List<Shop> shops = shopService.fetchAllShops(offset, max, tagId)

        Integer shopCount = shopService.fetchTotalNumberOfShops()
        Map<String, Object> paginatedShoptDetails = [totalRecords: shopCount, records: shops]

        respond(paginatedShoptDetails)
    }

    def createShop() {
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        List<Long> tags = request.JSON.tags ?: null
        List<Location> locations = request.JSON.locations?.each {
            if (it)
                Location.valueOfLocation(it)
        } ?: null

        Shop createdShop = shopService.createShop(name, description, tags, locations)

        respond(createdShop, [status: HttpStatus.CREATED])

    }

    def viewShop(Long shopId) {
        Shop shop = shopService.fetchShopById(shopId)

        respond(shop)
    }

    def updateShop(Long shopId) {
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        List<Long> tags = request.JSON.tags ?: null
        List<Location> locations = request.JSON.locations?.each {
            if (it)
                Location.valueOfLocation(it)
        } ?: null

        Shop updatedShop = shopService.updateShop(shopId, name, description, tags, locations)

        respond(updatedShop)
    }

    def deleteShop(Long shopId) {

        shopService.deleteShopById(shopId)

        render(status: HttpStatus.NO_CONTENT)
    }

    /*
    private def validateAndGetTagsFromJsonArray(t) {
        if (!t)
            return null

        def tagsErrorMessages = []

        List<Tag> tags = t.each {
            try {
                tagService.fetchTagById(it)
            } catch(Exception e) {
                tagsErrorMessages.add(e.getMessage())
            }
        }

        if (tagsErrorMessages)
            throw new InvalidRequestException((tagsErrorMessages as JSON).toString())

        return tags
    }
    */
}

package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus


class ShopController implements ErrorHandlingTrait {

    static responseFormats = ['json']

    ShopService shopService

    def index() {
        Integer offset = params.offset ? Integer.parseInt(params.offset) : null
        Integer max = params.max ? Integer.parseInt(params.max) : null

        List<Shop> shops = shopService.fetchAllShops(offset, max)

        Integer shopCount = shopService.fetchTotalNumberOfShops()
        Map<String, Object> paginatedShoptDetails = [totalRecords: shopCount, records: shops]

        respond(paginatedShoptDetails)
    }

    def create() {
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        List<Long> tags = request.JSON.tags ?: null
        def tmp = request.JSON.locations ?: [:]
        List<Location> locations = [:]

        tmp.each {
            locations.add(Location.valueOfLocation(it))
        }

        Shop shop = shopService.createNewShop(name, description, tags, locations)

        respond(shop, [status: HttpStatus.CREATED])
    }

    def view(Long shopId) {
        Shop shop = shopService.fetchShopById(shopId)

        respond(shop)
    }

    def update(Long shopId) {
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        List<Long> tags = request.JSON.tags ?: null

        Shop shop = shopService.updateShop(shopId, name, description, tags)

        respond(shop)
    }

    def delete(Long shopId) {

        shopService.deleteShopById(shopId)

        render(status: HttpStatus.NO_CONTENT)
    }
}

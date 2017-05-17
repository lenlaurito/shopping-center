package com.synacy.shoppingCenter

import org.springframework.http.HttpStatus

class ShopController {

    static responseFormats = ['json']

    ShopService shopService

    def index() { }

    def fetchShop(Long shopId){
        Shop shop = shopService.fetchShopById(shopId)
        respond(shop)
    }

    def fetchAllShop(){
        List<Shop> shopList = shopService.fetchAllShop()
        respond(shopList)
    }

    //============Update Test Later===========
    def createShop(){
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        List<Long> tagIds = request.JSON.tags ?: null

        Shop shop = shopService.createShop(name, description, tagIds)

        respond(shop, [status: HttpStatus.CREATED])
    }

    def updateShop(Long shopId){
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        Shop shop = shopService.updateShop(shopId, name, description)
        respond(shop)
    }

    def removeShop(Long shopId){
        shopService.deleteShop(shopId)
        render(status: HttpStatus.NO_CONTENT)
    }
}

package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exceptions.InvalidDataPassed
import com.synacy.shoppingCenter.utils.ControllerUtils
import org.springframework.http.HttpStatus

class ShopController {

    static responseFormats = ['json']

    ShopService shopService
    ControllerUtils controllerUtils = new ControllerUtils()

    def index() { }

    def fetchShop(Long shopId){
        Shop shop = shopService.fetchShopById(shopId)
        respond(shop)
    }

    def fetchAllShop(){
        List<Shop> shopList = shopService.fetchAllShop()
        respond(shopList)
    }

    def createShop(){
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        Integer location = request.JSON.location ?: null
        List<Long> tagIds = request.JSON.tags ?: null

        if(controllerUtils.argumentNullChecker(name,description,location)){
            throw new InvalidDataPassed("Missing required data")
        }

        Shop shop = shopService.createShop(name, description, location, tagIds)
        respond(shop, [status: HttpStatus.CREATED])
    }

    def updateShop(Long shopId){
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        Integer location = request.JSON.location ?: null

        if(controllerUtils.argumentNullChecker(name,description,location)){
            throw new InvalidDataPassed("Missing required data")
        }

        Shop shop = shopService.updateShop(shopId, name, description, location)
        respond(shop)
    }

    def removeShop(Long shopId){
        shopService.deleteShop(shopId)
        render(status: HttpStatus.NO_CONTENT)
    }

    def handleInvalidDataPassed(InvalidDataPassed e) {
        response.status = HttpStatus.NOT_ACCEPTABLE.value()
        respond([error: e.getMessage()])
    }
}

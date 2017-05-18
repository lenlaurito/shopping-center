package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exception.InvalidDataPassed
import com.synacy.shoppingCenter.exception.NoContentFoundException
import com.synacy.shoppingCenter.trait.ExceptionHandlerTrait
import com.synacy.shoppingCenter.util.ControllerUtils
import org.springframework.http.HttpStatus

class ShopController implements ExceptionHandlerTrait{

    static responseFormats = ['json']

    ShopService shopService
    ControllerUtils controllerUtils = new ControllerUtils()

    def index() { }

    def fetchShop(Long shopId){
        Shop shop = shopService.fetchShopById(shopId)
        if(!shop){ throw new NoContentFoundException("No shop with the Id found")}
        respond(shop)
    }

    def fetchAllShop(){
        Integer max = params.max ? Integer.parseInt(params.max) : null
        Integer offset = params.offset ? Integer.parseInt(params.offset) : null

        List<Shop> shopList

        if(!max && !offset){
            shopList = shopService.fetchAllShop()
            if(!shopList){ throw new NoContentFoundException("No shop with the Id found")}
            respond(shopList)
        }
        else{
            shopList = shopService.fetchShops(max, offset)
            if(!shopList){ throw new NoContentFoundException("No shop with the Id found")}
            Integer shopCount = shopService.fetchTotalNumberOfShops()
            Map<String, Object> paginatedShopDetails = [totalRecords: shopCount, records: shopList]
            respond(paginatedShopDetails)
        }
    }

    def createShop(){
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        Integer location = request.JSON.location ?: null
        List<Long> tagIds = request.JSON.tags ?: null

        if(controllerUtils.argumentNullChecker(name,description,location) || (tagIds ? tagIds.size():0) > 5){
            throw new InvalidDataPassed("Invalid Request Body")
        }

        Shop shop = shopService.createShop(name, description, location, tagIds)
        respond(shop, [status: HttpStatus.CREATED])
    }

    def updateShop(Long shopId){
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        Integer location = request.JSON.location ?: null
        List<Long> tagIds = request.JSON.tags ?: null

        if(controllerUtils.argumentNullChecker(name,description,location) || (tagIds ? tagIds.size():0) > 5){
            throw new InvalidDataPassed("Invalid Request Body")
        }

        Shop shop = shopService.updateShop(shopId, name, description, location, tagIds)
        respond(shop)
    }

    def removeShop(Long shopId){
        shopService.deleteShop(shopId)
        render(status: HttpStatus.NO_CONTENT)
    }

}

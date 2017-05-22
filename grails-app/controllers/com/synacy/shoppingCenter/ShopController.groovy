package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exception.InvalidDataPassed
import com.synacy.shoppingCenter.exception.NoContentFoundException
import com.synacy.shoppingCenter.trait.ExceptionHandlerTrait
import org.springframework.http.HttpStatus

class ShopController implements ExceptionHandlerTrait{

    static responseFormats = ['json']

    ShopService shopService

    def fetchShop(Long shopId){
        respond(shopService.fetchShopById(shopId))
    }

    def fetchAllShop(){
        Integer max = params.max ? Integer.parseInt(params.max) : null
        Integer offset = params.offset ? Integer.parseInt(params.offset) : null
        Long tagId = params.tagId ? Long.parseLong(params.tagId) : null

        if((max != null || offset != null) && (max < 0 || offset < 0)) throw new InvalidDataPassed("Offset and Max can't be a negative value")

        List<Shop> shopList = shopService.fetchShops(max, offset, tagId)
        Integer shopCount = shopService.fetchTotalNumberOfShops()
        Map<String, Object> shopDetails = [totalRecords: shopCount, records: shopList]

        respond(shopDetails)
    }

    def createShop(){
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        Integer location = request.JSON.location ?: null
        List<Long> tagIds = request.JSON.tags ?: null

        if(!name || !location){throw new InvalidDataPassed("Invalid Request Body. Name and location must not be nul")}
        if((tagIds ? tagIds.size():0) > 5){throw new InvalidDataPassed("Invalid Number of Tags, Maximum should be 5")}
        if(location < 1 || location > 4){throw new InvalidDataPassed("Invalid location value used")}

        Shop shop = shopService.createShop(name, description, location, tagIds)
        respond(shop, [status: HttpStatus.CREATED])
    }

    def updateShop(Long shopId){
        String name = request.JSON.name ?: null
        String description = request.JSON.description ?: null
        Integer location = request.JSON.location ?: null
        List<Long> tagIds = request.JSON.tags ?: null

        if(!name || !location){throw new InvalidDataPassed("Invalid Request Body. Name and location must not be nul")}
        if((tagIds ? tagIds.size():0) > 5){throw new InvalidDataPassed("Invalid Number of Tags, Maximum should be 5")}
        if(location < 1 || location > 4){throw new InvalidDataPassed("Invalid location value used")}

        Shop shop = shopService.updateShop(shopId, name, description, location, tagIds)
        respond(shop)
    }

    def removeShop(Long shopId){
        shopService.deleteShop(shopId)
        render(status: HttpStatus.NO_CONTENT)
    }

}
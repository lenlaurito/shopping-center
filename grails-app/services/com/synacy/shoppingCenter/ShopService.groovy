package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exception.InvalidDataPassed
import com.synacy.shoppingCenter.exception.NoContentFoundException
import grails.transaction.Transactional

@Transactional
class ShopService {

    TagService tagService

    def serviceMethod() {

    }

    Shop fetchShopById(Long shopId){
        Shop shop = Shop.findById(shopId)
        if(!shop){ throw new NoContentFoundException("No Shop with the Id found")}
        return shop
    }

    List<Shop> fetchAllShop(){
        List<Shop> shopList = Shop.findAll()
        if(!shopList){ throw new NoContentFoundException("No Shops found")}
        return shopList
    }

    List<Shop> fetchShops(Integer max, Integer offset) {
        List<Shop> shopList = Shop.list([offset: offset, max: max, sort: "id", order: "asc"])
        if(!shopList){ throw new NoContentFoundException("No Shops found")}
        return shopList
    }

    Integer fetchTotalNumberOfShops() {
        return Shop.count()
    }

    Shop createShop(String shopName, String shopDescription, Integer location, List<Long> tagIds){
         if(location < 1 || location > 4){throw new InvalidDataPassed("Invalid location used")}
        List<Tag> tags = shopTagValidator(tagIds)

        Shop shop = new Shop()
        shop.name = shopName
        shop.description = shopDescription
        shop.location = location
        shop.tags = tags

        return shop.save()
    }

    Shop updateShop(Long shopId, String shopName, String shopDescription, Integer location, List<Long> tagIds){
        if(location < 1 || location > 4){throw new InvalidDataPassed("Invalid location used")}
        List<Tag> tags = shopTagValidator(tagIds)

        Shop shopToBeUpdated = fetchShopById(shopId)
        shopToBeUpdated.name = shopName
        shopToBeUpdated.description = shopDescription
        shopToBeUpdated.location = location
        shopToBeUpdated.tags = tags

        return shopToBeUpdated.save()
    }

    void deleteShop(Long shopId){
        Shop shopToBeDeleted = fetchShopById(shopId)
        shopToBeDeleted.delete()
    }

    List<Tag> shopTagValidator(List<Long> tagIds){
        tagIds.unique()
        List<Tag> tagList = []
        for(Long id : tagIds){
            Tag tag = tagService.fetchTagById(id)
            if(tag){tagList.add(tag)}
            else{throw new InvalidDataPassed("A passed Tag doesn't exist in the database")}
        }
        return  tagList
    }
}

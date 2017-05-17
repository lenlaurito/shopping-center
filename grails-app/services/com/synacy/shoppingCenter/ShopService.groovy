package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exceptions.InvalidDataPassed
import grails.transaction.Transactional

@Transactional
class ShopService {

    TagService tagService;

    def serviceMethod() {

    }

    Shop fetchShopById(Long shopId){
        return Shop.findById(shopId)
    }

    List<Shop> fetchAllShop(){
        return Shop.findAll()
    }

    Shop createShop(String shopName, String shopDescription, Integer location,List<Long> tagIds){
        Shop shop = new Shop()

        if(location < 0 && location < 5){throw new InvalidDataPassed("Invalid location used")}

        List<Tag> tags = shopTagValidator(tagIds)

        shop.name = shopName
        shop.description = shopDescription
        shop.location = location
        shop.tags = tags

        return shop.save()
    }

    Shop updateShop(Long shopId, String shopName, String shopDescription){
        Shop shopToBeUpdated = fetchShopById(shopId)
        shopToBeUpdated.name = shopName
        shopToBeUpdated.description = shopDescription
        return shopToBeUpdated.save()
    }

    void deleteShop(Long shopId){
        Shop shopToBeDeleted = fetchShopById(shopId)
        shopToBeDeleted.delete()
    }

    List<Tag> shopTagValidator(List<Long> tagIds){

        List<Tag> tagList = []
        for(Long id : tagIds){
            Tag tag = tagService.fetchTagById(id)
            if(tag){tagList.add(tag)}
        }
        return  tagList
    }
}

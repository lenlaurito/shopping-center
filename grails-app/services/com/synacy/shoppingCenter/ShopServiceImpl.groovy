package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exception.InvalidDataPassed
import com.synacy.shoppingCenter.exception.NoContentFoundException
import grails.transaction.Transactional

@Transactional
class ShopServiceImpl implements ShopService{

    TagServiceImpl tagService

    Shop fetchShopById(Long shopId){
        return Shop.findById(shopId)
    }

    List<Shop> fetchShops(Integer max, Integer offset, Long tagId) {
        List<Shop> shopList = null

        if(tagId) {
            def tag = tagService.fetchTagById(tagId)
            if(!tag){ throw new NoContentFoundException("This tag does not exist.") }
            shopList = Shop.createCriteria().list(max: max, offset: offset) {
                tags {eq('id', tag.id)}
                order('id', 'asc')
            }
        }
        else { shopList = Shop.list([offset: offset, max: max, sort: "id", order: "asc"]) }
        return shopList
    }

    Integer fetchTotalNumberOfShops() {
        return Shop.count()
    }

    Shop createShop(String shopName, String shopDescription, Integer location, List<Long> tagIds){
        List<Tag> tags = shopTagValidator(tagIds)

        Shop shop = new Shop()
        shop.name = shopName
        shop.description = shopDescription
        shop.location = location
        shop.tags = tags

        return shop.save()
    }

    Shop updateShop(Long shopId, String shopName, String shopDescription, Integer location, List<Long> tagIds){
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

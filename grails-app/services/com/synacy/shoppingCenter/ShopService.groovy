package com.synacy.shoppingCenter

import grails.transaction.Transactional

@Transactional
class ShopService {

    def serviceMethod() {

    }

    Shop fetchShopById(Long shopId){
        return Shop.findById(shopId)
    }

    List<Shop> fetchAllShop(){
        return Shop.findAll()
    }

    Shop createShop(String shopName, String shopDescription){
        Shop shop = new Shop()
        shop.name = shopName
        shop.description = shopDescription
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
}

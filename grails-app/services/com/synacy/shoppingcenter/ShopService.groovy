package com.synacy.shoppingcenter

import grails.transaction.Transactional

@Transactional
class ShopService {

    public List<Shop> fetchAllShops() {
        return Shop.findAll()
    }

    public Shop createNewShop(String name, String description) {
        Shop shop = new Shop()

        shop.setName(name)
        shop.setDescription(description)

        return shop.save()
    }

    public Shop fetchShopById(Long shopId) {
        Shop shop = Shop.findById(shopId)

        if (!shop) {
            throw new ResourceNotFoundException("Shop not found")
        }

        return shop
    }

    public Shop updateShop(Long shopId, String name, String description) {
        Shop shop = fetchShopById(shopId)

        shop.setName(name)
        shop.setDescription(description)

        return shop.save();
    }

    public void deleteShop(Long shopId)  {
        Shop shop = fetchShopById(shopId)

        shop.delete()
    }
}

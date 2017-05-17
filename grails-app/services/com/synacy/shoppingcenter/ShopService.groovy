package com.synacy.shoppingcenter

import grails.transaction.Transactional

@Transactional
class ShopService {

    TagService tagService

    public List<Shop> fetchAllShops() {
        return Shop.findAll()
    }

    public Shop createNewShop(String name, String description, List<Long> tags) {
        Shop shop = new Shop()

        shop.setName(name)
        shop.setDescription(description)

        for (Long tagId : tags)
            shop.addToTags(tagService.fetchTagById(tagId))

        return shop.save()
    }

    public Shop fetchShopById(Long shopId) {
        Shop shop = Shop.findById(shopId)

        if (!shop) {
            throw new ResourceNotFoundException("Shop not found")
        }

        return shop
    }

    public Shop updateShop(Long shopId, String name, String description, List<Long> tags) {
        Shop shop = fetchShopById(shopId)

        shop.setName(name)
        shop.setDescription(description)

        return s.save()
    }

    public void deleteShop(Long shopId)  {
        Shop shop = fetchShopById(shopId)

        shop.delete()
    }
}

package com.synacy.shoppingcenter

import grails.transaction.Transactional

@Transactional
class ShopService {

    public List<Shop> fetchAllShops(Integer offset, Integer max, Long tagId) {
        if(tagId == null) {
            return Shop.list([offset: offset, max: max, sort: "id", order: "asc"])
        } else {
            return Shop.createCriteria().list(max: max, offset: offset) {
                tags {
                    eq('id', tagId)
                }
                order('id', 'asc')
            }
        }
    }

    public Shop fetchShopById(Long shopId) {
        return Shop.findById(shopId)
    }

    public Shop createNewShop(String name, String description, Location location, List<Tag> tags){
        Shop shop = new Shop()
        shop.name = name
        shop.description = description
        shop.location = location
        shop.tags = tags
        return shop.save()
    }

    public void deleteShop(Shop shop) {
        shop.delete()
    }

    public Shop updateShop(Shop shop, String name, String description, Location location, List<Tag> tags) {
        shop.name = name
        shop.description = description
        shop.location = location
        shop.tags = tags
        return shop.save()
    }

    public Integer fetchTotalNumberOfShops() {
        return Shop.count()
    }

}

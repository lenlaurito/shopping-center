package com.synacy.shoppingCenter

/**
 * Created by steven on 5/21/17.
 */
interface ShopService {

    Shop fetchShopById(Long shopId)

    List<Shop> fetchShops(Integer max, Integer offset, Long tagId)

    Integer fetchTotalNumberOfShops()

    Shop createShop(String shopName, String shopDescription, Integer location, List<Long> tagIds)

    Shop updateShop(Long shopId, String shopName, String shopDescription, Integer location, List<Long> tagIds)

    void deleteShop(Long shopId)

    List<Tag> shopTagValidator(List<Long> tagIds)
}

package com.synacy.shoppingCenter

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ShopService)
@Mock([Shop])
class ShopServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    void "fetchShopById should return the shop with the given id"(){
        given:
            Shop shop = new Shop(name: "Shop", description: "Description")
            shop.save()

        when:
            Shop fetchedShop = service.fetchById(shop.id)

        then:
            fetchedShop.id == shop.id
            fetchedShop.name == shop.name
            fetchedShop.description == shop.description
    }

    void "fetchAllShop should return list of all shops"(){
        given:
            Shop shop1 = new Shop(name: "Shop1", description: "Description1")
            Shop shop2 = new Shop(name: "Shop2", description: "Description2")
            shop1.save()
            shop2.save()

        when:
            List<Shop> fetchedShops = service.fetchAllShop()

        then:
        fetchedShops.size() == 2
        fetchedShops[0].id == shop1.id
        fetchedShops[0].name == shop1.name
        fetchedShops[0].description == shop1.description
        fetchedShops[1].id == shop2.id
        fetchedShops[1].name == shop2.name
        fetchedShops[1].description == shop2.description
    }

    void "createShop should create and return tag with the correct information"(){
        given:
            String shopName = "name"
            String shopDescription = "description"

        when:
            Shop createdShop = service.createShop(shopName, shopDescription)

        then:
            createdShop.name = shopName
            createdShop.description = shopDescription
            Shop.exists(createdShop.id)
    }

    void "updateShop should update the shops's information and save it"(){
        given:
            Long shopId = 1L
            String shopName = "name"
            String shopDescription = "description"
            Shop shop = new Shop(name: shopName, description: shopDescription)
            shop.save()

        when:
            Shop updatedShop = service.updateShop(shopId, shopName, shopDescription)

        then:
            updatedShop.name = shopName
            updatedShop.description = shopDescription
            Shop.exists(updatedShop.id)
    }

    void "deleteShop should delete the shop with the given id"(){
        given:
            Shop shop = new Shop(name: shopName, description: shopDescription)
            service.fetchShopById(shop.id) >> shop
            shop.save()

        when:
            service.deleteShop(shop.id)

        then:
            !Shop.exists(shop.id)
    }

}

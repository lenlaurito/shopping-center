package com.synacy.shoppingcenter

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

    // void "fetchAllShops should return the correct number of shops"() {
    //      given:
            //    Shop shop1 = new Shop(name: "Face Shop", location: "FIRST_FLOOR", description: "Buy faces...", tags: [])
            //    Shop shop2 = new Shop(name: "Clothes Shop", location: "SECOND_FLOOR", description: "Buy clothes...", tags: [])
    //            shop1.save()
    //            shop2.save()
    //
    //            def offset = 0
    //            def max = 0
    //
    //      when:
    //            List<Shop> shops = service.fetchAllShops(offset, max)
    //
    //      then:
    //            shops.size() == 2
    // }

    // void "fetchAllShops should return the shops with correct information"() {
    //      given:
    //            Shop shop1 = new Shop(name: "Face Shop", location: "FIRST_FLOOR", description: "Buy faces...", tags: [])
    //            Shop shop2 = new Shop(name: "Clothes Shop", location: "SECOND_FLOOR", description: "Buy clothes...", tags: [])
    //            shop1.save()
    //            shop2.save()
    //
    //            def offset = 0
    //            def max = 0
    //
    //      when:
    //            List<Shop> shops = service.fetchAllShops(offset, max)
    //
    //      then:
    //            shops.get(0) == shop1
    //            shops.get(1) == shop2
    // }

    // void "fetchShopById should return the correct shop"() {
    //      given:
    //            Shop shop = new Shop(name: "Face Shop", location: "FIRST_FLOOR", description: "Buy faces...", tags: [])
    //            shop.save()
    //
    //      when:
    //            Shop fetchedShop = service.fetchShopById(shop.id)
    //
    //      then:
    //            fetchedShop.id == shop.id
    //            fetchedShop.name == shop.name
    //            fetchedShop.location == shop.location
    //            fetchedShop.description == shop.description
    //            fetchedShop.tags == shop.tags
    // }

    // void "createShop should save shop with correct information"() {
    //      given:
               // String name = "Face Shop"
               // Location location = "FIRST_FLOOR"
               // String description = "Buy faces..."
               // List<Tag> tags = []
    //
    //       when:
    //            Shop createdShop = service.createShop(name, location, description, tags)
    //
    //       then:
    //            Shop.exists(createdShop.id)
               // createdShop.name == name
               // createdShop.location == location
               // createdShop.description == description
               // createdShop.tags == tags.toSet()
    // }

    // void "updateShop should save new shop details in existing shop"() {
    //      given:
               // Shop shop = new Shop(name: "Face Shop", location: "FIRST_FLOOR", description: "Buy faces...", tags: [])
               // shop.save()
    //
    //            String name = "Clothes Shop"
    //            Location location = "SECOND_FLOOR"
    //            String description = "Buy clothes..."
    //            List<Tag> tags = []
    //
    //       when:
    //            Shop updatedShop = service.updateShop(shop, name, location, description, tags)
    //
    //       then:
    //            Shop.exists(shop.id)
    //            updatedShop.name == name
    //            updatedShop.location == location
    //            updatedShop.description == description
    //            updatedShop.tags == tags.toSet()
    // }

    // void "deleteShop should remove shop"() {
    //      given:
    //            Shop shop = new Shop(name: "Face Shop", location: "FIRST_FLOOR", description: "Buy faces...", tags: [])
    //            shop.save()
    //
    //       when:
    //            service.deleteShop(shop)
    //
    //       then:
    //            !Shop.exists(shop.id)
    // }

    // void "fetchTotalNumberOfShops should return correct number of shops"() {
    //      given:
    //            Shop shop1 = new Shop(name: "Face Shop", location: "FIRST_FLOOR", description: "Buy faces...", tags: [])
    //            Shop shop2 = new Shop(name: "Clothes Shop", location: "SECOND_FLOOR", description: "Buy clothes...", tags: [])
    //            shop1.save()
    //            shop2.save()
    //
    //       when:
    //            def count = service.fetchTotalNumberOfShops()
    //
    //       then:
    //            count == Shop.count()
    //            count == 2
    // }
}

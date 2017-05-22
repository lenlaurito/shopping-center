package com.synacy.shoppingcenter

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ShopService)
@Mock([Shop, Tag])
class ShopServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "fetchAllShops should return all Shops"() {
        given:
        Shop shop1 = new Shop(name: "Shop 1", description: "Shop 1 description", location: Location.FIRST_FLOOR, tags: [])
        Shop shop2 = new Shop(name: "Shop 1", description: "Shop 1 description", location: Location.FIRST_FLOOR, tags: [])
        shop1.save()
        shop2.save()

        when:
        List<Shop> allShops = service.fetchAllShops(null, null, null)

        then:
        allShops.size() == 2
        allShops.contains(shop1)
        allShops.contains(shop2)
    }

    void "fetchAllShops should return all Shops filtered by tag id"() {
        given:
        Tag tag1 = new Tag(name: "Tag 1")
        Tag tag2 = new Tag(name: "Tag 2")
        Tag tag3 = new Tag(name: "Tag 3")
        tag1.save()
        tag2.save()
        tag3.save()

        Shop shop1 = new Shop(name: "Shop 1", description: "Shop 1 description", location: Location.FIRST_FLOOR, tags: [tag1, tag2])
        Shop shop2 = new Shop(name: "Shop 1", description: "Shop 1 description", location: Location.FIRST_FLOOR, tags: [tag1, tag2])
        Shop shop3 = new Shop(name: "Shop 1", description: "Shop 1 description", location: Location.FIRST_FLOOR, tags: [tag3, tag2])
        shop1.save()
        shop2.save()
        shop3.save()

        when:
        List<Shop> allShops = service.fetchAllShops(null, null, tag1.id)

        then:
        allShops.size() == 2
        allShops.contains(shop1)
        allShops.contains(shop2)
    }

    void "fetchShopById should return the Shop with the specified id"() {
        given:
        Shop shop = new Shop(name: "Shop 1", description: "Shop 1 description", location: Location.FIRST_FLOOR, tags: [])
        shop.save()

        when:
        Shop resultShop = service.fetchShopById(shop.id)

        then:
        shop == resultShop
        shop.id == resultShop.id
        shop.name == resultShop.name
        shop.description == resultShop.description
        shop.location == resultShop.location
        shop.tags == resultShop.tags
    }

    void "createNewShop should create a new Shop with the given details"() {
        given:
        String name = "My Shop"
        String description = "Short description"
        Location location = Location.valueOfLocation("First floor")
        List<Tag> tags = []

        when:
        Shop createdShop = service.createNewShop(name, description, location, tags)

        then:
        createdShop.name == name
        createdShop.description == description
        createdShop.location == location
        createdShop.tags as List == tags
    }

    void "deleteShop should delete the specified Shop"() {
        given:
        Shop shop = new Shop(name: "Shop 1", description: "Shop 1 description", location: Location.FIRST_FLOOR, tags: [])
        shop.save()

        when:
        service.deleteShop(shop)

        then:
        Shop.find {
            it == shop
        } == null
    }

    void "updateShop should update the given Shop with the new details"() {
        given:
        Shop shop = new Shop(name: "Shop 1", description: "Shop 1 description", location: Location.FIRST_FLOOR, tags: [])
        shop.save()

        String newName = "New Shop"
        String newDescription = "Latest description"
        Location location = Location.SECOND_FLOOR
        List<Tag> tags = []

        when:
        Shop updatedShop = service.updateShop(shop, newName, newDescription, location, tags)

        then:
        updatedShop.name == newName
        updatedShop.description == newDescription
        updatedShop.location == location
        updatedShop.tags as List == tags
    }

    void "fetchTotalNumberOfShops should return total number of existing shops"() {
        given:
        Shop shop1 = new Shop(name: "Shop 1", description: "Shop 1 description", location: Location.FIRST_FLOOR, tags: [])
        Shop shop2 = new Shop(name: "Shop 1", description: "Shop 1 description", location: Location.FIRST_FLOOR, tags: [])
        shop1.save()
        shop2.save()

        when:
        Integer totalCount = service.fetchTotalNumberOfShops()

        then:
        totalCount == 2
    }


}

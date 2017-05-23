package com.synacy.shoppingcenter

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ShopService)
@Mock([Shop, Tag, TagService])
class ShopServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "fetchAllShops default should_return all shops"() {
        given:
        Integer offset = null
        Integer max = null

        Shop shop1 = new Shop(name: "shop 1", description: "shop 1 descripiton")
        Shop shop2 = new Shop(name: "shop 2", description: "shop 2 descripiton")
        Shop shop3 = new Shop(name: "shop 3", description: "shop 3 descripiton")

        shop1.save()
        shop2.save()
        shop3.save()

        when:
        List<Shop> shops = service.fetchAllShops(offset, max, null)

        then:
        shops.size() == 3
        shops.get(0).equals(shop1)
        shops.get(1).equals(shop2)
        shops.get(2).equals(shop3)
    }

    void "fetchAllShops paging should return_limited shops"() {
        given:
        Integer offset = 2
        Integer max = 1

        Shop shop1 = new Shop(name: "shop 1", description: "shop 1 descripiton")
        Shop shop2 = new Shop(name: "shop 2", description: "shop 2 descripiton")
        Shop shop3 = new Shop(name: "shop 3", description: "shop 3 descripiton")

        shop1.save()
        shop2.save()
        shop3.save()

        when:
        List<Shop> shops = service.fetchAllShops(offset, max, null)

        then:
        shops.size() == 1
        shops.get(0).equals(shop3)
    }

    void "createNewShop should save new shop and return"() {
        given:
        String name = "sample"
        String description = "sample description"

        Tag tag1 = new Tag(name: "name")
        Tag tag2 = new Tag(name: "name")

        tag1.save()
        tag2.save()

        def tagIds = [tag1.id, tag2.id]
        def locations = ["FIRST_FLOOR"]

        when:
        Shop createdShop = service.createShop(name, description, tagIds, locations)


        then:
        createdShop.name == name
        createdShop.description == description
        createdShop.tags.getAt(0).id == tag1.id
        createdShop.tags.getAt(0).name == tag1.name
        createdShop.tags.getAt(1).id == tag2.id
        createdShop.tags.getAt(1).name == tag2.name
    }

    void "fetchShopById should return target shop"() {
        given:

        Shop shop1 = new Shop(name: "shop 1", description: "shop 1 descripiton")
        shop1.save()

        Long shopId = shop1.id

        when:
        Shop foundShop = service.fetchShopById(shopId)

        then:
        foundShop.name == shop1.name
        foundShop.description == shop1.description
    }

    void "updateShop should update new shop attributes"() {
        given:
        String name = "sample"
        String description = "sample description"

        Tag tag1 = new Tag(name: "name")
        Tag tag2 = new Tag(name: "name")

        tag1.save()
        tag2.save()

        Shop shop1 = new Shop(name: "shop 1", description: "shop 1 descripiton")
        shop1.tags = [tag1, tag2]

        shop1.save()

        Long shopId = shop1.id


        when:
        Shop updatedShop = service.updateShop(shopId, "new name", "shop 1 descripiton", [tag2.id], null)


        then:
        updatedShop.id == shopId
        updatedShop.name == "new name"
        updatedShop.description == "shop 1 descripiton"
        updatedShop.tags.size() == 1
        updatedShop.tags.getAt(0).id == tag2.id
        updatedShop.tags.getAt(0).name == tag2.name
    }

    void "deleteShopById should be able to delete target shop"() {
        given:
        Shop shop1 = new Shop(name: "shop 1", description: "shop 1 descripiton")
        shop1.save()

        Long shopId = shop1.id

        when:
        service.deleteShopById(shopId)

        then:
        Shop.findById(shopId) == null
    }
}

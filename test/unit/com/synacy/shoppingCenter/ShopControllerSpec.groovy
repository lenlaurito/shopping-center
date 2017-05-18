package com.synacy.shoppingCenter

import grails.test.mixin.TestFor
import org.springframework.http.HttpStatus
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ShopController)
class ShopControllerSpec extends Specification {

    ShopService shopService = Mock()

    def setup() {
        controller.shopService = shopService
    }

    void "fetchShop should respond with the Shop with the given id"(){
        given:
            Long shopId = 1L
            Shop shop = new Shop(name: "shopName", description: "shopDescription", location: 1, tags: [])
            shopService.fetchShopById(shopId) >> shop

        when:
            controller.fetchShop(shopId)

        then:
            response.status == HttpStatus.OK.value()
            response.json.name == "shopName"
            response.json.description == "shopDescription"
            response.json.location == 1
            response.json.tags == []
    }

    void "fetchAllShop max and offset are null should respond with all the Shops"(){
        given:
            Shop shop1 = new Shop(name: "shopName1", description: "shopDescription1", location: 1, tags: [])
            Shop shop2 = new Shop(name: "shopName2", description: "shopDescription2", location: 1, tags: [])
            shopService.fetchAllShop() >> [shop1, shop2]

        when:
            controller.fetchAllShop()

        then:
            response.status == HttpStatus.OK.value()
            response.json.size() == 2
            response.json.find{it.name == "shopName1" && it.description == "shopDescription1" && it.location == 1 && it.tags == []} != null
            response.json.find{it.name == "shopName2" && it.description == "shopDescription2" && it.location == 1 && it.tags == []} != null
    }

    void "fetchAllShop max and offset not null should respond with all the Paginated Shops"(){
        given:
            Integer max = 2
            Integer offset = 0
            Shop shop1 = new Shop(name: "shopName1", description: "shopDescription1", location: 1, tags: [])
            Shop shop2 = new Shop(name: "shopName2", description: "shopDescription2", location: 1, tags: [])
            List<Shop> shopList = [shop1, shop2]
            shopService.fetchShops(max, offset) >> shopList
            shopService.fetchTotalNumberOfShops() >> 2

        when:
            controller.fetchAllShop() >> [totalRecords: 2, records: shopList]

        then:
            response.status == HttpStatus.OK.value()
    }

    void "createShop should respond with the newly created Shop and its details"(){
        given:
            String name = "name"
            String description = "description"
            Integer location = 1
            List<Long> tagIds = null

            request.json = [name: name, description: description, location: location, tagIds: tagIds]

            Shop shop = new Shop(name: name, description: description, location: location, tagIds: tagIds)

        when:
            controller.createShop()
        then:
            1 * shopService.createShop(name, description, location, tagIds) >> shop
        then:
            response.status == HttpStatus.CREATED.value()
            response.json.name == shop.name
            response.json.description == shop.description
            response.json.location == shop.location
    }

    void "createShop should throw InvalidDataPassed exception"(){
        given:
            String shopName = null
            String shopDescription = null
            Integer location = null
            List<Long> tagIds = [1,2,3,4,5,6]

            request.json = [name: shopName, description: shopDescription, location: location, tagIds: tagIds]

        when:
            controller.createShop()

        then:
            response.status == HttpStatus.NOT_ACCEPTABLE.value()
    }

    void "updateShop should respond with the updated Shop and its details"(){
        given:
            Long shopId = 1L
            String shopName = "name"
            String shopDescription = "description"
            Integer location = 1
            List<Long> tagIds = null

            request.json = [name: shopName, description: shopDescription, location: location, tagIds: tagIds]

            Shop shop = new Shop(name: shopName, description: shopDescription, location: location, tagIds: tagIds)
            shopService.updateShop(shopId, shopName, shopDescription, location, tagIds) >> shop

        when:
            controller.updateShop(shopId)

        then:
            response.status == HttpStatus.OK.value()
            response.json.name == shop.name
            response.json.description == shop.description
    }

    void "updateShop should throw InvalidDataPassed exception"(){
        given:
            Long shopId = 1L
            String shopName = null
            String shopDescription = null
            Integer location = null
            List<Long> tagIds = [1,2,3,4,5,6]

            request.json = [name: shopName, description: shopDescription, location: location, tagIds: tagIds]

        when:
            controller.updateShop(shopId)

        then:
            response.status == HttpStatus.NOT_ACCEPTABLE.value()
    }

    void "removeShop should remove the Shop with the given shopID"(){
        given:
            Long shopId = 1L

        when:
            controller.removeShop(shopId)

        then:
            response.status == HttpStatus.NO_CONTENT.value()
    }
}
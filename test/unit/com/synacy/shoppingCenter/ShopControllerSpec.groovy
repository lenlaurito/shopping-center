package com.synacy.shoppingCenter

import grails.test.mixin.TestFor
import org.springframework.http.HttpStatus
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ShopController)
class ShopControllerSpec extends Specification {

    ShopService shopService = Mock();

    def setup() {
        controller.shopService = shopService;
    }

    def cleanup() {
    }

    void "test something"() {
    }

    void "fetchShop should respond with the Shop with the given id"(){
        given:
            Long shopId = 1L
            Shop shop = new Shop(name: "shopName", description: "shopDescription")
            shopService.fetchShopById(shopId) >> shop

        when:
            controller.fetchShop(shopId)

        then:
            response.status == HttpStatus.OK.value()
            response.json.name == "shopName"
            response.json.description == "shopDescription"
    }

    void "fetchAllShop should respond with all the Shops"(){
        given:
            Shop shop1 = new Shop(name: "shopName1", description: "shopDescription1")
            Shop shop2 = new Shop(name: "shopName2", description: "shopDescription2")
            shopService.fetchAllShop() >> [shop1, shop2]

        when:
            controller.fetchAllShop()

        then:
            response.status == HttpStatus.OK.value()
            response.json.size() == 2
            response.json.find{it.name == "shopName1" && it.description == "shopDescription1"} != null
            response.json.find{it.name == "shopName2" && it.description == "shopDescription2"} != null
    }

    void "createShop should respond with the newly created Shop and its details"(){
        given:
            String shopName = "name"
            String shopDescription = "description"
            request.json = [name: shopName, description: shopDescription]
            Shop shop = new Shop(name: shopName, description: shopDescription)

        when:
            controller.createShop()

        then:
            response.status == HttpStatus.CREATED.value()
//            response.json.name == shop.name
//            response.json.description == shop.description
    }

    void "updateShop should respond with the updated Shop and its details"(){
        given:
            Long shopId = 1L
            String shopName = "name"
            String shopDescription = "description"
            request.json = [name: shopName, description: shopDescription]
            Shop shop = new Shop(name: shopName, description: shopDescription)
            shopService.updateShop(shopId, shopName, shopDescription) >> shop

        when:
            controller.updateShop(shopId)

        then:
            response.status == HttpStatus.OK.value()
            response.json.name == shop.name
            response.json.description == shop.description
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

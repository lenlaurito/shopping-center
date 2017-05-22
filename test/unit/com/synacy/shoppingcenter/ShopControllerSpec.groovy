package com.synacy.shoppingcenter

import grails.test.mixin.TestFor
import spock.lang.Specification
import org.springframework.http.HttpStatus
import com.synacy.shoppingcenter.exception.NoContentException

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ShopController)
@Mock([Shop])
class ShopControllerSpec extends Specification {

    ShopService shopService = Mock()

    def setup() {
        controller.shopService = shopService
    }

    def cleanup() {
    }

    void "fetchShops should return a list of shops with null params"() {

        given:
            Shop shop1 = new Shop(name: "shop1",  location: Location.FIRST_FLOOR, description: "this is shop1", tags: [])
            Shop shop2 = new Shop(name: "shop2", location: Location.SECOND_FLOOR, description: "this is shop2", tags: [])

            shopService.fetchAllShops(null, null, null) >> [shop1, shop2]

        when:
            controller.fetchShops()

        then:
            response.json.size() == 2
            response.status == HttpStatus.OK.value()
            // response.json.find {
            //     it.name == "shop1" && it.location == Location.FIRST_FLOOR && it.description == "this is shop1"
            // } != null
            // response.json.find {
            //     it.name == "shop2" && it.location == Location.SECOND_FLOOR && it.description == "this is shop2"
            // } != null
    }

    // void "fetchShop should respond with the shop with the given id"() {
    //     given:
    //         Long shopId = 1L
    //         Shop shop = new Shop(name: "shop1", location: Location.FIRST_FLOOR, description: "this is shop1")
    //         tagService.fetchTagById(tagId) >> tag
    //
    //     when:
    //         controller.fetchTag(tagId)
    //
    //     then:
    //         response.status == HttpStatus.OK.value()
    //         response.json.name == "Cosmetics"
    // }
}

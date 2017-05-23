package com.synacy.shoppingcenter

import grails.test.mixin.TestFor
import spock.lang.Specification
import org.springframework.http.HttpStatus


/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ShopController)
@Mock(Shop)
class ShopControllerSpec extends Specification {

    ShopService shopService = Mock()
    def setup() {

        controller.shopService = shopService
    }

    def cleanup() {
    }

    void "fetchAllShops should respond with all the shops"() {
        given:
        Integer offset = ShopService.DEFAULT_PAGINATION_OFFSET
        Integer max = ShopService.DEFAULT_PAGINATION_MAX
        Long tagId = null


        Shop shop1 = new Shop(name: "shop 1", description: "shop 1 descripiton")
        Shop shop2 = new Shop(name: "shop 2", description: "shop 2 descripiton")


        when:
        controller.fetchAllShops()

        then:
        1 * shopService.fetchAllShops(offset, max, tagId) >> [shop1, shop2]
        1 * shopService.fetchTotalNumberOfShops() >> 2

        then:
        response.status == HttpStatus.OK.value()
        response.json.totalRecords == 2
        response.json.records.length() == 2
        response.json.records.get(0).name == shop1.name
        response.json.records.get(0).description == shop1.description
        response.json.records.get(1).name == shop2.name
        response.json.records.get(1).description == shop2.description
    }


    void "fetchAllShops when paginated should_respond with paginated the shops"() {
        given:

        Long tagId = null

        params.offset = "0"
        params.max = "1"

        Shop shop1 = new Shop(name: "shop 1", description: "shop 1 descripiton")
        Shop shop2 = new Shop(name: "shop 2", description: "shop 2 descripiton")

        when:
        controller.fetchAllShops()

        then:
        1 * shopService.fetchAllShops(0, 1, tagId) >> [shop1]
        1 * shopService.fetchTotalNumberOfShops() >> 2

        then:
        response.status == HttpStatus.OK.value()
        response.json.totalRecords == 2
        response.json.records.length() == 1
        response.json.records.get(0).name == shop1.name
        response.json.records.get(0).description == shop1.description
    }

    void "createShop without tags should respond with new created shop with no related_tags"() {
        given:

        request.json = [name: "sample", description: "sample"]

        Shop shop = new Shop(name: "sample", description:  "sample", tags: null, locations: null)

        when:
        controller.createShop()

        then:
        1 * shopService.createShop("sample", "sample", null, null) >> shop

        then:
        response.status == HttpStatus.CREATED.value()
        response.json.name == "sample"
        response.json.description == "sample"
        response.json.tags ?: null == null
        response.json.locations ?: null == null
    }

    void "createShop with tags and location should respond with new created shop with related tags and location"() {
        given:
        String name = "sample"
        String description = "sample"
        def tagIds = [1]
        def locString = ["FIRST_FLOOR"]

        request.json = [name: name, description: description, tags: tagIds, locations: locString]

        Tag tag1 = new Tag(name: "name")
        Tag tag2 = new Tag(name: "name")

        def tags = [tag1]
        def locations = [Location.FIRST_FLOOR]

        Shop shop = new Shop(name: name, description: description, tags: tags, locations: locations)

        when:
        controller.createShop()

        then:
        1 * shopService.createShop("sample", "sample",  tagIds, locString) >> shop

        then:
        response.status == HttpStatus.CREATED.value()
        response.json.name == "sample"
        response.json.description == "sample"
        response.json.tags.get(0).name == "name"
        response.json.locations.length() == 1
        response.json.locations.get(0).name == "FIRST_FLOOR"
    }

    void "viewShop should find existing shop by id"() {
        given:
        Long idToFind = 1L
        Shop shop = new Shop(name: "sample", description: "description")

        when:
        controller.viewShop(idToFind)

        then:
        1 * shopService.fetchShopById(idToFind) >> shop

        then:
        response.status == HttpStatus.OK.value()
        response.json.name == "sample"
        response.json.description == "description"
    }

    void "viewShop on non existing shop should result to 404 response status"() {
        given:
        Long idToFind = 123L

        when:
        controller.viewShop(idToFind)

        then:
        1 * shopService.fetchShopById(idToFind) >> null

        then:
        response.status == HttpStatus.NOT_FOUND.value()
    }

    void "updateShop should update the shop with"() {
        given:

        Long idToFind = 123L
        String name = "sample"
        String description = "sample"
        List<Long> tagIds = [1]

        request.json = [name: name, description: description, tags: tagIds]

        Shop shop = new Shop(name: name, description: description)

        Tag tag1 = new Tag(name: "name")

        List<Tag> tags = [tag1]
        shop.tags = tags


        when:
        controller.updateShop(idToFind)

        then:
        1 * shopService.updateShop(idToFind, name, description, tagIds, null) >> shop

        then:
        response.status == HttpStatus.OK.value()
        response.json.name == name
        response.json.description == description
        response.json.tags.get(0).id ?: null == tags.get(0).id
        response.json.tags.get(0).name == tags.get(0).name
    }

    void "deleteShop should delete the shop"() {
        given:
        Long idToFind = 123L

        when:
        controller.deleteShop(idToFind)

        then:
        1 * shopService.deleteShopById(idToFind)

        then:
        response.status == HttpStatus.NO_CONTENT.value()
    }
}

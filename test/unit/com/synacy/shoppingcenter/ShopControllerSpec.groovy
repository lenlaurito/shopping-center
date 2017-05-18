package com.synacy.shoppingcenter

import grails.test.mixin.TestFor
import spock.lang.Specification
import org.springframework.http.HttpStatus


/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ShopController)
class ShopControllerSpec extends Specification {

    ShopService shopService = Mock();
    def setup() {

        controller.shopService = shopService
    }

    def cleanup() {
    }

    void "index_should_respond_with_all_the_shops"() {
        given:

        Shop shop1 = new Shop(name: "shop 1", description: "shop 1 descripiton")
        Shop shop2 = new Shop(name: "shop 2", description: "shop 2 descripiton")


        when:
        controller.index()

        then:
        1 * shopService.fetchAllShops(null, null) >> [shop1, shop2]
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


    void "index_paginated_should_respond_with_paginated_the_shops"() {
        given:

        String offset = "0"
        String max = "1"

        params.offset = offset
        params.max = max

        Shop shop1 = new Shop(name: "shop 1", description: "shop 1 descripiton")
        Shop shop2 = new Shop(name: "shop 2", description: "shop 2 descripiton")

        when:
        controller.index()

        then:
        1 * shopService.fetchAllShops(0, 1) >> [shop1]
        1 * shopService.fetchTotalNumberOfShops() >> 2

        then:
        response.status == HttpStatus.OK.value()
        response.json.totalRecords == 2
        response.json.records.length() == 1
        response.json.records.get(0).name == shop1.name
        response.json.records.get(0).description == shop1.description
    }

    void "create_without_tags_should_respond_with_new_created_shop_with_no_related_tags"() {
        given:
        String name = "sample"
        String description = "sample"
        List<Long> tags = null

        request.json = [name: name, description: description]

        Shop shop = new Shop(name: name, description: description)

        when:
        controller.create()

        then:
        1 * shopService.createNewShop(name, description, null) >> shop

        then:
        response.status == HttpStatus.CREATED.value()
        response.json.name == name
        response.json.description == description
        response.json.tags ?: null == tags
    }

    void "create_with_tags_should_respond_with_new_created_shop_with_related_tags"() {
        given:
        String name = "sample"
        String description = "sample"
        List<Long> tagIds = [1]

        request.json = [name: name, description: description, tags: tagIds]

        Shop shop = new Shop(name: name, description: description)

        Tag tag1 = new Tag(name: "name")
        Tag tag2 = new Tag(name: "name")

        List<Tag> tags = [tag1, tag2]
        shop.tags = tags

        when:
        controller.create()

        then:
        1 * shopService.createNewShop(name, description, tagIds) >> shop

        then:
        response.status == HttpStatus.CREATED.value()
        response.json.name == name
        response.json.description == description
        response.json.tags.get(0).id ?: null == tags.get(0).id
        response.json.tags.get(0).name == tags.get(0).name
        response.json.tags.get(1).id ?: null == tags.get(1).id
        response.json.tags.get(1).name == tags.get(1).name
    }

    void "view_should_find_existing_shop_by_id"() {
        given:
        Long idToFind = 1L
        Shop shop = new Shop(name: "sample", description: "description")

        when:
        controller.view(idToFind)

        then:
        1 * shopService.fetchShopById(idToFind) >> shop

        then:
        response.status == HttpStatus.OK.value()
        response.json.name == "sample"
        response.json.description == "description"
    }

    void "view_on_non_existing_shop_should_result_to_404_response_status"() {
        given:
        Long idToFind = 123L

        when:
        controller.view(idToFind)

        then:
        1 * shopService.fetchShopById(idToFind) >> null

        then:
        response.status == HttpStatus.NOT_FOUND.value()
    }

    void "update_should_update_the_shop_with"() {
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
        controller.update(idToFind)

        then:
        1 * shopService.updateShop(idToFind, name, description, tagIds) >> shop

        then:
        response.status == HttpStatus.OK.value()
        response.json.name == name
        response.json.description == description
        response.json.tags.get(0).id ?: null == tags.get(0).id
        response.json.tags.get(0).name == tags.get(0).name
    }

    void "delete_should_delete_the_shop"() {
        given:
        Long idToFind = 123L

        when:
        controller.delete(idToFind)

        then:
        1 * shopService.deleteShopById(idToFind)

        then:
        response.status == HttpStatus.NO_CONTENT.value()
    }
}

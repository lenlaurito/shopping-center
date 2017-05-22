package com.synacy.shoppingcenter

import grails.test.mixin.TestFor
import spock.lang.Specification
import org.springframework.http.HttpStatus
import com.synacy.shoppingcenter.exceptions.*
import com.synacy.shoppingcenter.Location


/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ShopController)
class ShopControllerSpec extends Specification {

    ShopService shopService = Mock()
    TagService tagService = Mock()

    def setup() {
        controller.shopService = shopService
        controller.tagService = tagService
    }

    def cleanup() {
    }

    void "fetchAllShops should respond all given Shops"() {
        given:
        Shop workShop = new Shop(name: "Work Shop", description: "A work shop", location: Location.FIRST_FLOOR, tags: [])
        Shop clotheShop = new Shop(name: "Clothe Shop", description: "A clothe shop", location: Location.FIRST_FLOOR, tags: [])

        params.offset = "0"
        params.max = "2"
        params.tagId = "100"

        Integer offset = Integer.parseInt(params.offset)
        Integer max = Integer.parseInt(params.max)
        Long tagId = Long.parseLong(params.tagId)

        List<Shop> shopList = [workShop, clotheShop]

        shopService.fetchAllShops(offset, max, tagId) >> shopList

        shopService.fetchTotalNumberOfShops() >> 2

        when:
        controller.fetchAllShops()

        then:
        response.status == HttpStatus.OK.value()
        response.json.totalRecords == 2
        response.json.records.find {
            it.name == "Work Shop" && it.description  == "A work shop"
        } != null
        response.json.records.find {
            it.name == "Clothe Shop" && it.description == "A clothe shop"
        } != null

    }

    void "fetchShop should respond the correct Shop which has the specified id"() {
        given:
        Long shopId = 100L
        Location location = Location.valueOfLocation("First Floor")
        Shop shop = new Shop(name: "Work Shop", description: "A work shop", location: location, tags: [])
        shopService.fetchShopById(shopId) >> shop

        when:
        controller.fetchShop(shopId)

        then:
        response.status == HttpStatus.OK.value()
        response.json.name == shop.name
        response.json.description == shop.description
        response.json.location.name == "FIRST_FLOOR"
        response.json.tags as List == []
    }

    void "fetchShop should throw ResourceNotFoundException if shop does not exist"() {
        given:
        Long shopId = 100L

        shopService.fetchShopById(shopId) >> null

        when:
        controller.fetchShop(shopId)

        then:
        response.status == HttpStatus.NOT_FOUND.value()
    }

    void "createShop should respond with the newly created shop with the specified details"() {
        given:
        String name = "Work Shop"
        String description = "A work shop"

        Tag tag1 = new Tag(name: "first tag")
        Tag tag2 = new Tag(name: "second tag")

        List<Tag> tags = [tag1, tag2]
        List tagIds = [100L, 200L]

        tagService.checkTags(tagIds) >> tags

        String locationString = "First Floor"
        Location location = Location.valueOfLocation(locationString)

        request.json = [name: name, description: description, location: locationString, tagId: tagIds]

        Shop shop = new Shop(name: name, description: description, location: location, tags: tags)

        when:
        controller.createShop()

        then:
        1 * shopService.createNewShop(name, description, location, tags) >> shop

        then:
        response.status == HttpStatus.CREATED.value()
        response.json.name == name
        response.json.description == description
        response.json.location.name == "FIRST_FLOOR"
        response.json.tags.find {
            it.name == tag1.name
        } != null
        response.json.tags.find {
            it.name == tag2.name
        }
    }

    void "createShop should return NullPointerException if name request is null"() {
        given:
        String name = null
        String description = "A work shop"

        String location = "First floor"
        List tagIds = []

        request.json = [name: name, description: description, location: location, tagId: tagIds]

        when:
        controller.createShop()

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    void "createShop should return InvalidLocationException if request.location is null or not in the Location enum"() {
        given:
        String name = "Work Shop"
        String description = "A work shop"

        Tag tag1 = new Tag(name: "first tag")
        Tag tag2 = new Tag(name: "second tag")

        List<Tag> tags = [tag1, tag2]
        List tagIds = [100L, 200L]

        String location = "Fifth floor"

        request.json = [name: name, description: description, location: location, tagId: tagIds]

        when:
        controller.createShop()

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    void "createShop should return LimitExceedException if tags requested are more than 5"() {
        given:
        String name = "Work Shop"
        String description = "A work shop"

        Tag tag1 = new Tag(name: "first tag")
        Tag tag2 = new Tag(name: "second tag")
        Tag tag3 = new Tag(name: "third tag")
        Tag tag4 = new Tag(name: "fourth tag")
        Tag tag5 = new Tag(name: "fifth tag")
        Tag tag6 = new Tag(name: "sixth tag")

        List tagIds = [100L, 200L, 300L, 400L, 500L, 600L]

        String locationString = "First floor"

        request.json = [name: name, description: description, location: locationString, tagId: tagIds]

        when:
        controller.createShop()

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    void "removeShop should delete the Shop which has the specified id"() {
        given:
        Long shopId = 100L
        Shop shop = new Shop(name: "Workshop", description: "A workshop")

        shopService.fetchShopById(shopId) >> shop

        when:
        controller.removeShop(shopId)

        then:
        1 * shopService.deleteShop(shop)

        then:
        response.status == HttpStatus.NO_CONTENT.value()
    }

    void "updateShop should update the Shop which has the specified id"() {
        given:
        Long shopId = 100L

        String name = "Workshop"
        String description = "A workshop"
        String locationString = "First Floor"
        Location location = Location.valueOfLocation(locationString)

        Tag tag1 = new Tag(name: "first tag")
        Tag tag2 = new Tag(name: "second tag")

        List<Tag> tags = [tag1, tag2]
        List tagIds = [100L, 200L]

        tagService.checkTags(tagIds) >> tags

        request.json = [name: name, description: description, location: locationString, tagId: tagIds]

        Shop shop = new Shop(name: name, description: description, location: location, tags: tags)

        shopService.fetchShopById(shopId) >> shop

        Shop updatedShop = new Shop(name: "Flowershop", description: "A flowershop", location: location, tags: tags)

        when:
        controller.updateShop(shopId)

        then:
        1 * shopService.updateShop(shop, name, description, location, tags) >> updatedShop

        then:
        response.status == HttpStatus.OK.value()
        response.json.name == updatedShop.name
        response.json.description == updatedShop.description
        response.json.location.name == "FIRST_FLOOR"
        response.json.tags.find {
            it.name == tag1.name
        } != null
        response.json.tags.find {
            it.name == tag2.name
        }
    }

    void "updateShop should throw ResourceNotFoundException if shop does not exist"() {
        given:
        Long shopId = 100L

        shopService.fetchShopById(shopId) >> null

        when:
        controller.updateShop(shopId)

        then:
        response.status == HttpStatus.NOT_FOUND.value()
    }

    void "updateShop should throw NullPointerException if name request is null"() {
        given:
        Long shopId = 100L
        String name = null
        String description = "A work shop"

        String location = "First floor"

        request.json = [name: name, description: description, location: location, tagId: []]

        shopService.fetchShopById(shopId) >> new Shop()

        when:
        controller.updateShop(shopId)

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    void "updateShop should throw InvalidLocationException if request.location is null or not in the Location enum"() {
        given:
        Long shopId = 100L
        String name = "Work Shop"
        String description = "A work shop"

        String location = "Fifth floor"

        request.json = [name: name, description: description, location: location, tagId: []]

        shopService.fetchShopById(shopId) >> new Shop()

        when:
        controller.updateShop(shopId)

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    void "updateShop should return LimitExceedException if tags requested are more than 5"() {
        given:
        Long shopId= 100L
        String name = "Work Shop"
        String description = "A work shop"

        Tag tag1 = new Tag(name: "first tag")
        Tag tag2 = new Tag(name: "second tag")
        Tag tag3 = new Tag(name: "third tag")
        Tag tag4 = new Tag(name: "fourth tag")
        Tag tag5 = new Tag(name: "fifth tag")
        Tag tag6 = new Tag(name: "sixth tag")

        List<Tag> tags = [tag1, tag2, tag3, tag4, tag5, tag6]
        List tagIds = [100L, 200L, 300L, 400L, 500L, 600L]

        String locationString = "First floor"
        Location location = Location.valueOfLocation(locationString)

        request.json = [name: name, description: description, location: locationString, tagId: tagIds]

        Shop shop = new Shop(name: name, description: description, location: location, tags: tags)

        shopService.fetchShopById(shopId) >> shop

        when:
        controller.updateShop(shopId)

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }


}

package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exception.InvalidDataPassed
import com.synacy.shoppingCenter.exception.NoContentFoundException
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ShopService)
@Mock([Shop, Tag])
class ShopServiceSpec extends Specification {

    TagService tagService = Mock()

    def setup() {
        service.tagService = tagService
    }

    def cleanup() {
    }

    void "test something"() {
    }

    void "fetchShopById should return the shop with the given id"(){
        given:
            Shop shop = new Shop(name: "Shop", description: "Description", location: 1, tags: [])
            shop.save()

        when:
            Shop fetchedShop = service.fetchShopById(shop.id)

        then:
            fetchedShop.id == shop.id
            fetchedShop.name == shop.name
            fetchedShop.description == shop.description
            fetchedShop.location == shop.location
            fetchedShop.tags == shop.tags
    }

    void "fetchShopById no Shop found should throw NoContentFoundException"(){
        given:
            Long shopId = 1L
            Shop.findById(shopId) >> null

        when:
            service.fetchShopById(shopId)

        then:
            NoContentFoundException exception = thrown()
    }

    void "fetchShops should return list of all shops"(){
        given:
            Shop shop1 = new Shop(name: "Shop", description: "Description", location: 1, tags: [])
            Shop shop2 = new Shop(name: "Shop1", description: "Description1", location: 1, tags: [])
            shop1.save()
            shop2.save()

        when:
            List<Shop> fetchedShops = service.fetchShops(2, 0, null)

        then:
            fetchedShops.size() == 2
            fetchedShops.contains(shop1)
            fetchedShops.contains(shop2)
    }

    void "fetchShops should return list of all shops filtered by tag id"(){
        given:
            Long tagId = 1L

            Tag tag1 = new Tag(name: "tag1")
            Tag tag2 = new Tag(name: "tag2")
            tag1.save()
            tag2.save()

            Shop shop1 = new Shop(name: "Shop", description: "Description", location: 1, tags: [tag1, tag2])
            Shop shop2 = new Shop(name: "Shop1", description: "Description1", location: 1, tags: [tag2])
            shop1.save()
            shop2.save()

            tagService.fetchTagById(tagId) >> tag1

        when:
            List<Shop> fetchedShops = service.fetchShops(2, 0, tagId)

        then:
            fetchedShops.size() == 1
            fetchedShops[0] == shop1
    }

    void "fetchShops no tag found should throw NoContentFoundException"(){
        given:
            Long tagId = 1L

            tagService.fetchTagById(tagId) >> null

        when:
            List<Shop> fetchedShops = service.fetchShops(2, 0, tagId)

        then:
            NoContentFoundException exception = thrown()
    }

    void "fetchAllShop should throw NoContentFoundException"(){
        given:
            List<Shop> shopList = null

        when:
            service.fetchShops(2,0,1L)

        then:
            NoContentFoundException exception = thrown()
    }

    void "createShop should create and return tag with the correct information"(){
        given:
            String shopName = "name"
            String shopDescription = "description"
            Integer shopLocation = 1

        when:
            Shop createdShop = service.createShop(shopName, shopDescription, shopLocation, [])

        then:
            createdShop.name == shopName
            createdShop.description == shopDescription
            createdShop.location == shopLocation
            Shop.exists(createdShop.id)
    }

    void "updateShop should update the shops's information and save it"(){
        given:
            Long shopId = 1L
            String shopName = "name"
            String shopDescription = "description"
            Integer shopLocation = 1
            Shop shop = new Shop(name: shopName, description: shopDescription, location: shopLocation, tags: [])
            shop.save()

        when:
            Shop updatedShop = service.updateShop(shopId, shopName, shopDescription,shopLocation,[])

        then:
            updatedShop.name == shopName
            updatedShop.description == shopDescription
            Shop.exists(updatedShop.id)
    }

    void "deleteShop should delete the shop with the given id"(){
        given:
            String shopName = "name"
            String shopDescription = "description"
            Integer shopLocation = 1
            Shop shop = new Shop(name: shopName, description: shopDescription, location: shopLocation, tags: [])
            service.fetchShopById(shop.id) >> shop
            shop.save()

        when:
            service.deleteShop(shop.id)

        then:
            !Shop.exists(shop.id)
    }

    void "shopTagValidator should return validated tag lists"(){
        given:
            Long id = 1L
            List<Long> idList = [id]
            Tag tag = new Tag(name: "name")
            tagService.fetchTagById(id) >> tag

        when:
            List<Tag> tagList = service.shopTagValidator(idList)

        then:
            tagList.size() == 1
    }

    void "shopTagValidator should throw InvalidDataPassed"(){
        given:
            Long id = 1L
            List<Long> idList = [id]
            tagService.fetchTagById(id) >> null

        when:
            service.shopTagValidator(idList)

        then:
            InvalidDataPassed exception = thrown()

    }

}

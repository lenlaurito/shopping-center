package com.synacy.shoppingcenter.shop

import org.springframework.http.HttpStatus

import com.synacy.shoppingcenter.exception.ResourceNotFoundException;

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ShopService)
@Mock([Shop])
class ShopServiceSpec extends Specification {

	void "fetchById should return the shop with the given id"() {
		given:
		Shop shop = new Shop(name: "Jollibee", description: "Bida ang saya.", location: "FIRST_FLOOR")
		shop.save()

		when:
		Shop fetchedShop = service.fetchShopById(shop.id)

		then:
		fetchedShop.id == shop.id
		fetchedShop.name == shop.name
		fetchedShop.description == shop.description
		fetchedShop.location == shop.location
	}
	
	void "fetchById should throw exception if id is not found"() {
		when:
		Shop fetchedShop = service.fetchShopById(100L)

		then:
		thrown ResourceNotFoundException
	}
	
	void "fetchShops should return all shops with null tag id"() {
		given:
		Long tagId = null
		Integer offset = 0
		Integer max = 3
		
		Shop shop1 = new Shop(name: "Jollibee", description: "Bida ang saya.", location: "FIRST_FLOOR").save()
		Shop shop2 = new Shop(name: "McDonalds", description: "Love ko to.", location: "SECOND_FLOOR").save()
		Shop shop3 = new Shop(name: "KFC", description: "Kapag fried chicken.", location: "FIRST_FLOOR").save()

		when:
		List<Shop> fetchedShops = service.fetchShops(tagId, offset, max)

		then:
		fetchedShops.size() == 3
		fetchedShops.get(0) == shop1
		fetchedShops.get(1) == shop2
		fetchedShops.get(2) == shop3
	}

	void "createNewShop should create and return the new shop with the correct details"() {
		given:
		def name = "Adobe Photoshop"
		def description = "I-photoshop mo"
		Location location = "SECOND_FLOOR"
		def tags = []

		when:
		Shop createdShop = service.createNewShop(name, description, location, tags)

		then:
		createdShop.name == name
		createdShop.description == description
		createdShop.location == location
		Shop.exists(createdShop.id)
	}
	
	void "updateShop should update existing shop with the specified details"() {
		given:
		Shop shopToUpdate = new Shop(name: "Jollibee", description: "Bida ang saya.", location: Location.FIRST_FLOOR, tags: [])
		shopToUpdate.save()

		def name = "Adobe Photoshop"
		def description = "I-photoshop mo"
		Location location = Location.SECOND_FLOOR
		def tags = []
		
		when:
		Shop updatedShop = service.updateShop(shopToUpdate, name, description, location, tags)

		then:
		updatedShop.name == name
		updatedShop.description == description
		updatedShop.location == location
		updatedShop.tags as List == tags
	}
	
	void "deleteShop should delete an existing shop"() {
		given:
		String name = "Jollibee"
		String description = "Bida ang saya."
		Shop shopToDelete = new Shop(name: name, description: description)

		when:
		service.deleteShop(shopToDelete)

		then:
		!Shop.exists(shopToDelete.id)
	}
	
}

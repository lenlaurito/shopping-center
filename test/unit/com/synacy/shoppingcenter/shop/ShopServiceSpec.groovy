package com.synacy.shoppingcenter.shop

import org.springframework.http.HttpStatus
import com.synacy.shoppingcenter.ResourceNotFoundException

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
	
	void "deleteShop should delete shop"() {
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

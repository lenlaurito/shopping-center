package com.synacy.shoppingcenter.shop

import org.springframework.http.HttpStatus

import spock.lang.Specification

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
	
	void "fetchShop should respond with the shop which has the specified id"() {
		given:
		Long shopId = 2L
		Shop shop = new Shop(name: "Jollibee", description: "Bida ang saya!", location: Location.FIRST_FLOOR)
		shopService.fetchShopById(shopId) >> shop

		when:
		controller.fetchShop(shopId)

		then:
		response.status == HttpStatus.OK.value()
		response.json.name == "Jollibee"
		response.json.description == "Bida ang saya!"
		response.json.location.name == Location.FIRST_FLOOR.name()
	}
	
	void "fetchShop should respond not found status is id id not found"() {
		given:
		Long shopId = 2L
		shopService.fetchShopById(shopId) >> null

		when:
		controller.fetchShop(shopId)

		then:
		response.status == HttpStatus.NOT_FOUND.value()
	}

	void "fetchAllShops with null parameters should respond with the correct shop"() {
		given:
		Shop shop1 = new Shop(id: 1L, name: "Jollibee", description: "Bida ang saya!", location: Location.FIRST_FLOOR, tags: [])
		Shop shop2 = new Shop(id: 2L, name: "McDonalds", description: "Love ko to", location: Location.FIRST_FLOOR, tags: [])
		shopService.fetchShops(null, null, null) >> [shop1, shop2]
		
		when:
		controller.fetchAllShops()
		
		then:
		response.status == HttpStatus.OK.value()
		response.json.size() == 2
		response.json.shops.find {
			it.name == "Jollibee" && it.description == "Bida ang saya!" && it.location.name == Location.FIRST_FLOOR.name()
		} != null
		response.json.shops.find {
			it.name == "McDonalds" && it.description == "Love ko to" && it.location.name == Location.FIRST_FLOOR.name()
		} != null
	}
	
	void "fetchAllShops with specified parameters should respond with the correct shop"() {
		given:
		params.tagId = "1"
		params.offset = "0"
		params.max = "3"

		Long tagId = Long.parseLong(params.tagId)
		Integer offset = Integer.parseInt(params.offset)
		Integer max = Integer.parseInt(params.max)
		Shop shop1 = new Shop(id: 1L, name: "Jollibee", description: "Bida ang saya!", location: Location.FIRST_FLOOR, tags: [])
		Shop shop2 = new Shop(id: 2L, name: "McDonalds", description: "Love ko to", location: Location.FIRST_FLOOR, tags: [])

		shopService.fetchShops(tagId, offset, max) >> [shop1, shop2]

		when:
		controller.fetchAllShops()

		then:
		response.status == HttpStatus.OK.value()
		response.json.shops.size() == 2
		response.json.shops.find {
			it.name == "Jollibee" && it.description == "Bida ang saya!" && it.location.name == Location.FIRST_FLOOR.name()
		} != null
		response.json.shops.find {
			it.name == "McDonalds" && it.description == "Love ko to" && it.location.name == Location.FIRST_FLOOR.name()
		} != null
	}

	void "createShop should respond with the newly created shop with the details specified"() {
		given:
		String name = "Jollibee"
		String description = "Bida ang saya."
		Location location = Location.FIRST_FLOOR
		def tags = []

		request.json = [name: name, description: description, location: location.name(), tags: tags]

		Shop shop = new Shop(name: name, description: description, location: location, tags: tags)

		when:
		controller.createShop()

		then:
		1 * shopService.createNewShop(name, description, location, tags) >> shop

		then:
		response.status == HttpStatus.CREATED.value()
		response.json.name == name
		response.json.description == description
		response.json.location.name == location.name()
	}
	
	void "createShop should respond a status unprocessable entity if name is null"() {
		given:
		String description = "Bida ang saya."
		def tags = []

		request.json = [description: description]

		Shop shop = new Shop()
		shop.name = null
		
		when:
		controller.createShop()

		then:
		response.status == HttpStatus.UNPROCESSABLE_ENTITY.value()
	}
	
	void "updateShop should respond with the updated shop for the given details"() {
		given:
		Long shopId = 2L
		String name = "Jollibee"
		String description = "Bida ang saya."
		Location location = Location.FIRST_FLOOR
		def tags = []

		request.json = [name: name, description: description, location: location.name(), tags: tags]

		Shop shopToUpdate = new Shop(name: name, description: description, location: location, tags: tags)
		shopService.fetchShopById(shopId) >> shopToUpdate

		when:
		controller.updateShop(shopId)

		then:
		1 * shopService.updateShop(shopToUpdate, name, description, location, tags) >> shopToUpdate

		then:
		response.status == HttpStatus.OK.value()
		response.json.name == name
		response.json.description == description
		response.json.location.name == location.name()
	}
	
	void "deleteShop should delete shop"() {
		given:
		Long shopId = 2L
		Shop shopToDelete = new Shop(name: "Jollibee", description: "Bida ang saya.", location: Location.FIRST_FLOOR)
		shopService.fetchShopById(shopId) >> shopToDelete

		when:
		controller.removeShop(shopId)
		
		then:
		response.status == HttpStatus.NO_CONTENT.value()
	}

}

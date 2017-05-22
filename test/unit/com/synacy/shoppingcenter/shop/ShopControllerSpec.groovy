package com.synacy.shoppingcenter.shop

import java.util.List;

import com.synacy.shoppingcenter.tag.Tag
import com.synacy.shoppingcenter.tag.TagService
import com.synacy.shoppingcenter.exception.InvalidRequestException;

import org.springframework.http.HttpStatus

import spock.lang.Specification

@TestFor(ShopController)
class ShopControllerSpec extends Specification {

	ShopService shopService = Mock()
	TagService tagService = Mock()

	def setup() {
		controller.shopService = shopService
		controller.tagService = tagService
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
	
	void "fetchShop should respond not found status when id not found"() {
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
		params.tagId = null
		params.offset = null
		params.max = null
		
		Long tagId = params.tagId ? Long.parseLong(params.tagId) : null
		Integer offset = params.offset ? Integer.parseInt(params.offset) : null
		Integer max = params.max ? Integer.parseInt(params.max) : null
		
		Shop shop1 = new Shop(id: 1L, name: "Jollibee", description: "Bida ang saya!", location: Location.FIRST_FLOOR, tags: [])
		Shop shop2 = new Shop(id: 2L, name: "McDonalds", description: "Love ko to", location: Location.FIRST_FLOOR, tags: [])
		
		when:
		controller.fetchAllShops()
		
		then:
		0 * tagService.fetchTagById(tagId)
		1 * shopService.fetchShops(null, null, null) >> [shop1, shop2]
		1 * shopService.fetchTotalNumberOfShops() >> 2
		
		then:
		response.status == HttpStatus.OK.value()
		response.json.totalShops == 2
		response.json.shops.find {
			it.name == "Jollibee" && it.description == "Bida ang saya!" && it.location.name == Location.FIRST_FLOOR.name() && it.tags ==[]
		} != null
		response.json.shops.find {
			it.name == "McDonalds" && it.description == "Love ko to" && it.location.name == Location.FIRST_FLOOR.name() && it.tags ==[]
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
		Tag tag = new Tag(name: "Restaurant")

		when:
		controller.fetchAllShops()

		then:
		1 * tagService.fetchTagById(tagId) >> tag
		1 * shopService.fetchShops(tag, offset, max) >> [shop1, shop2]
		1 * shopService.fetchTotalNumberOfShops() >> 2
		
		then:
		response.status == HttpStatus.OK.value()
		response.json.totalShops == 2
		response.json.shops.find {
			it.name == "Jollibee" && it.description == "Bida ang saya!" && it.location.name == Location.FIRST_FLOOR.name() && it.tags == []
		} != null
		response.json.shops.find {
			it.name == "McDonalds" && it.description == "Love ko to" && it.location.name == Location.FIRST_FLOOR.name() && it.tags == []
		} != null
	}
	
	void "createShop with tags exceeding the limit (5) should throw InvalidRequestException and respond with HTTP Unprocessable Entity status"() {
		given:
		String name = "Jollibee"
		String description = "Bida ang saya."
		Location location = Location.FIRST_FLOOR
		def tagIds = [1, 2, 3, 4, 5, 6]
		
		request.json = [name: name, description: description, location: location.name(), tags: tagIds]

		when:
		controller.createShop()
		controller.validateTags(tagIds)

		then:
		thrown InvalidRequestException
		response.status == HttpStatus.UNPROCESSABLE_ENTITY.value()
	}
	
	void "createShop should respond with the newly created shop with the details specified"() {
		given:
		String name = "Jollibee"
		String description = "Bida ang saya."
		Location location = Location.FIRST_FLOOR
		def tagIds = [1, 2]
		
		Tag tag1 = new Tag(name: "Restaurant")
		Tag tag2 = new Tag(name: "Specialty")
		List<Tag> tags = [tag1, tag2]

		request.json = [name: name, description: description, location: location.name(), tags: tagIds]

		Shop shop = new Shop(name: name, description: description, location: location, tags: tags)

		when:
		controller.createShop()

		then:
		1 * tagService.fetchTagById(1) >> tag1
		1 * tagService.fetchTagById(2) >> tag2
		1 * shopService.createNewShop(name, description, location, tags) >> shop

		then:
		response.status == HttpStatus.CREATED.value()
		response.json.name == name
		response.json.description == description
		response.json.location.name == location.name()
		response.json.tags.find {
			it.name == "Specialty"
		} != null
		response.json.tags.find {
			it.name == "Restaurant"
		} != null
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
		def tagIds = [1, 2]
		
		Tag tag1 = new Tag(name: "Restaurant")
		Tag tag2 = new Tag(name: "Specialty")
		List<Tag> tags = [tag1, tag2]

		request.json = [name: name, description: description, location: location.name(), tags: tagIds]

		Shop shopToUpdate = new Shop(name: name, description: description, location: location, tags: tags)
		shopService.fetchShopById(shopId) >> shopToUpdate

		when:
		controller.updateShop(shopId)

		then:
		1 * tagService.fetchTagById(1) >> tag1
		1 * tagService.fetchTagById(2) >> tag2
		1 * shopService.updateShop(shopToUpdate, name, description, location, tags) >> shopToUpdate

		then:
		response.status == HttpStatus.OK.value()
		response.json.name == name
		response.json.description == description
		response.json.location.name == location.name()
		response.json.tags.find {
			it.name == "Specialty"
		} != null
		response.json.tags.find {
			it.name == "Restaurant"
		} != null
	}
	
	void "deleteShop should respond with HTTP No Content status"() {
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

package com.synacy.shoppingcenter.shop

import com.synacy.shoppingcenter.shop.ShopService
import com.synacy.shoppingcenter.ResourceNotFoundException
import com.synacy.shoppingcenter.InvalidRequestException

import org.springframework.http.HttpStatus

class ShopController {

	static responseFormats = ['json']

	ShopService shopService
	
	def fetchShopId(Long shopId) {
		Shop shop = shopService.fetchShopById(shopId)
		respond(shop)
	}

	def fetchAllShops() {
		Integer tagId = params.tagId ? Integer.parseInt(params.tagId) : null
		Integer offset = params.offset ? Integer.parseInt(params.offset) : null
		Integer max = params.max ? Integer.parseInt(params.max) : null
		
		List<Shop> shops = shopService.fetchShops(tagId, offset, max)
		Integer shopCount = shopService.fetchTotalNumberOfShops()
		Map<String, Object> paginatedShopDetails = [totalShops: shopCount, shops: shops]
		respond(paginatedShopDetails)
	}
	
	def createShop() {
		String name = request.JSON.name ?: null
		String description = request.JSON.description ?: null
		String locationString = request.JSON.location ?: null
		List tags = request.JSON.tags

		Location location = Location.valueOfLocation(locationString)
		
		// test not in enum location
		
		if (!name || !location) {
			throw new InvalidRequestException("Invalid request.")
		}

		Shop shop = shopService.createNewShop(name, description, location, tags)
		
		respond(shop, [status: HttpStatus.CREATED])
	}

	def updateShop(Long shopId) {
		String name = request.JSON.name ?: null
		String description = request.JSON.description ?: null
		String locationString = request.JSON.location ?: null
		List tags = request.JSON.tags
		
		Location location = Location.valueOfLocation(locationString)

		if (!name) {
			throw new InvalidRequestException("Invalid request.")
		}
		
		Shop shop = shopService.fetchShopById(shopId)
		shop = shopService.updateShop(shop, name, description, location, tags)
		respond(shop)
	}

	def removeShop(Long shopId) {
		Shop shop = shopService.fetchShopById(shopId)
		shopService.deleteShop(shop)

		render(status: HttpStatus.NO_CONTENT)
	}

	def handleResourceNotFoundException(ResourceNotFoundException e) {
		response.status = HttpStatus.NOT_FOUND.value()
		respond([error: e.getMessage()])
	}

	def handleInvalidRequestException(InvalidRequestException e) {
		response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
		respond([error: e.getMessage()])
	}
	
}

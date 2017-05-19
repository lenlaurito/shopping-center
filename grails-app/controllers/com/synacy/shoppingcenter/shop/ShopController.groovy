package com.synacy.shoppingcenter.shop

import com.synacy.shoppingcenter.exception.*;
import com.synacy.shoppingcenter.exception.handler.DatabaseExceptionHandler;
import com.synacy.shoppingcenter.exception.handler.ResourceExceptionHandler
import com.synacy.shoppingcenter.shop.ShopService

import org.springframework.http.HttpStatus

class ShopController implements DatabaseExceptionHandler, ResourceExceptionHandler {

	static responseFormats = ['json']

	ShopService shopService
	
	def fetchShop(Long shopId) {
		Shop shop = shopService.fetchShopById(shopId)
		respond(shop)
	}

	def fetchAllShops() {
		Long tagId = params.tagId ? Long.parseLong(params.tagId) : null
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
		
		if (!name || !location) {
			throw new InvalidRequestException("Request is invalid.")
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
		
		if (!name || !location) {
			throw new InvalidRequestException("Request is invalid.")
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
	
}

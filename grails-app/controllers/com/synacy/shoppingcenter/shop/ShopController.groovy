package com.synacy.shoppingcenter.shop

import com.synacy.shoppingcenter.exception.*
import com.synacy.shoppingcenter.exception.handler.RestExceptionHandler
import com.synacy.shoppingcenter.tag.Tag
import com.synacy.shoppingcenter.tag.TagService

import org.springframework.http.HttpStatus

class ShopController implements RestExceptionHandler {

	static responseFormats = ['json']
	static final SHOP_TAGS_LIMIT = 5;

	ShopService shopService
	TagService tagService

	def fetchShop(Long shopId) {
		if (!shopId) {
			throw new InvalidRequestException("Unable to parse id.")
		}
		Shop shop = shopService.fetchShopById(shopId)
		respond(shop)
	}

	def fetchAllShops() {
		Long tagId
		Integer offset, max
		try {
			tagId = params.tagId ? Long.parseLong(params.tagId) : null
			offset = params.offset ? Integer.parseInt(params.offset) : null
			max = params.max ? Integer.parseInt(params.max) : null
		} catch (NumberFormatException e) {
			throw new InvalidRequestException("Unable to parse params.")
		}
		Tag tag = (tagId) ? tagService.fetchTagById(tagId) : null
		List<Shop> shops = shopService.fetchShops(tag, offset, max)
		Integer shopCount = shopService.fetchTotalNumberOfShops()
		Map<String, Object> paginatedShopDetails = [totalShops: shopCount, shops: shops]
		respond(paginatedShopDetails)
	}

	def createShop() {
		String name = request.JSON.name ?: null
		String description = request.JSON.description ?: null
		List tagIds = request.JSON.tags

		if (!name) {
			throw new InvalidRequestException("Missing required parameter.")
		}
		Location location = validateLocation(request)
		List<Tag> tags = validateTags(tagIds)
		Shop shop = shopService.createNewShop(name, description, location, tags)
		respond(shop, [status: HttpStatus.CREATED])
	}

	def updateShop(Long shopId) {
		if (!shopId) {
			throw new InvalidRequestException("Unable to parse id.")
		}
		String name = request.JSON.name ?: null
		String description = request.JSON.description ?: null
		List tagIds = request.JSON.tags

		if (!name) {
			throw new InvalidRequestException("Missing required parameter.")
		}
		Location location = validateLocation(request)
		List<Tag> tags = validateTags(tagIds)
		Shop shop = shopService.fetchShopById(shopId)
		shop = shopService.updateShop(shop, name, description, location, tags)
		respond(shop)
	}

	def removeShop(Long shopId) {
		if (!shopId) {
			throw new InvalidRequestException("Unable to parse id.")
		}
		Shop shop = shopService.fetchShopById(shopId)
		shopService.deleteShop(shop)
		render(status: HttpStatus.NO_CONTENT)
	}
	
	private Location validateLocation(javax.servlet.http.HttpServletRequest request) {
		Location location
		if (request.JSON.location) {
			String locationString = request.JSON.location
			location = Location.valueOfLocation(locationString)
			if (!location) {
				throw new InvalidRequestException("Location is invalid. Refer to list of possible values.")
			}
		} else{
			throw new InvalidRequestException("Missing required parameter.")
		}
		return location
	}

	private List<Tag> validateTags(List tagIds) {
		List<Tag> tags = []
		if (tagIds) {
			if (tagIds.size() > SHOP_TAGS_LIMIT) {
				throw new InvalidRequestException("Could not tag a shop more than '${SHOP_TAGS_LIMIT}' times.")
			}
			tagIds.each {
				try {
					Long tagId = it
					Tag tag = tagService.fetchTagById(tagId)
					tags.add(tag)
				} catch (ClassCastException cce) {
					throw new InvalidRequestException("Tag with id '${it}' cannot be parsed. Tag ids should be a number.")
				}
			}
		}
		return tags
	}

}

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
		Shop shop = shopService.fetchShopById(shopId)
		respond(shop)
	}

	def fetchAllShops() {
		Long tagId = params.tagId ? Long.parseLong(params.tagId) : null
		Integer offset = params.offset ? Integer.parseInt(params.offset) : null
		Integer max = params.max ? Integer.parseInt(params.max) : null

		Tag tag;
		if (tagId) {
			tag = tagService.fetchTagById(tagId)
		}
		List<Shop> shops = shopService.fetchShops(tag?:null, offset, max)
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
		List<Tag> tags = validateTags(tagIds)
		Shop shop = shopService.createNewShop(name, description, location, tags)
		respond(shop, [status: HttpStatus.CREATED])
	}

	def updateShop(Long shopId) {
		String name = request.JSON.name ?: null
		String description = request.JSON.description ?: null
		String locationString = request.JSON.location ?: null
		List tagIds = request.JSON.tags

		Location location = Location.valueOfLocation(locationString)

		if (!name) {
			throw new InvalidRequestException("Invalid request.")
		}

		List<Tag> tags = validateTags(tagIds)
		Shop shop = shopService.fetchShopById(shopId)
		shop = shopService.updateShop(shop, name, description, location, tags)
		respond(shop)
	}

	def removeShop(Long shopId) {
		Shop shop = shopService.fetchShopById(shopId)
		shopService.deleteShop(shop)

		render(status: HttpStatus.NO_CONTENT)
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
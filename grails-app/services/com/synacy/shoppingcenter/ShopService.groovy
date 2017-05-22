package com.synacy.shoppingcenter

import grails.transaction.Transactional
import org.hibernate.criterion.CriteriaSpecification

@Transactional
class ShopService {
    public static DEFAULT_PAGINATION_OFFSET = 0
    public static DEFAULT_PAGINATION_MAX = 20

    TagService tagService

    public List<Shop> fetchAllShops(Integer offset, Integer max, String tagName) {
        return Shop.findAll().find {
            it.tags.find {
                it.id == Long.parseLong(tagName)
            }
        }
        /*
        def matches = Shop.withCriteria() {
            createAlias('tags', 't' , CriteriaSpecification.LEFT_JOIN)
            like("t.name", "%"+ tagName +"%")
            order("name", "asc")
            firstResult offset
            maxResults max
        }

        def resultSet = new HashSet()
        resultSet.addAll(matches)

        return new ArrayList<>(resultSet)
        */
    }

    public Integer fetchTotalNumberOfShops() {
        return Shop.count()
    }

    public Shop createShop(String name, String description, List<Long> tags, List<Location> locations) {
        Shop shop = new Shop(name: name, description: description)

        shop.tags = []
        shop.locations = []

        tags?.each {
            shop.addToTags(tagService.fetchTagById(it))
        }

        locations?.each {
            def location = Location.valueOfLocation(it)
            if (location)
                shop.addToLocations(location)
            else
                throw new InvalidRequestException("invalid location: " + it)
        }

        return shop.save()
    }

    public Shop fetchShopById(Long shopId) {
        Shop shop = Shop.findById(shopId)

        if (!shop) {
            throw new ResourceNotFoundException("Shop not found")
        }

        return shop
    }

    public Shop updateShop(Long shopId, String name, String description, List<Long> tags, List<Location> locations) {
        Shop shop = fetchShopById(shopId)

        shop.name = name
        shop.description = description

        shop.tags = []
        shop.locations = []

        tags?.each {
            shop.addToTags(tagService.fetchTagById(it))
        }

        locations?.each {
            def location = Location.valueOfLocation(it)
            if (location)
                shop.addToLocations(location)
            else
                throw new InvalidRequestException("invalid location: " + it)
        }

        return shop.save()
    }

    public void deleteShopById(Long shopId)  {
        Shop shop = fetchShopById(shopId)

        shop.delete()
    }
}

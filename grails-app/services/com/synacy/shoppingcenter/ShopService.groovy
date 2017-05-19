package com.synacy.shoppingcenter

import grails.transaction.Transactional
import org.hibernate.criterion.CriteriaSpecification

@Transactional
class ShopService {
    public static DEFAULT_PAGINATION_OFFSET = 0
    public static DEFAULT_PAGINATION_MAX = 20

    TagService tagService

    public List<Shop> fetchAllShops(Integer offset, Integer max, String tagName) {
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
    }

    public Integer fetchTotalNumberOfShops() {
        return Shop.count()
    }

    public Shop createNewShop(String name, String description, List<Long> tags, List<Location> locations) {
        Shop shop = new Shop()

        shop.setName(name)
        shop.setDescription(description)
        shop.locations = []

        locations.each {
            shop.addToLocations(it)
        }


        StringBuilder builder = new StringBuilder()

        tags.each {
            try {
                shop.addToTags(tagService.fetchTagById(it))
            } catch(ResourceNotFoundException e) {
                builder.append(e.getMessage()).append("\n")
            }
        }

        if (builder.length() > 0)
            throw new InvalidRequestException(builder.toString())

        if (shop.tags && shop.tags.size() > 5)
            throw new InvalidRequestException("maximum tags limit reached")

        return shop.save()
    }

    public Shop fetchShopById(Long shopId) {
        Shop shop = Shop.findById(shopId)

        if (!shop) {
            throw new ResourceNotFoundException("Shop not found")
        }

        return shop
    }

    public Shop updateShop(Long shopId, String name, String description, List<Long> tags) {
        Shop shop = fetchShopById(shopId)

        shop.setName(name)
        shop.setDescription(description)

        shop.tags = []

        StringBuilder builder = new StringBuilder()

        tags.each {
            try {
                shop.addToTags(tagService.fetchTagById(it))
            } catch(ResourceNotFoundException e) {
                builder.append(e.getMessage()).append("\n")
            }
        }

        if (builder.length() > 0)
            throw new InvalidRequestException(builder.toString())

        if (shop.tags.size() > 5)
            throw new InvalidRequestException("maximum tags limit reached");

        return shop.save()
    }

    public void deleteShopById(Long shopId)  {
        Shop shop = fetchShopById(shopId)

        shop.delete()
    }
}

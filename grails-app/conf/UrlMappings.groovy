class UrlMappings {

	static mappings = {
        //Tag
        "/api/v1/tag"(controller: "tag") {
            action = [GET: "fetchAllTags", POST: "createTag"]
        }
        "/api/v1/tag/${tagId}"(controller: "tag") {
            action = [GET: "fetchTag", PUT: "updateTag", DELETE: "removeTag"]
        }

        //Shop
        "/api/v1/shop"(controller: "shop") {
            action = [GET: "fetchAllShops", POST: "createShop"]
        }
        "/api/v1/shop/${shopId}"(controller: "shop") {
            action = [GET: "fetchShopId", PUT: "updateShop", DELETE: "removeShop"]
        }

        "/"(view: "/index")
        "500"(view: '/error')
	}

}

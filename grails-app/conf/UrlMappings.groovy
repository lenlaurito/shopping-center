class UrlMappings {

	static mappings = {
        "/api/v1/shop"(controller: "shop") {
            action = [GET: "fetchAllShops", POST: "createShop"]
        }
        "/api/v1/shop/${shopId}"(controller: "shop") {
            action = [GET: "viewShop", PUT: "updateShop", DELETE: "deleteShop"]
        }
        "/api/v1/tag"(controller: "tag") {
            action = [GET: "fetchAllTags", POST: "createTag"]
        }
        "/api/v1/tag/${tagId}"(controller: "tag") {
            action = [GET: "viewTag", PUT: "updateTag", DELETE: "deleteTag"]
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}

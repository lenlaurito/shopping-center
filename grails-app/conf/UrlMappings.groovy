class UrlMappings {

	static mappings = {
        "/api/v1/shop"(controller: "shop") {
            action = [GET: "fetchAllShops", POST: "createShop"]
        }
        "/api/v1/shop/${shopId}"(controller: "shop") {
            action = [GET: "viewShop", PUT: "updateShop", DELETE: "deleteShop"]
        }
        "/api/v1/tag"(controller: "tag") {
            action = [GET: "index", POST: "create"]
        }
        "/api/v1/tag/${tagId}"(controller: "tag") {
            action = [GET: "view", PUT: "update", DELETE: "delete"]
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}

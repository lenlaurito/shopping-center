class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        //Tag
        "/api/v1/tag"(controller: "tag") {
            action = [GET: "fetchAllTag", POST: "createTag"]
        }
        "/api/v1/tag/${tagId}"(controller: "tag") {
            action = [GET: "fetchTag", PUT: "updateTag", DELETE: "removeTag"]
        }

        //Shop
        "/api/v1/shop"(controller: "shop") {
            action = [GET: "fetchAllShop", POST: "createShop"]
        }
        "/api/v1/shop/${shopId}"(controller: "shop") {
            action = [GET: "fetchShop", PUT: "updateShop", DELETE: "removeShop"]
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}

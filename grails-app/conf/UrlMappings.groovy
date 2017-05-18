class UrlMappings {

	static mappings = {
     //    "/$controller/$action?/$id?(.$format)?"{
     //        constraints {
     //            // apply constraints here
     //        }
     //    }

	// SHOP
	"/api/v1/shop"(controller: "shop") {
		action = [GET: "fetchShops", POST:"createShop"]
	}
	"/api/v1/shop/${shopId}"(controller: "shop") {
		action = [GET: "fetchShop", PUT: "updateShop", DELETE: "deleteShop"]
	}

	//TAG
	"/api/v1/tag"(controller: "tag") {
		action = [GET: "fetchTags", POST:"createTag"]
	}
	"/api/v1/tag/${tagId}"(controller: "tag") {
		action = [GET: "fetchTag", PUT: "updateTag", DELETE: "deleteTag"]
	}

        "/"(view:"/index")
        "500"(view:'/error')
	}
}

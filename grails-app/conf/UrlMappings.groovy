class UrlMappings {

	static mappings = {
        "/api/v1/shop"(controller: "shop") {
            action = [GET: "index", POST: "create"]
        }
        "/api/v1/shop/${shopId}"(controller: "shop") {
            action = [GET: "view", PUT: "update", DELETE: "delete"]
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}

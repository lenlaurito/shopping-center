package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus

class TagController implements ErrorHandlingTrait {

    static responseFormats = ['json']

    TagService tagService;

    def index() {
        List<Tag> tags = tagService.fetchAllTags()

        respond(tags)
    }

    def create() {
        String name = request.JSON.name ?: null

        respond(tagService.createNewTag(name))
    }

    def view(Long tagId) {
        respond(tagService.fetchTagById(tagId))
    }

    def update(Long tagId) {
        String name = request.JSON.name ?: null

        respond(tagService.updateTag(tagId, name))
    }

    def delete(Long tagId) {
        tagService.deleteTag(tagId)


        render(status: HttpStatus.NO_CONTENT)
    }
}

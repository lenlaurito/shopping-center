package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus

class TagController implements ErrorHandlingTrait {

    static responseFormats = ['json']

    TagService tagService

    def fetchAllTags() {
        List<Tag> tags = tagService.fetchAllTags()

        respond(tags)
    }

    def createTag() {
        String name = request.JSON.name ?: null

        respond(tagService.createNewTag(name), [status: HttpStatus.CREATED])
    }

    def viewTag(Long tagId) {
        respond(tagService.fetchTagById(tagId))
    }

    def updateTag(Long tagId) {
        String name = request.JSON.name ?: null

        respond(tagService.updateTag(tagId, name))
    }

    def deleteTag(Long tagId) {

        tagService.deleteTagById(tagId)

        render(status: HttpStatus.NO_CONTENT)
    }
}

package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus
import org.springframework.dao.DataIntegrityViolationException

class TagController implements ErrorHandlingTrait {

    static responseFormats = ['json']

    TagService tagService;

    def index() {
        List<Tag> tags = tagService.fetchAllTags()

        respond(tags)
    }

    def create() {
        String name = request.JSON.name ?: null

        respond(tagService.createNewTag(name), [status: HttpStatus.CREATED])
    }

    def view(Long tagId) {
        respond(tagService.fetchTagById(tagId))
    }

    def update(Long tagId) {
        String name = request.JSON.name ?: null

        respond(tagService.updateTag(tagId, name))
    }

    def delete(Long tagId) {
        try {
            tagService.deleteTagById(tagId)
        } catch (DataIntegrityViolationException e) {
            throw new InvalidRequestException("Referential integrity error. This tag is still being used by shops")
        }

        render(status: HttpStatus.NO_CONTENT)
    }
}

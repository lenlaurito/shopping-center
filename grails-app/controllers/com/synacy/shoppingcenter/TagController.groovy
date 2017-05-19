package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus
import com.synacy.shoppingcenter.ExceptionHandlingTrait

import com.synacy.shoppingcenter.exceptions.*


class TagController implements ExceptionHandlingTrait {

    TagService tagService

    static responseFormats = ['json']

    def fetchAllTags() {
        List<Tag> tags = tagService.fetchAllTags()

        respond(tags)
    }

    def fetchTag(Long tagId) {
        Tag tag = tagService.fetchTagById(tagId)

        if(tag == null) {
            throw new ResourceNotFoundException("Tag not found.")
        }

        respond(tag)
    }

    def createTag() {
        String name = request.JSON.name ?: null

        Tag tag = tagService.createNewTag(name)
        respond(tag, [status: HttpStatus.CREATED])
    }

    def updateTag(Long tagId) {
        String name = request.JSON.name ?: null

        Tag tag = tagService.fetchTagById(tagId)

        if(tag == null) {
            throw new ResourceNotFoundException("Tag not found.")
        }

        tag = tagService.updateTag(tag, name)
        respond(tag)
    }

    def removeTag(Long tagId) {
        Tag tag = tagService.fetchTagById(tagId)

        if(tag == null) {
            return render([status: HttpStatus.NO_CONTENT])
        }

        if(!tag.shops.isEmpty()) {
            throw new ExistingResourceException("Problem deleting tag with an id of " + tag.id + ". Tag is still used by a shop.")
        }

        tagService.deleteTag(tag)
        render([status: HttpStatus.NO_CONTENT])
    }


}

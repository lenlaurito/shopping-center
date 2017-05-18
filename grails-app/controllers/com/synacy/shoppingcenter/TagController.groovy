package com.synacy.shoppingcenter

import com.synacy.shoppingcenter.exception.NoContentException
import com.synacy.shoppingcenter.exception.InvalidFieldException
import com.synacy.shoppingcenter.exception.InvalidUriException
import com.synacy.shoppingcenter.trait.ErrorHandler
import org.springframework.http.HttpStatus

class TagController implements ErrorHandler{

    static responseFormats = ['json']

    TagService tagService

    def fetchTags() {

        List<Tag> tags = tagService.fetchAllTags()

        if(!tags) {
            throw new NoContentException("No tags exist.")
        }else {
            respond(tags)
        }
    }

    def fetchTag(Long tagId) {

        Tag tag = tagService.fetchTagById(tagId)

        if(!tag) {
             throw new NoContentException("This tag does not exist.")
        }else {
             respond(tag)
        }
    }

    def createTag() {

         String name = request.JSON.name ?: null

         if(!name) {
             throw new InvalidFieldException("Missing or invalid input.")
         }else {
             Tag tag = tagService.createTag(name)
             respond(tag, [status: HttpStatus.CREATED])
         }
    }

    def updateTag(Long tagId) {

        if(!tagId) {
            throw new InvalidUriException("Missing information in the URI.")
        } else {
            String name = request.JSON.name ?: null

            if(!name) {
                throw new InvalidFieldException("Missing or invalid input.")
                }else {
                    Tag tag = tagService.fetchTagById(tagId)
                    if(!tag) {
                        throw new NoContentException("This tag does not exist.")
                    }else {
                        tag = tagService.updateTag(tag, name)
                        respond(tag)
                    }
                }
        }
    }

    def deleteTag(Long tagId) {

         Tag tag = tagService.fetchTagById(tagId)
         if(!tag) {
             throw new NoContentException("This tag does not exist.")
         }else {
             tagService.deleteTag(tag)
             render(status: HttpStatus.NO_CONTENT)
         }
    }
}

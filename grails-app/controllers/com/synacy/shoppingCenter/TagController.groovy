package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exception.EntityAlreadyExistsException
import com.synacy.shoppingCenter.trait.ExceptionHandlerTrait
import org.springframework.http.HttpStatus

class TagController implements ExceptionHandlerTrait{

    static responseFormats = ['json']

    TagService tagService

    def index() { }

    def fetchTag(Long tagId){
        respond(tagService.fetchTagById(tagId))
    }

    def fetchAllTag(){
        respond(tagService.fetchAllTag())
    }

    def createTag(){
        String name = request.JSON.name ?: null
        Tag tag = tagService.createTag(name)
        respond(tag)
    }

    def updateTag(Long tagId){
        String name = request.JSON.name ?: null
        Tag tag = tagService.updateTag(tagId,name)
        respond(tag)
    }

    def removeTag(Long tagId){
        tagService.deleteTag(tagId)
        render(status: HttpStatus.NO_CONTENT)
    }

}

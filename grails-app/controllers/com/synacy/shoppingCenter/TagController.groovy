package com.synacy.shoppingCenter

import org.springframework.http.HttpStatus

class TagController {

    static responseFormats = ['json']

    TagService tagService

    def index() { }

    def fetchTag(Long tagId){
        Tag tag = tagService.fetchTagById(tagId)
        respond(tag)
    }

    def fetchAllTag(){
        List<Tag> tagList = tagService.fetchAllTag()
        respond(tagList)
    }

    def createTag(){
        String name = request.JSON.name ?: null
        Tag tag = tagService.createTag(name)
        respond(tag, [status: HttpStatus.CREATED])
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

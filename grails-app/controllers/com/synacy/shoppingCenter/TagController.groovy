package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exception.InvalidDataPassed
import com.synacy.shoppingCenter.exception.NoContentFoundException
import com.synacy.shoppingCenter.trait.ExceptionHandlerTrait
import org.springframework.http.HttpStatus

class TagController implements ExceptionHandlerTrait{

    static responseFormats = ['json']

    TagServiceImpl tagServiceImpl

    def fetchTag(Long tagId){
        Tag tag = tagServiceImpl.fetchTagById(tagId)
        if(!tag){ throw new NoContentFoundException("No Tag with the Id found")}
        respond(tag)
    }

    def fetchAllTag(){
        respond(tagServiceImpl.fetchAllTag())
    }

    def createTag(){
        String name = request.JSON.name ?: null
        if(!name) { throw new InvalidDataPassed("Tag name in not nullable")}

        Tag tag = tagServiceImpl.createTag(name)
        respond(tag, status: HttpStatus.CREATED)
    }

    def updateTag(Long tagId){
        String name = request.JSON.name ?: null
        if(!name) { throw new InvalidDataPassed("Tag name in not nullable")}

        Tag tag = tagServiceImpl.updateTag(tagId,name)
        respond(tag)
    }

    def removeTag(Long tagId){
        tagServiceImpl.deleteTag(tagId)
        render(status: HttpStatus.NO_CONTENT)
    }

}

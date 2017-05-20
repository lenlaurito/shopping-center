package com.synacy.shoppingCenter

import grails.test.mixin.TestFor
import org.springframework.http.HttpStatus
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TagController)
class TagControllerSpec extends Specification {

    TagServiceImpl tagServiceImpl = Mock()

    def setup() {
        controller.tagService = tagServiceImpl
    }

    void "fetchTag should respond with the Tag with the given id"(){
        given:
            Long tagId = 1L
            Tag tag = new Tag(name: "Jewelry")
            tagServiceImpl.fetchTagById(tagId) >> tag

        when:
            controller.fetchTag(tagId)

        then:
            response.status == HttpStatus.OK.value()
            response.json.name == "Jewelry"
    }

    void "fetchAllTag should respond with all the Tags"(){
        given:
            Tag tag1 = new Tag(name: "Jewelry")
            Tag tag2 = new Tag(name: "Clothes")
            tagServiceImpl.fetchAllTag() >> [tag1, tag2]

        when:
            controller.fetchAllTag()

        then:
            response.status == HttpStatus.OK.value()
            response.json.size() == 2
            response.json.find{it.name == "Jewelry"} != null
            response.json.find{it.name == "Clothes"} != null
    }

    void "createTag should respond with the newly created Tag and its details"(){
        given:
            String tagName = "Jewelry"
            request.json = [name: tagName]
            Tag tag = new Tag(name: tagName)
            tagServiceImpl.createTag(tagName) >> tag

        when:
            controller.createTag()

        then:
            response.status == HttpStatus.CREATED.value()
            response.json.name == tag.name
    }

    void "createTag name is null should throw InvalidDataPassed Exception"(){
        given:
            request.json = [name: null]

        when:
            controller.createTag()

        then:
            response.status == HttpStatus.NOT_ACCEPTABLE.value()
    }

    void "updateTag should respond with the update Tag and its details"(){
        given:
            Long tagId = 1L
            String tagName = "Jewelry"
            request.json = [name: tagName]
            Tag tag = new Tag(name: tagName)
            tagServiceImpl.updateTag(tagId, tagName) >> tag

        when:
            controller.updateTag(tagId)

        then:
            response.status == HttpStatus.OK.value()
            response.json.name == tag.name
    }

    void "updateTag name is null should throw InvalidDataPassed Exception"(){
        given:
            request.json = [name: null]

        when:
            controller.updateTag(1L)

        then:
            response.status == HttpStatus.NOT_ACCEPTABLE.value()
    }

    void "removeTag should remove the Tag with the given tagId"(){
        when:
            controller.removeTag(1L)

        then:
            response.status == HttpStatus.NO_CONTENT.value()
    }
}

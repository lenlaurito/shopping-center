package com.synacy.shoppingCenter

import grails.test.mixin.TestFor
import org.springframework.http.HttpStatus
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TagController)
class TagControllerSpec extends Specification {

    TagService tagService = Mock()

    def setup() {
        controller.tagService = tagService
    }

    def cleanup() {
    }

    void "test something"() {
    }

//    void "fetchTag should respond with the Tag with the given id"(){
//        given:
//            Long tagId = 1L
//            Tag tag = new Tag(name: "Jewelry")
//            tagService.fetchTagById(tagId) >> tag
//
//        when:
//            controller.fetchTag(tagId)
//
//        then:
//            response.status == HttpStatus.OK.value()
//            response.json.name == "Jewelry"
//    }
//
//    void "fetchAllTag should respond with all the Tags"(){
//        given:
//            Tag tag1 = new Tag(name: "Jewelry")
//            Tag tag2 = new Tag(name: "Clothes")
//            tagService.fetchAllTag() >> [tag1, tag2]
//
//        when:
//            controller.fetchAllTag()
//
//        then:
//            response.status == HttpStatus.OK.value()
//            response.json.size() == 2
//            response.json.find{it.name == "Jewelry"} != null
//            response.json.find{it.name == "Clothes"} != null
//    }
//
//    void "createTag should respond with the newly created Tag and its details"(){
//        given:
//            String tagName = "Jewelry"
//            request.json = [name: tagName]
//            Tag tag = new Tag(name: tagName)
//
//        when:
//            controller.createTag()
//
//        then:
//            response.status == HttpStatus.CREATED.value()
//            response.json.name == tag.name
//    }
//
//    void "updateTag should respond with the update Tag and its details"(){
//        given:
//            Long tagId = 1L
//            request.json = [name: "Jewelry"]
//            Tag tag = new Tag(name: "Jewelry")
//            tagService.updateTag(tagId,"Jewelry") >> tag
//
//        when:
//            controller.updateTag(tagId)
//
//        then:
//            response.status == HttpStatus.OK.value()
//            response.json.name == name
//    }
//
//    void "removeTag should remove the Tag with the given tagId"(){
//        given:
//            Long tagId = 1L
//        when:
//            controller.removeTag(tagId)
//
//        then:
//            response.status == HttpStatus.NO_CONTENT.value()
//    }
}

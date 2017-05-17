package com.synacy.shoppingCenter

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TagController)
class TagControllerSpec extends Specification {

    TagService tagService = Mock()

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    void "fetchTag should respond with the Tag with the given id"(){
        given:
            Long tagId = 1L;
            Tag tag = new Tag(name: "Jewelry")
            tagService.fetchTagById(tagId) >> tag

        when:
            controller.fetchTag(tagId)

        then:
            reponse.status == HttpStatus.OK.value()
            response.json.name == "Jewelry"
    }

    void "fetchAllTag should respond with all the Tags"(){
        given:
            Tag tag1 = new Tag(name: "Jewelry")
            Tag tag2 = new Tag(name: "Clothes")
            tagService.fetchAllTag() >> [tag1, tag2]

        when:
            controller.fetchAllTag()

        then:
            reponse.status == HttpStatus.OK.value()
            response.json.size() == 2
            reponse.json.find{it.name == "Jewelry"} != null
            reponse.json.find{it.name == "Clothes"} != null
    }

    void "createTag should respond with the newly created Tag and its details"(){
        String name = "Jewelry"
        given:
            request.json = [name: name]
            Tag tag = new Tag(name: name)

        when:
            controller.createTag()

        then:
            response.status == HttpStatus.CREATED.value()
            response.json.name == name
    }

    void "updateTag should respond with the update Tag and its details"(){
        given:
            Long tagId = 1L
            String name = "Jewelry"
            request.json = [name: name]
            Tag tag = new Tag()
            tagService.fetchTagById(tagId) >> tag


        when:
            controller.updateTag(tagId)

        then:
            reponse.status == HttpStatus.OK.value()
            response.name == name
    }

    void "removeTag should remove the Tag with the given tagId"(){
        given:
            Long tagId = 1L
            Tag tag = new Tag(name: "Jewelry")
            tagService.fetchTagById(tagId) >> tag

        when:
            controller.removeTag(tagId)

        then:
            reponse.status == HttpStatus.NO_CONTENT.value()
    }
}

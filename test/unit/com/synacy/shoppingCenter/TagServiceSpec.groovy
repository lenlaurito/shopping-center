package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exception.EntityAlreadyExistsException
import com.synacy.shoppingCenter.exception.NoContentFoundException
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TagService)
@Mock([Tag])
class TagServiceSpec extends Specification {

    void "fetchTagById should return the tag with the given id"(){
        given:
            Tag tag = new Tag(name: "Jewelry")
            tag.save()

        when:
            Tag fetchedTag = service.fetchTagById(tag.id)

        then:
            fetchedTag.id == tag.id
            fetchedTag.name == tag.name
    }

    void "fetchTagById no tag found should throw NoContentFoundException"(){
        given:
            Long id = 1L

        when:
            service.fetchTagById(id)

        then:
            NoContentFoundException exception = thrown()
    }

    void "fetchAllTag should return list of all the tags"(){
        given:
            Tag tag1 = new Tag(name: "Jewelry")
            tag1.save()
            Tag tag2 = new Tag(name: "Clothes")
            tag2.save()

        when:
            List<Tag> fetchedTags = service.fetchAllTag()

        then:
            fetchedTags.size() == 2
            fetchedTags.contains(tag1)
            fetchedTags.contains(tag2)
    }

    void "fetchAllTag no tags found should throw NoContentFoundException"(){
        when:
            service.fetchAllTag()

        then:
            NoContentFoundException exception = thrown()
    }

    void "createTag should create and return new tag with the correct information"(){
        given:
            String tagName = "Jewelry"
            Tag.findByName(tagName) >> null

        when:
            Tag createdTag = service.createTag(tagName)

        then:
            createdTag.name == tagName
            Tag.exists(createdTag.id)
    }

    void "createTag should throw exception when tag name already exists"(){
        given:
            String tagName = "Jewelry"
            Tag tag = new Tag(name: tagName)
            tag.save()
            Tag.findByName(tagName) >> tag

        when:
            service.createTag(tagName)

        then:
            EntityAlreadyExistsException exception = thrown()
    }

    void "updateTag should update the tag's information and save it"(){
        given:
            Tag.findByName("Jewelry") >> null
            Tag tag = new Tag(name: "Jewelry")
            tag.save()

            service.fetchTagById(1L) >> tag

        when:
            Tag updatedTag = service.updateTag(1L, "wew")

        then:
            updatedTag.name == tag.name
            Tag.exists(updatedTag.id)
    }

    void "updateTag should throw exception when tag name already exists"(){
        given:
            String tagName = "Jewelry"
            Tag tag = new Tag(name: tagName)
            tag.save()
            Tag.findByName(tagName) >> tag

        when:
            service.updateTag(1L, tagName)

        then:
            EntityAlreadyExistsException exception = thrown()
    }

    void "deleteTag should delete the tag with the given id"(){
        given:
            Tag tag = new Tag(name: "Jewelry")
            service.fetchTagById(tag.id) >> tag
            tag.save()

        when:
            service.deleteTag(tag.id)

        then:
            !Tag.exists(tag.id)
    }
}

package com.synacy.shoppingcenter.tag

import com.synacy.shoppingcenter.exception.*
import com.synacy.shoppingcenter.exception.handler.RestExceptionHandler

import org.springframework.http.HttpStatus

class TagController implements RestExceptionHandler {

	static responseFormats = ['json']

	TagService tagService

	def fetchTag(Long tagId) {
		if (!tagId) {
			throw new InvalidRequestException("Unable to parse id.")
		}
		Tag tag = tagService.fetchTagById(tagId)
		respond(tag)
	}
	
	def fetchAllTags() {
		List<Tag> tags = tagService.fetchAllTags()
		Integer tagCount = tagService.fetchTotalNumberOfTags()
		Map<String, Object> tagDetails = [totalTags: tagCount, tags: tags]
		respond(tagDetails)
	}

	def createTag() {
		String name = request.JSON.name ?: null

		if (!name) {
			throw new InvalidRequestException("Missing required parameter.")
		}
		Integer tagCountByName = tagService.countByName(name)
		if (tagCountByName > 0) {
			throw new RequestConflictViolationException("Existing tag with name '" + name + "' already exists.")
		}
		Tag tag = tagService.createNewTag(name)
		respond(tag, [status: HttpStatus.CREATED])
	}

	def updateTag(Long tagId) {
		String name = request.JSON.name ?: null

		if (!name) {
			throw new InvalidRequestException("Missing required parameter.")
		}
		Tag tag = tagService.fetchTagById(tagId)
		if (!tag) {
			throw new ResourceNotFoundException("Tag with id " + tagId + " not found")
		}
		Integer tagCountByName = tagService.countByName(name)
		if (tagCountByName > 0) {
			throw new RequestConflictViolationException("Existing tag with name '" + name + "' already exists.")
		}
		tag = tagService.updateTag(tag, name)
		respond(tag)
	}

	def removeTag(Long tagId) {
		Tag tag = tagService.fetchTagById(tagId)
		try {
			tagService.deleteTag(tag)
		} catch (Exception e) {
			throw new RequestConflictViolationException("Unable to delete a tag attached to a shop.")
		}
		render(status: HttpStatus.NO_CONTENT)
	}

}

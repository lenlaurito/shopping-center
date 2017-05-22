package com.synacy.shoppingcenter.tag

import com.synacy.shoppingcenter.exception.*

import grails.transaction.Transactional

@Transactional
class TagService {

	public Tag fetchTagById(Long id) {
		Tag tag = Tag.findById(id)
		if (!tag) {
			throw new ResourceNotFoundException("Tag with id " + id + " not found")
		}
		return tag
	}
	
	public List<Tag> fetchAllTags() {
		return Tag.list([sort: "id", order: "asc"])
	}

	public Tag createNewTag(String name) {
		Tag tag = new Tag()
		tag.name = name
		tag.shops = []
		return tag.save()
	}

	public updateTag(Tag tag, String name) {
		tag.name = name
		return tag.save()
	}

	public deleteTag(Tag tag) {
		tag.delete()
	}
	
	public Integer fetchTotalNumberOfTags() {
		return Tag.count()
	}
	
	public Integer countByName(String name) {
		return Tag.countByName(name)
	}

}

package com.synacy.shoppingcenter

import grails.converters.JSON
import grails.validation.ValidationErrors
import org.springframework.validation.FieldError
import grails.util.Holders
import org.springframework.context.i18n.LocaleContextHolder

class ValidationErrorMarshaller {

    void register() {
        def validationErrorsMarshaller = {
            return [
                errors: it.getAllErrors().collect {
                    if (it instanceof FieldError) {
                        Locale locale = LocaleContextHolder.getLocale()

                        def msg = Holders.applicationContext.getBean("messageSource").getMessage(it, locale)

                        return msg
                    }
                }
            ]
        }

        JSON.registerObjectMarshaller(ValidationErrors, validationErrorsMarshaller)
    }
}
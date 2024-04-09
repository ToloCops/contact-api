package com.tolocops.contactapi.mapper;

import com.tolocops.contactapi.domain.Contact;
import com.tolocops.contactapi.dto.ContactDto;

public class ContactMapper {
    public static ContactDto toDto(Contact contact) {
        ContactDto contactDto = new ContactDto(
                contact.getId(),
                contact.getName(),
                contact.getEmail(),
                contact.getTitle(),
                contact.getPhone(),
                contact.getAddress(),
                contact.getStatus(),
                contact.getPhotoUrl()
        );
        return contactDto;
    }

    public static Contact toEntity(ContactDto contactDto) {
        Contact contact = new Contact(
                contactDto.getId(),
                contactDto.getName(),
                contactDto.getEmail(),
                contactDto.getTitle(),
                contactDto.getPhone(),
                contactDto.getAddress(),
                contactDto.getStatus(),
                contactDto.getPhotoUrl()
        );
        return contact;
    }
}

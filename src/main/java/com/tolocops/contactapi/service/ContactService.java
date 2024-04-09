package com.tolocops.contactapi.service;

import com.tolocops.contactapi.dto.ContactDto;

public interface ContactService {
        ContactDto createContact(ContactDto contactDto);
        ContactDto getContactById(String id);
}

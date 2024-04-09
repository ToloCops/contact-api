package com.tolocops.contactapi.service;

import com.tolocops.contactapi.domain.Contact;
import com.tolocops.contactapi.dto.ContactDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ContactService {
        Page<Contact> getAllContacts(int page, int size);
        ContactDto getContact(String id);
        ContactDto createContact(ContactDto contactDto);
        void deleteContact(String id);
        String uploadPhoto(String id, MultipartFile file);
}

package com.tolocops.contactapi.service.impl;

import com.tolocops.contactapi.domain.Contact;
import com.tolocops.contactapi.dto.ContactDto;
import com.tolocops.contactapi.mapper.ContactMapper;
import com.tolocops.contactapi.repo.ContactRepo;
import com.tolocops.contactapi.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepo contactRepo;

    @Override
    public ContactDto createContact(ContactDto contactDto) {
        Contact contact = ContactMapper.toEntity(contactDto);
        contact = contactRepo.save(contact);
        return ContactMapper.toDto(contact);
    }

    @Override
    public ContactDto getContactById(String id) {
        Optional<Contact> contact = contactRepo.findById(id);
        return contact.map(ContactMapper::toDto).orElse(null);
    }
}

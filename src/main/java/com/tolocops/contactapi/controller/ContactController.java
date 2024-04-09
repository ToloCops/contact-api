package com.tolocops.contactapi.controller;


import com.tolocops.contactapi.dto.ContactDto;
import com.tolocops.contactapi.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/contact")
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
        ContactDto createdContact = contactService.createContact(contactDto);
        if (createdContact != null) {
            return ResponseEntity.ok(createdContact);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        }
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable String id) {
        return ResponseEntity.ok(contactService.getContactById(id));
    }
}

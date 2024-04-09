package com.tolocops.contactapi.controller;


import com.tolocops.contactapi.domain.Contact;
import com.tolocops.contactapi.dto.ContactDto;
import com.tolocops.contactapi.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;



    @PostMapping
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
        ContactDto createdContact = contactService.createContact(contactDto);
        if (createdContact != null) {
            return ResponseEntity.ok(createdContact);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        }
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable String id) {
        return ResponseEntity.ok(contactService.getContact(id));
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> getAllContacts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(contactService.getAllContacts(page, size));
    }

    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id,
                                              @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(contactService.uploadPhoto(id, file));
    }

}

package com.tolocops.contactapi.service.impl;

import com.tolocops.contactapi.domain.Contact;
import com.tolocops.contactapi.dto.ContactDto;
import com.tolocops.contactapi.mapper.ContactMapper;
import com.tolocops.contactapi.repo.ContactRepo;
import com.tolocops.contactapi.service.ContactService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactServiceImpl implements ContactService {

    private final ContactRepo contactRepo;

    @Override
    public Page<Contact> getAllContacts(int page, int size) {
        return contactRepo.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public ContactDto getContact(String id) {
        Contact contact = contactRepo.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
        return ContactMapper.toDto(contact);
    }

    @Override
    public ContactDto createContact(ContactDto contactDto) {
        Contact contact = ContactMapper.toEntity(contactDto);
        contact = contactRepo.save(contact);
        return ContactMapper.toDto(contact);
    }

    @Override
    public void deleteContact(String id) {
        Optional<Contact> contact = contactRepo.findById(id);
        contact.ifPresent(contactRepo::delete);
    }

    @Override
    public String uploadPhoto(String id, MultipartFile file) {
        Contact contact = contactRepo.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
        // Check if the file is empty
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        // Generate a unique filename
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;

        // Define the path where the file will be stored
        Path storageDirectory = Paths.get("uploads");
        if (!Files.exists(storageDirectory)) {
            try {
                Files.createDirectories(storageDirectory);
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory.");
            }
        }

        // Store the file
        try {
            Path destinationPath = storageDirectory.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + uniqueFilename, e);
        }

        // Update the contact with the photo URL
        String photoUrl = "/uploads/" + uniqueFilename;
        contact.setPhotoUrl(photoUrl);
        contactRepo.save(contact);
        return photoUrl;
    }
}

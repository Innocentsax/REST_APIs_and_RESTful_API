package com.innocentcharles.springboot_swagger2;

import com.innocentcharles.springboot_swagger2.Models.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/api")
public class AddressBookResource {


    ConcurrentMap<String, Contact> contacts = new ConcurrentHashMap<>();


    @GetMapping("/{id}")
    public Contact getContact(@PathVariable String id){
        return contacts.get(id);
    }

    @GetMapping("/")
    public List<Contact> getAllContacts(){
        return new ArrayList<Contact>(contacts.values());
    }

    @PostMapping("/")
    public Contact addContact(@RequestBody Contact contact){
        contacts.put(contact.getId(), contact);
        System.out.println("contact added");
        return contact;

    }
}

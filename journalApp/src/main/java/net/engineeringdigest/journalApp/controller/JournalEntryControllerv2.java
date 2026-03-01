package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.journalEntryRepository;
import net.engineeringdigest.journalApp.service.journalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private journalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){ //localhost:8080/journal GET
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry  createEntry(@RequestBody JournalEntry myEntry){ //localhost:8080/journal POS
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return myEntry;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId){
        return journalEntryService.findById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId){
       journalEntryService.deleteById(myId);
       return true;
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateJournalById( @PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle():oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent():oldEntry.getContent());
        }
        journalEntryService.saveEntry(oldEntry);
        return oldEntry;
    }
}

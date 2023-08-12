package dk.blogpost.service;

import dk.blogpost.dto.TranslatedPostDTO;

public interface TranslationService {
    TranslatedPostDTO translatePostToEnglish(String title, String content);
    String translateToEnglish(String content);
}

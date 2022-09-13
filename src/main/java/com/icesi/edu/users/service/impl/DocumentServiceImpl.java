package com.icesi.edu.users.service.impl;


import com.icesi.edu.users.constant.DocumentErrorCode;
import com.icesi.edu.users.constant.DocumentStatus;
import com.icesi.edu.users.error.exception.DocumentError;
import com.icesi.edu.users.error.exception.DocumentException;
import com.icesi.edu.users.model.Document;
import com.icesi.edu.users.repository.DocumentRepository;
import com.icesi.edu.users.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import static com.icesi.edu.users.constant.DocumentErrorCode.*;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document getDocument(UUID documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        if(document.isPresent())
            return document.get();
        else
            throw new DocumentException(HttpStatus.NOT_FOUND,new DocumentError(CODE_01,CODE_01.getMessage()));
    }

    @Override
    public List<Document> getDocuments() {
        return StreamSupport.stream(documentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Document updateDocument(Document document) {
        if(getDocument(document.getDocumentId()).getStatus() == DocumentStatus.APPROVED)
            throw new DocumentException(HttpStatus.FORBIDDEN,new DocumentError(CODE_03,CODE_03.getMessage()));
        else
            return documentRepository.save(document);
    }


}

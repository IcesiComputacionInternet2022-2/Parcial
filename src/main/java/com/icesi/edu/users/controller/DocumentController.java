package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.DocumentAPI;
import com.icesi.edu.users.constant.DocumentErrorCode;
import com.icesi.edu.users.dto.DocumentDTO;
import com.icesi.edu.users.error.exception.DocumentError;
import com.icesi.edu.users.error.exception.DocumentException;
import com.icesi.edu.users.mapper.DocumentMapper;
import com.icesi.edu.users.service.DocumentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class DocumentController implements DocumentAPI {

    private final DocumentMapper documentMapper;

    private final DocumentService documentService;

    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
    	nameShouldOnlyContainLettersAndSpaces(documentDTO);
        return documentMapper.fromDocument(documentService.createDocument(documentMapper.fromDTO(documentDTO)));
    }
    
    private void nameShouldOnlyContainLettersAndSpaces(DocumentDTO documentDTO) {
    	String name = documentDTO.getName().replaceAll(" ", "");
    	if(!name.matches("^[a-zA-Z]*$")) {
    		throw new DocumentException(HttpStatus.BAD_REQUEST, new DocumentError(DocumentErrorCode.CODE_02.name(), DocumentErrorCode.CODE_02.getMessage()));
    	}
    }

    @Override
    public DocumentDTO getDocument(UUID documentId) {
        return documentMapper.fromDocument(documentService.getDocument(documentId));
    }

    @Override
    public List<DocumentDTO> getDocuments() {
        return documentService.getDocuments().stream().map(documentMapper::fromDocument).collect(Collectors.toList());
    }

    @Override
    public DocumentDTO updateDocument(UUID documentId, DocumentDTO documentDTO) {
        return documentMapper.fromDocument(documentService.updateDocument(documentMapper.fromDTO(documentId,documentDTO)));
    }

}

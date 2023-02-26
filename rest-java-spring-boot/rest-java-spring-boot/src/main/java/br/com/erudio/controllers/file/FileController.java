package br.com.erudio.controllers.file;

import br.com.erudio.data.vo.v1.UploadFileResponseVO;
import br.com.erudio.services.file.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Tag(name = "File endpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {

    private Logger logger = Logger.getLogger(FileController.class.getName());

    @Autowired
    private FileStorageService storageService;

    @PostMapping("/uploadFile")
    public UploadFileResponseVO uploadFile(@RequestParam("file")MultipartFile file){
       logger.info("Storing file to disk ");
        var filename = storageService.storeFile(file);
       String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
               .path("/api/file/v1/downloadFile/")
               .path(filename)
               .toUriString();
        return new UploadFileResponseVO(filename, fileDownloadUri,file.getContentType(), file.getSize());

    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files")MultipartFile[] files){
        logger.info("Storing files to disk ");

        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String filename, HttpServletRequest request){
        logger.info("Reading a file on disk ");

        Resource resource = storageService.loadFileAsResource(filename);
        String contentType = "";

        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception e){
            logger.info("Could not determine file type!");
        }

        if(contentType.isBlank()){
            contentType = "application/octet-strea";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=/ " + resource.getFilename() + "/")
                        .body(resource);

    }
}

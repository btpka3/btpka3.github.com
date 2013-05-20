package me.test.first.spring.rs;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.test.first.spring.rs.entity.User;
import me.test.first.spring.rs.exception.BusinessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UrlPathHelper;

@Controller
@RequestMapping("/file")
public class FileController {

    private final Map<Long, ByteArrayResource> fileMap = new LinkedHashMap<Long, ByteArrayResource>();

    @Autowired
    private UrlPathHelper urlPathHelper = null;

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {

        if (file == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "for uploading, parameter name must be \"file\"");
        }

        if (file.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "File could not be empty");
        }

        ByteArrayResource rsc = null;
        try {
            rsc = new ByteArrayResource(file.getBytes(),
                    file.getContentType());
        } catch (IOException e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not read the file uploaded.");
        }

        Long maxId = 0L;
        for (Long id : fileMap.keySet()) {
            if (id > maxId) {
                maxId = id;
            }
        }
        Long newId = maxId + 1;
        fileMap.put(newId, rsc);

        String contextPath = urlPathHelper.getContextPath(req);
        String servletPath = urlPathHelper.getServletPath(req);
        resp.setHeader("Location", contextPath + servletPath + "/file/" + newId);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ByteArrayResource get(@PathVariable("id") Long id) {

        if (!fileMap.containsKey(id)) {
            throw new BusinessException(HttpStatus.NOT_FOUND,
                    "File with id \"" + id + "\" not found.");
        }
        return fileMap.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void delete(Long id, HttpServletResponse resp) {
        if (!fileMap.containsKey(id)) {
            throw new BusinessException(HttpStatus.NOT_FOUND,
                    "File with id \"" + id + "\" not found.");
        }
        fileMap.remove(id);

        resp.setStatus(HttpStatus.NO_CONTENT.value());
    }
}

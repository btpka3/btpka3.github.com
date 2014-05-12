package me.test.first.spring.rs.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.test.first.spring.rs.exception.BusinessException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

/**
 * <table border=1 cellspacing=0 cellpadding=0 >
 * <tr>
 * <th>URL</th>
 * <th>HTTP方法</th>
 * <th>作用</th>
 * </tr>
 * <tr>
 * <td>/file</td>
 * <td>POST</td>
 * <td>上传新文件</td>
 * </tr>
 * <tr>
 * <td>/file/{id}</td>
 * <td>HEAD</td>
 * <td>检查资源是否可用</td>
 * </tr>
 * <tr>
 * <td>/file/{id}</td>
 * <td>GET</td>
 * <td>查询指定ID的文件内容</td>
 * </tr>
 * <tr>
 * <td>/file/{id}</td>
 * <td>DELETE</td>
 * <td>删除指定ID的文件信息</td>
 * </tr>
 * </table>
 *
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private final Map<Long, ByteArrayResource> fileMap = new LinkedHashMap<Long, ByteArrayResource>();

    @Autowired
    private ApplicationContext appCtx;

    @Autowired
    private UrlPathHelper urlPathHelper = null;

    @PostConstruct
    public void init() {
        try {
            byte[] avatar = IOUtils.toByteArray(appCtx.getResource("classpath:avatar.png").getInputStream());
            fileMap.put(1L, new ByteArrayResource(avatar, "image/png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Should not handle multi file, even should be only one file item in post data.
    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {

        if (file == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),
                    "for uploading, parameter name must be \"file\"");
        }

        if (file.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),
                    "File could not be empty");
        }

        ByteArrayResource rsc = null;
        try {
            rsc = new ByteArrayResource(file.getBytes(),
                    file.getContentType());
        } catch (IOException e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
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

        String uri = UriComponentsBuilder.newInstance().path("{contextPath}{servletPath}/file/{id}")
                .build()
                .expand(urlPathHelper.getContextPath(req),
                        urlPathHelper.getServletPath(req),
                        newId)
                .encode()
                .toUriString();
        resp.setHeader("Location", uri);

        resp.setStatus(HttpStatus.CREATED.value());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public void head(@PathVariable("id") String idStr, HttpServletResponse resp) {
        Long id = null;
        try {
            id = Long.valueOf(idStr);
        } catch (NumberFormatException e) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(),
                    "file with id =" + id + " not exists");
        }

        if (!fileMap.containsKey(id)) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(),
                    "file with id \"" + id + "\" not found.");
        }

        resp.setStatus(HttpStatus.NO_CONTENT.value());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ByteArrayResource get(@PathVariable("id") String idStr, HttpServletResponse resp) {
        head(idStr, resp);
        Long id = Long.valueOf(idStr);
        resp.setStatus(HttpStatus.OK.value());
        ByteArrayResource rsc = fileMap.get(id);
        String desp = rsc.getDescription();
        if (desp != null && desp.trim().length() != 0) {
            resp.setContentType(desp.trim());
        }
        return rsc;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String idStr, HttpServletResponse resp) {
        head(idStr, resp);
        Long id = Long.valueOf(idStr);
        fileMap.remove(id);
        resp.setStatus(HttpStatus.NO_CONTENT.value());
    }
}

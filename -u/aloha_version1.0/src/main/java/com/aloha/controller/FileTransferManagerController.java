package com.aloha.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.common.util.FileUploadBean;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes("sessionUser")
public class FileTransferManagerController {

	@RequestMapping(value = "process", method = RequestMethod.POST)
	@ResponseBody
	public String save(FileUploadBean request) throws Exception {
		String path = "G:\\imgupload\\";
		/*path = path.substring(0, path.indexOf("\\build"));
		path = path + "\\web\\upload\\";*/
		MultipartFile m=request.getFile();
		byte[] bytefile;
		
		bytefile=m.getBytes();
		FileOutputStream fos=new FileOutputStream(path+"/"+m.getOriginalFilename());
		fos.write(bytefile);
		fos.close();
	
		return "<h1>File Upload Was Successful Check Chat For Link For Download</h1>";
	}
	
	@RequestMapping("/downloadFile")
    public void downloadFile(@RequestParam String filename,HttpServletResponse response) throws IOException
    {
        OutputStream outputStream = null;
        InputStream in = null;
        try {
            in = new FileInputStream("G:\\imgupload\\" + filename); // I assume files are at imgupload
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            response.setHeader(
                "Content-Disposition",
                "attachment;filename=\"" + filename + "\"");
            outputStream = response.getOutputStream();
            while( 0 < ( bytesRead = in.read( buffer ) ) )
            {
                outputStream.write( buffer, 0, bytesRead );
            }
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally
        {
            if ( null != in )
            {
                in.close();
            }
        }

    }

}

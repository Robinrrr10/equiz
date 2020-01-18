package com.github.equiz.questions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Robin
 *
 */
public class QuizQuestionHelper {

	/**
	 * This method will give all lines in multipart file as string list
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public List<String> getAllLinesInList(MultipartFile file) throws IOException{
		List<String> allLines = new ArrayList<String>();
		InputStream inputStream =  file.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line;
		while((line = bufferedReader.readLine()) != null) {
			allLines.add(line);
		}
		return allLines;
	}
	
}

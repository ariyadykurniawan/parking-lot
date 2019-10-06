package com.gojek.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileProcessor extends AbstractProcessor {
	String filePath = null;

	public FileProcessor(String filePath) {
		this.filePath = filePath;
	}

	public void process() throws Exception {
		File inputFile = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		String line;
		while ((line = br.readLine()) != null) {
			validateAndProcess(line);
		}
	}
}

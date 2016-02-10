package com.totalwine.test.trials;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;


public class Results {
	
	@Test
	public void ArchiveResults() throws IOException {
		File file = new File("C:\\Users\\rsud\\.jenkins\\jobs\\Regression Suite (Bugfix)\\reports\\html\\BugfixTestResults.html");
		//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String nameAppend = sdf.format(file.lastModified());
		File archivedFile =  new File("C:\\Users\\rsud\\.jenkins\\jobs\\Regression Suite (Bugfix)\\reports\\html\\BugfixTestResults_"+nameAppend+".html");
		FileUtils.copyFile(file, archivedFile);
		System.out.println("Previous results are stored as BugfixTestResults_"+nameAppend+".html");
    }
}

package org.windman.go.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PackageSgf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			doPackage("data_sgf", "./");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void doPackage(String packageName, String dir) throws IOException{
		File targetFile = new File(dir + packageName);
		if (targetFile.exists()) {
			targetFile.delete();
		}
		targetFile.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile)));
		
		StringBuilder builder = new StringBuilder();
		builder.append("SGF_DATAaaa").append("{");
		
		File rootDir = new File(dir);
		if( rootDir.exists() && rootDir.isDirectory() ) {
			File[] fileList = rootDir.listFiles();
			for (File file : fileList) {
				if (file.getName().endsWith(".sgf")) {
					builder.append("(" + targetFile.length() + ",");
					writeFile(writer, file);
					writer.write("\n");
					builder.append(targetFile.length() + ");");
				}
			}
		}
		
		builder.append("}");
		System.out.println(builder.toString());
		String dirString = builder.toString();
		//writer.write(dirString, 0, dirString.length());
		
		writer.flush();
		writer.close();
	}
	
	private static boolean writeFile(BufferedWriter writer, File source) throws IOException{
		if (source == null || !source.exists()) {
			return false;
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(source)));  
		String lineStr = null;
		while ((lineStr = reader.readLine()) != null) {
			System.out.println(lineStr);
			writer.write(lineStr);
			//System.out.println("AAAAAAA---->" + DecodeUtil.decode(lineStr));
		}
		reader.close();
		return true;
	}

}

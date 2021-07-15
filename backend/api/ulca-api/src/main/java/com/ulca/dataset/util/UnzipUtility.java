package com.ulca.dataset.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UnzipUtility {
	/**
	 * Size of the buffer to read/write data
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Extracts a zip file specified by the zipFilePath to a directory specified by
	 * destDirectory (will be created if does not exists)
	 * 
	 * @param zipFilePath
	 * @param destDirectory
	 * @throws IOException
	 */
	public Map<String, String> unzip(String zipFilePath, String destDirectory, String serviceRequestNumber) throws IOException {

		log.info("************ Entry UnzipUtility :: unzip *********");
		log.info("zipFilePath :: " + zipFilePath);

		Map<String, String> fileMap = new HashMap<String, String>();
		String newDestDirectory = destDirectory + File.separator + serviceRequestNumber;
		fileMap.put("baseLocation", newDestDirectory);
		File destDir = new File(newDestDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}

		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = newDestDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				if (!filePath.contains("__MACOSX") && !filePath.contains("DS_Store")) {

					// if the entry is a file, extracts it

					String entryType = entry.getName();

					log.info("entryType ::" + entryType);
					String fileName = "";
					
					String fileDetails[] = entryType.split("/");
					if(fileDetails.length > 1) {
						String fileDest = "";
						for(int i = 0 ; i< fileDetails.length -1; i++) {
							fileDest = fileDest + fileDetails[i] +"/";
						}
						
						fileDest = newDestDirectory + "/"+fileDest;
						
						log.info("fileDestination :: " + fileDest);
						File fileDirect = new File(fileDest);
						if (!fileDirect.exists()) {
							
							log.info("creating destination folder :: " + fileDest);
							fileDirect.mkdirs();
						}
						fileName = fileDetails[fileDetails.length - 1];
						if(fileName.equals("params.json")) {
							fileMap.put("baseLocation", fileDest.substring(0, fileDest.length()-1));
						}
						log.info("file name :: " + fileName);
						log.info("unzipping file  :: " + entryType);
							
					}else {
						log.info("file destination is in current directory");
						fileName = fileDetails[fileDetails.length - 1];
					}
					
					
					if (fileName.equals("params.json") || fileName.equals("data.json") || fileName.contains(".wav")
							|| fileName.contains(".pcm") || fileName.contains(".mp3") || fileName.contains(".flac") 
							|| fileName.contains(".jpeg") || fileName.contains(".bmp") || fileName.contains(".png") 
							|| fileName.contains(".tiff") 
							) {

						extractFile(zipIn, filePath);
						log.info("filePath unzipped :: " + filePath);
						//fileMap.put(fileName, filePath);
					}

				}

			} else {
				// if the entry is a directory, make the directory
				log.info("folder created :: " +  filePath);
				File dir = new File(filePath);
				if (!dir.exists())
					dir.mkdirs();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();

		log.info(fileMap.toString());
		log.info("************ Exit UnzipUtility :: unzip *********");

		return fileMap;
	}

	/**
	 * Extracts a zip entry (file entry)
	 * 
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

}

package com.ulca.benchmark.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUtility {

	public Map<String, String> unzip(String zipFilePath, String destDirectory, String serviceRequestNumber)
			throws IOException {

		Map<String, String> fileMap = new HashMap<String, String>();
		String targetDir = destDirectory + File.separator + serviceRequestNumber;
		Path targetDirPath = Paths.get(targetDir);
		String startTime = new Date().toString();

		try (ZipFile zipFile = new ZipFile(zipFilePath)) {
			zipFile.stream().parallel() // enable multi-threading
					.forEach(e -> {
						try {
							unzipEntry(zipFile, e, targetDirPath, fileMap);
						} catch (Exception ex) {
							// TODO Auto-generated catch block
							log.info(ex.getMessage());
						}
					});
		} catch (IOException e) {
			throw new IOException("Error opening zip file '" + zipFilePath + "': " + e, e);
		}
		if (!fileMap.containsKey("baseLocation")) {
			throw new IOException("Uploaded zip file does not contains params.json");
		}
		log.info("unzip timings :: " + serviceRequestNumber);
		log.info("start time :: " + startTime);
		log.info("end time :: " + new Date());
		log.info("baseLocation :: " + fileMap.get("baseLocation"));

		return fileMap;
	}

	private void unzipEntry(ZipFile zipFile, ZipEntry entry, Path targetDir, Map<String, String> fileMap)
			throws IOException {
		try {
			Path targetPath = targetDir.resolve(Paths.get(entry.getName()));
			if (entry.isDirectory()) {
				String directoryPath = targetPath.toString();
				File fileDirect = new File(directoryPath);
				if (!fileDirect.exists()) {
					fileDirect.mkdirs();
				}

			} else {
				String filePath = targetPath.toString();

				if (!filePath.contains("__MACOSX") && !filePath.contains("DS_Store")) {
					String directoryPath = targetPath.getParent().toString();
					File fileDirect = new File(directoryPath);
					if (!fileDirect.exists()) {
						fileDirect.mkdirs();
					}
					try (InputStream in = zipFile.getInputStream(entry)) {
						Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
					}
					String entryType = entry.getName();
					String fileDetails[] = entryType.split("/");
					String fileName = fileDetails[fileDetails.length - 1];
					if (fileName.equals("params.json")) {
						fileMap.put("baseLocation", targetPath.getParent().toString());
					}
				}
			}
		} catch (Exception e) {
			throw new IOException("Error processing zip entry '" + entry.getName() + "': " + e.getMessage());
		}
	}
	
	public String downloadUsingNIO(String urlStr, String downloadFolder, String fileName) throws IOException {
		log.info("************ Entry FileUtility :: downloadUsingNIO *********");
		log.info("url :: " + urlStr);
		URL url = new URL(urlStr);
		String file = downloadFolder + "/" + fileName;
		log.info("downloadUsingNIO file path :: " + file);
		log.info(url.getPath());
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		log.info(url.getContent().toString());
		log.info(rbc.getClass().toString());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();

		log.info("************ Exit FileUtility :: downloadUsingNIO *********");
		return file;
	}

}
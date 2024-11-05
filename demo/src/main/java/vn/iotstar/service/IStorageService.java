package vn.iotstar.service;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;
public interface IStorageService {
	void init();

	void delete(String storeFilename) throws Exception;

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

	String getSorageFilename(MultipartFile file, String id);

	void store(MultipartFile file, String storeFilename);
}

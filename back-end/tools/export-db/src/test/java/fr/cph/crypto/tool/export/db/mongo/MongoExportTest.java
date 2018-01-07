package fr.cph.crypto.tool.export.db.mongo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import fr.cph.crypto.tools.file.FileSystem;
import fr.cph.crypto.tools.export.db.mongo.MongoExport;
import fr.cph.crypto.tools.export.db.mongo.MongoProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class MongoExportTest {

	private ArgumentCaptor<String> output = ArgumentCaptor.forClass(String.class);
	private ArgumentCaptor<String> directory = ArgumentCaptor.forClass(String.class);
	private MongoProperties mongoProperties = new MongoProperties(new MongoProperties.Mongo("/opt/mongodb/bin/mongodump", "cryptodb"));
	private Process process = Mockito.mock(Process.class);
	private FileSystem fileSystem = Mockito.mock(FileSystem.class);
	private Runtime runtime = Mockito.mock(Runtime.class);
	private MongoExport mongoExport = new MongoExport("/tmp", "/tmp/file.zip", fileSystem, mongoProperties, runtime);

	@Test
	void testExport() throws IOException, InterruptedException {
		// given
		given(runtime.exec(anyString())).willReturn(process);
		given(process.waitFor()).willReturn(0);
		given(fileSystem.zipFile(anyString(), anyString())).willReturn(new File(""));

		// when
		File actual = mongoExport.export();

		// then
		then(runtime).should().exec("/opt/mongodb/bin/mongodump --out /tmp --db cryptodb");
		then(fileSystem).should().zipFile(output.capture(), directory.capture());
		assertNotNull(actual);
		assertEquals("/tmp/file.zip", output.getValue());
		assertEquals("/tmp/cryptodb", directory.getValue());
	}

	@Test
	void testExportFail() throws IOException, InterruptedException {
		// given
		given(runtime.exec(anyString())).willReturn(process);
		given(process.waitFor()).willReturn(-1);

		// when
		Executable actualExecutable = () -> mongoExport.export();

		// then
		assertThrows(RuntimeException.class, actualExecutable);
		then(runtime).should().exec("/opt/mongodb/bin/mongodump --out /tmp --db cryptodb");
	}
}

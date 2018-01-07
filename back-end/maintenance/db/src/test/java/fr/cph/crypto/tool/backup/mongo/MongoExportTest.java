package fr.cph.crypto.tool.backup.mongo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import fr.cph.crypto.maintenance.db.mongo.FileSystem;
import fr.cph.crypto.maintenance.db.mongo.MongoExport;
import fr.cph.crypto.maintenance.db.mongo.MongoProperties;
import fr.cph.crypto.maintenance.db.mongo.Zipper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class MongoExportTest {

	private ArgumentCaptor<String> output = ArgumentCaptor.forClass(String.class);
	private ArgumentCaptor<List> listFiles = ArgumentCaptor.forClass(List.class);
	private MongoProperties mongoProperties = new MongoProperties(new MongoProperties.Mongo("/opt/mongodb/bin/mongodump", "/tmp", "cryptodb"));
	private Process process = Mockito.mock(Process.class);
	private Zipper zipper = Mockito.mock(Zipper.class);
	private FileSystem fileSystem = Mockito.mock(FileSystem.class);
	private Runtime runtime = Mockito.mock(Runtime.class);
	private MongoExport mongoExport = new MongoExport(zipper, fileSystem, mongoProperties, runtime);

	@Test
	void testExport() throws IOException, InterruptedException {
		// given
		given(runtime.exec(anyString())).willReturn(process);
		given(process.waitFor()).willReturn(0);
		given(fileSystem.listFiles(any())).willReturn(new File[0]);
		given(zipper.zip(anyString(), anyList())).willReturn(new File(""));

		// when
		File actual = mongoExport.export();

		// then
		assertNotNull(actual);
		then(runtime).should().exec("/opt/mongodb/bin/mongodump --out /tmp --db cryptodb");
		then(fileSystem).should().listFiles(new File("/tmp/cryptodb"));
		then(zipper).should().zip(output.capture(), listFiles.capture());
		String date = new SimpleDateFormat("MM-dd-yyyy").format(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime());
		assertEquals("/tmp/" + date + "-cryptodb.zip", output.getValue());
		assertThat((List<File>) listFiles.getValue(), hasSize(0));
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

	@Test
	void testCleanFileSystem() {
		// given
		String date = new SimpleDateFormat("MM-dd-yyyy").format(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime());
		given(fileSystem.deleteFile(any())).willReturn(true);

		// when
		mongoExport.cleanFileSystem();

		// then
		then(fileSystem).should().deleteDirectory(new File("/tmp/cryptodb"));
		then(fileSystem).should().deleteFile(new File("/tmp/" + date + "-cryptodb.zip"));
	}

	@Test
	void testCleanFileSystemFail() {
		// given
		String date = new SimpleDateFormat("MM-dd-yyyy").format(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime());
		given(fileSystem.deleteFile(any())).willReturn(false);

		// when
		Executable actualExecutable = () -> mongoExport.cleanFileSystem();

		// then
		assertThrows(RuntimeException.class, actualExecutable);
		then(fileSystem).should().deleteDirectory(new File("/tmp/cryptodb"));
		then(fileSystem).should().deleteFile(new File("/tmp/" + date + "-cryptodb.zip"));
	}
}

package pe.com.sedapal.asi.fileserver;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pe.com.sedapal.asi.AppConfig;
import pe.com.sedapal.asi.service.IFileServerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public abstract class AbstractFileTest {

	@Autowired
	protected IFileServerService fileServerService;
	
	protected String urlFileServer = "http://sedapal.test:8080/fileserver/asi/";
}

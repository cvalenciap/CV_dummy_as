package pe.com.sedapal.asi.fileserver;

import org.junit.Test;

import pe.com.sedmail.cliente.bean.ArchivoAdjunto;

public class DownloadFileTest extends AbstractFileTest {
	
	@Test
	public void testDownloadFile() throws Exception {
		String fileName = "b97f9109-e13b-4ec6-ba52-e84f28d69c72.jpg";
		
		ArchivoAdjunto archivoAdjunto = fileServerService.downloadFile(urlFileServer + "/" + fileName);
		
		System.out.println("Size file: " + archivoAdjunto != null ? archivoAdjunto.getSizeArchivo() : 0);
	}
}

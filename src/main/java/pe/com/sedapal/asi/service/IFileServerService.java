package pe.com.sedapal.asi.service;

import pe.com.sedapal.asi.model.response_objects.UploadFileResponse;
import pe.com.sedmail.cliente.bean.ArchivoAdjunto;

public interface IFileServerService {

	ArchivoAdjunto downloadFile(String fileName);
	UploadFileResponse uploadFile(ArchivoAdjunto archivoAdjunto, String url);
	void deleteFile(String urlFile);
	void getFileInfo(String urlFileServer, String realFileName);
	
}

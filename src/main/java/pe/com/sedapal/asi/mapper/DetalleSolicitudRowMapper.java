package pe.com.sedapal.asi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sedapal.asi.model.Categoria;
import pe.com.sedapal.asi.model.DetalleSolicitud;
import pe.com.sedapal.asi.model.UsuarioAfectado;

public class DetalleSolicitudRowMapper implements RowMapper<DetalleSolicitud> {

	@Override
	public DetalleSolicitud mapRow(ResultSet rs, int rowNum) throws SQLException {
		DetalleSolicitud detalleSolicitud = new DetalleSolicitud();
		
		detalleSolicitud.setN_det_soli(rs.getLong("N_DET_SOLI"));
		detalleSolicitud.setN_id_soli(rs.getLong("N_ID_SOLI"));
		
		Categoria categoria = new Categoria();
		categoria.setIdcategoria(rs.getLong("N_ID_CAT_PAR"));
		
		detalleSolicitud.setN_id_cat_par(categoria);
		
		UsuarioAfectado usuarioAfectado = new UsuarioAfectado();
		usuarioAfectado.setNcodtrabajador(rs.getLong("N_IDTRABAFEC"));
		detalleSolicitud.setN_idtrabafec(usuarioAfectado);
		
		return detalleSolicitud;
	}
}

package pe.com.sedapal.asi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.com.sedapal.asi.model.AdjuntoMensaje;
import pe.com.sedapal.asi.model.Solicitud;

public class SolicitudRowMapper implements RowMapper<Solicitud> {

	@Override
	public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException {
		Solicitud solicitud = new Solicitud();
		solicitud.setN_id_soli(rs.getLong("N_ID_SOLI"));
		solicitud.setN_idtrabsoli(rs.getLong("N_IDTRABSOLI"));
		solicitud.setD_fecha_soli(rs.getDate("D_FECHA_SOLI"));
		solicitud.setN_ind_alta(rs.getLong("N_IND_ALTA"));
		
		AdjuntoMensaje adjuntoMensaje = new AdjuntoMensaje();
		adjuntoMensaje.setIdAdjunto(rs.getInt("N_IDARCHADJU"));
		
		solicitud.setN_idarchadju(adjuntoMensaje);
		solicitud.setN_estado(rs.getLong("N_ESTADO"));
		
		return solicitud;
	}

}

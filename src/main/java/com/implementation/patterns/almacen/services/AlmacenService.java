package com.implementation.patterns.almacen.services;

import com.implementation.patterns.almacen.components.ContabilidadClient;
import com.implementation.patterns.almacen.components.ObrasClient;
import com.implementation.patterns.almacen.components.model.Tables;
import com.implementation.patterns.almacen.components.model.tables.pojos.Solicitud;
import com.implementation.patterns.almacen.model.ComprobanteDtoRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlmacenService {

    private final DSLContext context;
    private final ObrasClient obrasClient;
    private final ContabilidadClient contabilidadClient;


    public Integer generarSolicitudAlmacen(Solicitud solicitud) {
        context.insertInto(Tables.SOLICITUD,
                        Tables.SOLICITUD.DESCRIPCION,
                        Tables.SOLICITUD.TITULO,
                        Tables.SOLICITUD.ESTADO,
                        Tables.SOLICITUD.FECHA,
                        Tables.SOLICITUD.ID_REGISTRO_EXTERNO,
                        Tables.SOLICITUD.PROCESO_ORIGEN)
                .values(solicitud.getDescripcion(),
                        solicitud.getTitulo(),
                        solicitud.getEstado(),
                        solicitud.getFecha(),
                        solicitud.getIdRegistroExterno(),
                        solicitud.getProcesoOrigen())
                .execute();

        Solicitud result = context.selectFrom(Tables.SOLICITUD)
                .where(
                        Tables.SOLICITUD.ID_REGISTRO_EXTERNO.eq(Integer.valueOf(solicitud.getIdRegistroExterno()))
                )
                .fetchSingle().into(Solicitud.class);

        return result.getId();
    }

    public Boolean deliverOrder(Integer id) {
        Solicitud solicitud = context.selectFrom(Tables.SOLICITUD)
                .where(
                        Tables.SOLICITUD.ID.eq(Integer.valueOf(id))
                )
                .fetchSingle().into(Solicitud.class);

        Integer idRegistroExterno = solicitud.getIdRegistroExterno();

        obrasClient.callActualizarEstado(idRegistroExterno);

        var comprobanteDto = ComprobanteDtoRequest.builder()
                .idRegistroExterno(id)
                .build();
        contabilidadClient.callCreateComprobante(comprobanteDto);
        return Boolean.TRUE;
    }

    public List<Solicitud> findAll() {
        return context
                .selectFrom(Tables.SOLICITUD)
                .fetchInto(Solicitud.class);
    }
}

package com.usbbog.proyectovocacional.backend.application.service

import com.usbbog.proyectovocacional.backend.application.dto.response.ChartDatumResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.DashboardMetricResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.DashboardResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.GeographicDistributionResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.RecentResultResponse
import com.usbbog.proyectovocacional.backend.application.dto.response.TopProgramaResponse
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Area
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.Programa
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Departamento
import com.usbbog.proyectovocacional.backend.domain.model.catalogo.lugares.Municipio
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.AfinidadPrograma
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Prueba
import com.usbbog.proyectovocacional.backend.domain.model.evaluacion.Reporte
import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Usuario
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.AreaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.LugarRepository
import com.usbbog.proyectovocacional.backend.domain.repository.catalogo.ProgramaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.AfinidadProgramaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.PruebaRepository
import com.usbbog.proyectovocacional.backend.domain.repository.evaluacion.ReporteRepository
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.UsuarioRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.math.roundToInt

@Service
class DashboardService(
    private val usuarioRepository: UsuarioRepository,
    private val pruebaRepository: PruebaRepository,
    private val reporteRepository: ReporteRepository,
    private val afinidadProgramaRepository: AfinidadProgramaRepository,
    private val areaRepository: AreaRepository,
    private val programaRepository: ProgramaRepository,
    private val lugarRepository: LugarRepository
) {

    fun obtenerDashboard(): DashboardResponse {

        val usuariosActivos = usuarioRepository.obtenerTodos().filter { it.activo }

        val reportesActivos = reporteRepository.obtenerTodos().filter { it.activo }

        val pruebasPorId: Map<Long?, Prueba> = pruebaRepository.obtenerTodos().associateBy { it.id }

        val afinidadesPorPrueba: Map<Long, List<AfinidadPrograma>> = afinidadProgramaRepository
            .obtenerTodos()
            .filter { it.activo }
            .groupBy { it.idPrueba }

        val areasPorId: Map<Long?, Area> = areaRepository.obtenerTodos().associateBy { it.id }

        val programasPorId: Map<Long?, Programa> = programaRepository.obtenerTodos().associateBy { it.id }

        val departamentosPorId: Map<String, Departamento> =
            lugarRepository.obtenerDepartamentos().associateBy { it.idDepartamento }

        val municipiosPorId: Map<String, Municipio> =
            lugarRepository.obtenerMunicipios().associateBy { it.idMunicipio }

        val usuariosPorId: Map<Long?, Usuario> = usuariosActivos.associateBy { it.id }

        // 1. Métricas básicas
        val totalUsuarios = usuariosActivos.size
        val totalPruebas = reportesActivos.size

        val areaMasSeleccionada = reportesActivos
            .groupingBy { it.idAreaPredominante }
            .eachCount()
            .maxByOrNull { it.value }
            ?.let { (idArea, _) -> areasPorId[idArea]?.nombreArea }
            ?: "Sin datos"

        // 2. Distribución Internos/Externos
        val internos = usuariosActivos.count { it.idPrograma != null }.toLong()
        val externos = usuariosActivos.size.toLong() - internos

        val internosPct = if (usuariosActivos.isEmpty()) {
            0
        } else {
            ((internos * 100.0) / usuariosActivos.size).roundToInt().coerceIn(0, 100)
        }
        val externosPct = 100 - internosPct

        // 3. Distribución geográfica (por departamento)
        val reportesPorDepartamento = reportesActivos
            .mapNotNull { reporte ->
                val usuario = usuariosPorId[pruebasPorId[reporte.idPrueba]?.idUsuario]
                usuario?.departamento?.let { it to reporte }
            }
            .groupBy { it.first }
            .mapValues { it.value.size }

        val departamentosConDatos = (
            usuariosActivos.mapNotNull { it.departamento } +
                reportesPorDepartamento.keys
            ).distinct()

        val geographicDistribution = if (departamentosConDatos.isEmpty()) {
            listOf(GeographicDistributionResponse("Sin región", 0L, 0L))
        } else {
            departamentosConDatos
                .map { idDepartamento ->
                    GeographicDistributionResponse(
                        region = departamentosPorId[idDepartamento]?.nombreDepartamento ?: "Sin región",
                        users = usuariosActivos.count { it.departamento == idDepartamento }.toLong(),
                        completedTests = (reportesPorDepartamento[idDepartamento] ?: 0).toLong()
                    )
                }
                .sortedByDescending { it.users }
        }

        // 4. Resultados recientes (últimas pruebas con reporte)
        val recentResults = reportesActivos
            .sortedByDescending { reporte -> pruebasPorId[reporte.idPrueba]?.fecha ?: LocalDateTime.MIN }
            .take(10)
            .mapNotNull { reporte ->
                toRecentResult(
                    reporte,
                    pruebasPorId,
                    usuariosPorId,
                    areasPorId,
                    programasPorId,
                    afinidadesPorPrueba,
                    departamentosPorId,
                    municipiosPorId
                )
            }

        return DashboardResponse(
            metrics = listOf(
                DashboardMetricResponse(
                    id = "metric-users",
                    label = "TOTAL DE USUARIOS",
                    value = totalUsuarios.toString(),
                    change = "",
                    hint = "Usuarios internos y externos con acceso al sistema."
                ),
                DashboardMetricResponse(
                    id = "metric-tests",
                    label = "PRUEBAS COMPLETADAS",
                    value = totalPruebas.toString(),
                    change = "",
                    hint = "Aplicaciones finalizadas registradas en el sistema."
                ),
                DashboardMetricResponse(
                    id = "metric-area",
                    label = "ÁREA MÁS SELECCIONADA",
                    value = areaMasSeleccionada,
                    change = "",
                    hint = "Área predominante con mayor cantidad de resultados."
                )
            ),
            mostSelectedArea = areaMasSeleccionada,
            geographicDistribution = geographicDistribution,
            affinityDistribution = listOf(
                ChartDatumResponse("Internos", internosPct),
                ChartDatumResponse("Externos", externosPct)
            ),
            recentResults = recentResults,
            internos = internos,
            externos = externos
        )
    }

    private fun toRecentResult(
        reporte: Reporte,
        pruebasPorId: Map<Long?, Prueba>,
        usuariosPorId: Map<Long?, Usuario>,
        areasPorId: Map<Long?, Area>,
        programasPorId: Map<Long?, Programa>,
        afinidadesPorPrueba: Map<Long, List<AfinidadPrograma>>,
        departamentosPorId: Map<String, Departamento>,
        municipiosPorId: Map<String, Municipio>
    ): RecentResultResponse? {

        val prueba = pruebasPorId[reporte.idPrueba] ?: return null
        val usuario = usuariosPorId[prueba.idUsuario] ?: return null

        val ciudad = usuario.ciudad?.let { municipiosPorId[it]?.nombreMunicipio }
            ?: usuario.departamento?.let { departamentosPorId[it]?.nombreDepartamento }
            ?: "Sin ciudad"

        val afinidadesDeLaPrueba = afinidadesPorPrueba[reporte.idPrueba].orEmpty()
            .filter { it.idPrograma in listOf(reporte.idPrograma1, reporte.idPrograma2, reporte.idPrograma3) }

        val programasRecomendados = listOf(
            reporte.idPrograma1 to programasPorId[reporte.idPrograma1],
            reporte.idPrograma2 to programasPorId[reporte.idPrograma2],
            reporte.idPrograma3 to programasPorId[reporte.idPrograma3],
        ).mapNotNull { (idPrograma, programa) ->
            if (idPrograma == 0L || programa == null) {
                return@mapNotNull null
            }
            val afinidad = afinidadesDeLaPrueba
                .firstOrNull { it.idPrograma == idPrograma }
                ?.valorAfinidad
                ?.toInt()
                ?: 0
            TopProgramaResponse(
                idPrograma = idPrograma,
                nombrePrograma = programa.nombrePrograma,
                valorAfinidad = afinidad,
            )
        }.sortedByDescending { it.valorAfinidad }

        val top = programasRecomendados.firstOrNull()

        return RecentResultResponse(
            id = prueba.id?.toString() ?: reporte.idPrueba.toString(),
            studentName = "${usuario.nombre} ${usuario.apellidos}".trim(),
            document = usuario.documento,
            city = ciudad,
            primaryArea = areasPorId[reporte.idAreaPredominante]?.nombreArea ?: "Sin área",
            topCareer = top?.nombrePrograma ?: "Sin programa",
            affinity = top?.valorAfinidad ?: 0,
            programs = programasRecomendados,
            completedAt = prueba.fecha?.toString() ?: ""
        )
    }
}

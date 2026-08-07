package com.usbbog.proyectovocacional.backend.domain.model.evaluacion

import java.math.BigDecimal

data class AfinidadPrograma(
    val id: Long?,
    val idPrograma: Long,
    val idPrueba: Long,
    val valorAfinidad: BigDecimal,
    val activo: Boolean
)
package com.usbbog.proyectovocacional.backend.application.dto.response

data class DashboardResponse(
    val metrics: List<DashboardMetricResponse>,
    val mostSelectedArea: String,
    val geographicDistribution: List<GeographicDistributionResponse>,
    val affinityDistribution: List<ChartDatumResponse>,
    val recentResults: List<RecentResultResponse>,
    val internos: Long,
    val externos: Long
)

data class DashboardMetricResponse(
    val id: String,
    val label: String,
    val value: String,
    val change: String,
    val hint: String
)

data class GeographicDistributionResponse(
    val region: String,
    val users: Long,
    val completedTests: Long
)

data class ChartDatumResponse(
    val label: String,
    val value: Int
)

data class RecentResultResponse(
    val id: String,
    val studentName: String,
    val document: String,
    val city: String,
    val primaryArea: String,
    val topCareer: String,
    val affinity: Int,
    val programs: List<TopProgramaResponse>,
    val completedAt: String
)

data class TopProgramaResponse(
    val idPrograma: Long,
    val nombrePrograma: String,
    val valorAfinidad: Int
)

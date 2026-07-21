package com.usbbog.proyectovocacional.backend.infrastructure.persistence.repositoryimpl

import com.usbbog.proyectovocacional.backend.domain.model.seguridad.Rol
import com.usbbog.proyectovocacional.backend.domain.repository.seguridad.RolRepository
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.mapper.seguridad.RolMapper
import com.usbbog.proyectovocacional.backend.infrastructure.persistence.repository.RolJpaRepository
import org.springframework.stereotype.Repository

@Repository
class RolRepositoryImpl(

    private val jpaRepository: RolJpaRepository

) : RolRepository {

    override fun obtenerTodos(): List<Rol> =
        jpaRepository.findByActivoTrue()
            .map(RolMapper::toDomain)

    override fun obtenerPorId(id: Long): Rol? =
        jpaRepository.findById(id)
            .map(RolMapper::toDomain)
            .orElse(null)

    override fun guardar(rol: Rol): Rol {

        val entity = RolMapper.toEntity(rol)

        return RolMapper.toDomain(
            jpaRepository.save(entity)
        )
    }

    override fun eliminar(id: Long) {

        val entity = jpaRepository.findById(id)
            .orElseThrow()

        entity.activo = false

        jpaRepository.save(entity)
    }

    override fun existePorNombre(nombre: String): Boolean {
        return jpaRepository.existsByNombreRolIgnoreCase(nombre)
    }
}
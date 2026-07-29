package com.usbbog.proyectovocacional.backend.infrastructure.security.userdetails

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class CustomUserDetails(

    private val usernameValue: String,

    private val passwordValue: String,

    private val rol: String,

    private val activo: Boolean
) : UserDetails {


    override fun getAuthorities():

            MutableCollection<out GrantedAuthority> {

        return mutableListOf(

            SimpleGrantedAuthority(

                "ROLE_$rol"

            )

        )

    }


    override fun getPassword(): String {

        return passwordValue

    }


    override fun getUsername(): String {
        return usernameValue
    }

    override fun isEnabled():Boolean{

        return activo
    }


    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    fun getRol():String{
        return rol
    }

}
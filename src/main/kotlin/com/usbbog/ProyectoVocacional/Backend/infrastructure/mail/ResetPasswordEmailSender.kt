package com.usbbog.proyectovocacional.backend.infrastructure.mail

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class ResetPasswordEmailSender(
    private val javaMailSender: JavaMailSender
) {

    fun enviar(
        destinatario: String,
        nombreCompleto: String,
        enlace: String
    ) {
        val mensaje = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(mensaje, true, "UTF-8")

        helper.setTo(destinatario)
        helper.setSubject("Restablece tu contrasena - Orientacion Vocacional USBBOG")
        helper.setText(cuerpoHtml(nombreCompleto, enlace), true)

        javaMailSender.send(mensaje)
    }

    private fun cuerpoHtml(nombreCompleto: String, enlace: String): String {
        val nombreSeguro = escapeHtml(nombreCompleto)
        val enlaceSeguro = escapeHtml(enlace)

        return """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
            </head>
            <body style="margin:0;padding:0;background:#f4f6fa;font-family:Arial,Helvetica,sans-serif;">
                <table role="presentation" width="100%" cellspacing="0" cellpadding="0" style="background:#f4f6fa;padding:32px 16px;">
                    <tr>
                        <td align="center">
                            <table role="presentation" width="600" cellspacing="0" cellpadding="0" style="max-width:600px;width:100%;background:#ffffff;border-radius:12px;overflow:hidden;">
                                <tr>
                                    <td align="center" style="background:#0f2b5b;padding:24px 16px;">
                                        <h2 style="margin:0;color:#ffffff;font-size:20px;">Universidad de San Buenaventura</h2>
                                        <p style="margin:4px 0 0;color:#c9d4ea;font-size:13px;">Orientacion Vocacional</p>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding:32px 32px 16px;">
                                        <p style="margin:0 0 16px;font-size:15px;color:#222222;">Hola <strong>$nombreSeguro</strong>,</p>
                                        <p style="margin:0 0 16px;font-size:14px;color:#444444;line-height:1.6;">
                                            Recibimos una solicitud para restablecer la contrasena de tu cuenta en el
                                            Sistema de Orientacion Vocacional. Para continuar, haz clic en el siguiente
                                            boton:
                                        </p>
                                        <table role="presentation" cellspacing="0" cellpadding="0" style="margin:24px 0;">
                                            <tr>
                                                <td align="center" style="background:#0f2b5b;border-radius:8px;">
                                                    <a href="$enlaceSeguro"
                                                       style="display:inline-block;padding:14px 28px;color:#ffffff;text-decoration:none;font-size:14px;font-weight:bold;">
                                                        Restablecer contrasena
                                                    </a>
                                                </td>
                                            </tr>
                                        </table>
                                        <p style="margin:0 0 16px;font-size:13px;color:#777777;line-height:1.6;">
                                            Este enlace es valido por una hora. Si no puedes usar el boton, haz clic
                                            <a href="$enlaceSeguro" style="color:#0f2b5b;text-decoration:underline;">aqui</a>
                                            para restablecer tu contrasena.
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding:0 32px 28px;">
                                        <p style="margin:0;font-size:12px;color:#888888;line-height:1.6;">
                                            Si tu no solicitaste este cambio, ignora este correo y tu contrasena seguira
                                            siendo la misma. Por seguridad, no compartas este enlace con nadie.
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
        """.trimIndent()
    }

    private fun escapeHtml(valor: String): String {
        return valor
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;")
    }

}

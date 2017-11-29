package fr.cph.crypto.config

import fr.cph.crypto.controller.CustomPermissionEvaluator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@Configuration
class MethodSecurityConfig : GlobalMethodSecurityConfiguration() {

    @Autowired
    private lateinit var customPermissionEvaluator: CustomPermissionEvaluator

    override fun createExpressionHandler(): MethodSecurityExpressionHandler {
        val expressionHandler = DefaultMethodSecurityExpressionHandler()
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator)
        return expressionHandler
    }
}
package sdk

import kotlinx.coroutines.runBlocking
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.full.callSuspend

class Interop {

    companion object {
        fun makeSync(client: OsuSDK): OsuSDKSync {
            val clazz = client.javaClass

            val handler = InvocationHandler { _, method: Method, args: Array<Any>? ->
                val asyncMethod = client::class.members.firstOrNull { it.name == method.name }
                    ?: throw UnsupportedOperationException("Method not found: ${method.name}")

                return@InvocationHandler if (asyncMethod.isSuspend) {
                    runBlocking {
                        if (args != null) asyncMethod.callSuspend(client, *args)
                        else asyncMethod.callSuspend(client)
                    }
                } else {
                    when (method.name) {
                        "toString" -> client.toString()
                        "hashCode" -> client.hashCode()
                        "equals" -> args?.get(0) === client
                        else -> throw UnsupportedOperationException("The proxy doesn't support this method: ${method.name}")
                    }
                }
            }
            return Proxy.newProxyInstance(
                clazz.classLoader,
                arrayOf(OsuSDKSync::class.java),
                handler
            ) as OsuSDKSync
        }
    }
}
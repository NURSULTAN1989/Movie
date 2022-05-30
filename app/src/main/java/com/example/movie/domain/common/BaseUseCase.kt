package com.example.retrofitexample.domain.common

import com.example.retrofitexample.domain.exception.traceErrorException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

abstract class BaseUseCase<Type, in Params, Params2>() where Type : Any {

    abstract suspend fun run(params: Params? = null, params2: Params2? = null): Type


    fun invoke(scope: CoroutineScope, params: Params?, params2: Params2?, onResult: UseCaseResponse<Type>?) {
        scope.launch {
            try {
                val result = run(params, params2)
                onResult?.onSuccess(result)
            } catch (e: CancellationException) {
                e.printStackTrace()
                onResult?.onError(traceErrorException(e))
            } catch (e: Exception) {
                e.printStackTrace()
                onResult?.onError(traceErrorException(e))
            }
        }
    }

}
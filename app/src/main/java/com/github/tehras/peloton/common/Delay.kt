package com.github.tehras.peloton.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

suspend fun <T> minDelay(
  coroutineScope: CoroutineScope,
  time: Long,
  block: suspend CoroutineScope.() -> T
): T {
  val call = coroutineScope.async { block() }
  call.start()
  delay(time)

  return call.await()
}
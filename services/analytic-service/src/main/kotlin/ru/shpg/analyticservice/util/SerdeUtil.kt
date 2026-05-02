package ru.shpg.analyticservice.util

import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import java.math.BigDecimal

fun bigDecimalSerde(): Serde<BigDecimal> = Serdes.serdeFrom(
    { _, data -> data?.toString()?.toByteArray() },
    { _, data -> data?.let { BigDecimal(String(it)) } }
)
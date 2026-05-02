package ru.shpg.analyticservice.service

import org.springframework.stereotype.Service
import ru.shpg.analyticservice.config.property.AlertProperties
import ru.shpg.analyticservice.model.Alert
import ru.shpg.analyticservice.model.TransactionMetricResult
import java.time.Instant

@Service
class AlertService(
    val alertProperties: AlertProperties
) {


    fun evaluate(result: TransactionMetricResult): Alert? {

        if (result.sum > alertProperties.threshold) {
            return Alert(result.sum, alertProperties.threshold, Instant.now().toEpochMilli())
        }

        return null
    }

}
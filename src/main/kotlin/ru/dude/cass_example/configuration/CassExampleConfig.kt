package ru.dude.cass_example.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories


/**
 * @author Vladimir X
 * Date: 11.04.2026
 */
@Configuration
@EnableCassandraRepositories(
    basePackages = ["ru.dude.cass_example.repository"]
)
internal class CassExampleConfig {
}

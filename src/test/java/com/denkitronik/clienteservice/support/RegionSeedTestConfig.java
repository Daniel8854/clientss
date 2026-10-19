package com.denkitronik.clienteservice.support;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * En los tests, src/test/resources/import.sql (vacío) reemplaza al import.sql de
 * producción, así que la tabla regiones arranca vacía. Cuando se levanta la
 * aplicación completa (@SpringBootTest) garantizamos que exista al menos una
 * región para poder crear clientes. No aplica a @DataJpaTest ni @WebMvcTest.
 */
@Component
public class RegionSeedTestConfig implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    public RegionSeedTestConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM regiones", Integer.class);
        if (total == null || total == 0) {
            jdbcTemplate.update("INSERT INTO regiones (nombre) VALUES ('Suramerica')");
        }
    }
}

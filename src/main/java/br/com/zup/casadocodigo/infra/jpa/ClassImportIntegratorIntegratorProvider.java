package br.com.zup.casadocodigo.infra.jpa;

import br.com.zup.casadocodigo.book.list.ListBookResponse;
import com.vladmihalcea.hibernate.type.util.ClassImportIntegrator;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;

import java.util.List;

public class ClassImportIntegratorIntegratorProvider implements IntegratorProvider {

    @Override
    public List<Integrator> getIntegrators() {
        return List.of(
                new ClassImportIntegrator(
                        List.of(
                                ListBookResponse.class
                        )
                )
        );
    }
}
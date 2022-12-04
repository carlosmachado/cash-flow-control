package br.com.cmachado.cashflowcontrol.infrastructure.hibernate;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.internal.MetadataBuilderImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.util.StringUtils;

import javax.persistence.spi.PersistenceUnitInfo;

@Configuration
@AutoConfigureAfter({ HibernateJpaAutoConfiguration.class })
public class HibernateJavaConfig {

    @ConditionalOnMissingBean({ Metadata.class })
    @Bean
    public Metadata getMetadata(StandardServiceRegistry standardServiceRegistry,
                                PersistenceUnitInfo persistenceUnitInfo) {
        var metadataSources = new MetadataSources(standardServiceRegistry);

        var managedClassNames = persistenceUnitInfo.getManagedClassNames();

        for (String managedClassName : managedClassNames)
            metadataSources.addAnnotatedClassName(managedClassName);


        var builder = new MetadataBuilderImpl(metadataSources)
                .applyImplicitSchemaName("org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl");

        return builder.build();
    }

    @ConditionalOnMissingBean({ StandardServiceRegistry.class })
    @Bean
    public StandardServiceRegistry getStandardServiceRegistry(JpaProperties jpaProperties) {
        var ssrb = new StandardServiceRegistryBuilder();

        var properties = jpaProperties.getProperties();

        ssrb.applySettings(properties);

        return ssrb.build();
    }

    @ConditionalOnMissingBean({ PersistenceUnitInfo.class })
    @Bean
    public PersistenceUnitInfo getPersistenceUnitInfo(BeanFactory beanFactory) {
        var packagesToScan = EntityScanPackages.get(beanFactory).getPackageNames();

        if (packagesToScan.isEmpty() && AutoConfigurationPackages.has(beanFactory))
            packagesToScan = AutoConfigurationPackages.get(beanFactory);

        var persistenceUnitManager = new DefaultPersistenceUnitManager();

        var packagesToScanArr = StringUtils.toStringArray(packagesToScan);
        persistenceUnitManager.setPackagesToScan(packagesToScanArr);
        persistenceUnitManager.afterPropertiesSet();

        return persistenceUnitManager.obtainDefaultPersistenceUnitInfo();
    }
}

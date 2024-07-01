package com.ojt_final.office.global.config;

import com.ojt_final.office.domain.Category;
import com.ojt_final.office.domain.PartnerProduct;
import com.ojt_final.office.domain.StandardProduct;
import com.ojt_final.office.domain.Uploadable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ExcelDomainConfig {

    @Bean
    public List<Class<? extends Uploadable>> uploadableDomainClasses() {
        return List.of(Category.class, PartnerProduct.class, StandardProduct.class);
    }
}

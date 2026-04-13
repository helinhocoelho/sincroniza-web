package com.sincroniza.syncstock;

import com.sincroniza.syncstock.model.Permissao;
import com.sincroniza.syncstock.model.Usuario;
import com.sincroniza.syncstock.service.UsuarioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SyncstockApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SyncstockApplication.class, args);
    }
}

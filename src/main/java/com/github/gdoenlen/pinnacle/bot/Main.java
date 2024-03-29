// SPDX-License-Identifier: GPL-3.0-or-later
package com.github.gdoenlen.pinnacle.bot;

import java.util.concurrent.CountDownLatch;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import io.smallrye.config.inject.ConfigExtension;
import org.hibernate.validator.cdi.ValidationExtension;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SeContainer container = SeContainerInitializer.newInstance()
            .addExtensions(new ConfigExtension(), new ValidationExtension())
            .addPackages(true, Main.class)
            .initialize();

        try (container) {
            var latch = new CountDownLatch(1);
            Runtime.getRuntime().addShutdownHook(new Thread(latch::countDown));
            latch.await();
        }
    }
}

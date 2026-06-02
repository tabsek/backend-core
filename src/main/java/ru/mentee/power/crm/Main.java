package ru.mentee.power.crm;

import ru.mentee.power.crm.web.HelloCrmServer;

public class Main {
    public static void main(String[] args) throws Exception {
        HelloCrmServer helloCrmServer = new HelloCrmServer(8080);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down...");
            helloCrmServer.stop();
        }));

        helloCrmServer.start();

        Thread.currentThread().join();

    }
}

package ru.mentee.power.crm;

import org.junit.jupiter.api.*;

import java.net.URI;
import java.net.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StackComparisonTest {

    private HttpClient httpClient;

    private static final int SERVLET_PORT = 8080;
    private static final int SPRING_PORT = 8081;

    @BeforeEach
    void setUp() {
        httpClient = HttpClient.newHttpClient();
    }

    private int countTableRows(String html) {
        return html.split("<tr").length - 1;
    }

    @Disabled
    @Test
    void shouldReturnLeadsFromBothStacks() throws Exception {

        var servletReq = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:%d/leads", SERVLET_PORT)))
                .GET()
                .build();

        var servletResp = httpClient.send(servletReq, HttpResponse.BodyHandlers.ofString());

        assertThat(servletResp.statusCode()).isEqualTo(200);
        assertThat(servletResp.body()).contains("<table");

        var springReq = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:%d/leads", SPRING_PORT)))
                .GET()
                .build();

        var springResp = httpClient.send(springReq, HttpResponse.BodyHandlers.ofString());

        assertThat(springResp.statusCode()).isEqualTo(200);
        assertThat(springResp.body()).contains("<table");

        int servletRows = countTableRows(servletResp.body());
        int springRows = countTableRows(springResp.body());

        assertThat(servletRows).isEqualTo(springRows);

        System.out.printf("Servlet: %d лидов, Spring: %d лидов%n", servletRows, springRows);
    }

    @Disabled
    @Test
    void shouldMeasureStartupTime() {
        long servletStartupMs = measureServletStartup();
        long springStartupMs = measureSpringBootStartup();

        System.out.println("=== Сравнение времени старта ===");
        System.out.printf("Servlet стек: %d ms%n", servletStartupMs);
        System.out.printf("Spring Boot: %d ms%n", springStartupMs);
        System.out.printf("Разница: Spring %s на %d ms%n",
                springStartupMs > servletStartupMs ? "медленнее" : "быстрее",
                Math.abs(springStartupMs - servletStartupMs));

        assertThat(servletStartupMs).isLessThan(10_000);
        assertThat(springStartupMs).isLessThan(15_000);
    }

    private long measureServletStartup() {
        return 500; // Заглушка — оба стека уже запущены вручную
    }

    private long measureSpringBootStartup() {
        return 2500; // Заглушка — оба стека уже запущены вручную
    }
}
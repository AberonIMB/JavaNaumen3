package com.kriger.CinemaManager;


import com.kriger.CinemaManager.dto.SessionRequest;
import com.kriger.CinemaManager.dto.SessionResponse;
import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.model.Movie;
import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.service.interfaces.HallService;
import com.kriger.CinemaManager.service.interfaces.MovieService;
import com.kriger.CinemaManager.service.interfaces.SessionService;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


@SpringBootTest
public class SessionControllerTest {

    private final HallService hallService;
    private final MovieService movieService;
    private final SessionService sessionService;

    private Hall hall;
    private Movie movie;
    private String sessionId;


    @Autowired
    public SessionControllerTest(HallService hallService, MovieService movieService, SessionService sessionService) {
        this.hallService = hallService;
        this.movieService = movieService;
        this.sessionService = sessionService;
    }

    private final String baseUrl = "http://localhost:8080/api/sessions";

    @BeforeEach
    void setUp() {
        hall = hallService.createHall("Hall 1", 10, 10);
        movie = movieService.createMovie("Title 1", "Genre 1", "Desc 1", 120);

        sessionId = loginAndGetSessionId();
    }

    @AfterEach
    void tearDown() {
        sessionService.deleteAll();
        movieService.deleteAll();
        hallService.deleteAll();
    }

    /**
     * Проверка корректного создания сеанса
     */
    @Test
    public void testCreateSessionSuccess() {

        SessionRequest sessionRequest = new SessionRequest(
                LocalDateTime.parse("2025-04-29T12:00:00"), hall.getId(), movie.getId());

        SessionResponse sessionResponse = RestAssured.given()
                .cookie("JSESSIONID", sessionId)
                .body(sessionRequest)
                .contentType(ContentType.JSON)
                .post(baseUrl + "/create")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(SessionResponse.class);

        Assertions.assertEquals(sessionRequest.getStartTime(), sessionResponse.getStartTime());
        Assertions.assertEquals(hall.getName(), sessionResponse.getHallName());
        Assertions.assertEquals(movie.getTitle(), sessionResponse.getMovieTitle());
    }

    /**
     * Проверка создания пересекающегося сеанса
     */
    @Test
    public void testCreateSessionOverlapping() {
        Session session = sessionService.createSession(
                LocalDateTime.now(), hall.getId(), movie.getId());

        SessionRequest sessionRequest = new SessionRequest(
                LocalDateTime.now().plusMinutes(20), hall.getId(), movie.getId());

        RestAssured.given()
                .cookie("JSESSIONID", sessionId)
                .body(sessionRequest)
                .contentType(ContentType.JSON)
                .post(baseUrl + "/create")
                .then()
                .log().all()
                .statusCode(400)
                .body("message",
                        Matchers.containsString("Ваш сеанс пересекается с другим сеансом: " + session));
    }

    /**
     * Проверка создания сеанса с некорректной датой
     */
    @Test
    public void createSessionWithInvalidData() {

        String invalidJson = """
        {
            "startTime": "qwerty",
            "hallId": %d,
            "movieId": %d
        }
        """.formatted(hall.getId(), movie.getId());

        RestAssured.given()
                .cookie("JSESSIONID", sessionId)
                .body(invalidJson)
                .contentType(ContentType.JSON)
                .post(baseUrl + "/create")
                .then()
                .log().all()
                .statusCode(500).body(
                        "message", Matchers.containsString("Text 'qwerty' could not be parsed at index 0"));
    }

    /**
     * Проверка создания сеанса с некорректным идентификатором зала
     */
    @Test
    public void testCreateSessionWithNullHallId() {
        testIdError(null, movie.getId(), 500, "The given id must not be null");
    }

    /**
     * Проверка создания сеанса с несуществующим залом
     */
    @Test
    public void testCreateSessionWithNotExistHall() {
        testIdError(11111L, movie.getId(), 400, "Hall not found");
    }

    /**
     * Проверка создания сеанса с некорректным идентификатором фильма
     */
    @Test
    public void testCreateSessionWithNullMovieId(){
        testIdError(hall.getId(), null, 500, "The given id must not be null");
    }

    /**
     * Проверка создания сеанса с несуществующим фильмом
     */
    @Test
    public void testCreateSessionWithNotExistMovie(){
        testIdError(hall.getId(), 11111L, 400, "Movie not found");
    }

    /**
     * Проверка корректного получения фильмов по времени и залу
     */
    @Test
    public void testGetActiveSessionsByTimeAndHall() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime now = LocalDateTime.now();

        Session session1 = sessionService.createSession(
                now, hall.getId(), movie.getId());

        Session session2 = sessionService.createSession(
                now.plusHours(1), hall.getId(), movie.getId());

        List<SessionResponse> sessions = RestAssured.given()
                .cookie("JSESSIONID", sessionId)
                .queryParam("start", now.minusHours(1).format(formatter))
                .queryParam("end", now.plusHours(2).format(formatter))
                .queryParam("hallId", hall.getId())
                .get(baseUrl + "/filterTimeAndHall")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(new TypeRef<>() {});

        Assertions.assertEquals(2, sessions.size());

        assertEqualsSessionAndResponse(session1, sessions.getFirst());
        assertEqualsSessionAndResponse(session2, sessions.get(1));
    }

    /**
     * Проверка получения активных сеансов по дате и залу с некорректными датами
     */
    @Test
    public void testGetActiveSessionsByTimeAndHallWithInvalidDate() {
        RestAssured.given()
                .cookie("JSESSIONID", sessionId)
                .queryParam("start", "abc")
                .queryParam("end", "xyz")
                .queryParam("hallId", hall.getId())
                .get(baseUrl + "/filterTimeAndHall")
                .then()
                .log().all()
                .statusCode(500)
                .body("message", Matchers.containsString(
                        "Failed to convert value of type 'java.lang.String' to required type 'java.time.LocalDateTime'"));
    }

    /**
     * Проверка получения активных сеансов по дате и залу с несуществующим залом
     */
    @Test
    public void testGetActiveSessionsByTimeAndHallWithNotExistHall() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime now = LocalDateTime.now();

        RestAssured.given()
                .cookie("JSESSIONID", sessionId)
                .queryParam("start", now.minusHours(1).format(formatter))
                .queryParam("end", now.plusHours(2).format(formatter))
                .queryParam("hallId", 11111L)
                .get(baseUrl + "/filterTimeAndHall")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", Matchers.containsString("Hall not found"));
    }

    /**
     * Проверка получения сеансов по названию фильма
     */
    @Test
    public void testGetSessionsByMovieTitleSuccess() {
        LocalDateTime now = LocalDateTime.now();

        Session session1 = sessionService.createSession(now, hall.getId(), movie.getId());
        Movie movie2 = movieService.createMovie("Movie 2", "Genre 2", "Description 2", 120);
        Session session2 = sessionService.createSession(now.plusHours(3), hall.getId(), movie2.getId());

        List<SessionResponse> sessions = RestAssured.given()
                .cookie("JSESSIONID", sessionId)
                .queryParam("title", movie.getTitle())
                .get(baseUrl + "/filterMovie")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(new TypeRef<>() {});

        Assertions.assertEquals(1, sessions.size());

        assertEqualsSessionAndResponse(session1, sessions.getFirst());
    }

    /**
     * Проверка получения сеансов по названию фильма с несуществующим фильмом
     */
    @Test
    public void testGetSessionsByMovieTitleWithNotExistMovie() {

        List<SessionResponse> sessions = RestAssured.given()
                .cookie("JSESSIONID", sessionId)
                .queryParam("title", "")
                .get(baseUrl + "/filterMovie")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(new TypeRef<>() {});

        Assertions.assertEquals(0, sessions.size());
    }

    /**
     * Логин и получение JSESSIONID
     */
    private String loginAndGetSessionId() {
        Response loginResponse = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "2")
                .formParam("password", "111222")
                .post("http://localhost:8080/auth/login")
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .response();

        return loginResponse.getCookie("JSESSIONID");
    }

    /**
     * Проверка создания сеанса с некорректным id параметра
     */
    private void testIdError(Long hallId, Long movieId, int code, String message) {
        SessionRequest sessionRequest = new SessionRequest(
                LocalDateTime.parse("2025-04-29T20:00:00"), hallId, movieId);

        RestAssured.given()
                .cookie("JSESSIONID", sessionId)
                .body(sessionRequest)
                .contentType(ContentType.JSON)
                .post(baseUrl + "/create")
                .then()
                .log().all()
                .statusCode(code)
                .body("message", Matchers.containsString(message));
    }

    private void assertEqualsSessionAndResponse(Session session, SessionResponse sessionResponse) {
        Assertions.assertEquals(session.getStartTime().truncatedTo(ChronoUnit.SECONDS),
                sessionResponse.getStartTime().truncatedTo(ChronoUnit.SECONDS));
        Assertions.assertEquals(session.getHall().getName(), sessionResponse.getHallName());
        Assertions.assertEquals(session.getMovie().getTitle(), sessionResponse.getMovieTitle());
        Assertions.assertEquals(session.getId(), sessionResponse.getId());

    }
}
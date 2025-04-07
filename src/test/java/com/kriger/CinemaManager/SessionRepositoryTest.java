package com.kriger.CinemaManager;

import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.model.Movie;
import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.repository.CustomSessionRepository;
import com.kriger.CinemaManager.repository.HallRepository;
import com.kriger.CinemaManager.repository.MovieRepository;
import com.kriger.CinemaManager.repository.SessionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class SessionRepositoryTest {

    private SessionRepository sessionRepository;
    private HallRepository hallRepository;
    private MovieRepository movieRepository;
    private CustomSessionRepository customSessionRepository;

    private Session session1;
    private Session session2;
    private Hall hall;

    @Autowired
    public void SessionRepositoryTest(SessionRepository sessionRepository,
                                      HallRepository hallRepository,
                                      MovieRepository movieRepository,
                                      CustomSessionRepository customSessionRepository) {
        this.customSessionRepository = customSessionRepository;
        this.sessionRepository = sessionRepository;
        this.hallRepository = hallRepository;
        this.movieRepository = movieRepository;
    }


    @BeforeEach
    public void setUp() {
        hall = hallRepository.save(new Hall("1", 10, 10));
        Movie movie = movieRepository.save(new Movie("1", "1", "1", 120));
        session1 = sessionRepository.save(new Session(LocalDateTime.now(), hall, movie));
        session2 = sessionRepository.save(new Session(LocalDateTime.now().plusMinutes(180), hall, movie));
    }

    /**
     * Проверяет корректность поиска всех сеансов в промежутке времени
     */
    @Test
    public void testFindByIsActiveTrueAndStartTimeBetweenAllSessions() {

        List<Session> twoSessions = sessionRepository.findByIsActiveTrueAndStartTimeBetweenAndHall(
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now().plusMinutes(200),
                hall);

        checkFindAllSessions(twoSessions);
    }

    /**
     * Проверяет корректность нахождения пустого списка сеансов в промежутке времени
     */
    @Test
    public void testFindByIsActiveTrueAndStartTimeBetweenEmptyList() {
        List<Session> emptyList = sessionRepository.findByIsActiveTrueAndStartTimeBetweenAndHall(
                LocalDateTime.now().plusMinutes(200),
                LocalDateTime.now().plusMinutes(210),
                hall);

        checkFindZeroSessions(emptyList);
    }

    /**
     * Проверяет корректность нахождения части сеансов в промежутке времени
     */
    @Test
    public void testFindByIsActiveTrueAndStartTimeBetweenOneSession() {

        List<Session> oneSession = sessionRepository.findByIsActiveTrueAndStartTimeBetweenAndHall(
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now().plusMinutes(10),
                hall);

        checkFindOneSession(oneSession);
    }

    /**
     * Проверяет корректность поиска сеансов по названию фильма
     */
    @Test
    public void testFindByMovieTitle() {
        List<Session> sessions = sessionRepository.findByMovieTitle("1");

        checkFindByMovieTitle(sessions);
    }

    /**
     * Проверяет корректность поиска всех сеансов в промежутке времени в кастомном репозитории
     */
    @Test
    public void testFindByIsActiveTrueAndStartTimeBetweenAllSessionsInCustom() {

        List<Session> twoSessions = customSessionRepository.findByIsActiveTrueAndStartTimeBetweenAndHall(
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now().plusMinutes(200),
                hall);

        checkFindAllSessions(twoSessions);
    }

    /**
     * Проверяет корректность нахождения пустого списка сеансов в промежутке времени в кастомном репозитории
     */
    @Test
    public void testFindByIsActiveTrueAndStartTimeBetweenEmptyListInCustom() {
        List<Session> emptyList = customSessionRepository.findByIsActiveTrueAndStartTimeBetweenAndHall(
                LocalDateTime.now().plusMinutes(200),
                LocalDateTime.now().plusMinutes(210),
                hall);

        checkFindZeroSessions(emptyList);
    }

    /**
     * Проверяет корректность нахождения части сеансов в промежутке времени в кастомном репозитории
     */
    @Test
    public void testFindByIsActiveTrueAndStartTimeBetweenOneSessionInCustom() {

        List<Session> oneSession = customSessionRepository.findByIsActiveTrueAndStartTimeBetweenAndHall(
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now().plusMinutes(10),
                hall);

        checkFindOneSession(oneSession);
    }

    /**
     * Проверяет корректность поиска сеансов по названию фильма в кастомном репозитории
     */
    @Test
    public void testFindByMovieTitleInCustom() {
        List<Session> sessions = customSessionRepository.findByMovieTitle("1");

        checkFindByMovieTitle(sessions);
    }

    private void checkFindAllSessions(List<Session> twoSessions) {
        Assertions.assertNotNull(twoSessions);
        Assertions.assertEquals(2, twoSessions.size());
        Assertions.assertEquals(session1, twoSessions.getFirst());
        Assertions.assertEquals(session2, twoSessions.get(1));
    }

    private void checkFindOneSession(List<Session> oneSession) {
        Assertions.assertNotNull(oneSession);
        Assertions.assertEquals(1, oneSession.size());
        Assertions.assertEquals(session1, oneSession.getFirst());
    }

    private void checkFindZeroSessions(List<Session> emptyList) {
        Assertions.assertNotNull(emptyList);
        Assertions.assertEquals(0, emptyList.size());
    }

    private void checkFindByMovieTitle(List<Session> sessions) {
        Assertions.assertNotNull(sessions);
        Assertions.assertEquals(2, sessions.size());
        Assertions.assertEquals(session1, sessions.getFirst());
        Assertions.assertEquals("1", sessions.get(0).getMovie().getTitle());
        Assertions.assertEquals(session2, sessions.get(1));
        Assertions.assertEquals("1", sessions.get(1).getMovie().getTitle());
    }
}

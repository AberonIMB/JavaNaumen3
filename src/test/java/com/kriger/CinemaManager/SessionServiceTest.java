package com.kriger.CinemaManager;

import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.model.Movie;
import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.repository.SessionRepository;
import com.kriger.CinemaManager.service.impl.SessionServiceImpl;
import com.kriger.CinemaManager.service.interfaces.HallService;
import com.kriger.CinemaManager.service.interfaces.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepositoryMock;

    @Mock
    private HallService hallServiceMock;

    @Mock
    private MovieService movieServiceMock;

    @InjectMocks
    private SessionServiceImpl sessionService;

    private final Hall testHall = new Hall();
    private final Movie testMovie = new Movie("1", "1", "1", 120);
    private final LocalDateTime testStartTime = LocalDateTime.now();
    private final Session testSession = new Session(testStartTime, testHall, testMovie);

    /**
     * Проверка корректного создания сеанса
     */
    @Test
    public void testCreateSessionSuccess() {
        Mockito.when(hallServiceMock.getHallById(1L)).thenReturn(testHall);
        Mockito.when(movieServiceMock.getMovieById(1L)).thenReturn(testMovie);
        Mockito.when(sessionRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        Session session = sessionService.createSession(testStartTime, 1L, 1L);

        Assertions.assertEquals(testStartTime, session.getStartTime());
        Assertions.assertEquals(testHall, session.getHall());
        Assertions.assertEquals(testMovie, session.getMovie());

        Mockito.verify(sessionRepositoryMock, Mockito.times(1)).save(Mockito.any(Session.class));
    }

    /**
     * Проверяет случай, когда создаваемый сеанс начинается перед стартом и заканчивается после старта другого сеанса
     */
    @Test
    public void testCreateOverlappingSessionWithStartBeforeStartAndEndAfterStart() {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(60);

        testCreateOverlappingSessions(startTime, testMovie);
    }

    /**
     * Проверяет случай, когда создаваемый сеанс начинается перед концом и заканчивается после конца другого сеанса
     */
    @Test
    public void CreateOverlappingSessionWithStartBeforeEndAndEndAfterEnd() {
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(60);

        testCreateOverlappingSessions(startTime, testMovie);
    }

    /**
     * Проверяет случай, когда создаваемый сеанс начинается перед стартом и заканчивается после конца другого сеанса
     */
    @Test
    public void CreateOverlappingSessionWithStartBeforeStartAndEndAfterEnd() {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(10);
        Movie testMovie2 = new Movie("2", "2", "2", 180);

        testCreateOverlappingSessions(startTime, testMovie2);
    }

    /**
     * Проверяет случай, когда создаваемый сеанс начинается после старта и заканчивается перед концом другого сеанса
     */
    @Test
    public void CreateOverlappingSessionWithStartAfterStartAndEndBeforeEnd() {
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(10);
        Movie testMovie2 = new Movie("2", "2", "2", 60);

        testCreateOverlappingSessions(startTime, testMovie2);
    }

    /**
     * Проверяет случай, когда время начала и конца сеансов совпадают
     */
    @Test
    public void CreateOverlappingSessionWithStartAndEndEqual() {
        testCreateOverlappingSessions(testStartTime, testMovie);
    }

    /**
     * Проверка корректного удаления сеанса
     */
    @Test
    public void DeleteSessionSuccess() {
        Mockito.when(sessionRepositoryMock.findById(1L)).thenReturn(Optional.of(testSession));

        Session session = sessionService.deleteSession(1L);

        Mockito.verify(sessionRepositoryMock, Mockito.times(1)).delete(testSession);
        assertSessionEquals(testSession, session);
    }

    /**
     * Проверка удаления несуществующего сеанса
     */
    @Test
    public void DeleteSessionNotFound() {
        Mockito.when(sessionRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> sessionService.deleteSession(1L));

        Assertions.assertEquals("Session not found", e.getMessage());

        Mockito.verify(sessionRepositoryMock, Mockito.times(0))
                .delete(Mockito.any(Session.class));
    }

    /**
     * Проверка получения списка сеансов
     */
    @Test
    public void GetAllSessions() {
        List<Session> sessions = new ArrayList<>(List.of(testSession));

        Mockito.when(sessionRepositoryMock.findAll()).thenReturn(sessions);

        List<Session> allSessions = sessionService.getAllSessions();

        Mockito.verify(sessionRepositoryMock, Mockito.times(1)).findAll();
        Assertions.assertEquals(sessions.size(), allSessions.size());
        assertSessionEquals(testSession, allSessions.getFirst());
    }

    /**
     * Проверка корректного получения сеанса по id
     */
    @Test
    public void GetSessionSuccess() {
        Mockito.when(sessionRepositoryMock.findById(1L)).thenReturn(Optional.of(testSession));

        Session session = sessionService.getSession(1L);

        Mockito.verify(sessionRepositoryMock, Mockito.times(1)).findById(1L);
        assertSessionEquals(testSession, session);
    }

    /**
     * Проверка получения несуществующего сеанса
     */
    @Test
    public void GetSessionNotFound() {
        Mockito.when(sessionRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> sessionService.getSession(1L));

        Assertions.assertEquals("Session not found", e.getMessage());
    }

    /**
     * Проверка получения активных сеансов по времени и залу
     */
    @Test
    public void GetActiveSessionsByTimeAndHall() {
        LocalDateTime testEndTime = LocalDateTime.now().plusMinutes(10);
        List<Session> sessions = new ArrayList<>(List.of(testSession));

        Mockito.when(hallServiceMock.getHallById(1L)).thenReturn(testHall);
        Mockito.when(sessionRepositoryMock.findByIsActiveTrueAndStartTimeBetweenAndHall(testStartTime, testEndTime, testHall)).thenReturn(sessions);

        List<Session> activeSessions = sessionService.getActiveSessionsByTimeAndHall(1L, testStartTime, testEndTime);

        Mockito.verify(sessionRepositoryMock, Mockito.times(1)).findByIsActiveTrueAndStartTimeBetweenAndHall(testStartTime, testEndTime, testHall);

        Assertions.assertEquals(sessions.size(), activeSessions.size());
        assertSessionEquals(testSession, activeSessions.getFirst());
    }

    /**
     * Проверяет корректное возвращение пустого списка при отсутствии сеансов удовлетворяющих условиям
     */
    @Test
    public void getActiveSessionsByTimeAndHallWithEmptyList() {
        LocalDateTime testEndTime = LocalDateTime.now().plusMinutes(10);

        Mockito.when(hallServiceMock.getHallById(1L)).thenReturn(testHall);
        Mockito.when(sessionRepositoryMock.findByIsActiveTrueAndStartTimeBetweenAndHall(testStartTime, testEndTime, testHall)).thenReturn(new ArrayList<>());

        List<Session> activeSessions = sessionService.getActiveSessionsByTimeAndHall(1L, testStartTime, testEndTime);

        Mockito.verify(sessionRepositoryMock, Mockito.times(1)).findByIsActiveTrueAndStartTimeBetweenAndHall(testStartTime, testEndTime, testHall);

        Assertions.assertEquals(0, activeSessions.size());
    }

    /**
     * Проверяет что выбрасывается ошибка, если зал не существует
     */
    @Test
    public void getActiveSessionsByTimeAndHallWithHallNotFound() {
        LocalDateTime testEndTime = LocalDateTime.now().plusMinutes(10);

        Mockito.when(hallServiceMock.getHallById(1L)).thenThrow(new IllegalArgumentException("Hall not found"));

        Exception e = Assertions.assertThrows(IllegalArgumentException.class,
                () -> sessionService.getActiveSessionsByTimeAndHall(1L, testStartTime, testEndTime));

        Assertions.assertEquals("Hall not found", e.getMessage());

        Mockito.verify(sessionRepositoryMock, Mockito.times(0)).findByIsActiveTrueAndStartTimeBetweenAndHall(testStartTime, testEndTime, testHall);
    }

    /**
     * Проверка созданного сеанса на пересечение с другими сеансами
     */
    private void testCreateOverlappingSessions(LocalDateTime startTime, Movie testMovie) {
        Mockito.when(hallServiceMock.getHallById(1L)).thenReturn(testHall);
        Mockito.when(movieServiceMock.getMovieById(1L)).thenReturn(testMovie);
        Mockito.when(sessionRepositoryMock.findAll()).thenReturn(List.of(testSession));

        Exception e = Assertions.assertThrows(IllegalStateException.class,
                () -> sessionService.createSession(startTime, 1L, 1L));

        Assertions.assertEquals("Ваш сеанс пересекается с другим сеансом: " + testSession, e.getMessage());

        Mockito.verify(sessionRepositoryMock, Mockito.times(0)).save(Mockito.any(Session.class));
    }

    /**
     * Проверяет, равны ли друг другу сеансы
     */
    private void assertSessionEquals(Session expected, Session actual) {
        Assertions.assertEquals(expected.getStartTime(), actual.getStartTime());
        Assertions.assertEquals(expected.getHall(), actual.getHall());
        Assertions.assertEquals(expected.getMovie(), actual.getMovie());
    }
}
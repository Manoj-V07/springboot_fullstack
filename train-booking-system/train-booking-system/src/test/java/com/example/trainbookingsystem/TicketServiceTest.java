package com.example.trainbookingsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.trainbookingsystem.entity.Ticket;
import com.example.trainbookingsystem.entity.Train;
import com.example.trainbookingsystem.entity.User;
import com.example.trainbookingsystem.repository.TicketRepository;
import com.example.trainbookingsystem.service.TicketService;
import com.example.trainbookingsystem.service.TrainService;
import com.example.trainbookingsystem.service.UserService;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserService userService;

    @Mock
    private TrainService trainService;

    @InjectMocks
    private TicketService ticketService;

    private User user;
    private Train train;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(10L);
        user.setName("John Doe");

        train = new Train();
        train.setId(20L);
        train.setName("Express");
        train.setBasePrice(1000.0);
        train.setDiscountPercentage(22.0);

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUser(user);
        ticket.setTrain(train);
        ticket.setBookingDate(LocalDateTime.of(2025, 10, 16, 9, 15, 45));
        ticket.setFinalPrice(780.0);
    }

    @Test
    void getAllTickets_ShouldReturnListOfTickets() {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> result = ticketService.getAllTickets();

        assertEquals(1, result.size());
        assertEquals(ticket.getId(), result.get(0).getId());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    void getTicketById_WhenTicketExists_ShouldReturnTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Optional<Ticket> result = ticketService.getTicketById(1L);

        assertTrue(result.isPresent());
        assertEquals(ticket.getId(), result.get().getId());
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void getTicketById_WhenTicketDoesNotExist_ShouldReturnEmpty() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Ticket> result = ticketService.getTicketById(1L);

        assertFalse(result.isPresent());
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void createTicket_ShouldSaveAndReturnTicket() {
        when(userService.getUserById(user.getId())).thenReturn(Optional.of(user));
        when(trainService.getTrainById(train.getId())).thenReturn(Optional.of(train));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket result = ticketService.createTicket(user.getId(), train.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(train.getId(), result.getTrain().getId());
        assertEquals(780.0, result.getFinalPrice()); // 1000 - 22% = 780
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void updateTicket_WhenTicketExists_ShouldUpdateAndReturnTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User newUser = new User();
        newUser.setId(11L);
        Train newTrain = new Train();
        newTrain.setId(21L);
        newTrain.setBasePrice(1200.0);
        newTrain.setDiscountPercentage(10.0);

        Ticket details = new Ticket();
        details.setUser(newUser);
        details.setTrain(newTrain);

        Ticket result = ticketService.updateTicket(1L, details);

        assertEquals(newUser.getId(), result.getUser().getId());
        assertEquals(newTrain.getId(), result.getTrain().getId());
        assertEquals(1080.0, result.getFinalPrice()); // 1200 - 10% = 1080
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void updateTicket_WhenTicketDoesNotExist_ShouldThrowException() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        Ticket details = new Ticket();

        assertThrows(RuntimeException.class, () -> ticketService.updateTicket(1L, details));
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void deleteTicket_ShouldDeleteTicket() {
        doNothing().when(ticketRepository).deleteById(1L);

        ticketService.deleteTicket(1L);

        verify(ticketRepository, times(1)).deleteById(1L);
    }
}


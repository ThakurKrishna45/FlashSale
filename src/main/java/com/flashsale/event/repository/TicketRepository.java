package com.flashsale.event.repository;

import com.flashsale.event.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketType, UUID> {
}

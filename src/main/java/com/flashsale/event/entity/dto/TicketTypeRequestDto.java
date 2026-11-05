package com.flashsale.event.entity.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TicketTypeRequestDto {

    @NotBlank(message = "Ticket type name is required")
    @Size(max = 100, message = "Ticket type name cannot exceed 100 characters")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;

    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter uppercase code")
    private String currency;

    @NotNull(message = "Total quantity is required")
    @Positive(message = "Total quantity must be greater than zero")
    private Integer totalQuantity;
}

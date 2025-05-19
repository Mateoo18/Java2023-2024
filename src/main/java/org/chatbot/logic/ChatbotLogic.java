package org.chatbot.logic;

import org.chatbot.database.IDatabaseConnection;
import org.chatbot.response.Response;
import org.chatbot.response.ResponseType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatbotLogic {
    private final IDatabaseConnection dbConnection;
    private boolean awaitingConfirmation;
    private int pendingReservationId;
    private boolean isFirstMessage = true;

    public ChatbotLogic(IDatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.awaitingConfirmation = false;
        this.pendingReservationId = -1;
    }

    public Response processInput(String input) {
        if (isFirstMessage) {
            isFirstMessage = false;
            return new Response(ResponseType.WELCOME, ResponseType.WELCOME.getMessage());
        }

        try {
            if (input.equalsIgnoreCase(CommandConstants.RESERVE)) {
                dbConnection.addReservation("Klient", "2023-01-01 19:00", 2);
                return new Response(ResponseType.RESERVATION_SUCCESS, ResponseType.RESERVATION_SUCCESS.getMessage());
            } else if (input.equalsIgnoreCase(CommandConstants.SHOW_RESERVATIONS)) {
                ResultSet rs = dbConnection.listReservations();
                StringBuilder sb = new StringBuilder(ResponseType.RESERVATION_LIST.getMessage() + " ");
                while (rs.next()) {
                    int reservationId = rs.getInt("id");
                    String customerName = rs.getString("customer_name");
                    String reservationTime = rs.getString("reservation_time");
                    int numberOfGuests = rs.getInt("number_of_guests");
                    String message = String.format("id: %d, customer_name: %s, reservation_time: %s, number_of_guests: %d ",
                            reservationId, customerName, reservationTime, numberOfGuests);
                    sb.append(message);
                }
                rs.close();
                return new Response(ResponseType.RESERVATION_LIST, sb.toString());
            } else if (awaitingConfirmation) {
                if (input.equalsIgnoreCase(CommandConstants.CONFIRM)) {
                    dbConnection.deleteReservation(pendingReservationId);
                    Response response = new Response(ResponseType.RESERVATION_DELETED, ResponseType.RESERVATION_DELETED.getMessage(pendingReservationId));
                    resetConfirmationState();
                    return response;
                } else if (input.equalsIgnoreCase(CommandConstants.DENY)) {
                    resetConfirmationState();
                    return new Response(ResponseType.CANCELLATION_CONFIRMED, ResponseType.CANCELLATION_CONFIRMED.getMessage());
                } else {
                    return new Response(ResponseType.ERROR, ResponseType.ERROR.getMessage("Proszę odpowiedzieć 'tak' lub 'nie' na potwierdzenie usunięcia rezerwacji."));
                }
            } else if (input.startsWith(CommandConstants.DELETE_RESERVATION)) {
                try {
                    int reservationId = parseReservationId(input);
                    pendingReservationId = reservationId;
                    awaitingConfirmation = true;
                    return new Response(ResponseType.CONFIRMATION_REQUEST, ResponseType.CONFIRMATION_REQUEST.getMessage(reservationId));
                } catch (NumberFormatException e) {
                    return new Response(ResponseType.ERROR, ResponseType.ERROR.getMessage("Brak liczby całkowitej w podanej wiadomości"));
                }
            }

        } catch (SQLException e) {
            return new Response(ResponseType.ERROR, ResponseType.ERROR.getMessage(e.getMessage()));
        }

        return new Response(ResponseType.INVALID_COMMAND, ResponseType.INVALID_COMMAND.getMessage());
    }

    public void exit() {
        try {
            dbConnection.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int parseReservationId(String input) {
        String numericPart = input.replaceAll("\\D", "");
        return numericPart.isEmpty() ? -1 : Integer.parseInt(numericPart);
    }

    private void resetConfirmationState() {
        awaitingConfirmation = false;
        pendingReservationId = -1;
    }
}

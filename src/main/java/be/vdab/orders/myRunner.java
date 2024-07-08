package be.vdab.orders;

import be.vdab.orders.orders.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.rmi.ServerError;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@Component
public class myRunner implements CommandLineRunner {
    private final WerknemerService werknemerService;
    private final OrderService orderService;

    public myRunner(WerknemerService werknemerService, OrderService orderService){
        this.werknemerService = werknemerService;
        this.orderService = orderService;
    }


    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Geef het id van de gewenste werknemer.");
        int id = scanner.nextInt();
        try {
            Werknemer werknemer = werknemerService.findById(id);
            System.out.println("Je werknemer van vandaag is: "+ werknemer.getVoornaam() + " "+ werknemer.getFamilienaam());
            System.out.println("\nMaak uw keuze:\n" + "-".repeat(14));
            System.out.println("1.Order toevoegen\n2.Lijst orders\n3.Order goedkeuren");
            String keuze = scanner.next();
            switch (keuze){
            case "1" :
                System.out.println("Geef de omschrijving van de order");
                String omschrijving = scanner.next();
                System.out.println("Wat is het bedrag");
                BigDecimal bedrag = scanner.nextBigDecimal();
                orderService.voegOrderToe(new Order(0, werknemer.getId(), omschrijving, bedrag, werknemer.getChefId() == null ? LocalDateTime.now() : null));
                System.out.println("Order toegevoegd");
                break;
            case "2" :
                List<OrdersPerWerknemer> orders = orderService.findOrdersByWerknemerId(werknemer.getId());
                if (orders.isEmpty()){
                    System.err.println("Werknemer met id " +werknemer.getId() + " heeft nog geen orders opgenomen");
                }
                        orders.forEach(order-> System.out.println(
                        "Id: "+order.id() + " : " +
                        "Omschrijving: "+ order.omschrijving() + " : " +
                        "Bedrag: " + order.bedrag() + " : " +
                        "Status order: " + (order.goedgekeurd() == null ? "Nog niet" :order.goedgekeurd())));
                break;
            case "3" :
                    if(werknemer.getChefId() != null){
                        throw new IllegalArgumentException("U kan geen goedkeuringen doen");
                    }
                    System.out.println("Geef een ordernummer dat je wil goedkeuren");
                    int orderId = scanner.nextInt();
                    orderService.updateGoedkeuringOrder(orderId, werknemer);
                    System.out.println("Goedkeuring gelukt");
                break;
            default:
                System.out.println("U gaf een verkeerde keuze in");
            }


        }
        catch(WerknemerNietGevondenException ex){
            System.err.println("Werknemer met id " + ex.getId() + " bestaat niet.");
        }
        catch(IllegalArgumentException ex){
            System.err.println(ex.getMessage());
        }
        catch(OrderNietGevondenException ex){
            System.err.println("Order met id " + ex.getId() + " bestaat niet.");
        }





    }




}

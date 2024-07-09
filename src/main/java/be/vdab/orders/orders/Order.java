package be.vdab.orders.orders;



import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Order {
    private final int id;
    private final int werknemerId;
    private String omschrijving;
    private BigDecimal bedrag;
    private LocalDateTime goedgekeurd;

    public Order(int id, int werknemerId, String omschrijving, BigDecimal bedrag, LocalDateTime goedgekeurd) {
        setOmschrijving(omschrijving);
        setBedrag(bedrag);
        this.id = id;
        this.werknemerId = werknemerId;
        this.goedgekeurd = goedgekeurd;
    }

    private void setOmschrijving(String omschrijving){
        if(omschrijving.isEmpty()){
            throw new IllegalArgumentException("Omschrijving kan niet leeg zijn");
        }this.omschrijving = omschrijving;
    }

    private void setBedrag(BigDecimal bedrag){
        if(bedrag.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Bedrag kan niet negatief zijn");
        }this.bedrag = bedrag;
    }



    public void updateGoedgekeurd(){
       goedgekeurd = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getWerknemerId() {
        return werknemerId;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public LocalDateTime getGoedgekeurd() {
        return goedgekeurd;
    }
}

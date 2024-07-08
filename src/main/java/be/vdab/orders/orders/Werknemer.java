package be.vdab.orders.orders;

public class Werknemer {
    private final int id;
    private final Integer chefId;
    private final String voornaam;
    private final String familienaam;

    public Werknemer(int id, Integer chefId, String voornaam, String familienaam) {
        this.id = id;
        this.chefId = chefId;
        this.voornaam = voornaam;
        this.familienaam = familienaam;
    }

    public int getId() {
        return id;
    }

    public Integer getChefId() {
        return chefId;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }
}

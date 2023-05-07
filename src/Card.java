public class Card {
    // Fields
    private String name;
    private String description;
    private Role[] onCardRoles;
    private int budget;
    private String img;
    private int sceneNumber;

    public Card Card(String description, Role[] roles) {
        return null;
    }

    public void setDescription(String desc) {
        description = desc;
        return;
    }

    public String getDescription() {
        return this.description;
    }

    public void setName(String newName) {
        name = newName;
        return;
    }
    public void setBudget(int newbudget) {
        budget = newbudget;
        return;
    }
    public void setSceneNumber(int num) {
        sceneNumber = num;
        return;
    }
    public void setImg(String newImg) {
        img = newImg;
        return;
    }


    public void setOnCardRoles(Card[] roles) {
        onCardRoles = roles;
        return;
    }

    public Role[] getOnCardRoles() {
        return this.onCardRoles;
    }
}
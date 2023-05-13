public class Card {
    // Fields
    private String name;
    private String description;
    private Role[] onCardRoles;
    private int budget;
    private String img;
    private int sceneNumber;

    public Card(String name) {
        name = name;
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
    public String getName() {
        return name;
    }
    public void setBudget(int newbudget) {
        budget = newbudget;
        return;
    }
    public int getBudget() {
        return budget;
    }
    public void setSceneNumber(int num) {
        sceneNumber = num;
        return;
    }
    public int getSceneNumber() {
        return sceneNumber;
    }
    public void setImg(String newImg) {
        img = newImg;
        return;
    }
    public String getImg() {
        return img;
    }


    public void setOnCardRoles(Role[] roles) {
        onCardRoles = roles;
        return;
    }

    public Role[] getOnCardRoles() {
        return this.onCardRoles;
    }
    
    public void addRole(Role role, int i) {
        this.onCardRoles[i] = role;
    }
}
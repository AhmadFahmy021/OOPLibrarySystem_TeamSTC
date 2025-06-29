package data;

public class Member {
    private int id;
    private String id_member;
    private String name;
    private String email;

    public Member(int id, String id_member, String name, String email){
        this.id = id;
        this.id_member = id_member;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getIdMember() { return id_member; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}

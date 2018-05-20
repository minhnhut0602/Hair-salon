import java.util.List;
import org.sql2o.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Client {
  private String description;
  private boolean completed;
  private LocalDateTime createdAt;
  private int id;
  private int stylistId;

  public Client(String description, int stylistId) {
    this.description = description;
    completed = false;
    createdAt = LocalDateTime.now();
    this.stylistId = stylistId;
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return completed;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public int getId() {
    return id;
  }
  public int getStylistId() {
    return stylistId;
  }

  public static Client find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM client where id=:id";
      Client client = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Client.class);
      return client;
    }
  }

  // database initiation
  public static List<Client> all() {
    String sql = "SELECT id, description, stylistId FROM client";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  @Override
  public boolean equals(Object otherClient){
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getDescription().equals(newClient.getDescription()) &&
             this.getId() == newClient.getId() &&
             this.getStylistId() == newClient.getStylistId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO client(description, stylistId) VALUES (:description, :categoryId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .addParameter("categoryId", this.stylistId)
        .executeUpdate()
        .getKey();
    }
  }


}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java_rush.DatabaseConnection;
import models.Apprenant;
import models.Promotion;

public class PromotionDAO {

    public void ajouterPromotion(Promotion promotion) throws SQLException {
        String sql = "INSERT INTO promotions (nom, annee) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, promotion.getNom());
            stmt.setInt(2, promotion.getAnnee());
            stmt.executeUpdate();
        }
    }

    public void supprimerPromotion(int id) throws SQLException {
        String sql = "DELETE FROM promotions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Promotion> getAllPromotions() throws SQLException {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM promotions";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Promotion promotion = new Promotion(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getInt("annee"),
                    getApprenantsByPromotionId(rs.getInt("id"))
                );
                promotions.add(promotion);
            }
        }
        return promotions;
    }

    public Promotion getPromotionById(int id) throws SQLException {
        String sql = "SELECT * FROM promotions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Promotion(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getInt("annee"),
                    getApprenantsByPromotionId(rs.getInt("id"))
                );
            }
        }
        return null;
    }

    private List<Apprenant> getApprenantsByPromotionId(int promotionId) throws SQLException {
        List<Apprenant> apprenants = new ArrayList<>();
        String sql = "SELECT * FROM apprenants WHERE promotion_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, promotionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                apprenants.add(new Apprenant(
                    rs.getInt("id"),
                    new Promotion(promotionId, "", 0, null),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getInt("absences"),
                    List.of(rs.getString("roles").split(","))
                ));
            }
        }
        return apprenants;
    }
}

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

public class ApprenantDAO {
    public void ajouterApprenant(Apprenant apprenant) throws SQLException {
        if (apprenantExiste(apprenant)) {
            throw new SQLException("Un apprenant avec les mêmes informations existe déjà.");
        }

        String sql = "INSERT INTO apprenants (promotion_id, nom, prenom, email, telephone, absences, roles) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, apprenant.getPromotion().getId());
            stmt.setString(2, apprenant.getNom());
            stmt.setString(3, apprenant.getPrenom());
            stmt.setString(4, apprenant.getEmail());
            stmt.setString(5, apprenant.getTelephone());
            stmt.setInt(6, apprenant.getAbsences());
            stmt.setString(7, String.join(",", apprenant.getRoles()));
            stmt.executeUpdate();
        }
    }

    public void supprimerApprenant(int id) throws SQLException {
        if (estDelegue(id)) {
            throw new SQLException("Impossible de supprimer le délégué.");
        }

        String sql = "DELETE FROM apprenants WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Apprenant> getAllApprenants() throws SQLException {
        List<Apprenant> apprenants = new ArrayList<>();
        String sql = "SELECT * FROM apprenants";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                apprenants.add(new Apprenant(
                    rs.getInt("id"),
                    new Promotion(rs.getInt("promotion_id"), "DI23", 2023, null),
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

    public void modifierAbsences(int id, int nouvellesAbsences) throws SQLException {
        String sql = "UPDATE apprenants SET absences = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nouvellesAbsences);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public List<Apprenant> rechercherParPromotion(String nomPromotion) throws SQLException {
        List<Apprenant> apprenants = new ArrayList<>();
        String sql = "SELECT a.* FROM apprenants a JOIN promotions p ON a.promotion_id = p.id WHERE p.nom = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomPromotion);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                apprenants.add(new Apprenant(
                    rs.getInt("id"),
                    new Promotion(rs.getInt("promotion_id"), "DI23", 2023, null),
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

    public double calculerTauxAbsenteisme(int promotionId) throws SQLException {
        String sql = "SELECT AVG(absences) AS moyenneAbsences FROM apprenants WHERE promotion_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, promotionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double moyenneAbsences = rs.getDouble("moyenneAbsences");
                return moyenneAbsences;
            }
        }
        return 0;
    }

    private boolean apprenantExiste(Apprenant apprenant) throws SQLException {
        String sql = "SELECT COUNT(*) FROM apprenants WHERE (nom = ? AND prenom = ?) OR email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, apprenant.getNom());
            stmt.setString(2, apprenant.getPrenom());
            stmt.setString(3, apprenant.getEmail());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private boolean estDelegue(int id) throws SQLException {
        String sql = "SELECT * FROM apprenants WHERE id = ? AND roles LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, "%delegue%");
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}

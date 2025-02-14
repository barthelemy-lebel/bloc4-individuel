package controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.PromotionDAO;
import models.Apprenant;
import models.Promotion;

public class PromotionController {
    private PromotionDAO promotionDAO = new PromotionDAO();

    public void ajouterPromotion(Scanner scanner) throws SQLException {
        System.out.println("Nom de la promotion:");
        String nom = scanner.nextLine();
        System.out.println("Année de la promotion:");
        int annee = Integer.parseInt(scanner.nextLine());

        // Ajouter la promotion
        Promotion promotion = new Promotion(0, nom, annee, null);
        promotionDAO.ajouterPromotion(promotion);
    }

    public void supprimerPromotion(Scanner scanner) throws SQLException {
        System.out.println("ID de la promotion à supprimer:");
        int id = Integer.parseInt(scanner.nextLine());
        promotionDAO.supprimerPromotion(id);
    }

    public void afficherPromotions() throws SQLException {
        List<Promotion> promotions = promotionDAO.getAllPromotions();
        for (Promotion promotion : promotions) {
            System.out.println(promotion);
        }
    }

    public void afficherApprenantsParPromotion(Scanner scanner) throws SQLException {
        System.out.println("ID de la promotion:");
        int promotionId = Integer.parseInt(scanner.nextLine());

        Promotion promotion = promotionDAO.getPromotionById(promotionId);
        if (promotion != null) {
            System.out.println("Apprenants de la promotion " + promotion.getNom() + " (" + promotion.getAnnee() + "):");
            List<Apprenant> apprenants = promotion.getApprenants();
            for (Apprenant apprenant : apprenants) {
                System.out.println(apprenant);
            }
        } else {
            System.out.println("Promotion non trouvée.");
        }
    }

    public List<Promotion> getAllPromotions() throws SQLException {
        return promotionDAO.getAllPromotions();
    }
}

package controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.ApprenantDAO;
import models.Apprenant;
import models.Promotion;

public class ApprenantController {
    private ApprenantDAO apprenantDAO = new ApprenantDAO();
    private PromotionController promotionController = new PromotionController();

    public void ajouterApprenant(Scanner scanner) throws SQLException {
        System.out.println("Nom:");
        String nom = scanner.nextLine();
        System.out.println("Prénom:");
        String prenom = scanner.nextLine();
        System.out.println("Email:");
        String email = scanner.nextLine();
        System.out.println("Numéro de téléphone:");
        String telephone = scanner.nextLine();
        System.out.println("Nombre d'absences:");
        int absences = Integer.parseInt(scanner.nextLine());
        System.out.println("Rôles (séparés par des virgules):");
        List<String> roles = List.of(scanner.nextLine().split(","));

        // Afficher les promotions disponibles
        List<Promotion> promotions = promotionController.getAllPromotions();
        System.out.println("Promotions disponibles:");
        for (int i = 0; i < promotions.size(); i++) {
            System.out.println((i + 1) + ". " + promotions.get(i).getNom() + " (" + promotions.get(i).getAnnee() + ")");
        }

        System.out.println("Sélectionnez une promotion (entrez le numéro):");
        int promotionIndex = Integer.parseInt(scanner.nextLine()) - 1;
        Promotion promotion = promotions.get(promotionIndex);

        // Ajouter l'apprenant
        Apprenant apprenant = new Apprenant(0, promotion, nom, prenom, email, telephone, absences, roles);
        apprenantDAO.ajouterApprenant(apprenant);
    }

    public void ajouterPromotion(Scanner scanner) throws SQLException {
        System.out.println("Nom de la promotion:");
        String nom = scanner.nextLine();
        System.out.println("Année de la promotion:");
        int annee = Integer.parseInt(scanner.nextLine());

        Promotion promotion = new Promotion(0, nom, annee, null);
        promotionDAO.ajouterPromotion(promotion);
    }

    public void supprimerApprenant(Scanner scanner) throws SQLException {
        System.out.println("ID de l'apprenant à supprimer:");
        int id = Integer.parseInt(scanner.nextLine());
        apprenantDAO.supprimerApprenant(id);
    }

    public void afficherApprenants() throws SQLException {
        List<Apprenant> apprenants = apprenantDAO.getAllApprenants();
        for (Apprenant apprenant : apprenants) {
            System.out.println(apprenant);
        }
    }

    public void modifierAbsences(Scanner scanner) throws SQLException {
        System.out.println("ID de l'apprenant à modifier:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Nouveau nombre d'absences:");
        int nouvellesAbsences = Integer.parseInt(scanner.nextLine());
        apprenantDAO.modifierAbsences(id, nouvellesAbsences);
    }

    public void rechercherParPromotion(Scanner scanner) throws SQLException {
        System.out.println("Nom de la promotion:");
        String nomPromotion = scanner.nextLine();
        List<Apprenant> apprenants = apprenantDAO.rechercherParPromotion(nomPromotion);
        for (Apprenant apprenant : apprenants) {
            System.out.println(apprenant);
        }
    }

    public void calculerTauxAbsenteisme(Scanner scanner) throws SQLException {
        System.out.println("ID de la promotion:");
        int promotionId = Integer.parseInt(scanner.nextLine());
        double tauxAbsenteisme = apprenantDAO.calculerTauxAbsenteisme(promotionId);
        System.out.println("Taux d'absentéisme de la promotion: " + tauxAbsenteisme);
    }
}
